package com.usm4.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;



    public void sendSms(String to, String body) {
        Twilio.init(accountSid, authToken);
        Message message = Message.creator(
                new PhoneNumber(to),
                new PhoneNumber("+12176016195"),
                body
        ).create();
        System.out.println("SMS sent with SID: "+message.getSid());
    }
}
