package com.example.demo;

import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
public class ImageController {

    @PostMapping("/changedPicture64")
    public String changedPicture64(@RequestBody String picture64, @RequestParam int factor) throws IOException {
        // Decode
        byte[] imageBytes = Base64.getDecoder().decode(picture64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage img = ImageIO.read(bis);

        // Process image
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.setImage(img);
        imageProcessor.increaseBrightness(factor);

        // Encode
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(imageProcessor.getImage(), "jpg", baos);
        baos.flush();
        imageBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    @PostMapping("/changedPicture")
    public BufferedImage changedPicture(@RequestBody String picture64, @RequestParam int factor) throws IOException {
        // Decode
        byte[] imageBytes = Base64.getDecoder().decode(picture64);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage img = ImageIO.read(bis);

        // Process image
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.setImage(img);
        imageProcessor.increaseBrightness(factor);

        return imageProcessor.getImage();
    }
}
