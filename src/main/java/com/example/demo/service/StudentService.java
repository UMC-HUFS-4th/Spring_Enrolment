package com.example.demo.service;

import com.example.demo.domain.model.Student;
import com.example.demo.domain.repository.StudentRepository;
import com.example.demo.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor // RequiredArgConstructor은 꼭 초기화가 되어야하는 생성자를 만들어줌. final 사용할때 사용
//@Transactional
@Service
public class StudentService {
    private final StudentRepository studentRepository; // StudentRepository 의존성 추가

    public void register(StudentDto studentDto) {
        // StudentDto를 이용하여 회원가입 정보를 생성하고 저장
        Student student = Student.builder()
                .name(studentDto.getName())
                .studentNum(studentDto.getStudentNum())
                .password(studentDto.getPassword())
                .maxCredit(studentDto.getMaxCredit())
                .curCredit(studentDto.getCurCredit())
                .build();

        // 저장된 회원가입 정보를 데이터베이스에 저장합니다.
        studentRepository.save(student);
    }

    public boolean login(String studentNum, String password) {
        // 주어진 학번과 비밀번호로 학생을 조회합니다.
        Student student = studentRepository.findByStudentNum(studentNum);

        if (student != null && student.getPassword().equals(password)) {
            // 학생이 존재하고 비밀번호가 일치하면 로그인 성공
            return true;
        } else {
            // 학생이 존재하지 않거나 비밀번호가 일치하지 않으면 로그인 실패
            return false;
        }
    }

}
