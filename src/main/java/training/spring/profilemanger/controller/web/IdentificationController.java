package training.spring.profilemanger.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import training.spring.profilemanger.model.UserLogin;

@Controller
public class IdentificationController {

	private static final String VIEW_PATH = "identification/";

	@GetMapping({"/login", "/logout"})
	public ModelAndView loginForm(ModelAndView modelAndView) {
		modelAndView.addObject("userLogin", new UserLogin());
		modelAndView.setViewName(VIEW_PATH + "login");
		return modelAndView;
	}
}
