package training.spring.profilemanger.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/user")
	public ModelAndView userForm() {
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "user");
		modelAndView.addObject("user", new User());
		modelAndView.addObject("users", userService.findAll());
		return modelAndView;
	}

	@PostMapping("/user")
	public ModelAndView userFormSubmit(ModelAndView modelAndView, @ModelAttribute User user) {
		modelAndView.setViewName("redirect:/user");
		userService.save(user);
		return modelAndView;
	}
}
