package com.tripiq.userservice;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.tripiq.userservice.kafka.MessageProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	MessageProducer messageProducer;
	
	//@Autowired
	//UserServiceConfig userServiceConfig;

	
	private  final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	
	public User saveUser(User user){
		
		User userFromDB = userRepository.save(user);
		logger.info("The user data saved");
		ObjectMapper mapper = new ObjectMapper();
		try {

			messageProducer.sendMessage("user", mapper.writeValueAsString(user));
			logger.info("The user data is sent to kafka topic");
			//logger.info(" The message from consul"+userServiceConfig.getDataMessage());

		}catch(Exception ex) {
			
			System.out.println(ex.getMessage());
		}
		
		return userFromDB;
		
	}
	
	public User updateUser(String id, User user) {
		
		Optional<User> userData = userRepository.findById(id);

	    if (userData.isPresent()) {
	    	
	    	  User userForUpdation = userData.get();
	    	  userForUpdation.setName(user.getName());
	    	  userForUpdation.setEmail(user.getEmail());
	    	  userForUpdation.setCity(user.getCity());
	         
	    	  user = userRepository.save(userForUpdation);
	    }
		
	    return user;
	}
	public void deleteUser(String id) {
		
		userRepository.deleteById(id);
		
	}
	
	public User getUser(String id) {
		
		Optional<User> userData = userRepository.findById(id);
		return userData.get();
	}


	public List<User> getUsers() {
		
		return userRepository.findAll();
		
	}

}
