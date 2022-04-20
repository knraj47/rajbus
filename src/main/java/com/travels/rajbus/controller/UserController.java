package com.travels.rajbus.controller;

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

import com.travels.rajbus.entity.User;
import com.travels.rajbus.model.ServiceStatus;
import com.travels.rajbus.service.EmailSenderServiceImpl;
import com.travels.rajbus.service.Userservice;

@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private EmailSenderServiceImpl emailSenderServiceImpl;

	@Autowired
	private Userservice userService;

	@PostMapping("/createUser")
	public User createUser(@RequestBody User user) {
		userService.createUser(user);
		return user;

	}

	@GetMapping("/login")
	public ServiceStatus validateUser(@RequestParam String userName, @RequestParam String password) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {

			if (userName != null && !userName.isEmpty()) {
				if (password != null && !password.isEmpty()) {
					serviceStatus = userService.validator(userName, password);
				} else {
					serviceStatus.setStatus("failure");
					serviceStatus.setMesaage("password cannot be empty or null");
					serviceStatus.setStatus("failure");
					serviceStatus.setMesaage("username cannot be empty or null");
				}

			}

//	     	if (username != null && password != null) {
//				serviceStatus.setStatus("sucess");
//			} else {
//				serviceStatus.setStatus("failure");
//				serviceStatus.setMessage("password cannot be empty");
//			}
//		
		} catch (Exception e) {
			e.printStackTrace();
			serviceStatus.setStatus("failure");
			serviceStatus.setMesaage(e.getMessage());
		}
		return serviceStatus;
	}

	@GetMapping(value = "/sendmail")
	public ServiceStatus sendmail(@RequestParam("email") String email) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			emailSenderServiceImpl.sendSimpleEmail(email);
			serviceStatus.setStatus("SUCCESS");
			serviceStatus.setMesaage("email sent successfully");

		} catch (Exception e) {
			e.printStackTrace();
			serviceStatus.setStatus("FAILURE");
			serviceStatus.setMesaage("Failed to send mail");
		}
		return serviceStatus;
	}

//	@GetMapping("/getUserByName")
//	public List<User> findUserByName(@RequestParam String userName) {
//
//		return (List<User>) userServiceImpl.findUserByName(userName);
//	}
//	@GetMapping("/getUserById")
//	public List<User> findUserById(@RequestParam long id) {
//		
//		return (List<User>) userServiceImpl.findUserById(id);
//		
//	}
	// get all students
	@GetMapping("/getAllUsers")
	public List<User> findAllUsers() {

		return (List<User>) userService.getAllUsers();

	}

	@PutMapping("/updateUser")
	public User updateUser(@RequestBody User user) {

		userService.updateUser(user);
		return user;
	}

	@DeleteMapping("/deleteUser")
	public String deleteUSer(@RequestParam Long id) {
		userService.deleteUser(id);
		return "User has been deleted";

	}

}
