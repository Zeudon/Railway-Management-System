package com.cmpe275.cusr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cmpe275.cusr.model.User;
import com.cmpe275.cusr.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	
	
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired(required = false)
//	private FirebaseService firebaseService;
	
	
	public User findUser() {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getCredentials();
	
		return u;
	}
	
	
	
	public void signout() {
		SecurityContextHolder.clearContext();
	}
	
	public User getUserFromDB(String email) {
		User userLoaded = userRepository.findByEmail(email);
		
		if(userLoaded == null) {
			userLoaded = new User();
			userLoaded.setEmail(email);
			
			userRepository.save(userLoaded);
		} 
		return userLoaded;
	}



	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		return null;
	}




}