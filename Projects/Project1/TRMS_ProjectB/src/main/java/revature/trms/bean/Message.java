package revature.trms.bean;

import java.sql.Date;

public class Message {
	private String sender, receiver, message;
	private Date date;

	public Message(String sender, String receiver, String message, Date date) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.message = message;
		this.date = date;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	} 
	
	
	
}
