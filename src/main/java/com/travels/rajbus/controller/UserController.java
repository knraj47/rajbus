package com.travels.rajbus.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travels.rajbus.model.ServiceStatus;
import com.travels.rajbus.model.User;
import com.travels.rajbus.service.EmailSenderServiceImpl;
import com.travels.rajbus.service.UserServiceImpl;

@RestController
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
    private EmailSenderServiceImpl emailSenderServiceImpl;
    
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
		userServiceImpl.createUser(user);
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
		
		return (List<User>) userServiceImpl.getAllUsers();
		
	}
	//updating student details
	@PutMapping("/updateUser")
	public User updateStudent(@RequestBody User user) {
		
		userServiceImpl.updateUser(user);
	    return user;
	}
	//delete student byId
	@DeleteMapping("/deleteUser")
	public String deleteUSer(@RequestParam Long id) {
		userServiceImpl.deleteUser(id);
		return "User has been deleted";
		
	}
}
