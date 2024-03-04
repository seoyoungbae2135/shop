package com.example.shop.dto;
//20240229-1
import com.example.shop.constant.OrderStatus;
import com.example.shop.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {
    private Long orderId;
    private String orderDate;
    private OrderStatus orderStatus; //주문상태

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public OrderHistDto(Order order){
        this.orderId=order.getId();
        this.orderDate=order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm"));
        this.orderStatus=order.getOrderStatus();
    }

    public void addOrderItemDto(OrderItemDto orderItemDto){ //주문상품목록
        orderItemDtoList.add(orderItemDto);
    }

}
