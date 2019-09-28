package com.sweagle.codeassignment.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.sweagle.codeassignment.model.KafkaMessage;

@Service
public class MessageService {

	private static final String TOPIC = "Kafka_Example";
	private static Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	@Autowired
	private KafkaTemplate<String, KafkaMessage> kafkaTemplate;

	@Autowired
	private DatabaseService databaseService;

	public String sendMessage(String sender, String receiver, String subject, String body) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		KafkaMessage message = new KafkaMessage(sender, receiver, subject, body, formatter.format(date));
		kafkaTemplate.send(TOPIC, message);
		String logMessage = "Published successfully from " + message.getSender() + " to " + message.getReceiver();
		logger.debug(logMessage);
		return logMessage;

	}

	@KafkaListener(topics = TOPIC, groupId = "group_json", containerFactory = "userKafkaListenerFactory")
	public KafkaMessage consumeJson(KafkaMessage message) {
		System.out.println("Consumed JSON Message: " + message.toString());
		databaseService.saveMessage(message);
		return message;
	}

}
