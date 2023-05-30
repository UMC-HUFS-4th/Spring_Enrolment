package com.example.demo.controller;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.model.Registration;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.repository.CourseRepository;
import com.example.demo.domain.repository.RegistrationRepository;
import com.example.demo.domain.repository.StudentRepository;
import com.example.demo.service.RegistrationService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.web.bind.annotation.*;annotation

import javax.transaction.Transactional;
import java.awt.print.Pageable;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Transactional
public class RegistrationController {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private com.example.demo.domain.model.Registration Registration;

    @PermitStudent
    @PostMapping("/enroll")
    @Valid
    public void enroll(
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Long studentId,
            @RequestParam @NotNull Long courseId) {
        Student student = studentRepository.findById(studentId).orElseThrow(NoExistEntityException::new);
        Course course = courseRepository.findById(courseId).orElseThrow(NoExistEntityException::new);

        RegistrationService.enroll(student, course);
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
        Registration registration = RegistrationRepository.findById(enrollmentId)
                .orElseThrow(NoExistEntityException::new);

        RegistrationService.drop(student, Registration);
    }

    @PermitAnyLogin
    @PostMapping("/not-semester/student")
    @valid
    public Page<NotSemesterRegistrationResponse> notSemesterEnrollments(
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Long studentId,
            Pageable pageable) {
        Student student = studentRepository.findById(studentId).orElseThrow(NoExistEntityException::new);
        return RegistrationRepository.findAllByStudentAndOnSemesterFalse(student, pageable)
                .map(NotSemesterRegistrationResponse::new);
    }

    @PermitAnyLogin
    @PostMapping("/on-semester/student")
    public Page<OnSemesterRegistrationResponse> onSemesterEnrollments(
            @Parameter(hidden = true) @SessionAttribute(name = SessionConst.LOGIN_STUDENT, required = false) Long studentId,
            Pageable pageable) {
        Student student = studentRepository.findById(studentId).orElseThrow(NoExistEntityException::new);

        return RegistrationRepository.findAllByStudentAndOnSemesterTrue(student, pageable)
                .map(OnSemesterRegistrationResponse::new);
    }

}
