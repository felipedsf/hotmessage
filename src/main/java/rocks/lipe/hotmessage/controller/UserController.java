package rocks.lipe.hotmessage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/register")
	public void register(@RequestBody User user) {
		userService.registerUser(user.getName(), user.getPassword());
	}

	@PostMapping("/login")
	public User login(@RequestBody User user) {
		return userService.getUser(user.getName(), user.getPassword());
	}
}
