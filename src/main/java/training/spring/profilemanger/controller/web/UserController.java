package training.spring.profilemanger.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
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
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "users");
		modelAndView.addObject("users", userService.findAll());
		return modelAndView;
	}
}
