package com.sweagle.codeassignment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.SettableListenableFuture;
import com.sweagle.codeassignment.model.KafkaMessage;
import com.sweagle.codeassignment.repository.MessageRepository;
import com.sweagle.codeassignment.service.DatabaseService;
import com.sweagle.codeassignment.service.MessageService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CodingtaskApplicationTests {
	
	@Autowired
	private DatabaseService databaseService;
	
	@Autowired
	private MessageService messageService;
	
	@MockBean
	private MessageRepository messageRepository;
	
	@MockBean
	private KafkaTemplate<String, KafkaMessage> kafkaTemplate;
	
	@Test
	public void saveMessageTest() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		KafkaMessage kafkaMessage = new KafkaMessage("ali", "ahmet", "selam", "merhaba", formatter.format(date));
		when(messageRepository.save(kafkaMessage)).thenReturn(kafkaMessage);
		assertEquals("selam", databaseService.saveMessage(kafkaMessage).getSubject());
	}
	
	@Test
	public void getMessagesByReceiverTest() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		KafkaMessage kafkaMessage = new KafkaMessage("ali", "ahmet", "selam", "merhaba", formatter.format(date));
		KafkaMessage kafkaMessage2 = new KafkaMessage("mehmet", "ahmet", "how are you?", "hello there", formatter.format(date));
		List<KafkaMessage> messages = new ArrayList<KafkaMessage>();
		messages.add(kafkaMessage);
		messages.add(kafkaMessage2);
		when(messageRepository.findKafkaMessageByReceiver("ahmet")).thenReturn(messages);
		List<String> messageBody = new ArrayList<>();
		messageBody.add(kafkaMessage.getBody());
		messageBody.add(kafkaMessage2.getBody());
		assertEquals(messageBody, databaseService.getMessagesByReceiver("ahmet"));
		assertEquals(messageBody.size(), databaseService.getMessagesByReceiver("ahmet").size());
	}
	
	@Test
	public void getMessagesBySenderTest() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		KafkaMessage kafkaMessage = new KafkaMessage("ali", "ahmet", "selam", "merhaba", formatter.format(date));
		KafkaMessage kafkaMessage2 = new KafkaMessage("ali", "mehmet", "how are you?", "hello there", formatter.format(date));
		List<KafkaMessage> messages = new ArrayList<KafkaMessage>();
		messages.add(kafkaMessage);
		messages.add(kafkaMessage2);
		when(messageRepository.findKafkaMessageBySender("ali")).thenReturn(messages);
		List<String> messageBody = new ArrayList<>();
		messageBody.add(kafkaMessage.getBody());
		messageBody.add(kafkaMessage2.getBody());
		assertEquals(messageBody, databaseService.getMessagesBySender("ali"));
		assertEquals(messageBody.size(), databaseService.getMessagesBySender("ali").size());
	}
	
	@Test
	public void getAllMessagesTest() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		KafkaMessage kafkaMessage = new KafkaMessage("ali", "ahmet", "selam", "merhaba", formatter.format(date));
		KafkaMessage kafkaMessage2 = new KafkaMessage("ali", "mehmet", "how are you?", "hello there", formatter.format(date));
		List<KafkaMessage> messages = new ArrayList<KafkaMessage>();
		messages.add(kafkaMessage);
		messages.add(kafkaMessage2);
		when(messageRepository.findAll()).thenReturn(messages);
		assertEquals(messages, databaseService.getAllMessages());
		assertEquals(messages.size(), databaseService.getAllMessages().size());
	}
	
	@Test
	public void sendMessageTest() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		KafkaMessage message = new KafkaMessage("ali", "ahmet", "selam", "merhaba", formatter.format(date));
		SettableListenableFuture<SendResult<String, KafkaMessage>> future = new SettableListenableFuture<>();
		RecordMetadata recordMetadata = new RecordMetadata(new TopicPartition("Kafka_Example", 0), 0, 0, 1234567890L, 1234567890L, 1024, 1024);
		String mustReturnFromService = "Published successfully from " + message.getSender() + " to " 
				+ message.getReceiver();
		future.set(new SendResult<>(new ProducerRecord<String, KafkaMessage>("Kafka_Example", message), recordMetadata));
		when(kafkaTemplate.send("Kafka_Example", message)).thenReturn(future);
		assertEquals("Published successfully from " + message.getSender() + " to " 
				+ message.getReceiver(), messageService.sendMessage("ali", "ahmet", "selam", "merhaba"));
	}
	
	@Test
	public void consumeJsonTest() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		KafkaMessage message = new KafkaMessage("ali", "ahmet", "selam", "merhaba", formatter.format(date));
		when(messageService.consumeJson(message)).thenReturn(message);
		assertEquals(databaseService.saveMessage(message), messageService.consumeJson(message));
	}
	

	@Test
	public void contextLoads() {
	}

}
