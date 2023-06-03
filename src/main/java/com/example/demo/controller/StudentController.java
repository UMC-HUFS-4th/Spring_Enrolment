package com.example.demo.controller;

import com.example.demo.dto.StudentDto;
import com.example.demo.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerStudent(@RequestBody StudentDto studentDto) {
        studentService.register(studentDto);
        return ResponseEntity.ok("Student registered successfully.");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String studentNum, @RequestParam String password) {
        boolean loginSuccess = studentService.login(studentNum, password);

        if (loginSuccess) {
            return ResponseEntity.ok("Login successful.");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed.");
        }
    }


    // 필요는 없을거같지만 회원가입한 회원들이 올바르게 들어가 있나 확인하기위해서 작성해놓음( Get요청으로 확인해보려고)
    @GetMapping("/get")
    public List<StudentDto> getAllStudents() {
        List<StudentDto> students = studentService.getAllStudents();
        return students;
    }

    // 회원가입 구현했으니 이제 로그인이랑 다른 기능 추가하면 될듯?

}
