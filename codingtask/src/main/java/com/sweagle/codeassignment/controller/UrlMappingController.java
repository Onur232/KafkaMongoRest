package com.sweagle.codeassignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.sweagle.codeassignment.model.KafkaMessage;
import com.sweagle.codeassignment.service.DatabaseService;
import com.sweagle.codeassignment.service.MessageService;

@RestController
public class UrlMappingController {

	@Autowired
	private MessageService messageService;
	
	@Autowired
	private DatabaseService databaseService;


	@PostMapping("/publish/{sender}/{receiver}/{subject}")
	public String sendMessage(@PathVariable("sender") String sender, @PathVariable("receiver") String receiver,
			@PathVariable("subject") String subject, @RequestBody String body) {
		return messageService.sendMessage(sender, receiver, subject, body);
	}
	
	@GetMapping("/query/receiver/{receiver}")
	public List<String> getMessagesByReceiver(@PathVariable("receiver") String receiver) {
		return databaseService.getMessagesByReceiver(receiver);
	}
	
	@GetMapping("/query/sender/{sender}")
	public List<String> getMessagesBySender(@PathVariable("sender") String sender) {
		return databaseService.getMessagesBySender(sender);
	}

	@GetMapping("/query/messageDetails")
	public List<KafkaMessage> getAllMessages() {
		return databaseService.getAllMessages();
	}
	

}
