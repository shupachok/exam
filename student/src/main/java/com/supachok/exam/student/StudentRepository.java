package com.supachok.exam.student;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
