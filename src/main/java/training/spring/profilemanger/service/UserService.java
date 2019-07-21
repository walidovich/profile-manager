package training.spring.profilemanger.service;

import org.springframework.stereotype.Service;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User save(User user) {
		user = trimWhiteSpaces(user);
		if (userRepository.findByEmail(user.getEmail()) == null) {
			return userRepository.save(user);
		} else {
			return null;
		}
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

	public User findByEmail(String email) {
		email = email.trim();
		return userRepository.findByEmail(email);
	}
}
