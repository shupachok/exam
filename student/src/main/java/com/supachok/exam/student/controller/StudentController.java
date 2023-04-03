package com.supachok.exam.student.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.supachok.exam.student.entity.Student;
import com.supachok.exam.student.repository.StudentRepository;

@RestController
public class StudentController {
	
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	StudentRepository studentRepository;

	@GetMapping(value = "/students")
	public List<Student> retrieveStudents() {
		return studentRepository.findAll();
	}

	@GetMapping(value = "/students/{id}")
	public Student retrieveStudentsById(@PathVariable Long id) {
		Optional<Student> student = studentRepository.findById(id);
		if(student.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return student.get();
	}

	@PostMapping(value = "/students")
	public ResponseEntity<Object> insertStudents(@RequestBody Student student) {
		String encodedPassword = bCryptPasswordEncoder.encode(student.getPassword());
		student.setPassword(encodedPassword);
		Student studentInserted = studentRepository.save(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{studentId}")
				.buildAndExpand(studentInserted.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping(value = "/students/{id}")
	public ResponseEntity<Object> updateStudents(@PathVariable Long id, @RequestBody Student student) {
		Optional<Student> studentSearched = studentRepository.findById(id);
		if (studentSearched.isEmpty())
			return ResponseEntity.notFound().build();
		student.setId(id);
		studentRepository.save(student);

		return ResponseEntity.noContent().build();

	}

	@DeleteMapping(value = "/students/{id}")
	public ResponseEntity<Object> deleteStudents(@PathVariable Long id) {
		studentRepository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

}
