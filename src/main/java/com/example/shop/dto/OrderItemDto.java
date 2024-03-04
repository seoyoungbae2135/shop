package com.example.shop.dto;
//20240229-1
import com.example.shop.entity.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    private String itemNm;
    private int count;
    private int orderPrice;
    private String imgUrl;

    public OrderItemDto(OrderItem orderItem, String imgUrl){
        this.count=orderItem.getCount();
        this.itemNm=orderItem.getItem().getItemNm();
        this.imgUrl=imgUrl;
        this.orderPrice=orderItem.getOrderPrice();
    }

}
