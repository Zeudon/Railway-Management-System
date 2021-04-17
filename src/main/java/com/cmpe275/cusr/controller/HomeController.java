package com.cmpe275.cusr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cmpe275.cusr.model.CustomUserDetails;
import com.cmpe275.cusr.model.User;
import com.cmpe275.cusr.repository.UserRepository;
import com.cmpe275.cusr.service.UserService;



@Controller
public class HomeController {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	CustomUserDetailsService userService;
	
	@Autowired
	UserService userSer;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
	}
	@GetMapping("/dashboard")
	public String viewHomePage11() {
		return "MainDB";
	}
	
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		
		return "signup_form";
	}
	
	@PostMapping("/process_register")
	public String processRegister(User user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		//
		userRepo.save(user);
		
		return "register_success";
	}
	@GetMapping("/users")
	public String index(Model model,@AuthenticationPrincipal CustomUserDetails userdetails) {
		User users = new User();
		String user1=userdetails.getUsername();
		User user=userSer.getUserFromDB(user1);
		long id=user.getUserId();
		
		model.addAttribute("user", id);
		
		return "users";
	}
	
	@RequestMapping("/update")
	public String viewHomePage(Model model) {
		List<User> listUsers = userService.listAll();
		model.addAttribute("listUsers", listUsers);
		
		return "NewFile";
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") User user) {
		userService.save(user);
		
		return "redirect:/users";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") long id) {
		ModelAndView mav = new ModelAndView("NewFile1");
		User user = userService.findbyId(id);
		mav.addObject("user", user);
		
		return mav;
	}
	
	
}