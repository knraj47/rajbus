package com.travels.rajbus.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
//import com.travels.rajbus.service.OtpVerification;
import com.travels.rajbus.service.Userservice;


@RestController
@CrossOrigin("*")
public class UserController {
		
	Map<String,String> userOtp = new HashMap<String,String>();
	
	@Autowired
    private EmailSenderServiceImpl emailSenderServiceImpl;

	@Autowired
	private Userservice userService;
	
//	@Autowired
//	private OtpVerification otpVerification;
	
	@PostMapping("/createUser")
	public User createUser(@RequestBody User user) {
			userService.createUser(user);
		return user;
		
	}
	
	@PostMapping("/login")
	public ServiceStatus validateUser(@RequestParam String username, @RequestParam String password) {
		ServiceStatus serviceStatus = new ServiceStatus();
		try {
//			   if (username != null && password != null) {
//				   serviceStatus.setStatus("failure");
//				   serviceStatus.setMesaage("username cannot be empty");
//			   } else {
//				   serviceStatus.setStatus("failure");
//				   serviceStatus.setMesaage("password cannot be empty");
//			   }
			serviceStatus = userService.validator(username, password);
//			serviceStatus.setResult("succesfully");
    		serviceStatus.setStatus("rajbus sucess");
    		serviceStatus.setMesaage("rajbus Login Successfully");
    		serviceStatus.setResult(" Successfully");
    		return serviceStatus;
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
	//get all students
	@GetMapping("/getAllUsers")
	public List<User> findAllStudents() {
		
		return (List<User>) userService.getAllUsers();
		
	}
	//updating student details
	@PutMapping("/updateUser")
	public User updateStudent(@RequestBody User user) {
		
		userService.updateUser(user);
	    return user;
	}
	//delete student byId
	@DeleteMapping("/deleteUser")
	public String deleteUSer(@RequestParam Long id) {
		userService.deleteUser(id);
		return "User has been deleted";
		
	}

}
