package com.example.lecturer_demo.controllers;

import com.example.lecturer_demo.dto.ApiResponse;
import com.example.lecturer_demo.dto.EnrollCourseRequest;
import com.example.lecturer_demo.dto.EnrollmentDetail;
import com.example.lecturer_demo.models.StudentEnrollment;
import com.example.lecturer_demo.services.EnrollmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/app/enrollment")
public class EnrollmentController {
    private EnrollmentService enrollmentService;

    // Constructor Injection
    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }
    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentEnrollment>>> getAllEnrollments() {
        try {
            List<StudentEnrollment> enrollments = enrollmentService.getAllEnrollments();
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã truy xuất thành công", enrollments));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> getEnrollmentById(@PathVariable long id) {
        try {
            StudentEnrollment enrollment = enrollmentService.getEnrollmentById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Đã truy xuất thành công", enrollment));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentEnrollment>> createEnrollment(@RequestBody StudentEnrollment enrollment) {
        try {
            StudentEnrollment created = enrollmentService.createEnrollment(enrollment);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Đã tạo thanhf công", created));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(ApiResponse.error(e.getMessage()));
        }
    }
    @PostMapping("/enroll-course")
    public ResponseEntity<ApiResponse<EnrollmentDetail>> enrollCourse(@RequestBody EnrollCourseRequest request) {
        try {
            EnrollmentDetail detail = enrollmentService.enrollCourse(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Enroll course successfully", detail));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(ApiResponse.error(e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentEnrollment>> updateEnrollment(@PathVariable long id, @RequestBody StudentEnrollment enrollment) {
        try {
            StudentEnrollment updated = enrollmentService.updateEnrollment(id, enrollment);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cập nhập ok", updated));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEnrollment(@PathVariable Long id) {
        try {
            // Không gán vào biến StudentEnrollment nữa vì hàm trả về void
            enrollmentService.deleteEnrollmentById(id);
            return ResponseEntity.ok(new ApiResponse<>(true, "Xóa thông tin đăng ký thành công", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage()));
        }
    }
}
