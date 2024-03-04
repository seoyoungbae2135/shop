package com.example.shop.entity;
//20240228
import com.example.shop.constant.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
@Getter
@Setter
@Table(name="orders") //order은 예약어 이므로 추가 20240229-2
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private  Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column
    private LocalDateTime orderDate; //주문일
    @Enumerated(EnumType.STRING) //20240229-2 수정
    private OrderStatus orderStatus; //주문 상태

    public void addOrderItem(OrderItem orderItem){
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public static Order createOrder(Member member, List<OrderItem> orderItems){
        Order order = new Order();
        order.setMember(member);
        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
    public int getTotalPrice(){ //여러상품 구매시 전체 총 결제금액
        int total =0;
        for(OrderItem orderItem : orderItems){
            total += orderItem.getTotalPrice();
        }
        return  total;
    }

    //구매한 상품 구매취소 20240229-1
    public void cancelOrder(){
        this.orderStatus = OrderStatus.CANCEL;
        for(OrderItem orderItem:orderItems){ //아이템 수량 원복
            orderItem.cancel();
        }
    }
}


/*20240229-1
    1정규화 (일정규화)
        -1. 각각의 컬럼이 하나의 속성만 가져야 한다
        
        -2. 하나의 컬럼은 같은 종류나 타입을 가져야만 한다.
        -3. 각 칼럼이 유일한 이름을 가져야 한다.
            컬럼 순서는 무관하다.
            
            홍길동     하나은행    111, 222, 333
            김갑동     신한은행    444
            이을숙     농협      555, 666 이와같이 계좌가 있다면
            
            홍길동     하나은행    111  // 동일한데이터가 같은 칼럼이 있으면 안된다
            홍길동     하나은행    222
            홍길동     하나은행    333
            김갑동     신한은행    444
            이을숙     농협      555
            이을숙     농협      666 와 같이 구분하여
            
            아래와같이 테이블을 나누어서 설계해야한다 (2정규화)
            홍길동     하나은행
            김갑동     신한은행
            이을숙     농협
            
            1 홍길동     111
            2 홍길동     222
            3 홍길동     333
            4 김갑동     444
            5 이을숙     555
            6 이을숙     666

 */
