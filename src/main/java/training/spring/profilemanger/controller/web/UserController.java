package training.spring.profilemanger.controller.web;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.service.UserService;

@Controller
public class UserController {
	private static final String VIEW_PATH = "user/";

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/users")
	public ModelAndView userList() {
		User connectedUser = getConnectedUser();
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "users");
		modelAndView.addObject("users", userService.findAll());
		modelAndView.addObject("connectedUser", connectedUser);
		return modelAndView;
	}

	private User getConnectedUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = null;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return userService.findByEmail(username);
	}
}
