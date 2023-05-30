package com.example.demo.service;


import com.example.demo.domain.model.Course;
import com.example.demo.domain.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    // Course 열기
    public Course openCourse(Course course) {
        return courseRepository.save(course);
    }
    // Course 닫기
    public Course closeCourse(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            courseRepository.delete(course);
            return course;
        }
        return null;
    }
}
