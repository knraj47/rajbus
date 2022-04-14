package com.travels.rajbus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.travels.rajbus.entity.User;
import com.travels.rajbus.model.ServiceStatus;
import com.travels.rajbus.repository.UserRepository;

@Service
public class Userserviceimpl  implements Userservice{
	@Autowired
	private UserRepository userRepository;
	
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
	@Override
	public User getUserByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}
//	@Override
//	public User getUserByEmail(String email) {
//		return userRepository.findByEmail(email);
//	}
	@Override
	public ServiceStatus validator(String email,String password) {
		
		    ServiceStatus serviceStatus = new ServiceStatus();
		    if (email != null) {
		    	    User user = userRepository.findByEmail(email);
		    	    if (user !=null) {
		    	    	String userPassword =user.getPassword();
		    	    	if (userPassword !=null) {
		    	    		serviceStatus.setResult("succesfully");
		    	    		serviceStatus.setStatus("rajbus sucess");
		    	    		serviceStatus.setMesaage("rajbus Login Successfully");
		    	    		return serviceStatus;
		    	    	} else {
		    	    		   serviceStatus.setStatus("rajbus failure");
		    	    		   serviceStatus.setMesaage("Ïnvalid Credentials");
		    	    		   return serviceStatus;
		    	    	} 
		    	    	} 
		    	    
		    }
			return serviceStatus;
		    	    	
		    	    	}




		    	    	
		    	    }
		    
		    
	



