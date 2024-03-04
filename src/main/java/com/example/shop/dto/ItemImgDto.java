package com.example.shop.dto;

import com.example.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter   @Setter
public class ItemImgDto {
    private Long id;
    private String imgName; //이미지 이름
    private String imgUrl;  // 이미지 경로
    private String oriImgName;  // 원본 이미지 이름
    private String repImgYn;  //대표 이미지 설정

    private static ModelMapper mapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){
        return mapper.map(itemImg, ItemImgDto.class);
    }
}