package com.example.shop.control;

import com.example.shop.dto.ItemFormDto;
import com.example.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {
    //상품등록, 상품수정, 상품삭제, 상품작성폼, 상품상세, 상품관리
    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemForm(Model model){
        model.addAttribute("itemFormDto",new ItemFormDto());
        return "item/itemForm";
    }
    @PostMapping("/admin/item/new")
    public  String itemNew(@Valid ItemFormDto itemFormDto,
                           BindingResult bindingResult, Model model,
                           @RequestParam("itemImgFile") List<MultipartFile> multipartFileList){
        if(bindingResult.hasErrors()){ //유효성 검사에서 에러 발생
            return "item/itemForm";
        }
        if( multipartFileList.get(0).isEmpty() && itemFormDto.getId()==null){
            //이미지가 한장도 선택되지 않았다면, 무조건 1장이상은 선택해야된다.
            model.addAttribute("errorMassage", "첫번째 상품 이미지는 필수 등록입니다.");
        }
        try{
            itemService.saveItem(itemFormDto, multipartFileList);
        }catch(Exception e){
            model.addAttribute("errorMassage", "상품 등록중 에러가 발생하였습니다.");
            return "item/itemForm";
        }

        return "redirect:/";
    }

}
