package com.example.studentspringboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.studentspringboot.*;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByName(String name);
    List<Student> findBySurname(String surname);
    List<Student> findByGroup(String group);

}