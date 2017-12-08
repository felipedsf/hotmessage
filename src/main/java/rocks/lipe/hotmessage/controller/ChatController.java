package rocks.lipe.hotmessage.controller;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import rocks.lipe.hotmessage.domain.ChatMessage;
import rocks.lipe.hotmessage.domain.ChatMessageDto;
import rocks.lipe.hotmessage.repository.ChatMessageRepository;

@Slf4j
@RestController
public class ChatController {

	private ChatMessageRepository chatMessageRepository;

	public ChatController(ChatMessageRepository chatMessageRepository) {
		this.chatMessageRepository = chatMessageRepository;
	}

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

	@MessageMapping("/{receiver}/send")
	@SendTo("/channel/{receiver}/send")
	public ChatMessage sendMessage(@Payload ChatMessageDto message, @DestinationVariable("receiver") String receiver) {
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setReceiver(receiver);
		chatMessage.setSender(message.getSender());
		chatMessage.setContent(message.getContent());
		log.info("/channel/{receiver}: " + chatMessage);
		chatMessageRepository.save(chatMessage);
		return chatMessage;
	}

}
