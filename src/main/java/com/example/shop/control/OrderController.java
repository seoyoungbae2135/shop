package com.example.shop.control;
//20240228-7, 20240229-2
import com.example.shop.dto.OrderDto;
import com.example.shop.dto.OrderHistDto;
import com.example.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    @PostMapping("/order")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDto orderDto,
                                              BindingResult bindingResult, Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder sb = new StringBuilder(); //json 응답은 문자열로 보내야 해서
            List<FieldError> fieldErrors = bindingResult.getFieldErrors(); //유효성 검사오류내용

            for( FieldError fieldError : fieldErrors){
                sb.append(fieldError.getDefaultMessage()); //json응답을 보내기위해 StringBuilder에 저장
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long orderId;
        try{
            orderId = orderService.order(orderDto, email);
        }catch (Exception e){
            System.out.println("상품을 구매하기 위한 데이터베이스저장 실패");
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    @GetMapping(value = {"/order", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0 , 10);
        Page<OrderHistDto> orderHistDtos = orderService.getOrderList(principal.getName(),pageable);

        model.addAttribute("orders", orderHistDtos);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);

        return "order/OrderHist";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancel(@PathVariable("orderId")Long orderId,
                                               Principal principal){
        orderService.cancelOrder(orderId);
        return  new ResponseEntity<Long>( orderId, HttpStatus.OK);
    }

}
