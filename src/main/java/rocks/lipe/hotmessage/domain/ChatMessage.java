package rocks.lipe.hotmessage.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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

	@NonNull
	private String content;

	@NonNull
	@OneToOne
	private User sender;

	private String reciver;

	private Date sendDate = new Date();

}
