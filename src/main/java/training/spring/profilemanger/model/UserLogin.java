package training.spring.profilemanger.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLogin {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
}
