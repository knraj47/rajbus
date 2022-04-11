package com.travels.rajbus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class Usercontroller {
	
	@Autowired
	private Userserviceimpl  userServiceImpl;
	
	@PostMapping("/createUser")
	public User createUser(@RequestBody User user) {
		userServiceImpl.createUser(user);
		return user;
		
	}
	@GetMapping("/getAllUsers")
	public List<User> findAllStudents() {
		
		return (List<User>) userServiceImpl.getAllUsers();
		
	}
	@PutMapping("/updateUser")
	public User updateStudent(@RequestBody User user) {
		
		userServiceImpl.updateUser(user);
	    return user;
	}
	@DeleteMapping("/deleteUser")
	public String deleteUSer(@RequestParam Long id) {
		userServiceImpl.deleteUser(id);
		return "User has been deleted";
		
	}
}

