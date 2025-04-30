package com.bway.springdemo.controller;


import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bway.springdemo.model.User;
import com.bway.springdemo.repository.UserRepo;
import com.bway.springdemo.service.EventImageService;
import com.bway.springdemo.service.UserService;
import com.bway.springdemo.utils.UserPdf;


@Controller
@RequestMapping("/admin")
public class AdminController {
	    private static String broadcastMessage = "";
	
		@Autowired
	    private UserService userService;
		@Autowired
		private  UserRepo userRepo;
		@Autowired
		private EventImageService eventImageService;
		
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
		
		
		@GetMapping("/memberAdd")
		public String getUserAddForm(Model model) {
			model.addAttribute("user", new User());
			return"admin/userAdd";
		}
		
		@PostMapping("/memberAdd")
			public String addMember(@ModelAttribute User user,Model model) {
				userService.addUser(user);
				return"redirect:/admin/ulist";
		}
		
		@GetMapping("/broadcast")
		public String getEventForm() {
			return"admin/addEventForm";
		}
	
		 @PostMapping("/save-broadcast")
		    public String saveBroadcast(@RequestParam("message") String message) {
		        broadcastMessage = message;
		        return "redirect:/admin/event";
		 }
		 
		 public static String getBroadcastMessage() {
		        return broadcastMessage;
		 }
		 
		 @GetMapping("/search")
		 public String searchUsers(@RequestParam("query") String query, Model model) {
		     List<User> results = userService.searchUsers(query);
		     if (results.isEmpty()) {
		         model.addAttribute("notFound", true);
		     } else {
		         model.addAttribute("user", results);
		     }
		     return "admin/userList";
		 }
		 
		 @GetMapping("/uploadImage")
		    public String showUploadForm(Model model) {
		        return "admin/Image";
		    }

	    @PostMapping("/uploadImage")
	    public String handleImageUpload(@RequestParam("title") String title,
	                                    @RequestParam("image") MultipartFile image,
	                                    RedirectAttributes redirectAttributes) {
	        try {
	            eventImageService.saveImage(title, image);
	            redirectAttributes.addFlashAttribute("message", "Image uploaded successfully!");
	        } catch (IOException e) {
	            redirectAttributes.addFlashAttribute("error", "Failed to upload image.");
	        }
	        return "redirect:/admin/uploadImage";
	    }

 }
