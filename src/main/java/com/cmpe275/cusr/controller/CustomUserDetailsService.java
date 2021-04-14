package com.cmpe275.cusr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cmpe275.cusr.model.CustomUserDetails;
import com.cmpe275.cusr.model.User;
import com.cmpe275.cusr.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}
	
	public List<User> listAll() {
		return userRepo.findAll();
	}
	
	public void save(User user) {
		userRepo.save(user);
	}
	
	public User get(long id) {
		User auth = (User) SecurityContextHolder.getContext().getAuthentication();
		Long id1 = auth.getUserId();

		return userRepo.findOne(id);
	}
	
	public User findbyId(long id)
	{
		
		
		return userRepo.findbyId(id);
		
		
		
	}
	
	

}
