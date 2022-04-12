package com.travels.rajbus.service;

import java.util.List;

import com.travels.rajbus.entity.User;

public interface Userservice {
	
	public User createUser(User user);
	
	public User updateUser(User user);
	
	public List<User> getAllUsers();
	
	public void deleteUser(long id);


}

