package tn.esprit.pi.configuration;



public interface SmsSender {

	 public void sendSms(SmsRequest smsRequest);

   // or maybe void sendSms(String phoneNumber, String message);
}