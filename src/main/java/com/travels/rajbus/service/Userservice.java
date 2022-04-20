package com.travels.rajbus.service;

import java.util.List;

import com.travels.rajbus.entity.User;
import com.travels.rajbus.model.ServiceStatus;

public interface Userservice {

	public User createUser(User user);
	
	public User updateUser(User user);
	
	public List<User> getAllUsers();
	
	public void deleteUser(long id);

	User login(String userName, String Password);
	public ServiceStatus validator(String userName, String password);

}

