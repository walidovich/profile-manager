package training.spring.profilemanger.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.service.UserService;

import javax.validation.Valid;

@Controller
public class UserController {
	private static final String VIEW_PATH = "user/";

	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/userList")
	public ModelAndView userList() {
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "userList");
		modelAndView.addObject("users", userService.findAll());
		return modelAndView;
	}

	@GetMapping("/user")
	public ModelAndView userForm() {
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "user");
		modelAndView.addObject("users", userService.findAll());
		modelAndView.addObject("user", new User());
		return modelAndView;
	}

	@PostMapping("/user")
	public ModelAndView userFormSubmit(ModelAndView modelAndView, @ModelAttribute @Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName(VIEW_PATH + "user");
		} else {
			User savedUser = userService.save(user);
			if (savedUser != null) {
				modelAndView.setViewName("redirect:/user");
			} else {
				modelAndView.setViewName(VIEW_PATH + "user");
				modelAndView.addObject("duplicateEmail", "This email is already used");
			}
		}
		modelAndView.addObject("users", userService.findAll());
		return modelAndView;
	}
}
