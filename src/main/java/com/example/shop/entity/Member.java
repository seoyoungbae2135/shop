package com.example.shop.entity;

import com.example.shop.constant.Role;
import com.example.shop.dto.MemberFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString
public class Member extends BaseEntity{

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    private String zipCode; //우편번호

    @Column
    private String addr1;// 기본주소

    @Column
    private String addr2;// 상세주소

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail()); //20240227-1

        String password = passwordEncoder.encode((memberFormDto.getPassword())); //암호화 처리
        member.setPassword(password);

        member.setZipCode(memberFormDto.getZipCode());
        member.setAddr1(memberFormDto.getAddr1());
        member.setAddr2(memberFormDto.getAddr2());
        member.setRole(Role.USER);
        return member;
    }

}