package com.travels.rajbus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import com.travels.rajbus.entity.User;
import com.travels.rajbus.model.ServiceStatus;
import com.travels.rajbus.repository.UserRepository;

@Service

public class Userserviceimpl  implements Userservice{

	@Autowired
	private UserRepository userRepository;
	
	
	public void resetPassword(String newPassword, String email) throws UserNotFoundException {
		User user = userRepository.findByemail(email);
		if (user != null) {
			user.setPassword(newPassword);
			userRepository.save(user);
		} else {
			throw new UserNotFoundException("Could not find any user with the email " + email);
			
		}
		
	}
	public User getByResetPassword(String newPassword) {
		return userRepository.findByPassword(newPassword);		
	}
	public void updatePassword(User user, String newPassword) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodePassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodePassword);
		
		user.setPassword(newPassword);
		userRepository.save(user);
	}
	@Override
	public User createUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUsers() {
		// TODO Auto-generated method stub
		return (List<User>) userRepository.findAll();
	}

	@Override
	public void deleteUser(long id) {
		// TODO Auto-generated method stub
		userRepository.deleteById(id);
	}

//	@Override
//	public User getUserByEmail(String email) {
//		// TODO Auto-generated method stub
////		return userRepository.findByEmail(email);
//	}
@Override
	public User login(String userName, String Password) {
		User user = userRepository.findByemail(userName);
		return user;
	}

	public ServiceStatus validator(String userName,String password) {
		
	    ServiceStatus serviceStatus = new ServiceStatus();
	    
	    try {
	    	if (userName != null) {
			    User user=userRepository.findByemail(userName);
			    
			    if (user !=null) {
			    	String Password =user.getPassword();
			    	if(Password.equals(password)) {
			    		serviceStatus.setResult("succesfully");
			    		serviceStatus.setStatus("rajbus sucess");
			    		serviceStatus.setMesaage("rajbus Login Successfully");
			    		return serviceStatus;
			    
			    	}else {
		    		    serviceStatus.setStatus("failure");
		    		    serviceStatus.setMesaage("invalid password");
		    			return serviceStatus;
		
			    
			    	}
			    }else {
//			    	logic for if user object is empty
	    		    serviceStatus.setStatus("failure");
	    		    serviceStatus.setMesaage("user not registered");
	    			return serviceStatus;
			    }
	    	}
	    }
			    catch (Exception e) {
			
	    		e.printStackTrace();
		}
		return
	    
	     serviceStatus;
	  
	}

	

}