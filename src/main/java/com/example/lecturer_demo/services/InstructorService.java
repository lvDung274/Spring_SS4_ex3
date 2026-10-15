package com.example.lecturer_demo.services;

import com.example.lecturer_demo.dto.InstructorDetailResponse;
import com.example.lecturer_demo.models.Course;
import com.example.lecturer_demo.models.Instructor;
import com.example.lecturer_demo.models.StudentEnrollment;
import com.example.lecturer_demo.repositories.CourseRepository;
import com.example.lecturer_demo.repositories.EnrollmentRepository;
import com.example.lecturer_demo.repositories.InstructorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public InstructorService(InstructorRepository instructorRepository,
                             CourseRepository courseRepository,
                             EnrollmentRepository enrollmentRepository) {
        this.instructorRepository = instructorRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    public List<Instructor> getAllInstructors() {
        return instructorRepository.findAll();
    }

    public Instructor getInstructorById(long id) {
        return instructorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
    }

    public Instructor createInstructor(Instructor instructor) {
        return instructorRepository.save(instructor);
    }

    public Instructor updateInstructor(long id, Instructor instructorDetails) {
        Instructor existing = getInstructorById(id);
        existing.setName(instructorDetails.getName());
        existing.setEmail(instructorDetails.getEmail());
        return instructorRepository.save(existing);
    }

    public void deleteInstructorById(long id) {
        Instructor existing = getInstructorById(id);
        instructorRepository.delete(existing);
    }

    public List<InstructorDetailResponse> getInstructorsWithFilteredCourses() {
        List<Instructor> instructors = instructorRepository.findAll();
        List<Course> allCourses = courseRepository.findAll();
        List<StudentEnrollment> allEnrollments = enrollmentRepository.findAll();

        return instructors.stream().map(instructor -> {
            List<Course> filteredCourses = allCourses.stream()
                    .filter(course -> course.getInstructor() != null && course.getInstructor().getId().equals(instructor.getId()))
                    .filter(course -> "ACTIVE".equalsIgnoreCase(course.getStatus().name()))
                    .filter(course -> allEnrollments.stream()
                            .anyMatch(enrollment -> enrollment.getCourse().getId().equals(course.getId())))
                    .collect(Collectors.toList());

            return new InstructorDetailResponse(
                    instructor.getId(),
                    instructor.getName(),
                    instructor.getEmail(),
                    filteredCourses
            );
        }).collect(Collectors.toList());
    }
}