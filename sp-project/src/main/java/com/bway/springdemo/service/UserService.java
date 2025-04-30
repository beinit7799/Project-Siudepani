package com.bway.springdemo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bway.springdemo.model.User;
@Service
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
	public List<User> searchUsers(String query);
	
}
