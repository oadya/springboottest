package com.testing.springboottest.controller.tu;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.springboottest.controller.StudentController;
import com.testing.springboottest.entity.Student;
import com.testing.springboottest.service.StudentService;



@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class) // Classe de ressource REST à tester
//OU 
//@SpringBootTest
//@AutoConfigureMockMvc
public class StudentControllerMockBeanTest {

	private static final int UNKNOWN_ID = Integer.MAX_VALUE;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StudentService userService;

	@Before
	public void init() {
	}

	// =========================================== Get All Students
	// ==========================================

	@Test
	public void test_get_all_success() throws Exception {
		List<Student> users = Arrays.asList(new Student(1, "Daenerys Targaryen"), new Student(2, "John Snow"));

		when(userService.getAll()).thenReturn(users);

		mockMvc.perform(get("/students/all")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].username", is("John Snow")));

		verify(userService, times(1)).getAll();
		verifyNoMoreInteractions(userService);
	}

	// =========================================== Get Student By ID
	// =========================================

	@Test
	public void test_get_by_id_success() throws Exception {
		Student student = new Student(1, "Daenerys Targaryen");

		when(userService.findById(1)).thenReturn(student);

		mockMvc.perform(get("/students/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.username", is("Daenerys Targaryen")));

		verify(userService, times(1)).findById(1);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_get_by_id_fail_404_not_found() throws Exception {

		when(userService.findById(1)).thenReturn(null);

		mockMvc.perform(get("/students/{id}", 1)).andExpect(status().isNotFound());

		verify(userService, times(1)).findById(1);
		verifyNoMoreInteractions(userService);
	}

	// =========================================== Create New Student
	// ========================================

	@Test
	public void test_create_student_success() throws Exception {
		Student student = new Student("Arya Stark");

		when(userService.exists(student)).thenReturn(false);
		doNothing().when(userService).create(student);

		mockMvc.perform(post("/students/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(student)))
				.andExpect(status().isCreated());

		verify(userService, times(1)).exists(student);
		verify(userService, times(1)).create(student);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_create_student_fail_409_conflict() throws Exception {
		Student student = new Student("username exists");

		when(userService.exists(student)).thenReturn(true);

		mockMvc.perform(post("/students/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(student)))
				.andExpect(status().isConflict());

		verify(userService, times(1)).exists(student);
		verifyNoMoreInteractions(userService);
	}

	// =========================================== Update Existing Student
	// ===================================

	@Test
	public void test_update_student_success() throws Exception {
		Student student = new Student(3, "Arya Stark");

		when(userService.findById(student.getId())).thenReturn(student);
		doNothing().when(userService).update(student);

		mockMvc.perform(put("/students/update/{id}", student.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(student))).andExpect(status().isOk());

		verify(userService, times(1)).findById(student.getId());
		verify(userService, times(1)).update(student);
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_update_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");

		when(userService.findById(student.getId())).thenReturn(null);

		mockMvc.perform(put("/students/update/{id}", student.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(student))).andExpect(status().isNotFound());

		verify(userService, times(1)).findById(student.getId());
		verifyNoMoreInteractions(userService);
	}

	// =========================================== Delete Student
	// ============================================

	@Test
	public void test_delete_student_success() throws Exception {
		Student student = new Student(1, "Arya Stark");

		when(userService.findById(student.getId())).thenReturn(student);
		doNothing().when(userService).delete(student.getId());

		mockMvc.perform(delete("/students/delete/{id}", student.getId())).andExpect(status().isOk());

		verify(userService, times(1)).findById(student.getId());
		verify(userService, times(1)).delete(student.getId());
		verifyNoMoreInteractions(userService);
	}

	@Test
	public void test_delete_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");

		when(userService.findById(student.getId())).thenReturn(null);

		mockMvc.perform(delete("/students/delete/{id}", student.getId())).andExpect(status().isNotFound());

		verify(userService, times(1)).findById(student.getId());
		verifyNoMoreInteractions(userService);
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
