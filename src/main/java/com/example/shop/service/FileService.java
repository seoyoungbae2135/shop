package com.example.shop.service;

import lombok.extern.java.Log;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.UUID;
//20240223-8
@Service
@Log
public class FileService {  // 그림
    public String uploadFile(String uploadPath, String originalFileName,
                             byte[] fileData) throws Exception{
        UUID uuid = UUID.randomUUID();
        String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savefName = uuid.toString()+ext; /*새로만들어진 이름에 확장자 붙이기*/
        String fileUploadUrl = uploadPath+"/"+savefName;
        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData);
        fos.close();
        return  savefName;
    }


    public  void  deleteFile(String filePath) throws Exception{

    }
}
