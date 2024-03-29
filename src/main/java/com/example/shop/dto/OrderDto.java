package com.example.shop.dto;
//20240229-1
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class OrderDto {
    @NotNull(message = "상품 아이디는 필수입력값입니다.")
    private Long itemId;

    @Min(value = 1,message = "최소 주문수량은 한개 입니다.")
    @Max(value = 999, message = "최대 주문수량은 999개 입니다.")
    private int count;


}
