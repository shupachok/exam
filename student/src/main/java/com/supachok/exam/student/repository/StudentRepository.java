package com.supachok.exam.student.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.supachok.exam.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{
	
	Optional<Student> findByEmail(String email);

}
