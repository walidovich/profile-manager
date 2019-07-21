package training.spring.profilemanger.service;

import org.springframework.stereotype.Service;
import training.spring.profilemanger.exception.PasswordsNotMatchingException;
import training.spring.profilemanger.exception.UserEmailExistsException;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.repository.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void validateAllFields(User user, String passwordConfirmation) throws PasswordsNotMatchingException, UserEmailExistsException {
		user = trimWhiteSpaces(user);
		checkPasswordsMatching(user.getPassword(), passwordConfirmation);
		if (userRepository.findByEmail(user.getEmail()) != null) {
			throw new UserEmailExistsException();
		}
	}

	public User save(User user) {
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
}
