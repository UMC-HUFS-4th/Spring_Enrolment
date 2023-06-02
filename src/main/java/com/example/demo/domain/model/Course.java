package com.example.demo.domain.model;

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
    private Long courseId;
    private String courseName;
    private String courseTime;
    private String courseRoomNum;
    private String professorName;
    private int point;
    private int maxStudentNum;
    private int curStudentNum;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
    private List<Registration> registrations = new ArrayList<>();
}