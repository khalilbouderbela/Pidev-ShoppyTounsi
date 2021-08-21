package tn.esprit.pi.paypal;


import com.paypal.api.payments.Payment;

public class CompletePaymentResponse {
	private String success;
	private Payment payment;
	public CompletePaymentResponse() {
		super();
	}
	public CompletePaymentResponse(String success, Payment payment) {
		super();
		this.success = success;
		this.payment = payment;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
