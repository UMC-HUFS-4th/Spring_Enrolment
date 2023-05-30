package com.example.demo.controller;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.repository.CourseRepository;
import com.example.demo.domain.repository.EnrollmentRepository;
import com.example.demo.domain.repository.StudentRepository;
import com.example.demo.service.EnrollmentService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.awt.print.Pageable;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Transactional
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    @PermitStudent
    @PostMapping("/enroll")
    @Valid
    public void enroll(
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Long studentId,
            @RequestParam @NotNull Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(NoExistEntityException::new);
        Course course = courseRepository.findById(courseId).orElseThrow(NoExistEntityException::new);

        enrollmentService.enroll(student, course);
    }

    @PermitStudent
    @PostMapping("/drop")
    @Valid
    public void drop(
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Long studentId,
            @RequestParam @NotNull Long enrollmentId
    ) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(NoExistEntityException::new);
        Enrollment enrollment = enrollmentRepository.findById(enrollmentId)
                .orElseThrow(NoExistEntityException::new);

        enrollmentService.drop(student, enrollment);
    }

    @PermitAnyLogin
    @PostMapping("/not-semester/student")
    @valid
    public Page<NotSemesterEnrollmentResponse> notSemesterEnrollments(
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Long studentId,
            Pageable pageable) {
        Student student = studentRepository.findById(studentId).orElseThrow(NoExistEntityException::new);
        return enrollmentRepository.findAllByStudentAndOnSemesterFalse(student, pageable)
                .map(NotSemesterEnrollmentResponse::new);
    }

    @PermitAnyLogin
    @PostMapping("/on-semester/student")
    public Page<OnSemesterEnrollmentResponse> onSemesterEnrollments(
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Long studentId,
            Pageable pageable) {
        Student student = studentRepository.findById(studentId).orElseThrow(NoExistEntityException::new);

        return enrollmentRepository.findAllByStudentAndOnSemesterTrue(student, pageable)
                .map(OnSemesterEnrollmentResponse::new);
    }

}
