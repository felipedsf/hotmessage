package rocks.lipe.hotmessage.service;

import java.util.List;

import rocks.lipe.hotmessage.domain.User;

public interface UserService {

	User save(User user);

	User getUserByName(String username);

	void setLogged(User user);

	List<User> getAllUsers();
}
