package rocks.lipe.hotmessage.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUser(String name, String password) {
		Optional<User> user = userRepository.findByName(name);
		if (!user.isPresent()) {
			throw new RuntimeException("Usuário não cadastrado.");
		}
		if (!user.get().getPassword().equals(password)) {
			throw new RuntimeException("Senha inválida.");
		}

		return user.get();
	}

	public void registerUser(String name, String password) {
		User user = new User(name, password);
		userRepository.save(user);
	}

}
