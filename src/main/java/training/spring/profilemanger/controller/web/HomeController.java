package training.spring.profilemanger.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import training.spring.profilemanger.model.User;

@Controller
public class HomeController {

	private static final String VIEW_PATH = "identification/";

	@GetMapping("")
	public ModelAndView home() {
		return new ModelAndView("index");
	}

	@GetMapping("/sign-up")
	public ModelAndView signUpForm() {
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "login");
		modelAndView.addObject("user", new User());
		return modelAndView;
	}

	@PostMapping("/sign-up")
	public ModelAndView signUpSubmit() {
		ModelAndView modelAndView = new ModelAndView(VIEW_PATH + "login");
		modelAndView.addObject("user", new User());
		return modelAndView;
	}
}
