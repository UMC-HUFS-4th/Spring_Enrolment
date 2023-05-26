package com.example.demo.controller;

import com.example.demo.domain.model.Course;
import com.example.demo.domain.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // 모든 과목 조회
    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // 특정 과목 조회
    @GetMapping("/{courseId}")
    public Course getCourseById(@PathVariable Long courseId) {
        return courseRepository.findById(courseId).orElse(null);
    }

    // 과목 추가
    @PostMapping
    public Course addCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    // 과목 수정
    @PutMapping("/{courseId}")
    public Course updateCourse(@PathVariable Long courseId, @RequestBody Course updatedCourse) {
        Course course = courseRepository.findById(courseId).orElse(null);
        if (course != null) {
            course.setCourseTime(updatedCourse.getCourseTime());
            course.setCourseRoomNum(updatedCourse.getCourseRoomNum());
            course.setProfessorName(updatedCourse.getProfessorName());
            course.setPoint(updatedCourse.getPoint());
            course.setMaxStudentNum(updatedCourse.getMaxStudentNum());
            course.setCurStudentNum(updatedCourse.getCurStudentNum());
            return courseRepository.save(course);
        }
        return null;
    }

    // 과목 삭제
    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
