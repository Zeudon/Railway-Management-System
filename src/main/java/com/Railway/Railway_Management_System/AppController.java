package com.Railway.Railway_Management_System;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.Railway.Railway_Management_System.models.OneWayList;
import com.Railway.Railway_Management_System.models.SearchContent;
import com.Railway.Railway_Mangement_System.service.TrainService;

@Controller
public class AppController {

//	@Autowired
//	private TrainService trainService;
	
	@Autowired
	private UserRepository userRepo;
	
	@GetMapping("")
	public String viewHomePage() {
		return "index";
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
		
		userRepo.save(user);
		
		return "register_success";
	}
	
	@GetMapping("/users")
	public String index(Model model) {
		SearchContent users = new SearchContent();
		model.addAttribute("searchContent", users);
		return "users";
	}
	
	
	
 /**	@PostMapping("/users")
	public String searchTrip(@ModelAttribute SearchContent search, HttpServletRequest request,
			Model model) {
				
				//add search inquiry in the view
				model.addAttribute("searchContent", search);
				
				request.setAttribute("numberOfConnections", search.getNumberOfConnections());
		
				//create search result container
				OneWayList result = new OneWayList();
				
				//verify input date and time
				if (trainService.verfiyDateAndTime(search, result)) {
					//search for forward trip 
					trainService.searchOneWay(search, result);
				}
				model.addAttribute("oneWayList", result);
				
				return "searchResult";
	}
	**/
	}

