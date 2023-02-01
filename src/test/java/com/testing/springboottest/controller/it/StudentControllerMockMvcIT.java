package com.testing.springboottest.controller.it;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.springboottest.entity.Student;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-it.properties")
public class StudentControllerMockMvcIT {

	private static final int UNKNOWN_ID = Integer.MAX_VALUE;

	@Autowired
	private MockMvc mockMvc;

	// =========================================== Get All Student
	// ==========================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void test_get_all_success() throws Exception {

		mockMvc.perform(get("/students/all")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].username", is("student1")))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].username", is("student2")));

	}

	// =========================================== Get Student By ID
	// =========================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table

	public void test_get_by_id_success() throws Exception {

		mockMvc.perform(get("/students/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.username", is("student1")));

	}

	@Test
	public void test_get_by_id_fail_404_not_found() throws Exception {

		mockMvc.perform(get("/students/{id}", 1)).andExpect(status().isNotFound());

	}

	// =========================================== Create New Student
	// ========================================

	@Test
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	public void test_create_student_success() throws Exception {
		Student student = new Student("student1");

		mockMvc.perform(post("/students/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(student)))
				.andExpect(status().isCreated());

	}

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table

	public void test_create_student_fail_409_conflict() throws Exception {
		Student student = new Student(1, "student1");

		mockMvc.perform(post("/students/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(student)))
				.andExpect(status().isConflict());

	}

	// =========================================== Update Existing Student
	// ===================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void test_update_student_success() throws Exception {
		Student student = new Student(2, "student22");

		mockMvc.perform(put("/students/update/{id}", student.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(student))).andExpect(status().isOk())
				.andExpect(jsonPath("$.username", is("student22")));
		;

	}

	@Test
	public void test_update_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");
		mockMvc.perform(put("/students/update/{id}", student.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(student))).andExpect(status().isNotFound());
	}

	// =========================================== Delete Student
	// ============================================

	@Test
	@Sql(scripts = "/sql/init1.sql") // script d'initialisation
	@Sql(scripts = "/sql/close1.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD) // Vider table
	public void test_delete_student_success() throws Exception {
		Student student = new Student(1, "student1");

		mockMvc.perform(delete("/students/delete/{id}", student.getId())).andExpect(status().isOk());

	}

	@Test
	public void test_delete_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");

		mockMvc.perform(delete("/students/delete/{id}", student.getId())).andExpect(status().isNotFound());

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
