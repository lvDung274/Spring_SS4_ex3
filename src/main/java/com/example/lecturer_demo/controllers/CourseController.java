package com.example.lecturer_demo.controllers;

import com.example.lecturer_demo.dto.ApiResponse;
import com.example.lecturer_demo.models.Course;
import com.example.lecturer_demo.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/app/course")
public class CourseController {
    private CourseService courseService;

    // Constructor Injection
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<Course>>> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã truy xuất thành công", courses));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> getCourseById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    new ApiResponse<>(true, "Đã truy xuất thành công", courseService.getCourseById(id))
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.error(e.getMessage())); //[cite: 4]
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Course>> createCourse(@RequestBody Course course) {
        try {
            Course created = courseService.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Tạo thành công", created));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Course>> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        try {
            Course updated = courseService.updateCourse(id, course);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhập thành công", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCourseById(@PathVariable Long id) {
        try {
            courseService.deleteCourseById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Xóa thành công khóa học", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

}
