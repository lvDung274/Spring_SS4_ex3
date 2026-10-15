package com.example.lecturer_demo.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EnrollCourseRequest {
    private Long id;
    private String studentName;
    private Long courseId;
}
