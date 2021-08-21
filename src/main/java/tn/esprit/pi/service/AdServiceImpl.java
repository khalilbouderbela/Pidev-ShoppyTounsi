package tn.esprit.pi.service;

import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import tn.esprit.pi.entities.Ad;
import tn.esprit.pi.entities.CategoryEnum;
import tn.esprit.pi.entities.OrderLine;
import tn.esprit.pi.entities.Product;
import tn.esprit.pi.repository.AdRepository;
import tn.esprit.pi.repository.OrderLineRepository;
import tn.esprit.pi.repository.ProductRepository;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.*;
import java.util.*;

@Service

public class AdServiceImpl implements IAdService {

	@Autowired
	private AdRepository adRepository;
	@Autowired
	private OrderLineRepository orderLineRepository;
	@Autowired
	private  ProductRepository productRepository;
	private  HttpServletRequest request;
	private HttpServletResponse response;
	private Environment env;
	
	

	public AdServiceImpl(HttpServletRequest request, HttpServletResponse response, Environment env) {
		super();
		this.request = request;
		this.response = response;
		this.env = env;
	}

	@Override
	public void TouchedPeopleorderLigneFromAd(Long Id, int quantity) {

		Ad ad = this.adRepository.findById(Id).get();
		ad.setTouchedPeople(ad.getTouchedPeople() + 1);
		this.adRepository.save(ad);
		OrderLine ol = new OrderLine();
		ol.setProduct(ad.getProduct());
		ol.setQuantity(quantity);
		ol.setPrice(ad.getProduct().getPriceV()*quantity);
		this.orderLineRepository.save(ol);

	}

	@Override
	public List<Map<String, String>> calculateProfitability() {
		List<Map<String, String>> profitability = new ArrayList<>();
		List<Ad> list = adRepository.findAll();
		list.stream().forEach(p->{
			int touchedPeople = p.getTouchedPeople();
			System.out.println("cc" + touchedPeople);
			int views = p.getViews();
			System.out.println("views"+views);
			Map<String, String> map = new HashMap<>();
			if (views == 0) {
				map.put(p.getName(), "not seen yet");
				System.out.println(map);
			} else {
				float profitabilityPercentage = ((float) touchedPeople / views) * 100;
				if (profitabilityPercentage >= 50) {
					map.put(p.getProduct().getName(), "profitable");
				} else {
					map.put(p.getProduct().getName(), "not profitable");
				}
			}
			profitability.add(map);
			System.out.println(profitability);
		});

		return profitability;
	}

	@Override
	public Ad markAsSeen(String adId) {
		// check the existency of the ad

		Ad ad = adRepository.findById(Long.parseLong(adId)).get();

		Cookie[] cookies = request.getCookies();

		// conversion from Date to LocalDate
		LocalDate adEndLocalDate = Instant.ofEpochMilli(ad.getEndDate().getTime()).atZone(ZoneId.systemDefault())
				.toLocalDate();

		long remainingDays = Period.between(LocalDate.now(), adEndLocalDate).getDays();
		long cookieLifeTime = Duration.ofDays(remainingDays).getSeconds();

		String cookieName = "advertisment" + ad.getId();
		Cookie cookie = new Cookie(cookieName, ad.getName());
		cookie.setMaxAge((int) cookieLifeTime);

		List<Cookie> ck = Optional.ofNullable(cookies).map(Arrays::asList).orElse(Collections.emptyList());

		boolean seen = ck.stream().anyMatch(x -> x.getName().equals(cookieName));
		RequestContextHolder.currentRequestAttributes().getSessionId();

		if (!seen) {
			ad.setViews(ad.getViews() + 1);
			this.adRepository.save(ad);
			response.addCookie(cookie);
			System.out.println("NB Views ++++");
			return ad;
		} else {
			return ad;
		}

	}

	public void sendEmail(String email, int templateId)
			throws MailjetSocketTimeoutException, MailjetException, JSONException {
		MailjetClient client;
		MailjetRequest request;
		MailjetResponse response;
		client = new MailjetClient(env.getProperty("mailjet.apiKey"), env.getProperty("mailjet.apiSecret"),
				new ClientOptions("v3.1"));
		request = new MailjetRequest(Emailv31.resource).property(Emailv31.MESSAGES,
				new JSONArray().put(new JSONObject()
						.put(Emailv31.Message.FROM,
								new JSONObject().put("Email", "ahmed.benabdallah.1@esprit.tn").put("Name",
										"ConsommiTounsi"))
						.put(Emailv31.Message.TO,
								new JSONArray().put(new JSONObject().put("Email", email).put("Name", email)))
						.put(Emailv31.Message.TEMPLATEID, templateId).put(Emailv31.Message.TEMPLATELANGUAGE, true)
						.put(Emailv31.Message.SUBJECT, "Opportunity!!")
						.put(Emailv31.Message.VARIABLES, new JSONObject())));
		response = client.post(request);
		System.out.println(response.getStatus());
		System.out.println(response.getData());
	}

	@Override
	//@Scheduled(cron = "*/10 * * * * ?")
	public String deleteExpiredAds() {
		List<Ad> expiredAds = adRepository.findByEndDate(new Date());
		if (expiredAds.size() != 0) {
			expiredAds.stream().forEach(a -> {
				Product p = a.getProduct();
				float price = p.getPriceA() + (((p.getPriceV() - p.getPriceA()) * (100)) / (100 - a.getDiscount()));
				p.setPriceV(price);
				p.setInPromo(false);
				a.getProduct().setPriceV(price);
				productRepository.save(p);
				this.adRepository.save(a);
				adRepository.delete(a);

			});
			return "expired Ads deleted";
		} else {
			return "no expired Ads to delete";
		}

	}

	@Override
//@Scheduled(cron = "*/30 * * * * ?") // chaque 28-29-30-31 à 10:25
	public void sendPromotionalMonth() throws MailjetSocketTimeoutException, MailjetException, JSONException {
		//final Calendar c = Calendar.getInstance();
		//if (c.get(Calendar.DATE) == c.getActualMinimum(Calendar.DATE)) {
            try{
			List<Product> ls = this.productRepository.findAll();
			ls.stream().forEach(p -> {
				if (p.getCategory().getCategoryType() == CategoryEnum.Alimentier && adRepository.findByProduct(p)==null) {
					Ad ad = new Ad();
					ad.setImage(p.getImage());
					ad.setName(p.getName());
					ad.setPrice(((p.getPriceV() - p.getPriceA()) / 2) + p.getPriceA());
					p.setPriceV(((p.getPriceV() - p.getPriceA()) / 2) + p.getPriceA());
					p.setInPromo(true);
					
					ad.setDiscount(50);
					ad.setStartDate(new Date());
					ad.setEndDate(new Date(ad.getStartDate().getTime() + (1000 * 60 * 60 * 24 * 5)));
					ad.setViews(0);
					ad.setTouchedPeople(0);
					ad.setProduct(p);
					this.adRepository.save(ad);
					p.setAd(ad);
					this.productRepository.save(p);
				}
			});

			// userRepository.findAll().forEach(user->
			//sendEmail("ahmed.benabdallah.1@esprit.tn",2679659);

		//}
			}catch(Exception e)
            {
				System.out.println(e);
            }
	}

	@Override
	//@Scheduled(cron = "*/30 * * * * ?") // chaque 28-29-30-31 à 10:25
	public void sendPromotionalSchool() throws MailjetSocketTimeoutException, MailjetException, JSONException {
		//final Calendar c = Calendar.getInstance();
		//if (c.get(Calendar.DATE) == c.getActualMinimum(Calendar.DATE)) {

			List<Product> ls = this.productRepository.findAll();
			
			ls.stream().forEach(p -> {
				if (p.getCategory().getCategoryType() == CategoryEnum.School && adRepository.findByProduct(p)==null) {
					Ad ad = new Ad();
					ad.setImage(p.getImage());
					ad.setName(p.getName());
					ad.setPrice((float) (((p.getPriceV() - p.getPriceA()) * 0.8) + p.getPriceA()));
					p.setPriceV((float) (((p.getPriceV() - p.getPriceA()) * 0.8) + p.getPriceA()));
					p.setInPromo(true);
					ad.setDiscount(20);
					ad.setStartDate(new Date());
					ad.setEndDate(new Date(ad.getStartDate().getTime() + (1000 * 60 * 60 * 24 * 29)));
					ad.setViews(0);

					ad.setTouchedPeople(0);
					ad.setProduct(p);
					this.adRepository.save(ad);
					p.setAd(ad);
					this.productRepository.save(p);
				}
			});
			// userRepository.findAll().forEach(user->
			// sendEmail("ahmed.benabdallah.1@esprit.tn",2679654);

		//}
	}

	@Override
	public String addAd(Ad ad) throws MailjetSocketTimeoutException, MailjetException, JSONException {
		// add the ad

		if (ad.getEndDate().after(ad.getStartDate())) {

			this.adRepository.save(ad);

			sendEmail("ahmed.benabdallah.1@esprit.tn", 2679654);
			return "saved ad";
		} else {

			return "not saved because the start date is superior then the end date";
		}
		// userRepository.findAll().forEach(user->
		// sendEmail(user.getEmail(),2679659));
	}

	@Override
	public List<Ad> findAll() {

		return this.adRepository.findAll();
	}

	public void deleteAd(Long Id) {
		this.adRepository.deleteById(Id);
	}

}
