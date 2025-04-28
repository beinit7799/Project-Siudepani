package com.bway.springdemo.service;

import java.util.List;

import com.bway.springdemo.model.User;

public interface UserService {

	void addUser(User u);
	void DeleteUser(long id);
	void UpdateUser(User u);
    void saveUser(User user);

	
	void userSignup(User user);
	User userLogin(String un, String pass);
	
	User getUserByUsername(String username);
	User getUserById(long id);
	User getUserByEmail(String email);
	List<User> getAllUsers();
	
}
