package rocks.lipe.hotmessage.bootstrap;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.repository.UserRepository;

@Slf4j
@Component
@Profile("dev")
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
		userRepository.save(Arrays.asList(new User("felipe", "123"), new User("paula", "321")));

	}

}
