package rocks.lipe.hotmessage.bootstrap;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.repository.UserRepository;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

	private UserRepository userRepository;

	public Bootstrap(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Creating users");
		createUsers();
	}

	private void createUsers() {
		userRepository.saveAll(Arrays.asList(new User("Felipe", "123"), new User("Paula", "321")));

	}

}
