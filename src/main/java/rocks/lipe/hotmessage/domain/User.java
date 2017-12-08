package rocks.lipe.hotmessage.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@RequiredArgsConstructor

@Entity
public class User implements Serializable {

	private static final long serialVersionUID = 2117299615875629976L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NonNull
	private String name;

	@NonNull
	private String password;
	
	private boolean logged = false;

	@ManyToOne
	@JoinColumn(name = "user_role", nullable = false)
	private Role role;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

}
