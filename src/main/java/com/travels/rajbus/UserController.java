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
	private Userservice userservice;

	@PostMapping("/saveUser")
	public ServiceStatus save(@RequestBody User user) {
		ServiceStatus serviceStatus = new ServiceStatus();
			if (user.getEmail() == null || user.getEmail().isEmpty()) {
				serviceStatus.setStatus("failure");
				serviceStatus.setMesaage("Email cannot be empty");
			} else if (user.getPassword() == null || user.getPassword().isEmpty()) {
				serviceStatus.setStatus("failure");
				serviceStatus.setMesaage("Password cannot be empty");
			}
		return serviceStatus;
	}
	@PostMapping("/login")
	public ServiceStatus validateUser(@RequestParam String username, @RequestParam String password) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
			serviceStatus = userservice.validator(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			serviceStatus.setStatus("failure");
			serviceStatus.setMesaage(e.getMessage());
		}
		return serviceStatus;
	}


//	@EventListener(ApplicationReadyEvent.class)
//	//@PostMapping(value="/requestOtp/{Email}")
//	public void sendMail() throws MailException{
//		String otp= new DecimalFormat("0000").format(new Random().nextInt(9999));
//
//		emailSenderServiceImpl.sendSimpleEmail("mahendramahi414@gmail.com",
//				"Hi ,\n"
//				+ "welcome to ****Rajbus****"
//				+ "It's your OTP to login  "+otp+" \n"
//						+ "enjoy the traveeling with Rajbus",
//				"This email has attachment");
//		System.out.println("Email delivered with OTP");
//	}
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

			
		
		@PostMapping("/createUser")
	public User createUser(@RequestBody User user) {
			userservice.createUser(user);
		return user;
		
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
	//get all students
	@GetMapping("/getAllUsers")
	public List<User> findAllStudents() {
		
		return (List<User>) userservice.getAllUsers();
		
	}
	//updating student details
	@PutMapping("/updateUser")
	public User updateStudent(@RequestBody User user) {
		
		userservice.updateUser(user);
	    return user;
	}
	//delete student byId
	@DeleteMapping("/deleteUser")
	public String deleteUSer(@RequestParam Long id) {
		userservice.deleteUser(id);
		return "User has been deleted";
		
	}
	
}
