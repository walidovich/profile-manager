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
		if (userRepository.findByEmail(user.getEmail()) == null) {
			return userRepository.save(user);
		} else {
			return null;
		}
	}

	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
}
