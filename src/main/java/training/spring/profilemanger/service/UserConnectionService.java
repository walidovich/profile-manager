package training.spring.profilemanger.service;

import org.springframework.stereotype.Service;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.repository.UserRepository;

@Service
public class UserConnectionService {
	private UserRepository userRepository;

	public UserConnectionService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void connectUsers(Long user1Id, Long user2Id) {
		User user1 = userRepository.findById(user1Id).get();
		User user2 = userRepository.findById(user2Id).get();
		user1.getConnections().add(user2);
		user2.getConnections().add(user1);
		userRepository.save(user1);
		userRepository.save(user2);
	}
}
