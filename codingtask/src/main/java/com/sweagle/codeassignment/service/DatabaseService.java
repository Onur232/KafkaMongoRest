package com.sweagle.codeassignment.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.sweagle.codeassignment.model.KafkaMessage;
import com.sweagle.codeassignment.repository.MessageRepository;

@Service
public class DatabaseService {
	
	private static Logger logger = LoggerFactory.getLogger(DatabaseService.class);
	
	@Autowired
	private MessageRepository messageRepository;

	public void saveMessage(KafkaMessage message) {
		messageRepository.save(message);
		logger.debug("Message Saved Successfully To Mongo: " + message.toString());
	}

	public List<String> getMessagesByReceiver(@PathVariable("receiver") String receiver) {
		List<KafkaMessage> findKafkaMessageBySender = messageRepository.findKafkaMessageByReceiver(receiver);
		List<String> messages = new ArrayList<String>();
		for (KafkaMessage message : findKafkaMessageBySender) {
			messages.add(message.getBody());
		}
		return messages;
	}

	public List<String> getMessagesBySender(@PathVariable("sender") String sender) {
		List<KafkaMessage> findKafkaMessageBySender = messageRepository.findKafkaMessageBySender(sender);
		List<String> messages = new ArrayList<String>();
		for (KafkaMessage message : findKafkaMessageBySender) {
			messages.add(message.getBody());
		}
		return messages;
	}
	
	public List<KafkaMessage> getAllMessages() {
		return messageRepository.findAll();
	}
	
}
