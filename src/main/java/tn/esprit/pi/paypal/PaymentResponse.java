package tn.esprit.pi.paypal;

public class PaymentResponse {
	private String redirectUrl;
    private String status;
    
    
	public PaymentResponse() {
	}
	
	
	public PaymentResponse(String redirectUrl, String status) {
		super();
		this.redirectUrl = redirectUrl;
		this.status = status;
	}


	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
