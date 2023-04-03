package com.supachok.exam.student.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.supachok.exam.student.entity.Student;

public interface StudentService extends UserDetailsService {
	Student getStudentByEmail(String email);
}
