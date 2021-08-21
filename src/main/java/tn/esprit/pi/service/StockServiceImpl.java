package tn.esprit.pi.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import tn.esprit.pi.configuration.EmailConfig;
import tn.esprit.pi.entities.BonCommande;
import tn.esprit.pi.entities.Entry;
import tn.esprit.pi.entities.MailHistory;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Orders;
import tn.esprit.pi.entities.Product;
import tn.esprit.pi.entities.Provider;
import tn.esprit.pi.repository.BonCommandeRepository;
import tn.esprit.pi.repository.EntryRepository;
import tn.esprit.pi.repository.MailHistoryRepository;
import tn.esprit.pi.repository.ProductRepository;
import tn.esprit.pi.repository.ProviderRepository;
@Service
public class StockServiceImpl implements IStockService {
	
	private EmailConfig emailCfg;

    public StockServiceImpl(EmailConfig emailCfg) {
        this.emailCfg = emailCfg;
    }
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	MailHistoryRepository mailHistoryRepo;
	@Autowired
	EntryRepository entryRepository;
	@Autowired
	ProviderRepository providerRepository;
	@Autowired
	BonCommandeRepository bonCommandeRepository;
	
	
	
	@Override
	public List<Product> getListMissigProduct() {

		return productRepository.listMissingProduct();
	}

	@Override
	public String addEntry(Entry entry) {
		Provider provider = providerRepository.findById(entry.getProvider().getProviderId()).get();
		Product product = productRepository.findById(entry.getProduct().getProductId()).get();
		entry.setProvider(provider);
		entry.setProduct(product);
		int quantity = entry.getQuantity();
		product.setQuantity(product.getQuantity()+quantity);
		
		float m=entry.getQuantity()*entry.getArticalPrice()+provider.getDeleviryFees();
		
		if(entryRepository.NbEntryProvider(provider.getProviderId())>=3||m>=provider.getSeuilMontant())
		{
			
			m = m - (m*provider.getReductionPercentage())/100;
		}
		
		entry.setMontant(m);
		float priceAchat=0;
		priceAchat= (m+product.getQuantity()*product.getPriceA())/(entry.getQuantity()+product.getQuantity());
		product.setPriceA(priceAchat);
		
		productRepository.save(product);
        entryRepository.save(entry);
        System.out.println(product.getName());
        bonCommandeRepository.deleteBon(product.getName(),provider.getName(),entry.getQuantity());
        if(product.getPriceA()>product.getPriceV())
        	return "stock updated with success please update the vente price of the product";
		return "stock updated with success";
	}

	@Override
	public String deleteEntry(long entryId) {
		Entry entry = entryRepository.findById(entryId).get();
		if (entry == null) {
			return "introuvable";
		}
		entryRepository.delete(entry);
		return "delete succes";
	}

	@Override
	public List<Entry> getAllEntry() {

		return (List<Entry>) entryRepository.findAll();
	}

	@Override
	public Entry getEntryById(long entryId) {
		return entryRepository.findById(entryId).get();
	}

	@Override
	public List<Entry> getEntryByProduct(long productId) {
		return entryRepository.getEntryByProduct(productId);
	}

	@Override
	public List<Entry> getEntryByProvider(long providerId) {
		return entryRepository.getEntryByProvider(providerId);
	}

	@Override
	public int getNomberProvider(long providerId) {
	return entryRepository.NbEntryProvider(providerId);
	}
	
	@Override
	public List<Provider> getProviderByProduct(long productId)
	{
		List<Provider> providers = new ArrayList<Provider>();
		providers= entryRepository.getProviderByProduct(productId);
		List<Provider> providers2 = new ArrayList<Provider>();
		
		for(Provider p : providers)
		{
			if(p.getDisponibility()==true)
			{
				providers2.add(p);
			}
		}
		return providers2;
	}

	@Override
	public int getLastSevenDaysQuantity(long productId)
	{   Product p = productRepository.findById(productId).get();
		int nb=0;
		List<OrderLine> ordersLine = new ArrayList<OrderLine>();
		List<Orders> orders= new ArrayList<Orders>();
		Date d = new Date(System.currentTimeMillis());
		Date sevenDaysAgo = new Date(d.getTime() - (604800000));
		System.out.println(d);
		System.out.println(sevenDaysAgo);	
		orders =  entryRepository.getOrdersLastThreeDays(sevenDaysAgo);
		ordersLine = entryRepository.getAllOrderLine();
	 for (Orders o : orders)
	 {
		 for(OrderLine ord :o.getOrderLine())
		 {
			 if(ord.getProduct()==p)
			 {
				 nb=nb+ord.getQuantity();
			 }
		 }
	 }
		
		return nb;
	}
	@Override
	public void NotifyProvider(long productId) {
		List<Provider> provs = this.getProviderByProduct(productId)	;
		Product product = productRepository.findById(productId).get();
		for(Provider p : provs)
		{
			if(p.getDisponibility()==false)
			provs.remove(p);
		}
		for (Provider p : provs )
			System.out.println(p.getName());
		Collections.sort(provs);
		for (Provider p : provs )
			System.out.println(p.getName());
        int q= this.getLastSevenDaysQuantity(productId);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(this.emailCfg.getHost());
		mailSender.setPort(this.emailCfg.getPort());
		mailSender.setUsername(this.emailCfg.getUsername());
		mailSender.setPassword(this.emailCfg.getPassword());
		// Create an email instance
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom("ShoppyTounsi@Gmail.com");
		mailMessage.setTo(provs.get(0).getEmail());
		mailMessage.setSubject("New order for "+product.getName());
		mailMessage.setText("Hi "+provs.get(0).getName()+" we need "+q+ " from "+product.getName());
		// Send mail
		mailSender.send(mailMessage);	
		Date d = new Date(System.currentTimeMillis());
		MailHistory m = new MailHistory();
		m.setDistination(provs.get(0).getEmail());
		m.setBody("Hi "+provs.get(0).getName()+" we need "+q+ " from "+product.getName());
		m.setSendDate(d);
		m.setType("provider");
		mailHistoryRepo.save(m);
		BonCommande b = new BonCommande();
		b.setProductName(product.getName());
		b.setProviderName(provs.get(0).getName());
		b.setQuantity(q);
		bonCommandeRepository.save(b);
	}
	
	@Override
	public int getSumOutlay() {
		return entryRepository.getSumOutlay();
	}
	
	@Override
	public List<BonCommande> getBonCommandListOutOfDate() {
		List<BonCommande> bn= (List<BonCommande>) bonCommandeRepository.findAll();	
		Date d = new Date(System.currentTimeMillis());
		Date expiration = new Date(d.getTime() - (2 * 86400000));
		List<BonCommande> bn2 = new ArrayList<BonCommande>();
		for(BonCommande b : bn)
		{
			if(b.getBonCommandeDate().compareTo(expiration)<0)
			{
				bn2.add(b);
			}
		}
		
	return bn2;	
	}
	
	@Override
	public List<BonCommande> getBonCommandInProccess() {
		List<BonCommande> bn= (List<BonCommande>) bonCommandeRepository.findAll();	
		Date d = new Date(System.currentTimeMillis());
		Date expiration = new Date(d.getTime() - (2 * 86400000));
		List<BonCommande> bn2 = new ArrayList<BonCommande>();
		for(BonCommande b : bn)
		{
			if(b.getBonCommandeDate().compareTo(expiration)>0)
			{
				bn2.add(b);
			}
		}
		
	return bn2;	
	}
	
}