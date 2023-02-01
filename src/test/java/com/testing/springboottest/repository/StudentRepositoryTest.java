package com.testing.springboottest.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import com.testing.springboottest.config.RepositoryConfig;
import com.testing.springboottest.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RepositoryConfig.class)
@TestPropertySource(locations = "classpath:application-it.properties")
public class StudentRepositoryTest {

	@Autowired
	StudentRepository studentRepository;

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void testFindAllStudents() {

		List<Student> students = new ArrayList<Student>();

		studentRepository.findAll().forEach(u -> students.add(u));

		assertThat(students).isNotEmpty();
		assertThat(students).hasSize(2);
	}

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void testFindStudentbyUsername() {

		Student studentExpected = new Student("student1");

		Student student = studentRepository.findByUsername("student1");

		assertThat(student.getUsername()).isEqualTo(studentExpected.getUsername());
	}

}
