package com.example.demo.domain.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity // 엔티티는 기본생성자가 필요험. 따라서 위 ArgsConstructor 어노테이션 사용
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String studentNum; // 학번(로그인시 아이디로 사용)
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private int grade;
    @CreatedDate  // 엔티티가 생성되어 저장될 때 시간이 자동으로 저장
    private LocalDateTime createdDate; // 계정 생성날짜
    @LastModifiedDate // 엔티티가 마지막으로 수정된 시간이 자동으로 저장
    private LocalDateTime modifiedDate; // 계정 수정날짜
    @Column(nullable = false)
    private int maxCredit;
    @Column(nullable = false)
    private int curCredit;
    @Column(nullable = false)
    private String major;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY) // EAGER를 사용할 경우 연관된 객체를 모두 한번에 가져오기 때문에 개발자가 원하지 않는 Query가 DB에 날라갈 수 있음.
    private List<Registration> registrations = new ArrayList<>();


}
