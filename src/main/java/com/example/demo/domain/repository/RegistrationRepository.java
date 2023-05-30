package com.example.demo.domain.repository;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.model.Registration;
import com.example.demo.domain.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Long countAllByCourse(Course course);

    static List<Registration> findAllByStudent(Student student);

    Page<Registration> findAllByStudnetAndOnSemesterFalse(Student student, Pageable pageable);

    Page<Registration> FindAllByStudentAndOnSemesterTrue(Student student, Pageable pageable);

//    @Query("select e from Registration e join e.course c where e.onSemester = TRUE")
}
