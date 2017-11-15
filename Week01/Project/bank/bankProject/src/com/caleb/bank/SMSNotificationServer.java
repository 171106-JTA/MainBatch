package com.caleb.bank;

import java.util.Arrays;

import org.apache.log4j.Logger;

import com.textmagic.sdk.RestClient;
import com.textmagic.sdk.RestException;
import com.textmagic.sdk.resource.instance.TMNewMessage;

/**
 * The SMSNotificationServer class is used to send
 * users SMS message notifications 
 * @author calebschumake
 *
 */
public class SMSNotificationServer {
	/* RestClient will be where I direct the text message, textMessage is the actual message object
	 * I want to send. 
	 **/
	RestClient client;
	TMNewMessage textMessage;


	/**
	 * Constructor that creates a new rest client given my credentials and also creates a 
	 * new text message object
	 */
	SMSNotificationServer() {
		client = new RestClient("calebschumake", "my-key");
		textMessage = client.getResource(TMNewMessage.class);
	}
	
	/**
	 * This method is used to set the message before sending it to the
	 * end user
	 * @param message
	 */
	public void setTextMessage(String message) {
		textMessage.setText(message);
	}
	
	/**
	 * This method is used to set the phone number that the message will be sent to
	 * @param number
	 */
	public void setPhoneNumber(String number) {
		textMessage.setPhones(Arrays.asList(new String[] { number }));
	}
	
	/**
	 * This method sends the notification to the user
	 */
	public void sendNotification() {
		try {
			textMessage.send();
		} catch (final RestException e) {
			System.out.println("The number the user specified is invalid");
		}
	}

}
