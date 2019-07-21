package training.spring.profilemanger.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.model.UserLogin;
import training.spring.profilemanger.service.UserService;

import javax.validation.Valid;

@Controller
public class IdentificationController {

	private UserService userService;

	public IdentificationController(UserService userService) {
		this.userService = userService;
	}

	private static final String VIEW_PATH = "identification/";

	@GetMapping({"/login", "/logout"})
	public ModelAndView loginForm(ModelAndView modelAndView) {
		modelAndView.addObject("userLogin", new UserLogin());
		modelAndView.setViewName(VIEW_PATH + "login");
		return modelAndView;
	}

	@GetMapping("/signup")
	public ModelAndView signupForm(ModelAndView modelAndView) {
		modelAndView.addObject("user", new User());
		modelAndView.addObject("passwordConfirmation", null);
		modelAndView.addObject("duplicateEmail", null);
		modelAndView.setViewName(VIEW_PATH + "signup");
		return modelAndView;
	}

	@PostMapping("/signup")
	public ModelAndView userFormSubmit(ModelAndView modelAndView, @ModelAttribute @Valid User user,
	                                   BindingResult bindingResult, @ModelAttribute("passwordConfirmation") String passwordConfirmation) {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName(VIEW_PATH + "signup");
		} else {
			if (userService.arePasswordsMatching(user.getPassword(), passwordConfirmation)
					&& userService.findByEmail(user.getEmail()) == null) {
				userService.save(user);
				modelAndView.addObject("users", userService.findAll());
				modelAndView.setViewName("redirect:/users");
			} else {
				modelAndView.setViewName(VIEW_PATH + "signup");
				if (!userService.arePasswordsMatching(user.getPassword(), passwordConfirmation)) {
					bindingResult.rejectValue("password", "error.user", "passwords are not matching");
				}
				if (userService.findByEmail(user.getEmail()) != null) {
					bindingResult.rejectValue("email", "error.user", "email already in use");
				}
			}
		}
		return modelAndView;
	}
}
