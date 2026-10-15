package com.example.lecturer_demo.repositories;

import com.example.lecturer_demo.models.Course;
import com.example.lecturer_demo.models.CourseStatus;
import com.example.lecturer_demo.models.Instructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseRepository {
    List<Course> courses = new ArrayList<>(
            Arrays.asList(
                    Course.builder()
                            .id(101L)
                            .title("Lập trình Java cơ bản")
                            .status(CourseStatus.ACTIVE)
                            .instructor(Instructor.builder().id(1L).name("Tên Giảng Viên").email("email@example.com").build())
                            .build(),
                    Course.builder()
                            .id(102L)
                            .title("Spring Boot từ A-Z")
                            .status(CourseStatus.ACTIVE)
                            .instructor(Instructor.builder().id(2L).name("Tên Giảng Viên").email("email@example.com").build())
                            .build()
            )
    );

    public List<Course> findAll() {
        return courses;
    }

    public Optional<Course> findById(Long id) {
        return courses.stream().filter(c -> c.getId().equals(id)).findFirst();
    }

    public Course create(Course course) {
        courses.add(course);
        return course;
    }

    public Course update(Long id, Course updatedCourse) {
        Course existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        existing.setTitle(updatedCourse.getTitle());
        existing.setStatus(updatedCourse.getStatus());
        existing.setInstructor(updatedCourse.getInstructor());
        return existing;
    }

    public Course deleteById(Long id) {
        Course existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa học"));
        courses.remove(existing);
        return existing;
    }
}