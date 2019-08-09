package training.spring.profilemanger.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import training.spring.profilemanger.exception.PasswordsNotMatchingException;
import training.spring.profilemanger.exception.UserEmailExistsException;
import training.spring.profilemanger.model.MyUserDetails;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public void validateAllFields(User user, String passwordConfirmation) throws PasswordsNotMatchingException, UserEmailExistsException {
		user = trimWhiteSpaces(user);
		checkPasswordsMatching(user.getPassword(), passwordConfirmation);
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserEmailExistsException();
		}
	}

	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	private User trimWhiteSpaces(User user) {
		user.setFirstName(user.getFirstName().trim());
		user.setLastName(user.getLastName().trim());
		user.setEmail(user.getEmail().trim());
		return user;
	}

	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	private void checkPasswordsMatching(String password, String passwordConfirmation) throws PasswordsNotMatchingException {
		if (passwordConfirmation == null || !passwordConfirmation.equals(password)) {
			throw new PasswordsNotMatchingException();
		}
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user != null) {
			return new MyUserDetails(user);
		} else {
			throw new UsernameNotFoundException(username + " doesn't exist");
		}
	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public void addUsersConnection(Long id1, Long id2) {
		User user1 = userRepository.findById(id1).get();
		User user2 = userRepository.findById(id2).get();
		user1.getConnections().add(user2);
		user2.getConnections().add(user1);
		userRepository.save(user1);
	}

	public void removeUsersConnection(Long id1, Long id2) {
		User user1 = userRepository.findById(id1).get();
		User user2 = userRepository.findById(id2).get();
		user1.getConnections().remove(user2);
		user2.getConnections().remove(user1);
		userRepository.save(user1);
	}
}
