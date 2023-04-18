package com.supachok.exam.student.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supachok.exam.student.constant.SystemConstant;
import com.supachok.exam.student.dto.StudentDto;
import com.supachok.exam.student.entity.Role;
import com.supachok.exam.student.entity.Student;
import com.supachok.exam.student.repository.RoleRepository;
import com.supachok.exam.student.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

	StudentRepository studentRepository;

	RoleRepository roleRepository;

	BCryptPasswordEncoder bCryptPasswordEncoder;

	public StudentServiceImpl(StudentRepository studentRepository, RoleRepository roleRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		super();
		this.studentRepository = studentRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.roleRepository = roleRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Student> student = studentRepository.findByEmail(username);

		if (student.isEmpty())
			throw new UsernameNotFoundException(username);

		Student studentEntity = student.get();
		String role = SystemConstant.ROLE_PREFIX + studentEntity.getRole().getName();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));

		return new User(studentEntity.getEmail(), studentEntity.getPassword(), true, true, true, true,
				authorities);
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

	public Optional<Student> findStudentById(String id) {
		return studentRepository.findById(id);
	}

	public void saveStudent(Student student) {

		Role roleUser = roleRepository.findByName(SystemConstant.USER);
		String encodedPassword = bCryptPasswordEncoder.encode(student.getPassword());
		student.setId(UUID.randomUUID().toString());
		student.setPassword(encodedPassword);
		student.setRole(roleUser);

		studentRepository.save(student);
	}

	public void updateStudent(Student student, StudentDto studentDto) {
		String encodedPassword = bCryptPasswordEncoder.encode(studentDto.getPassword());
		student.setName(studentDto.getName());
		student.setEmail(studentDto.getEmail());
		student.setPassword(encodedPassword);
	}

	public void deleteStudent(String id) {
		studentRepository.deleteById(id);
	}

}
