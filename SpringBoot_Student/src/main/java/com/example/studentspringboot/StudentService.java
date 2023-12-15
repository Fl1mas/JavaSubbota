package com.example.studentspringboot;
import com.example.studentspringboot.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class StudentService {

    @Autowired
    public StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }


    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, Student student) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    existingStudent.setName(student.getName());
                    existingStudent.setSurname(student.getSurname());
                    existingStudent.setGroup(student.getGroup());
                    return studentRepository.save(existingStudent);
                })
                .orElse(null);
    }

}