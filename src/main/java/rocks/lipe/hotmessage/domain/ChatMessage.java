package rocks.lipe.hotmessage.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

@Entity
public class ChatMessage implements Serializable {

	private static final long serialVersionUID = 3436724970222894074L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String content;

	@NonNull
	private String sender;

	private String receiver;

	private boolean read;

	private Date sendDate = new Date();

	public ChatMessage(String content, String sender) {
		this.content = content;
		this.sender = sender;
	}

}
