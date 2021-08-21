package tn.esprit.pi.configuration;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Qualifier;

@org.springframework.stereotype.Service
public class ServiceT {

	private final SmsSender smsSender;

	@Autowired
	public ServiceT(@Qualifier("twilio") TwilioSmsSender smsSender) {
		this.smsSender = smsSender;
	}

	public void sendSms(SmsRequest smsRequest) {
		smsSender.sendSms(smsRequest);
	}
}
