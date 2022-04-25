package com.travels.rajbus.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
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
import com.travels.rajbus.service.UserNotFoundException;
import com.travels.rajbus.service.Userservice;
import com.travels.rajbus.service.Userserviceimpl;


@RestController
@CrossOrigin("*")
public class UserController {

	@Autowired
	private EmailSenderServiceImpl emailSenderServiceImpl;

	@Autowired
	private Userservice userService;
	
	@Autowired
	private Userserviceimpl userserviceimpl;
	
	Map<String,String> userOtp = new HashMap<String,String>();

	private String newPassword;


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
		String   otpStr= new DecimalFormat("000000").format(new Random().nextInt(999999));
	
		userOtp.put(email,otpStr);
		//System.out.println(userOtp.get(email));
		//String otpnum=otpStr;
		try {
			String toEmail=email;
			String text="Rajbus verification code";
			String subject="OTP for Rajbus login  :"+otpStr+" \n enjoy the journey";
			emailSenderServiceImpl.sendSimpleEmail(email,subject,text);
			serviceStatus.setStatus("SUCCESS");
			serviceStatus.setMesaage("email sent successfully");
			serviceStatus.setResult("check your Email for OTP");
			
			
		} catch (Exception e) {
			e.printStackTrace();
			serviceStatus.setStatus("FAILURE");
			serviceStatus.setMesaage("Failed to send mail");
		}
		return serviceStatus;
	}

	@PostMapping(path = "/verifyOtp")
	public ServiceStatus verifyOtp(@RequestParam String email,String otp) {
		ServiceStatus serviceStatus = new ServiceStatus();
		System.out.println(userOtp.get(email));
		String otpSt=userOtp.get(email);
		
		try {
			   if (email!= null && otp != null && !email.isEmpty() && !otp.isEmpty())  {
//				   if (!userOtp.isEmpty() && userOtp.containsKey(email)) {
						if(otpSt.equals(otp)) {
							serviceStatus.setMesaage("Valid Otp");
							serviceStatus.setResult("sucesses");
							serviceStatus.setStatus("logged into Rajbus");
						}else {
							serviceStatus.setMesaage("Invalid Otp");
							serviceStatus.setResult("Failure");
							serviceStatus.setStatus("Login Failed");
						}
//					}
				   		//serviceStatus=otpVerification.verifyOTP(email, otp);
//			 serviceStatus.setStatus("Sucesses");
//				serviceStatus.setMesaage("otp entered sucessfully");
			// String serverOtp = otp;
			//if(email !=null && !(otp==null)) {
			//boolean s=otp.equals(serverOtp);
		//	System.out.println(s);
				
//			if(otp.equals(0)){
//				String  serverOtp = otpVerification.getOtp(email);
//				if(otp == serverOtp){
//				otpVerification.clearOTP(email);
//				}
//			
			   }else {
				serviceStatus.setMesaage("Invalid Otp");
				serviceStatus.setStatus("Failure");		
			}
		}
		 catch (NumberFormatException e) {
			e.printStackTrace();
			serviceStatus.setStatus("Failure");
			serviceStatus.setMesaage(e.getMessage());
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
	
//	
//	@GetMapping("/reset_password")
//	public String showResetPasswordForm(@RequestParam(value = "token") String token, Model model) {
//	    User user = userService.getByResetPasswordToken(token);
//	    
	
//	@PostMapping("/reset_password")
//	public User processResetPassword() {
//		return userserviceimpl.getByResetPassword(newPassword);
//	}
	
//	@GetMapping("/reset_password")
//	public User processResetPa() {
//		return null;
//	}
	@GetMapping("/reset_password")
    public String resetPassword(@RequestParam String newPassword, String email) {
		try {
			userService.resetPassword(newPassword, email);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "password has been reset";
 
    }
	
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
	public String deleteUser(@RequestParam Long id) {
		userService.deleteUser(id);
		return "User has been deleted";

	}

}
