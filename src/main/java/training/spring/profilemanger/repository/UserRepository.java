package training.spring.profilemanger.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import training.spring.profilemanger.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	User findByEmail(String email);
}
