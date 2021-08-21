package tn.esprit.pi.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.pi.configuration.ServiceT;
import tn.esprit.pi.configuration.SmsRequest;
import tn.esprit.pi.entities.Category;
import tn.esprit.pi.entities.HistoriqueShelf;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Orders;
import tn.esprit.pi.entities.Product;
import tn.esprit.pi.entities.Shelf;
import tn.esprit.pi.entities.ShelfRating;
import tn.esprit.pi.entities.ShelfType;
import tn.esprit.pi.entities.User;
import tn.esprit.pi.payload.CategoryRevenulastThreeDays;
import tn.esprit.pi.payload.ShelfOcc;
import tn.esprit.pi.payload.ShelfRevenu;
import tn.esprit.pi.repository.CategoryRepository;
import tn.esprit.pi.repository.HistoriqueShelfRepository;
import tn.esprit.pi.repository.ProductRepository;
import tn.esprit.pi.repository.ShelfRatingRepository;
import tn.esprit.pi.repository.UserRepository;
import tn.esprit.pi.repository.ShelfRepository;

@Service
public class ShelfServiceImpl implements IShelfService {

	@Autowired
	HistoriqueShelfRepository historiqueShelfRepository;
	@Autowired
	ShelfRepository shelfRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ShelfRatingRepository shelfRatingRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	ServiceT s;


	@Override
	public String addShelf(Shelf shelf) {

		if (shelf.getType().equals(ShelfType.PROMO)) {
			if(shelf.getReductionPercantage()==0)
			{
				
			}
			Date d = new Date(System.currentTimeMillis());
			Date expiration = new Date(d.getTime() + (2 * 86400000));
			shelf.setDateExpiration(expiration);
			List<CategoryRevenulastThreeDays> cat= new ArrayList<CategoryRevenulastThreeDays>();
			cat=this.getCategoryLastThreeDays();
			CategoryRevenulastThreeDays c = new CategoryRevenulastThreeDays();
			c=cat.get(0);
			System.out.println(c.getCategoryName());
			Category category = categoryRepository.findByName(c.getCategoryName());
			System.out.println(category.getName());
			shelfRepository.save(shelf);
			this.affecterCategoryShelf(category.getCategoryId(), shelf.getShelfId());
			List<User> users = new ArrayList<User>();
			users = userRepository.findAll();
			/*
			 * for(User u : users) { SmsRequest smsRequest = new
			 * SmsRequest(u.getNumTel(),
			 * "nouvelle promo disponible jusqu'a minuit ");
			 * s.sendSms(smsRequest); }
			 */
		}
		if (shelf.getType().equals(ShelfType.RAMADHAN)) {
			Date d = new Date(System.currentTimeMillis());

			Date expiration = new Date(d.getTime() + (24 * 86400000));
			Date expiration2 = new Date(expiration.getTime() + (6 * 86400000));
			shelf.setDateExpiration(expiration2);
			shelfRepository.save(shelf);
			Category category = categoryRepository.findByName("Ramadansupplies");
			System.out.println(category.getName());
			this.affecterCategoryShelf(category.getCategoryId(), shelf.getShelfId());
		}
		shelfRepository.save(shelf);
		return "added";
	}

	@Override
	public String DeleteShelfById(long shelfId) {
		System.out.println("hi1");
		Shelf shelf = shelfRepository.findById(shelfId).get();
		Category category = categoryRepository.findByName("Ramadansupplies");

		for (Category cat : shelf.getCategory()) {
if (cat.equals(category))
{System.out.println("hi");
	cat.setShelf(null);
	break;
}
			for (Product p : cat.getProduct()) {
				p.setInPromo(false);
				p.setPriceV(p.getPriceV() + (p.getPriceV() * shelf.getReductionPercantage()) / 100);
                productRepository.save(p);
			}
			
			Shelf s= shelfRepository.findById(cat.getLastShelf()).get();
			cat.setShelf(s);

		}
		
		HistoriqueShelf h = new HistoriqueShelf();
		List<ShelfRevenu> shelfRevs = new ArrayList<ShelfRevenu>();

		shelfRevs = this.getShelfsRevenu();

		for (ShelfRevenu srv : shelfRevs) {
			if (shelf.getShelfname().equals(srv.getShelf())) {
				h.setName(shelf.getShelfname());
				h.setMontant(srv.getRevenu());
			}
		}

		historiqueShelfRepository.save(h);
        shelfRatingRepository.deleteAllShelfRating(shelf.getShelfId());
        
		shelfRepository.delete(shelf);
		return "supprimer avec succ";
	}

	@Override
	public List<Shelf> getAllShelfs() {

		return (List<Shelf>) shelfRepository.findAll();

	}

	@Override
	public List<Shelf> getShelfs(Authentication auth) {
		List<Shelf> shelfs = new ArrayList<Shelf>();
		List<ShelfOcc> shelfocc = new ArrayList<ShelfOcc>();
		shelfs = (List<Shelf>) shelfRepository.findAll();
		Date d = new Date(System.currentTimeMillis());
		List<Product> products = new ArrayList<Product>();
		List<Shelf> shelfs2 = new ArrayList<Shelf>();
		System.out.println("anis");
		for (Shelf s : shelfs) {
			if (s.getType().equals(ShelfType.PROMO) && s.getDateExpiration().compareTo(d) > 0) {
				Shelf shelf=s.calculateNewPrice();
				shelfs2.add(shelf);
			}
		}
		
		for (Shelf s : shelfs) {
		
			if (s.getType().equals(ShelfType.RAMADHAN) && s.getDateExpiration().compareTo(d) > 0) {
				shelfs2.add(s);
				
			}
		}
		System.out.println("anis");
		if (auth == null) {
			System.out.println("hi");
			for (Shelf s : this.listShelfForNotAuth()) {
				shelfs2.add(s);
			}
		} else {

			User u = userRepository.findByName(auth.getName()).get();
			products = this.getOders(auth);

			for (Shelf s : shelfs) {
				ShelfOcc sho = new ShelfOcc();
				int occ = 0;
				for (Product p : products) {
					if (p.getCategory().getShelf().getShelfId() == s.getShelfId()) {
						occ++;
					}
				}
				System.out.println(occ);
				sho.setOcc(occ);
				sho.setShelf(s);
				shelfocc.add(sho);
			}

			Collections.sort(shelfocc);
			List<ShelfOcc> shelfocc2 = new ArrayList<ShelfOcc>();
			for (ShelfOcc s : shelfocc) {
				if (!s.getShelf().getType().equals(ShelfType.RAMADHAN)
						&& !s.getShelf().getType().equals(ShelfType.PROMO) && s.getOcc() > 0)
					shelfs2.add(s.getShelf());
				else if (!s.getShelf().getType().equals(ShelfType.RAMADHAN)
						&& !s.getShelf().getType().equals(ShelfType.PROMO))
					shelfocc2.add(s);
			}

			if (u.getAge() < 15) {

				for (ShelfOcc s : shelfocc2) {

					if (s.getShelf().getType().equals(ShelfType.ENFANT)) {

						shelfs2.add(s.getShelf());

					}
				}

				for (ShelfOcc s : shelfocc2) {
					if (u.getSex().equals("FEMME")) {
						if (s.getShelf().getType().equals(ShelfType.FEMME)) {
							shelfs2.add(s.getShelf());

						}
					} else if (s.getShelf().getType().equals(ShelfType.HOMME)) {
						shelfs2.add(s.getShelf());

					}
				}

				for (ShelfOcc s : shelfocc2) {

					if (!shelfs2.contains(s.getShelf())) {
						// if not exist in shelfs2

						shelfs2.add(s.getShelf());
					}

				}

			} else {
				for (ShelfOcc s : shelfocc2) {
					if (u.getSex().equals("FEMME")) {
						if (s.getShelf().getType().equals(ShelfType.FEMME)) {
							shelfs2.add(s.getShelf());

						}
					} else if (s.getShelf().getType().equals(ShelfType.HOMME)) {
						shelfs2.add(s.getShelf());

					}
				}

				for (ShelfOcc s : shelfocc2) {

					if (!shelfs2.contains(s.getShelf())) {

						shelfs2.add(s.getShelf());
					}

				}

			}
		}
		return shelfs2;
	}

	public List<Shelf> listShelfForNotAuth() {
		List<Shelf> shelfs = new ArrayList<Shelf>();
		List<Shelf> shelfsord = new ArrayList<Shelf>();
		shelfsord = shelfRepository.getShelfdOrderByRating();
		for (Shelf s1 : shelfsord) {
			
			if (!s1.getType().equals(ShelfType.RAMADHAN) && !s1.getType().equals(ShelfType.PROMO)) {
				shelfs.add(s1);
			}
		}

		return shelfs;

	}

	@Override
	public List<Product> getOders(Authentication auth) {
		List<OrderLine> ordersLine = new ArrayList<OrderLine>();
		User u = userRepository.findByName(auth.getName()).get();
		List<Product> p = new ArrayList<Product>();
		List<Orders> orders = new ArrayList<Orders>();
		orders = shelfRepository.getAllOrder();
		if (u.getShoppingcart() != null) {
			ordersLine = u.getShoppingcart().getOrderLines();

			for (OrderLine o : ordersLine) {
				for (Orders ord : orders) {
					if (ord.getOrderLine().contains(o)) {
						p.add(o.getProduct());
					}
				}
			}
			return p;
		}

		return p;
	}

	@Override
	public List<Product> getOrdersByShelf(long shelfId) {
		List<OrderLine> ordersLine = new ArrayList<OrderLine>();
		Shelf shelf = shelfRepository.findById(shelfId).get();
		List<Product> p = new ArrayList<Product>();
		List<Orders> orders = new ArrayList<Orders>();
		orders = shelfRepository.getAllOrder();
		
		ordersLine = shelfRepository.getOrderByShelf(shelfId);
		for (OrderLine o : ordersLine) {
			for (Orders ord : orders) {
				if(ord.getOrderDate().compareTo(shelf.getDateCreation())>0)
                {
				if (ord.getOrderLine().contains(o)) {
					p.add(o.getProduct());
				}
                 }
			}

		}

		return p;
	}

	@Override
	public List<ShelfRevenu> getShelfsRevenu() {
		List<ShelfRevenu> rev = new ArrayList<ShelfRevenu>();
		List<Shelf> shelfs = new ArrayList<Shelf>();
		shelfs = (List<Shelf>) shelfRepository.findAll();

		for (Shelf s : shelfs) {
			float revenu = 0;
			ShelfRevenu sr = new ShelfRevenu();
			List<Product> products = new ArrayList<Product>();
			products = this.getOrdersByShelf(s.getShelfId());
			for (Product p : products) {
				for (OrderLine o : shelfRepository.getAllOrderLine()) {
					if (o.getProduct().equals(p)) {
						revenu = revenu + o.getQuantity() * o.getPrice();
						
					}
				}

			}

			sr.setShelf(s.getShelfname());
			sr.setRevenu(revenu);
			rev.add(sr);
		}
Collections.sort(rev);
		return rev;
	}

	// lite des vendu last 3 days
	@Override
	public List<Orders> getOrdersLastThreeDays() {
		// subtract 3 days from today

		Date d = new Date(System.currentTimeMillis());

		Date thirtyDaysAgo = new Date(d.getTime() - (3 * 86400000));

		return shelfRepository.getOrdersLastThreeDays(thirtyDaysAgo);

	}
	// liste cat last 3 days

	@Override
	public List<Product> getOrdersByCategory(long categoryId) {
		List<OrderLine> ordersLine = new ArrayList<OrderLine>();
		List<Product> p = new ArrayList<Product>();
		List<Orders> orders = new ArrayList<Orders>();
		orders = this.getOrdersLastThreeDays();

		for (Orders o : orders) {
			for (OrderLine ord : o.getOrderLine()) {
				ordersLine.add(ord);
			}
		}

		for (OrderLine o : ordersLine) {
			for (Orders ord : orders) {
				if (ord.getOrderLine().contains(o)) {
					if(o.getProduct().getCategory().equals(categoryRepository.findById(categoryId).get()))
					p.add(o.getProduct());
				}
			}

		}

		return p;
	}

	@Override
	public List<CategoryRevenulastThreeDays> getCategoryLastThreeDays() {

		List<Orders> orders = new ArrayList<Orders>();
		List<Category> category = new ArrayList<Category>();
		List<CategoryRevenulastThreeDays> catRev = new ArrayList<CategoryRevenulastThreeDays>();
		category = shelfRepository.getAllCategory();
		List<OrderLine> orderLines = new ArrayList<OrderLine>();
		orders = this.getOrdersLastThreeDays();

		for (Orders o : orders) {
			for (OrderLine ord : o.getOrderLine()) {
				orderLines.add(ord);
			}
		}
	
		for (Category c : category) {
			float revenu = 0;
			
			CategoryRevenulastThreeDays cat = new CategoryRevenulastThreeDays();
			List<Product> products = new ArrayList<Product>();
			products=this.getOrdersByCategory(c.getCategoryId());
			for (Product p : products) {
				System.out.println(p.getName());
				for (OrderLine o : orderLines) {
					if (o.getProduct().equals(p)) {
						revenu =revenu + o.getQuantity() * o.getPrice();
						cat.setRevenu(revenu);
					}
				}

			}
			cat.setCategoryName(c.getName());
			
			catRev.add(cat);
		}

		Collections.sort(catRev);
		return catRev;
	}

	@Override
	public int getNombreShelf() {
		return shelfRepository.countshelf();

	}

	@Override
	public List<Shelf> getShelfByType(ShelfType type) {
		return shelfRepository.findAllByType(type);
	}

	@Override
	public List<String> getAllCategoriesNameByShelfId(long shelfId) {
		Shelf shelf = shelfRepository.findById(shelfId).get();
		List<String> catNames = new ArrayList<>();
		for (Category cat : shelf.getCategory()) {
			catNames.add(cat.getName());
		}

		return catNames;
	}

	@Override
	public Shelf updateShelf(Shelf shelf) {
		shelfRepository.save(shelf);
		return shelf;
	}

	@Override
	public List<Category> getAllCategoryByShelfJPQL(long Shelfid) {
		return shelfRepository.getAllCategoryByShelfJPQL(Shelfid);
	}

	@Override
	public List<String> getAllProductByShelfJPQL(long Shelfid) {

		return shelfRepository.getAllProductByShelfJPQL(Shelfid);
	}

	@Override
	public Shelf getShelfById(long Shelfid) {

		Shelf s = shelfRepository.findById(Shelfid).get();
		s.calculateNewPrice();
		return s;
	}

	@Scheduled(cron = "*/20 * * * * *")
	@Override
	@Transactional
	public void deleteShelfByDate() {
		List<Shelf> shelfs = new ArrayList<Shelf>();
		shelfs = (List<Shelf>) shelfRepository.findAll();
		Date d = new Date(System.currentTimeMillis());
		Date expiration = new Date(d.getTime() + (1 * 86400000));
		for (Shelf s : shelfs) {

			if (s.getDateExpiration() != null) {
				System.out.println(expiration);
				if (s.getDateExpiration().compareTo(expiration) < 0) {
			
					this.DeleteShelfById(s.getShelfId());

				}
			}
		}
		System.out.println(expiration);
		System.out.println("anis");
	}

	// update de pourcentage de reduction

	@Override
	public void UpdateReductin(int red, long shelfId) {
		
		shelfRepository.updateReductionPercentage(red, shelfId);
	}

	// affecter et deaffcter

	@Override
	public String affecterCategoryShelf(long categoryId, long shelfId) {
		Shelf shelf = shelfRepository.findById(shelfId).get();
		Category category = categoryRepository.findById(categoryId).get();
		category.setShelf(shelf);
       System.out.println("Shelf");
		if (shelf.getType().equals(ShelfType.PROMO)) {
			for (Product p : category.getProduct()) {
				p.setInPromo(true);
				float prix;
				prix = p.getPriceV() - (p.getPriceV() * shelf.getReductionPercantage()) / 100;
				if (prix < p.getPriceA()) {
					return "il y' a perte dans vente de produit " + p.getName();
				}
				p.setPriceV(prix);
				productRepository.save(p);
			}
		
		} else if (!shelf.getType().equals(ShelfType.RAMADHAN)) {
			category.setLastShelf(shelf.getShelfId());
		}
		categoryRepository.save(category);
		return "affecter";
	}

	@Override
	public String daffecterCategoryShelf(long categoryId, long shelfId) {

		Shelf shelf = shelfRepository.findById(shelfId).get();
		Category category = categoryRepository.findById(categoryId).get();

		if (shelf.getType().equals(ShelfType.PROMO)) {
			category.setShelf(shelfRepository.findById(category.getLastShelf()).get());
			for(Product p : category.getProduct())
			{
			p.setInPromo(false);
			float price=0;
			int per =shelf.getReductionPercantage();
			float var= 100/per;
			price=p.getPriceV()*(var/(var-1));
			
			p.setPriceV(price);
            productRepository.save(p);
			}
		}
		if (shelf.getType().equals(ShelfType.RAMADHAN)) {
			category.setShelf(shelfRepository.findById(category.getLastShelf()).get());
		}
		categoryRepository.save(category);
		return "daffecter";
	}

	// Rating

	@Override
	public ShelfRating saveOrUpdateRating(Authentication auth, long Shelfid, int rating) {
		Shelf shelf = shelfRepository.findById(Shelfid).get();
		User user = userRepository.findByName(auth.getName()).get();
		List<ShelfRating> reviews = shelfRatingRepository.findByShelf(shelf);
		ShelfRating review = new ShelfRating();

		if (reviews == null) {
			review.setRating(rating);
			review.setShelf(shelf);
			review.setUser(user);
			shelfRatingRepository.save(review);
		} else {
			for (ShelfRating r : reviews) {
				if (r.getUser().getUserId() == user.getUserId()) {
					r.setRating(rating);
					shelfRatingRepository.save(r);
					updateRatingById(shelf.getShelfId());
					return r;
				}
			}
			review.setRating(rating);
			review.setShelf(shelf);
			review.setUser(user);
			shelfRatingRepository.save(review);
			updateRatingById(shelf.getShelfId());
		}

		return review;
	}

	public void updateRatingById(long shelfId) {
		Shelf shelf = shelfRepository.findById(shelfId).get();
		shelf.setRating(shelfRatingRepository.sumshelfRate(shelfId) / shelfRatingRepository.countshelfRate(shelfId));
		shelfRepository.save(shelf);
	}

	@Override
	public void deleteRating(long userId  ,long shelfId) {
		Shelf shelf = shelfRepository.findById(shelfId).get();
		User u = userRepository.findById(userId).get();
		ShelfRating r = shelfRatingRepository.getRatingByUserAndShelf(u.getUserId(),shelfId);
		r.setShelf(null);
		r.setUser(null);
		shelfRatingRepository.deleteById(r.getRatingId());
		updateRatingById(shelf.getShelfId());
	}

	@Override
	public ShelfRating getRatingbyId(long rating_id) {

		return shelfRatingRepository.findById(rating_id).get();

	}

	@Override
	public List<ShelfRating> getAllRating() {

		return (List<ShelfRating>) shelfRatingRepository.findAll();

	}

	@Override
	public List<User> getUSersByShelf(long shelfId) {
		List<User> l2 = new ArrayList<>();
		;
		Shelf shelf = shelfRepository.findById(shelfId).get();
		List<ShelfRating> l1 = getAllRating();

		List<ShelfRating> l3 = new ArrayList<>();
		;
		for (ShelfRating s : l1) {
			if (s.getShelf() == shelf) {
				l3.add(s);
			}
		}
		for (ShelfRating s : l3) {
			l2.add(s.getUser());
		}
		System.out.println("anis");
		return l2;

	}

}
