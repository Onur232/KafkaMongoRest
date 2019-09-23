package com.sweagle.codeassignment.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "kafka_message")
public class KafkaMessage {
	
	private String sender;
	private String receiver;
	private String subject;
	private String body;
	private String sentDate;
	
	public KafkaMessage(String sender, String receiver, String subject, String body, String sentDate) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.subject = subject;
		this.body = body;
		this.sentDate = sentDate;
	}
	
	public KafkaMessage() {
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	public String getSentDate() {
		return sentDate;
	}

	public void setSentDate(String sentDate) {
		this.sentDate = sentDate;
	}

	@Override
	public String toString() {
		return "KafkaMessage [sender=" + sender + ", receiver=" + receiver + ", subject=" + subject + ", body=" + body
				+ ", sentDate=" + sentDate + "]";
	}
	
}
