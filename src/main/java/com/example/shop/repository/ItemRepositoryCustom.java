package com.example.shop.repository;
//20240227
import com.example.shop.dto.ItemSearchDto;
import com.example.shop.dto.MainItemDto;
import com.example.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<MainItemDto> getMainItem(ItemSearchDto itemSearchDto, Pageable pageable);
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
