package com.example.lecturer_demo.services;

import com.example.lecturer_demo.models.Course;
import com.example.lecturer_demo.repositories.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
    }

    public Course createCourse(Course course) {
        // Dùng .save() thay cho .create()
        return courseRepository.save(course);
    }

    public Course updateCourse(Long id, Course courseDetails) {
        Course existing = getCourseById(id);
        existing.setTitle(courseDetails.getTitle());
        existing.setStatus(courseDetails.getStatus());
        existing.setInstructor(courseDetails.getInstructor());
        return courseRepository.save(existing);
    }

    public void deleteCourseById(Long id) {
        Course existing = getCourseById(id);
        courseRepository.delete(existing);
    }
}