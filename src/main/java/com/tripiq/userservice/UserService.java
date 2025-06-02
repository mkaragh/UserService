package com.tripiq.userservice;

import java.util.List;

public interface UserService {
	
	User saveUser(User user);
	List<User> getUsers();
	User updateUser(String id, User user);
	void deleteUser(String id);
	

}
