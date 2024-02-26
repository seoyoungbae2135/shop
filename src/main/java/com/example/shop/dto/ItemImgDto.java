package com.example.shop.dto;

import com.example.shop.entity.ItemImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class ItemImgDto {
    private Long id;
    private String imgName;
    private String imgUrl;
    private String oriImgName;
    private String repImgYn;

    private static ModelMapper mapper = new ModelMapper();

    public static ItemImgDto of(ItemImg itemImg){
        return mapper.map(itemImg, ItemImgDto.class);
    }

}
