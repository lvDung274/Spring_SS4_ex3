package com.example.lecturer_demo.repositories;

import com.example.lecturer_demo.models.Instructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class InstructorRepository {
    List<Instructor>  instructors = new ArrayList<>(
            Arrays.asList(
                    Instructor.builder().id(1L).name("Nguyễn Văn A").email("a.nguyen@example.com").build(),
                    Instructor.builder().id(2L).name("Trần Thị B").email("b.tran@example.com").build(),
                    Instructor.builder().id(3L).name("Nguyễn Văn c").email("uc.nguyen@example.com").build()

            )
    );
    public List<Instructor> findAll() {
        return instructors;
    }

    public Optional<Instructor> findById(long id) {
        return instructors.stream().filter(i -> i.getId().equals(id)).findFirst();
    }

    public Instructor create(Instructor instructor) {
        instructors.add(instructor);
        return instructor;
    }

    public Instructor update(long id, Instructor updatedInstructor) {
        Instructor existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
        existing.setName(updatedInstructor.getName());
        existing.setEmail(updatedInstructor.getEmail());
        return existing;
    }

    public Instructor deleteById(long id) {
        Instructor existing = findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giảng viên"));
        instructors.remove(existing);
        return existing;
    }

}
