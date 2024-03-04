package com.example.shop.repository;
//20240229-2
import com.example.shop.entity.Order;
import com.example.shop.entity.OrderItem;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("select o from Order o where o.member.email = :email "+ //Order 엔티티의 별명을 o 로 해주겠다
            "order by o.orderDate desc")
    List<Order> findOrders(@Param("email") String email, Pageable pageable);

    // 현재로그인한 회원의
    @Query("select count(o) from Order o where o.member.email = :email")
    Long countOrder(@Param("email") String email);
}
