package tn.esprit.pi.service;

import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;

import org.json.JSONException;
import tn.esprit.pi.entities.Ad;
import java.util.List;
import java.util.Map;

public interface IAdService {
	


	List<Ad> findAll();

    void TouchedPeopleorderLigneFromAd(Long Id,int quantity) ;

    List<Map<String, String>> calculateProfitability() ;
    
    void deleteAd(Long Id);

    Ad markAsSeen(String adId) ;

    String deleteExpiredAds();

    String addAd(Ad ad) throws MailjetSocketTimeoutException, MailjetException, JSONException;

    void sendPromotionalMonth() throws MailjetSocketTimeoutException, MailjetException, JSONException;
    
    void sendPromotionalSchool() throws MailjetSocketTimeoutException, MailjetException, JSONException;
    
   


}
