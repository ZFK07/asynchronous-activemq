package com.activemq.activemq.service;

import com.activemq.activemq.entities.Student;
import com.activemq.activemq.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DatabaseService {

    StudentRepository studentRepository;

    public Student save(Student student) {
        return studentRepository.save(student);
    }
}
