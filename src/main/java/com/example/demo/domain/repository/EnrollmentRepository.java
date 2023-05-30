package com.example.demo.domain.repository;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Long countAllByCourse(Course course);

    List<Enrollment> findAllByStudent(Student student);

    Page<Enrollment> findAllByStudnetAndOnSemesterFalse(Student student, Pageable pageable);

    Page<Enrollment> FindAllByStudentAndOnSemesterTrue(Student student, Pageable pageable);

    @Query("select e from Enrollment e join e.course c where e.onSemester = TRUE")
}
