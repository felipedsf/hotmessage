package rocks.lipe.hotmessage.controller;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import rocks.lipe.hotmessage.domain.ChatMessage;
import rocks.lipe.hotmessage.domain.ChatMessageDto;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChatControllerTest {

	@Value("${local.server.port}")
	private int port;

	private String URL;

	private CompletableFuture<ChatMessage> completableFuture;

	WebSocketStompClient stompClient;

	private StompSession stompSession;

	@Before
	public void setUp() throws Exception {
		completableFuture = new CompletableFuture<>();
		URL = "ws://localhost:" + port + "/chat";

		stompClient = new WebSocketStompClient(new SockJsClient(createTransports()));
		stompClient.setMessageConverter(new MappingJackson2MessageConverter());

		stompSession = stompClient.connect(URL, new StompSessionHandlerAdapter() {
		}).get(1, SECONDS);

	}

	@After
	public void tearDown() throws Exception {
		stompSession.disconnect();
		stompClient.stop();
	}

	@Test
	public void connectsToSocket() throws Exception {
		assertThat(stompSession.isConnected(), equalTo(true));
	}

	@Test
	public void sendMessageToSocket() throws Exception {
		stompSession.subscribe("/channel/global", new CreateStompFrameHandler());
		stompSession.send("/app/global", new ChatMessageDto("Mensagem de teste 1", "felipe"));

		ChatMessage message = completableFuture.get();
		assertThat(message, notNullValue());
		assertThat(message.getContent(), equalTo("Mensagem de teste 1"));
	}

	@Test
	public void addUserToSocket() throws Exception {
		stompSession.subscribe("/channel/global/add", new CreateStompFrameHandler());
		stompSession.send("/app/global/add", new ChatMessage("Mensagem de teste", "paula"));

		ChatMessage message = completableFuture.get();
		assertThat(message, notNullValue());
	}

	@Test
	public void sendMessage() throws Exception {
		stompSession.subscribe("/channel/paula/send", new CreateStompFrameHandler());
		stompSession.send("/app/paula/send", new ChatMessageDto("Mensagem de teste 1", "felipe"));

		ChatMessage message = completableFuture.get();
		assertThat(message, notNullValue());
	}

	private List<Transport> createTransports() {
		List<Transport> transports = new ArrayList<>(1);
		transports.add(new WebSocketTransport(new StandardWebSocketClient()));
		return transports;
	}

	private class CreateStompFrameHandler implements StompFrameHandler {

		@Override
		public Type getPayloadType(StompHeaders headers) {
			return ChatMessage.class;
		}

		@Override
		public void handleFrame(StompHeaders headers, Object payload) {
			completableFuture.complete((ChatMessage) payload);
		}

	}
}
