package com.example.shop.service;

import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;

import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String imgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile multipartFile) throws Exception{
        String oriName = multipartFile.getOriginalFilename();
        String imgName="";
        String imgUrl="";

        //파일 업로드 부분
        if( !StringUtils.isEmpty(oriName)){ //사용자가 업로드한 원본 이미지 이름
            imgName = fileService.uploadFile(imgLocation,oriName,multipartFile.getBytes());
            imgUrl = "/images/item/"+imgName;
        }
        itemImg.setImgUrl(imgUrl);
        itemImg.setImgName(imgName);
        itemImg.setOriImgName(oriName);
        //데이터베이스에 저장할 때는 entity객체로 저장한다.
        itemImgRepository.save(itemImg);
    }


}
