package rocks.lipe.hotmessage.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rocks.lipe.hotmessage.domain.Role;
import rocks.lipe.hotmessage.domain.User;
import rocks.lipe.hotmessage.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> opUser = userRepository.findByName(username);
		if (!opUser.isPresent()) {
			return null;
		}
		User user = opUser.get();
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
		return buildUserForAuthentication(user, authorities);
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), true, true,
				true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Role role) {
		return Arrays.asList(new SimpleGrantedAuthority(role.getDescription()));
	}

}
