package com.example.lecturer_demo.repositories;

import com.example.lecturer_demo.models.StudentEnrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<StudentEnrollment, Long> {
}