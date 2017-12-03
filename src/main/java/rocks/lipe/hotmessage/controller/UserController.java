package rocks.lipe.hotmessage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@ApiOperation(value = "Register user", notes = "Register user in the database")
	@PostMapping("/register")
	public void register(@RequestBody User user) {
		userService.registerUser(user.getName(), user.getPassword());
	}

	@ApiOperation(value = "Login", notes = "Verify if the user exists in the database")
	@PostMapping("/login")
	public User login(@RequestBody User user) {
		return userService.getUser(user.getName(), user.getPassword());
	}
}
