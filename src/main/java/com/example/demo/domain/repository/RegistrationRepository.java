package com.example.demo.domain.repository;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.model.Registration;
import com.example.demo.domain.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    Long countAllByCourse(Course course);

    List<Registration> findAllByStudent(Student student);
}
