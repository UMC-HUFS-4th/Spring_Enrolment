package com.example.demo.controller;

import com.example.demo.domain.model.Registration;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.repository.RegistrationRepository;
import com.example.demo.domain.repository.StudentRepository;
import com.example.demo.dto.RegistrationRequestDto;
import com.example.demo.exception.NotFoundException;
import com.example.demo.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;

    @PostMapping("/register")
    public Registration registCourse(@RequestBody RegistrationRequestDto registrationRequestDto) {
        Long studentId = registrationRequestDto.getStudentId();
        Long courseId = registrationRequestDto.getCourseId();

        return registrationService.regist(studentId, courseId);
    }

    @DeleteMapping("/cancel/{registrationId}")
    public ResponseEntity<String> cancelEnrollment(@PathVariable Long registrationId, @RequestParam Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("Student not found"));
        Registration registration = registrationRepository.findById(registrationId)
                .orElseThrow(() -> new NotFoundException("Registration not found"));

        registrationService.cancel(student, registration);

        return ResponseEntity.ok("수강신청이 취소되었습니다.");
    }

    @GetMapping("/get")
    public List<Registration> getAllRegistrations() {
        return registrationRepository.findAll();
    }

}
