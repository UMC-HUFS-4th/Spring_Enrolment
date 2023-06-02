package com.example.demo.domain.repository;

import com.example.demo.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
//    Course findByCourseName(String courseName);
}

