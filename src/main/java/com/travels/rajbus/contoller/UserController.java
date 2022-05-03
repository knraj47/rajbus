package com.travels.rajbus.controller;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import com.travels.rajbus.entity.User;
import com.travels.rajbus.entity.Utility;
import com.travels.rajbus.model.ServiceStatus;
import com.travels.rajbus.repository.UserRepository;
import com.travels.rajbus.service.EmailSenderServiceImpl;
import com.travels.rajbus.service.UserNotFoundException;
import com.travels.rajbus.service.Userservice;
import com.travels.rajbus.service.Userserviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.bytebuddy.utility.RandomString;

@RestController
@CrossOrigin("*")
public class UserController {
	private static final Logger Logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private EmailSenderServiceImpl emailSenderServiceImpl;
@Autowired
private Userserviceimpl userServiceimpl;
	@Autowired
	private Userservice userService;

	Map<String, String> userOtp = new HashMap<String, String>();

	@PostMapping("/forget_password")
	public String ForgetpasswordForm(HttpServletRequest request) {
		String email = request.getParameter("email");
		String password = RandomString.make(45);

		Logger.info("Email : " + email);
		Logger.info("Password: " + password);

		try {
			userService.resetPassword(password, email);

			String resetPasswordLink = Utility.getSiteURL(request) + "/reset_password?password=" + password;
			sendmail(email, resetPasswordLink);
			Logger.info(resetPasswordLink);

		} catch (UserNotFoundException e) {
//			 model.addAttribute("error", e.getMessage()){
//			 catch (UnsupportedEncodingException | MessagingException e) {
//				    model.addAttribute("error", "Error while sending email");
			e.printStackTrace();
		}
		return "ForgotpasswordForm";

	}

	@GetMapping("/reset_password")
	public String resetPassword(@RequestParam String password, String email) {
		try {
			userService.resetPassword(password, email);
		} catch (UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "password has been reset";

	}
	

	@PutMapping("/updatePassword")
	public ResponseEntity<Object> updatePassword( @RequestBody User user) {
		
		return ResponseEntity.ok().body(userServiceimpl.updatePassword(user));
	}

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
	public ServiceStatus sendmail(@RequestParam("email") String email, String restpasswordLink) {
		ServiceStatus serviceStatus = new ServiceStatus();
		String otpStr = new DecimalFormat("000000").format(new Random().nextInt(999999));

		userOtp.put(email, otpStr);
		// System.out.println(userOtp.get(email));
		// String otpnum=otpStr;
		try {
			String toEmail = email;
			String text = restpasswordLink;
			String subject = "OTP for Rajbus login  :" + otpStr + " \n enjoy the journey";
			emailSenderServiceImpl.sendSimpleEmail(email, subject, text);
			serviceStatus.setStatus("SUCCESS");
			serviceStatus.setMesaage("email sent successfully");
			serviceStatus.setResult("check your Email for OTP & Link");

		} catch (Exception e) {
			e.printStackTrace();
			serviceStatus.setStatus("FAILURE");
			serviceStatus.setMesaage("Failed to send mail");
		}
		return serviceStatus;
	}

	@PostMapping(path = "/verifyOtp")
	public ServiceStatus verifyOtp(@RequestParam String email, String otp) {
		ServiceStatus serviceStatus = new ServiceStatus();
		Logger.info(userOtp.get(email));
		String otpSt = userOtp.get(email);

		try {
			if (email != null && otp != null && !email.isEmpty() && !otp.isEmpty()) {
//				   if (!userOtp.isEmpty() && userOtp.containsKey(email)) {
				if (otpSt.equals(otp)) {
					serviceStatus.setMesaage("Valid Otp");
					serviceStatus.setResult("sucesses");
					serviceStatus.setStatus("logged into Rajbus");
				} else {
					serviceStatus.setMesaage("Invalid Otp");
					serviceStatus.setResult("Failure");
					serviceStatus.setStatus("Login Failed");
				}

			} else {
				serviceStatus.setMesaage("Invalid Otp");
				serviceStatus.setStatus("Failure");
			}
		} catch (NumberFormatException e) {
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
