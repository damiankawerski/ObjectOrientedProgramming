package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Controller
public class ImageFormController {
    @GetMapping("/imageform")
    public String imageForm() {
        return "index";
    }

    @PostMapping("/imageform/upload")
    public String uploadImage(@RequestParam("image") MultipartFile file,
                              @RequestParam("brightness") int brightness,
                              Model model) throws IOException {
        byte[] imageBytes = file.getBytes();
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage img = ImageIO.read(bis);

        // Process image to increase brightness
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.setImage(img);
        imageProcessor.increaseBrightness(brightness);

        // Convert processed image to Base64
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageProcessor.getImage(), "jpg", baos);
        baos.flush();
        byte[] processedImageBytes = baos.toByteArray();
        String encodedImage = Base64.getEncoder().encodeToString(processedImageBytes);

        model.addAttribute("image", encodedImage);
        return "image";
    }
}
