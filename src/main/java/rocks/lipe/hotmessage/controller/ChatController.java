package rocks.lipe.hotmessage.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import rocks.lipe.hotmessage.domain.ChatMessage;
import rocks.lipe.hotmessage.domain.ChatMessageDto;
import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.service.UserService;

@Slf4j
@Controller
public class ChatController {

	private UserService userService;

	public ChatController(UserService userService) {
		this.userService = userService;
	}

	@MessageMapping("/global")
	@SendTo("/channel/global")
	public ChatMessage sendPublicMessage(@Payload ChatMessageDto message) {
		log.info("/channel/public: " + message);
		User sender = userService.getUser(message.getSender());
		return new ChatMessage(message.getContent(), sender);
	}

	@MessageMapping("/global/add")
	@SendTo("/channel/global/add")
	public ChatMessage addUserChat(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", message.getSender().getName());
		return message;
	}

	@MessageMapping("/{reciver}/send")
	@SendTo("/channel/{reciver}/send")
	public ChatMessage sendMessage(@Payload ChatMessageDto message, @DestinationVariable("reciver") String reciver) {
		User sender = userService.getUser(message.getSender());
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setReciver(reciver);
		chatMessage.setSender(sender);
		chatMessage.setContent(message.getContent());
		log.info("/channel/{reciver}: " + chatMessage);
		return chatMessage;
	}

}
