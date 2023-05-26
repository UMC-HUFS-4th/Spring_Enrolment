package com.example.demo.dto;

import com.example.demo.domain.model.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StudentDto {
    private String name;
    private String studentNum;
    private String password;
    private int maxCredit;
    private int curCredit;

    public static StudentDto from(Student student) {
        return StudentDto.builder()
                .name(student.getName())
                .studentNum(student.getStudentNum())
                .password(student.getPassword())
                .maxCredit(student.getMaxCredit())
                .curCredit(student.getCurCredit())
                .build();
    }
}

