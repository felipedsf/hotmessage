package rocks.lipe.hotmessage.controller;

import java.util.stream.Collectors;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.annotations.ApiOperation;
import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.service.UserService;

@Controller
public class UserController {

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping({ "", "/" })
	public String index(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			User user = userService.getUserByName(authentication.getName());
			userService.setLogged(user);
			model.addAttribute("user", user);
			model.addAttribute("username", user.getName());
			model.addAttribute("receiver", null);
		} else {
			return "redirect:/login";
		}
		return "index";
	}

	@GetMapping("/u/{receiver}")
	public String chat(@PathVariable("receiver") String receiver, Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			User user = userService.getUserByName(authentication.getName());
			userService.setLogged(user);
			model.addAttribute("user", user);
			model.addAttribute("username", user.getName());
			model.addAttribute("receiver", receiver);
		} else {
			return "redirect:/login";
		}
		return "index";
	}

	@GetMapping("/users")
	public String loggedUsers(Model model, Authentication authentication) {
		model.addAttribute("allUsers",
				userService.getAllUsers().stream().filter(user -> !user.getName().equals(authentication.getName()))
						.map(user -> user.getName()).collect(Collectors.toList()));
		return "users";
	}

	@GetMapping("/logout")
	public String logout(Authentication authentication) {
		User user = userService.getUserByName(authentication.getName());
		userService.setLogged(user);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@ApiOperation(value = "Login", notes = "Verify if the user exists in the database")
	@PostMapping("/login")
	public String login() {
		return "redirect:/";
	}

	@GetMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@ApiOperation(value = "Register user", notes = "Register user in the database")
	@PostMapping(path = "/register", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE,
			MediaType.APPLICATION_JSON_VALUE })
	public String register(User userForm) {
		User user = userService.save(userForm);

		Model model = new ExtendedModelMap();
		model.addAttribute("user", user);

		return "redirect:/";
	}

}
