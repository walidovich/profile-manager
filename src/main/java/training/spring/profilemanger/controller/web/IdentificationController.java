package training.spring.profilemanger.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import training.spring.profilemanger.exception.PasswordsNotMatchingException;
import training.spring.profilemanger.exception.UserEmailExistsException;
import training.spring.profilemanger.model.User;
import training.spring.profilemanger.model.UserLogin;
import training.spring.profilemanger.service.UserService;

import javax.validation.Valid;
import java.io.IOException;

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
	public ModelAndView signupSubmit(ModelAndView modelAndView, @ModelAttribute @Valid User user,
	                                 @RequestParam("image") MultipartFile image, BindingResult bindingResult,
	                                 @ModelAttribute("passwordConfirmation") String passwordConfirmation) {
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName(VIEW_PATH + "signup");
		} else {
			try {
				userService.validateAllFields(user, passwordConfirmation);
				userService.save(user, image);
				modelAndView.addObject("users", userService.findAll());
				modelAndView.setViewName("redirect:/users");
			} catch (PasswordsNotMatchingException e) {
				bindingResult.rejectValue("password", "error.user", "passwords are not matching");
				modelAndView.setViewName(VIEW_PATH + "signup");
			} catch (UserEmailExistsException e) {
				bindingResult.rejectValue("email", "error.user", "email already in use");
				modelAndView.setViewName(VIEW_PATH + "signup");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return modelAndView;
	}

	@PostMapping("/logout")
	public ModelAndView logout() {
		return new ModelAndView("redirect:/login");
	}
}

