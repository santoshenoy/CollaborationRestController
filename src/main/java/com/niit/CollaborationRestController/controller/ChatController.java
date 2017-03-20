package com.niit.CollaborationRestController.controller;

import java.util.Date;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niit.CollaborationRestController.chat.Message;
import com.niit.CollaborationRestController.chat.OutputMessage;

@Controller
@RequestMapping("/")
public class ChatController {

	@MessageMapping("/chat")
	@SendTo("/topic/message")
	public OutputMessage sendMessage(Message message) {
		// logger.info("Message sent");
		return new OutputMessage(message, new Date());
	}
}
