package com.example.demo.service;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.model.Registration;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.repository.CourseRepository;
import com.example.demo.domain.repository.RegistrationRepository;
import com.example.demo.domain.repository.StudentRepository;
import com.example.demo.exception.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    //
    private final RegistrationRepository registrationRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public Registration regist(Long studentId, Long courseId) {

        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Invalid studentId"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("Invalid courseId"));

        if (isFullCapacity(course))
            throw new RuntimeException("코스 인원이 꽉 찼습니다. 수강신청할 수 없습니다");
        if (isDuplicatedTime(student, course))
            throw new RuntimeException("신청한 과목의 수업시간이 기존 시간표와 중복됩니다");
        if (isExceedsCreditLimit(student, course))
            throw new RuntimeException("수강 신청할 수 있는 학점을 초과했습니다");

        List<Registration> registrations = registrationRepository.findAllByStudent(student);
        if (isDuplicatedOnSemesterEnroll(course, registrations))
            throw new RuntimeException("이미 신청한 과목입니다");


        Registration registration = Registration.builder()
                .student(student)
                .course(course)
                .build();

        // 현재 수강 인원 +1
        course.setCurStudentNum(course.getCurStudentNum() + 1);
        courseRepository.save(course);

        // 학생 학점 업데이트
        student.setCurCredit(student.getCurCredit() + course.getPoint());
        studentRepository.save(student);

        return registrationRepository.save(registration);
    }

    public void cancel(Student student, Registration registration) {
        if (!registration.getStudent().equals(student)) {
            throw new NotAuthorizedException("Student is not authorized to cancel this registration");
        }

        Course course = registration.getCourse();

        // curStudentNum 감소
        course.setCurStudentNum(course.getCurStudentNum() - 1);
        courseRepository.save(course);

        // curCredit 감소
        int coursePoint = course.getPoint();
        student.setCurCredit(student.getCurCredit() - coursePoint);
        studentRepository.save(student);

        registrationRepository.delete(registration);
        registrationRepository.flush(); // 즉시 데이터베이스 동기화
    }

    // 강의 수강인원 제한
    private boolean isFullCapacity(Course course) {
        return course.getCurStudentNum() >= course.getMaxStudentNum();
    }

    // 이미 수강신청한 과목과 수업시간이 중복되는지 확인하는 기능
    private boolean isDuplicatedTime(Student student, Course course) {
        List<Registration> registrations = registrationRepository.findAllByStudent(student);
        for (Registration registration : registrations) {
            Course enrolledCourse = registration.getCourse();
            if (enrolledCourse.getCourseTime().equals(course.getCourseTime())) {
                return true;
            }
        }
        return false;
    }

    // 학생이 이미 현재학기에 수강한 동일한 과목이 있는지 확인
    private boolean isDuplicatedOnSemesterEnroll(Course course, List<Registration> registrations) {
        String courseName = course.getCourseName();
        for (Registration registration : registrations) {
            Course enrolledCourse = registration.getCourse();
            if (enrolledCourse.getCourseName().equals(courseName)) {
                return true;
            }
        }
        return false;
    }

    // 학생이 최대 수강학점을 넘는지 안넘는지 확인
    private boolean isExceedsCreditLimit(Student student, Course course) {
        int coursePoint = course.getPoint();
        int curCredit = student.getCurCredit();
        int maxCredit = student.getMaxCredit();

        return curCredit + coursePoint > maxCredit;
    }

}