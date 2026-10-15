package com.example.lecturer_demo.repositories;

import com.example.lecturer_demo.models.Course;
import com.example.lecturer_demo.models.Student;
import com.example.lecturer_demo.models.StudentEnrollment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class EnrollmentRepository {
        List<StudentEnrollment> enrollments = new ArrayList<>(
                Arrays.asList(
                        StudentEnrollment.builder()
                                .id(1001L)
                                .student(Student.builder().id(1L).name("Lê Văn Nam").build())
                                .course(Course.builder().id(101L).title("Lập trình Java cơ bản").build())
                                .build(),
                        StudentEnrollment.builder()
                                .id(1002L)
                                .student(Student.builder().id(2L).name("Phạm Thị Hoa").build())
                                .course(Course.builder().id(102L).title("Spring Boot từ A-Z").build())
                                .build()
                )
        );
    public List<StudentEnrollment> findAll() {
        return enrollments;
    }
    public Optional<StudentEnrollment> findById(long id) {
        return enrollments.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public StudentEnrollment create(StudentEnrollment enrollment) {
        enrollments.add(enrollment);
        return enrollment;
    }

    public StudentEnrollment update(long id, StudentEnrollment updatedEnrollment) {
        StudentEnrollment existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin đăng ký"));
        existing.setStudent(updatedEnrollment.getStudent());
        existing.setCourse(updatedEnrollment.getCourse());
        return existing;
    }

    public StudentEnrollment deleteById(long id) {
        StudentEnrollment existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin đăng ký"));
        enrollments.remove(existing);
        return existing;
    }
}
