package rocks.lipe.hotmessage.bootstrap;

import java.util.Arrays;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.repository.RoleRepository;
import rocks.lipe.hotmessage.repository.UserRepository;

@Slf4j
@Component
@Profile("dev")
public class Bootstrap implements CommandLineRunner {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	public Bootstrap(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Creating users");
		createUsers();
	}

	private void createUsers() {
		User admin = new User("admin", "admin");
		admin.setCreatedDate(new Date());
		admin.setRole(roleRepository.findByDescription("admin"));

		User user = new User("user", "user");
		user.setCreatedDate(new Date());
		user.setRole(roleRepository.findByDescription("user"));

		userRepository.save(Arrays.asList(admin, user));
	}

}
