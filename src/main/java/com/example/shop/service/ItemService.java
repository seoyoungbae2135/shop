package com.example.shop.service;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.dto.ItemImgDto;
import com.example.shop.dto.ItemSearchDto;
import com.example.shop.dto.MainItemDto;
import com.example.shop.entity.Item;
import com.example.shop.entity.ItemImg;
import com.example.shop.repository.ItemImgRepository;
import com.example.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImgRepository itemImgRepository;
    private final ItemImgService itemImgService;

    //아이템 상세보기
    public ItemFormDto getItemDtl(Long itemId){
        List<ItemImg> itemImgs = itemImgRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for(ItemImg itemImg : itemImgs){
            ItemImgDto itemImgDto = ItemImgDto.of(itemImg);
            itemImgDtoList.add(itemImgDto);
        }
        Item item = itemRepository.findById(itemId).get();
        ItemFormDto itemFormDto = ItemFormDto.of(item);
        itemFormDto.setItemImgDtoList(itemImgDtoList);
        return itemFormDto;
    }

    //메인페이지에 출력할 상품들 가져오기( 페이징 - 5개씩 보여주기 )
    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItem(ItemSearchDto itemSearchDto, Pageable pageable){
        return itemRepository.getMainItem(itemSearchDto,pageable);
    }
    // 상품의 내용과 이미지 저장
    public Long saveItem(ItemFormDto itemFormDto,
                         List<MultipartFile> multipartFileList) throws Exception{
        //상품 정보 데이터베이스에 저장
        Item item = itemFormDto.createItem();
        itemRepository.save(item);

        //이미지 업로드및 데이터베이스 저장
        for(int i=0; i<multipartFileList.size(); i++){
            ItemImg itemImg=new ItemImg();
            itemImg.setItem(item); // 이미지에 상품 번호(item_id)가 같이 저장된다.

            if(i==0)  // 대표이미지 설정 - 무조건 첫번째 이미지를 대표이지미로 사용한다.
                itemImg.setRepImgYn("Y");
            else
                itemImg.setRepImgYn("N");
            // 업로드및 데이터베이스저장 하기위해 service클래스의 메서드 호출
            itemImgService.saveItemImg(itemImg, multipartFileList.get(i) );
        }
        return item.getId();
    }
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) { //20240229-6 me 추가
        // itemRepository에서 관리자용 페이지를 가져오는 로직 추가 20240229-6 me
        return itemRepository.findByItemSellStatus(itemSearchDto, pageable);
    }
}