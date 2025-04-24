package com.bway.springdemo.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepo;
import com.bway.springdemo.service.UserService;

@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public void addUser(User u) {
        userRepo.save(u);
    }

    @Override
    public void DeleteUser(long id) {
        userRepo.deleteById(id);  
    }

    @Override
    public void UpdateUser(User u) {
        userRepo.save(u);
    }

    @Override
    public User getUserById(long id) {
        return userRepo.findById(id).orElse(null); // Handle optional to avoid NoSuchElementException
    }
    

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    @Override
	public void userSignup(User user) {
		userRepo.save(user);
		
	}

	@Override
	public User userLogin(String un, String pass) {
		
		return userRepo.findByUsernameAndPassword(un, pass);
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}
