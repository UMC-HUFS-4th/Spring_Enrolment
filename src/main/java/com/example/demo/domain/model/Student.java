package com.example.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity // 엔티티는 기본생성자가 필요험. 따라서 위 ArgsConstructor 어노테이션 사용
public class Student {

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
    // EAGER를 사용할 경우 연관된 객체를 모두 한번에 가져오기 때문에 개발자가 원하지 않는 Query가 DB에 날라갈 수 있음.
    @JsonIgnore
    private final List<Registration> registrations = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String studentNum; // 학번(로그인시 아이디로 사용)
    @Column(nullable = false)
    private String password;
    //    @Column(nullable = false) // null값을 허용안함으로 하면 500에러 발생(DTO에서 설정을 안해주기 때문)
    private int grade;
    @CreatedDate  // 엔티티가 생성되어 저장될 때 시간이 자동으로 저장
    private LocalDateTime createdDate; // 계정 생성날짜
    @LastModifiedDate // 엔티티가 마지막으로 수정된 시간이 자동으로 저장
    private LocalDateTime modifiedDate; // 계정 수정날짜
    @Column(nullable = false)
    private int maxCredit;
    @Column(nullable = false)
    private int curCredit;
    //    @Column(nullable = false)
    private String major;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
        modifiedDate = LocalDateTime.now();
    }
}
