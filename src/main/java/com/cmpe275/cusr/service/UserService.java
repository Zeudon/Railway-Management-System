package com.cmpe275.cusr.service;

import java.util.List;

import com.cmpe275.cusr.model.User;

public interface UserService {
	public User findUser();
	public User update(User user);
	public void signout();
	public User getUserFromDB(String email);

}
