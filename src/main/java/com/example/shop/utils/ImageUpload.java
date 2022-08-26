package com.example.shop.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUpload {
    String current = System.getProperty("user.dir") + "/src/main/resources/static/image";
    private final String UPLOAD_FOLDER = current;

    public boolean uploadImage(MultipartFile multipartFile){
        boolean isUpload = false;
        try{
            Files.copy(multipartFile.getInputStream(), Paths.get(UPLOAD_FOLDER + File.separator, multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
            isUpload = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return isUpload;
    }


    public boolean checkExisted(MultipartFile multipartFile){
        boolean isExisted = false;
        try{
            File file = new File(UPLOAD_FOLDER + "\\" + multipartFile.getOriginalFilename());
            isExisted = file.exists();
        }catch(Exception e){
            e.printStackTrace();
        }
        return isExisted;
    }

}
