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

@Slf4j
@Controller
public class ChatController {

	@MessageMapping("/global")
	@SendTo("/channel/global")
	public ChatMessage sendPublicMessage(@Payload ChatMessageDto message) {
		log.info("/channel/public: " + message);
		return new ChatMessage(message.getContent(), message.getSender());
	}

	@MessageMapping("/global/add")
	@SendTo("/channel/global/add")
	public ChatMessage addUserChat(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", message.getSender());
		return message;
	}

	@MessageMapping("/{reciver}/send")
	@SendTo("/channel/{reciver}/send")
	public ChatMessage sendMessage(@Payload ChatMessageDto message, @DestinationVariable("reciver") String reciver) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setReciver(reciver);
		chatMessage.setSender(message.getSender());
		chatMessage.setContent(message.getContent());
		log.info("/channel/{reciver}: " + chatMessage);
		return chatMessage;
	}

}
