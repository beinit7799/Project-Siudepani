package com.bway.springdemo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bway.springdemo.model.User;
import com.bway.springdemo.service.UserService;
import com.bway.springdemo.utils.UserPdf;


@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
    private UserService userService;
	
	@GetMapping("/adminPanel")
	public String getAdminPanel() {
		return "admin/AdminPanel";
	}
	
	@GetMapping("/ulist")
	public String getAll(Model model) {
		model.addAttribute("user",userService.getAllUsers());
		return "admin/userList";
	}
	
	@GetMapping("/edit/{id}")
	public String editUserForm(@PathVariable Long id, Model model) {
	    model.addAttribute("eUser", userService.getUserById(id));  
	    return "admin/userEditForm";
	}

	
	@PostMapping("/update")
	public String updateUser(@ModelAttribute User user) {
		userService.UpdateUser(user);
		return "redirect:/admin/ulist";	
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable long id) {
		userService.DeleteUser(id);
		return "redirect:/admin/ulist";
	}
	
	@GetMapping("/pdf")
	public ModelAndView exportUserPdf() {
	    ModelAndView mv = new ModelAndView();
	    mv.addObject("user", userService.getAllUsers());
	    mv.setView(new UserPdf());
	    return mv;
	}




}
