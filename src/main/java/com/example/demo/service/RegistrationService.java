package com.example.demo.service;

import com.example.demo.domain.model.Registration;
import com.example.demo.domain.model.Student;
import com.example.demo.domain.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
    private Registration enroll(Student student, Course course) {

        if (isFullCapacity(course))
            throw new RuntimeException("코스 인원이 꽉 찼습니다. 수강신청할 수 없습니다.");
        if (isDuplicatedTime(student, course))throw new RuntimeException("신청한 과목의 수업시간이 기존 시간표와 중복됩니다");
        if (isDuplicatedEnroll(student, course))
            throw new RuntimeException("이미 수강한 과목입니다, 재수강은 B0 미만만 가능합니다");
        if(isNotQualified(student, course))
            throw new RuntimeException("수강 금지과입니다");

        List<Registration> enrollments = enrollmentRepository.findAllByStudent(student);
        if (isDuplicatedOnSemesterEnroll(course, enrollments))
            throw new RuntimeException("이미 신청한 과목입니다");

        Registration registration = Registration.builder()
                .student(student)
                .course(course)
                .build();

        return enrollmentRepository.save(registration);
    }

    public void drop(Student student, Registration registration) {
        if (!student.equals(registration.getStudent()))
            throw new NotAuthorizedException();

        RegistrationRepository.delete(registration);
        RegistrationRepository.flush();
    }

    private boolean isDuplicatedTime(Student student, Course course) {
        List<Registration> enrollments = RegistrationRepository.findAllByStudent(student);

        Map<Day, List<CourseTime>> courseTimeGroupByDay = enrollments.stream()
                .filter(Registration::isOnSemester)
                .map(Registration::getCourse)
                .map(Course::getCourseTime)
                .collect(Collectors.groupingBy(CourseTime::getDay));

        Integer startHour = course.getCourseTime().getStartHour();
        Integer endHour = course.getCourseTime().getEndHour();
        Day day = course.getCourseTime().getDay();

        return courseTimeGroupByDay.getOrDefault(day, List.of()).stream()
                .anyMatch(e -> startHour < e.getStartHour() && e.getStartHour() < endHour
                        && startHour < e.getEndHour() && e.getEndHour() < endHour);
    }

    private boolean isFullCapacity(Course course) {
        Long cnt = RegistrationRepository.countAllByCourse(course);
        return cnt >= course.getCapacity();
    }

    private boolean isDuplicatedOnSemesterEnroll(Course course, List<Registration> enrollments) {
        return enrollments.stream()
                .filter(Registration::isOnSemester)
                .map(e -> e.getCourse().getSubject().getCode())
                .anyMatch(e -> e.equals(course.getSubject().getCode()));
    }

    private boolean isDuplicatedEnroll(Student student, Course course) {
        return student.getEnrollments().stream()
                .filter(e -> e.getCourse().getSubject().getCode().equals(course.getSubject().getCode()))
                .filter(e -> !e.isOnSemester())
                .anyMatch(e -> e.getScoreType().getDigit() >= ScoreType.B_ZERO.getDigit());
    }
}