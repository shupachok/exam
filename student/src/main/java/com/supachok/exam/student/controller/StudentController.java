package com.supachok.exam.student.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.supachok.exam.student.dto.StudentDto;
import com.supachok.exam.student.entity.Student;
import com.supachok.exam.student.service.StudentServiceImpl;

@RestController
public class StudentController {
	
	@Autowired
	StudentServiceImpl studentServiceImpl;

	@GetMapping(value = "/students")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Student> retrieveStudents() {
		return studentServiceImpl.findAllStudent();
	}

	@GetMapping(value = "/students/{id}")
	@PreAuthorize("hasRole('ADMIN') or principal == #id")
	public Student retrieveStudentsById(@PathVariable String id) {
		Optional<Student> student = studentServiceImpl.findStudentById(id);
		if(student.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		return student.get();
	}

	@PostMapping(value = "/students")
	public ResponseEntity<Object> insertStudents(@RequestBody Student student) {
		studentServiceImpl.saveStudent(student);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{studentId}")
				.buildAndExpand(student.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping(value = "/students/{id}")
	@PreAuthorize("hasRole('ADMIN') or principal == #id")
	public ResponseEntity<Object> updateStudents(@PathVariable String id, @RequestBody StudentDto student) {
		Optional<Student> studentSearched = studentServiceImpl.findStudentById(id);
		if (studentSearched.isEmpty())
			return ResponseEntity.notFound().build();
		studentServiceImpl.updateStudent(studentSearched.get(),student);

		return ResponseEntity.noContent().build();

	}

	@DeleteMapping(value = "/students/{id}")
	@PreAuthorize("hasRole('ADMIN') or principal == #id")
	public ResponseEntity<Object> deleteStudents(@PathVariable String id) {
		studentServiceImpl.deleteStudent(id);

		return ResponseEntity.noContent().build();

	}

}
