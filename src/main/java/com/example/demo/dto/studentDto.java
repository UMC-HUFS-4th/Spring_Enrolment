package com.example.demo.dto;

import com.example.demo.domain.model.Student;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class studentDto {
    private String name;
    private String studentNum;
    private String password;
    private int maxCredit;
    private int curCredit;

    public static studentDto from(Student student) {
        return studentDto.builder()
                .name(student.getName())
                .studentNum(student.getStudentNum())
                .maxCredit(student.getMaxCredit())
                .curCredit(student.getCurCredit())
                .build();
    }
}

