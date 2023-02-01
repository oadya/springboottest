package com.testing.springboottest.controller.it;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.springboottest.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-it.properties")
public class StudentControllerEmbeddedIT {

	private static final int UNKNOWN_ID = Integer.MAX_VALUE;

	@LocalServerPort // récupération du port aléatoire généré
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void hello_doit_retourner_hello_world() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/students/", String.class);
		assertThat(response).isEqualTo("Hello World");
	}

	// =========================================== Get All Users
	// ==========================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void test_get_all_success() throws Exception {

		ResponseEntity<List> responseEntity = restTemplate.getForEntity("/students/all", List.class);

		List<Student> students = responseEntity.getBody();

		assertThat(responseEntity).isNotNull();
		assertThat(students).hasSize(2);

	}

	// =========================================== Get Student By ID
	// =========================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table

	public void test_get_by_id_success() throws Exception {

		ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/students/" + "1", Student.class);

		Student student = responseEntity.getBody();

		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(student.getUsername()).isEqualTo("student1");

	}

	@Test
	public void test_get_by_id_fail_404_not_found() throws Exception {

		ResponseEntity<Student> responseEntity = restTemplate.getForEntity("/students/" + "1", Student.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	// =========================================== Create New Student
	// ========================================

	@Test
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void test_create_student_success() throws Exception {
		Student student = new Student(1, "student1");

		HttpEntity<Student> request = new HttpEntity<>(student);

		ResponseEntity<Student> responseEntity = restTemplate.exchange("/students/create", HttpMethod.POST, request,
				Student.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
	}

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table

	public void test_create_student_fail_409_conflict() throws Exception {
		Student student = new Student(1, "student1");

		HttpEntity<Student> request = new HttpEntity<>(student);

		ResponseEntity<Student> responseEntity = restTemplate.exchange("/students/create", HttpMethod.POST, request,
				Student.class);

		assertThat(responseEntity.getBody()).isNull();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);

	}

	// =========================================== Update Existing Student
	// ===================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void test_update_student_success() throws Exception {
		Student student = new Student(2, "student22");

		HttpEntity<Student> request = new HttpEntity<>(student);

		ResponseEntity<Student> responseEntity = restTemplate.exchange("/students/update" + "/" + student.getId(),
				HttpMethod.PUT, request, Student.class);

		Student resp = responseEntity.getBody();

		assertThat(responseEntity.getBody()).isNotNull();
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(resp.getUsername()).isEqualTo("student22");

	}

	@Test
	public void test_update_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");

		HttpEntity<Student> request = new HttpEntity<>(student);

		ResponseEntity<Student> responseEntity = restTemplate.exchange("/students/update" + "/" + student.getId(),
				HttpMethod.PUT, request, Student.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	// =========================================== Delete Student
	// ============================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void test_delete_student_success() throws Exception {

		HttpHeaders header = new HttpHeaders();
		HttpEntity<Student> request = new HttpEntity<Student>(header);

		ResponseEntity<Object> responseEntity = restTemplate.exchange("/students/delete" + "/" + 1, HttpMethod.DELETE,
				request, Object.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

	}

	@Test
	public void test_delete_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");

		HttpEntity<Student> request = new HttpEntity<Student>(student);

		ResponseEntity<Object> responseEntity = restTemplate.exchange("/students/delete" + "/" + student.getId(),
				HttpMethod.DELETE, request, Object.class);

		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);

	}

	/*
	 * converts a Java object into JSON representation
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
