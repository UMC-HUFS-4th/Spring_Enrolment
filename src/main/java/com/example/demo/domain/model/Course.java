package com.example.demo.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long courseId;          // key
    private String courseName;      // 과목명
    private String courseTime;      // 강의시간
    private String courseRoomNum;   // 강의실
    private String professorName;   // 교수명
    private int point;              // 학점수
    private int maxStudentNum;      // 정원
    private int curStudentNum;      // 신청인원


    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Registration> registrations = new ArrayList<>();
}