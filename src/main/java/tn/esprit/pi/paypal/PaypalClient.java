package tn.esprit.pi.paypal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import tn.esprit.pi.paypal.CompletePaymentRequest;
import tn.esprit.pi.paypal.PaymentRequest;

@Service
@PropertySource(ignoreResourceNotFound = true, value = "keys.properties")
public class PaypalClient {
	@Value("${paypal.clientId}")
	private String clientId;
	
	@Value("${paypal.clientSecret}")
	private String clientSecret;
	
	private static String executionMode = "sandbox";
	
	public Map<String, Object> createPayment(PaymentRequest paymentRequest){
		Map<String, Object> response = new HashMap<String, Object>();
		
		Amount amount = new Amount();
		amount.setCurrency("USD");
		amount.setTotal(paymentRequest.getSum());
		
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		
		Payer payer = new Payer();
		payer.setPaymentMethod("paypal");
		
		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setTransactions(transactions);
		
		RedirectUrls redirectUrls = new RedirectUrls();	
		redirectUrls.setCancelUrl("http://10.148.53.47:4200/cancel");
		redirectUrls.setReturnUrl("http://10.148.53.47:4200");
		payment.setRedirectUrls(redirectUrls);
		
		Payment createdPayment;
		
		try {
			String redirectUrl = "";
			APIContext context = new APIContext(clientId, clientSecret, executionMode);
			createdPayment = payment.create(context);
			if(createdPayment != null) {
				List<Links> links = createdPayment.getLinks();
				for(Links link: links) {
					if(link.getRel().equals("approval_url")) {
						redirectUrl = link.getHref();
						break;
					}
				}
				response.put("status", "success");
				response.put("redirect_url", redirectUrl);
			}
		} catch(PayPalRESTException e) {
			System.out.println(e);
		}
		
		return response;
	}
	
	public Map<String, Object> completePayment(CompletePaymentRequest completePaymentRequest){
	    Map<String, Object> response = new HashMap<>();
	    Payment payment = new Payment();
	    payment.setId(completePaymentRequest.getPaymentId());

	    PaymentExecution paymentExecution = new PaymentExecution();
	    paymentExecution.setPayerId(completePaymentRequest.getPayerId());
	    try {
	        APIContext context = new APIContext(clientId, clientSecret, "sandbox");
	        Payment createdPayment = payment.execute(context, paymentExecution);
	        if(createdPayment!=null){
	        	response.put("status", "success");
	            response.put("payment", createdPayment);
	        }
	    } catch (PayPalRESTException e) {
	        System.err.println(e);
	    }
	    return response;
	}


}

