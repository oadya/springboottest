package com.testing.springboottest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.testing.springboottest.entity.Student;
import com.testing.springboottest.service.StudentService;

@RestController
@RequestMapping("/students")
//@Api(description = "STUDENTS API", value = "/students")
public class StudentController {

	private final Logger LOG = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private StudentService studentService;

	//@Autowired
	//private StudentClient studentClient;

	@GetMapping("/")
	public String get() {
		return "Hello World";
	}

	/*@GetMapping("/stuedents-feign")
	public List<Student> hello() {
		return studentClient.getAllStudents();
	}*/

	// ===================== Get All Student ==================== //

	@GetMapping("/all")
	// @ApiOperation(value = "Get all students", response = Student.class,
	// responseContainer = "List")
	public ResponseEntity<List<Student>> getAll() {
		LOG.info("getting all students");
		List<Student> students = studentService.getAll();

		if (students == null || students.isEmpty()) {
			LOG.info("no students found");
			return new ResponseEntity<List<Student>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
	}

	// ============== Get Student By ID =================

	@GetMapping("/{id}")
	// @ApiOperation(value = "Get alID", response = Student.class, responseContainer
	// = "List")
	public ResponseEntity<Student> get(@PathVariable("id") int id) {
		LOG.info("getting student with id: {}", id);
		Student student = studentService.findById(id);

		if (student == null) {
			LOG.info("student with id {} not found", id);
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Student>(student, HttpStatus.OK);
	}

	// ============== Create New Student ===============

	@PostMapping("/create")
	// @ApiOperation(value = "Create New Student", response = Void.class)
	public ResponseEntity<Void> create(@RequestBody Student student, UriComponentsBuilder ucBuilder) {
		LOG.info("creating new student: {}", student);

		if (studentService.exists(student)) {
			LOG.info("a student with name " + student.getUsername() + " already exists");
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}

		studentService.create(student);

		HttpHeaders headers = new HttpHeaders();
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	// ============== Update Existing Student ===============

	@PutMapping("/update/{id}")
	// @ApiOperation(value = "Update Existing Student", response = Student.class)
	public ResponseEntity<Student> update(@PathVariable int id, @RequestBody Student student) {
		LOG.info("updating student: {}", student);
		Student currentUser = studentService.findById(id);

		if (currentUser == null) {
			LOG.info("student with id {} not found", id);
			return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
		}

		currentUser.setId(student.getId());
		currentUser.setUsername(student.getUsername());

		studentService.update(student);
		return new ResponseEntity<Student>(currentUser, HttpStatus.OK);
	}

	// ============== Delete Student ==================

	@DeleteMapping("/delete/{id}")
	// @ApiOperation(value = "Delete Student", response = Void.class)
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
		LOG.info("deleting student with id: {}", id);
		Student student = studentService.findById(id);

		if (student == null) {
			LOG.info("Unable to delete. Student with id {} not found", id);
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}

		studentService.delete(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
}