package com.sweagle.codeassignment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sweagle.codeassignment.model.KafkaMessage;

public interface MessageRepository extends MongoRepository<KafkaMessage, Integer>{

	public List<KafkaMessage> findKafkaMessageByReceiver(String receiver);
	
	public List<KafkaMessage> findKafkaMessageBySender(String sender);
}
