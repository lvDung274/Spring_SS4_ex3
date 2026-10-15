package com.example.lecturer_demo.services;

import com.example.lecturer_demo.dto.EnrollCourseRequest;
import com.example.lecturer_demo.dto.EnrollmentDetail;
import com.example.lecturer_demo.models.Course;
import com.example.lecturer_demo.models.Student;
import com.example.lecturer_demo.models.StudentEnrollment;
import com.example.lecturer_demo.repositories.CourseRepository;
import com.example.lecturer_demo.repositories.EnrollmentRepository;
import com.example.lecturer_demo.repositories.InstructorRepository;
import com.example.lecturer_demo.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final StudentRepository studentRepository; // Khai báo StudentRepository
    // Constructor Injection
    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             CourseRepository courseRepository,
                             InstructorRepository instructorRepository,
                             StudentRepository studentRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
    }
    public List<StudentEnrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    public StudentEnrollment getEnrollmentById(long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin đăng ký"));
    }

    public StudentEnrollment createEnrollment(StudentEnrollment enrollment) {
        return enrollmentRepository.create(enrollment);
    }

    public StudentEnrollment updateEnrollment(long id, StudentEnrollment enrollment) {
        return enrollmentRepository.update(id, enrollment);
    }

    public StudentEnrollment deleteEnrollmentById(long id) {
        return enrollmentRepository.deleteById(id);
    }

    public EnrollmentDetail enrollCourse(EnrollCourseRequest request) {
        // 1. Tìm Course theo ID
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Khóa học không tìm thấy"));

        // 2. Kiểm tra trạng thái khóa học
        if (!"ACTIVE".equalsIgnoreCase(course.getStatus().name())) {
            throw new RuntimeException("Khóa học hiện không mở đăng ký");
        }

        // 3. Tìm Student theo ID từ request (thay vì lấy tên trực tiếp)
        Student student = studentRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sinh viên"));

        // 4. Khởi tạo StudentEnrollment mới bằng Builder theo cấu trúc N-N
        StudentEnrollment newEnrollment = StudentEnrollment.builder()
                .student(student)
                .course(course)
                .build();

        enrollmentRepository.create(newEnrollment);

        // 5. Trả về thông tin chi tiết (lấy tên sinh viên thông qua đối tượng student)
        return new EnrollmentDetail(newEnrollment.getId(), student.getName(), course);
    }
}
