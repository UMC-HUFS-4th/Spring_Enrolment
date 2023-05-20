package com.example.demo.domain.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // 엔티티는 기본생성자가 필요험. 따라서 위 ArgsConstructor 어노테이션 사용
public class Student {

    //@OneToMany

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email; // 로그인시 아이디로 사용
    private String password;
    private LocalDateTime createdDate; // 계정 생성날짜
    private LocalDateTime modifiedDate; // 계정 수정날짜



}
