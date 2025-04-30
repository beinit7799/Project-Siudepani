package com.bway.springdemo.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bway.springdemo.model.EventImage;
import com.bway.springdemo.repository.EventImageRepo;
import com.bway.springdemo.service.EventImageService;
@Service
public class EventImageServiceImpl implements EventImageService  {
	
    private static final String UPLOAD_DIR = "src/main/resources/static/images/";

    @Autowired
    private EventImageRepo repository;

    @Override
    public void saveImage(String title, MultipartFile file) throws IOException {
        // Generate unique filename
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // Create directory if not exists
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Save the file to the directory
        Path filePath = uploadPath.resolve(filename);
        Files.write(filePath, file.getBytes());

        // Save metadata in database
        EventImage image = new EventImage();
        image.setTitle(title);
        image.setFileName(filename);
        image.setUploadTime(LocalDateTime.now());

        repository.save(image);
    }

    @Override
    public List<EventImage> getAllImages() {
        return repository.findAll();
    }

}
