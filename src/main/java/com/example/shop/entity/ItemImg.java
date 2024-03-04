package com.example.shop.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class ItemImg extends BaseEntity{
    @Id
    @Column(name="item_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String imgName; //이미지 이름
    @Column
    private String oriImgName; // 원본 이미지 이름
    @Column
    private String imgUrl; // 이미지 경로
    @Column
    private String repImgYn; // 대표이미지 설정



    @ManyToOne
    @JoinColumn(name="item_id")
    private Item item;

}
    /*@Column 20240226-7 수정
    private LocalDateTime regDate;
    @Column
    private LocalDateTime updateDate;*/





