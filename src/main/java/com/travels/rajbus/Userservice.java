package com.travels.rajbus;

import java.util.List;

public interface Userservice {
	
	public User createUser(User user);
	
	public User updateUser(User user);
	
	public List<User> getAllUsers();
	
	public void deleteUser(long id);


}

