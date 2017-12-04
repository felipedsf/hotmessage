package rocks.lipe.hotmessage.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ChatMessageDto implements Serializable {

	private static final long serialVersionUID = 3436724970222894074L;

	@NonNull
	private String content;

	@NonNull
	private String sender;

}
