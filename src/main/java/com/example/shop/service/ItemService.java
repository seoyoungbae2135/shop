package com.example.shop.service;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import com.example.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> multipartFileList) throws Exception{
        //상품정보를 데이터베이스에 저장
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 업로드및 데이터베이스저장
        for(int i=0; i<multipartFileList.size(); i++){
            ItemImg itemImg=new ItemImg();
            itemImg.setItem(item); //이미지 상품번호(item_id)가 같이 저장된다.

            if(i==0) // 대표이미지 설정 - 무조건 첫번째 이미지를 대표이미지로 사용한다.
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");
            itemImgService.saveItemImg(itemImg, multipartFileList.get(0));
        }
        return item.getId();
    }


}
