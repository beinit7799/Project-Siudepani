package com.bway.springdemo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bway.springdemo.model.EventImage;


public interface EventImageService {
	  void saveImage(String title, MultipartFile file) throws IOException;
	    List<EventImage> getAllImages();
}
