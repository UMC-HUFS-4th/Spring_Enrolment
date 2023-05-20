package com.example.demo.domain.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @ManyToOne // 다대일을 나타내기 위한 어노테이션
    @JoinColumn(name = "student_id")  // student_id는 Student 테이블의 기본 키인 id와 연결됨 (외래키)
    private Student student;
    private String courseTime;
    private String courseRoomNum;
    private String professorName;
    private int point;
    private int maxStudentNum;
    private int curStudentNum;


}