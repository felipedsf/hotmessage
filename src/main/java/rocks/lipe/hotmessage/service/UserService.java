package rocks.lipe.hotmessage.service;

import rocks.lipe.hotmessage.domain.User;

public interface UserService {

	User save(User user);

	User getUserByName(String username);

}
