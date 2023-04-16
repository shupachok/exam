package com.supachok.exam.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supachok.exam.student.dto.StudentDto;
import com.supachok.exam.student.entity.Student;
import com.supachok.exam.student.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	StudentRepository studentRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public StudentServiceImpl(StudentRepository studentRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.studentRepository = studentRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Student> student = studentRepository.findByEmail(username);

		if (student.isEmpty())
			throw new UsernameNotFoundException(username);

		Student studentEntity = student.get();

		return new User(studentEntity.getEmail(), studentEntity.getPassword(), true, true, true, true,
				new ArrayList<>());
	}

	@Override
	public Student getStudentByEmail(String email) {
		Optional<Student> student = studentRepository.findByEmail(email);

		if (student.isEmpty())
			throw new UsernameNotFoundException(email);

		return student.get();
	}

	public List<Student> findAllStudent() {
		return studentRepository.findAll();
	}

	public Optional<Student> findStudentById(UUID id) {
		return studentRepository.findById(id);
	}

	public void saveStudent(Student student) {
		String encodedPassword = bCryptPasswordEncoder.encode(student.getPassword());
		student.setPassword(encodedPassword);
		studentRepository.save(student);
	}

	public void updateStudent(Student student, StudentDto studentDto) {
		String encodedPassword = bCryptPasswordEncoder.encode(studentDto.getPassword());
		student.setName(studentDto.getName());
		student.setEmail(studentDto.getEmail());
		student.setPassword(encodedPassword);
	}

	public void deleteStudent(UUID id) {
		studentRepository.deleteById(id);
	}

}
