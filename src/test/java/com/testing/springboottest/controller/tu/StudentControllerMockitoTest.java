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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.springboottest.config.CORSFilter;
import com.testing.springboottest.controller.StudentController;
import com.testing.springboottest.entity.Student;
import com.testing.springboottest.service.StudentService;

public class StudentControllerMockitoTest {

	private static final int UNKNOWN_ID = Integer.MAX_VALUE;

	private MockMvc mockMvc;

	@Mock
	private StudentService studentService;

	@InjectMocks
	private StudentController studentController;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(studentController).addFilters(new CORSFilter()).build();
	}

	// =========================================== Get All Students
	// ==========================================

	@Test
	public void test_get_all_success() throws Exception {
		List<Student> students = Arrays.asList(new Student(1, "Daenerys Targaryen"), new Student(2, "John Snow"));

		when(studentService.getAll()).thenReturn(students);

		mockMvc.perform(get("/students/all")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
				.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].username", is("John Snow")));

		verify(studentService, times(1)).getAll();
		verifyNoMoreInteractions(studentService);
	}

	// =========================================== Get Student By ID
	// =========================================

	@Test
	public void test_get_by_id_success() throws Exception {
		Student student = new Student(1, "Daenerys Targaryen");

		when(studentService.findById(1)).thenReturn(student);

		mockMvc.perform(get("/students/{id}", 1)).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.username", is("Daenerys Targaryen")));

		verify(studentService, times(1)).findById(1);
		verifyNoMoreInteractions(studentService);
	}

	@Test
	public void test_get_by_id_fail_404_not_found() throws Exception {

		when(studentService.findById(1)).thenReturn(null);

		mockMvc.perform(get("/students/{id}", 1)).andExpect(status().isNotFound());

		verify(studentService, times(1)).findById(1);
		verifyNoMoreInteractions(studentService);
	}

	// =========================================== Create New Student
	// ========================================

	@Test
	public void test_create_student_success() throws Exception {
		Student student = new Student("Arya Stark");

		when(studentService.exists(student)).thenReturn(false);
		doNothing().when(studentService).create(student);

		mockMvc.perform(post("/students/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(student)))
				.andExpect(status().isCreated());

		verify(studentService, times(1)).exists(student);
		verify(studentService, times(1)).create(student);
		verifyNoMoreInteractions(studentService);
	}

	@Test
	public void test_create_student_fail_409_conflict() throws Exception {
		Student student = new Student("username");

		when(studentService.exists(Mockito.any())).thenReturn(true);

		mockMvc.perform(post("/students/create").contentType(MediaType.APPLICATION_JSON).content(asJsonString(student)))
				.andExpect(status().isConflict());

		verify(studentService, times(1)).exists(student);
		verifyNoMoreInteractions(studentService);
	}

	// =========================================== Update Existing Student
	// ===================================

	@Test
	public void test_update_student_success() throws Exception {
		Student student = new Student(3, "Arya Stark");

		when(studentService.findById(student.getId())).thenReturn(student);
		doNothing().when(studentService).update(student);

		mockMvc.perform(put("/students/update/{id}", student.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(student))).andExpect(status().isOk());

		verify(studentService, times(1)).findById(student.getId());
		verify(studentService, times(1)).update(student);
		verifyNoMoreInteractions(studentService);
	}

	@Test
	public void test_update_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");

		when(studentService.findById(student.getId())).thenReturn(null);

		mockMvc.perform(put("/students/update/{id}", student.getId()).contentType(MediaType.APPLICATION_JSON)
				.content(asJsonString(student))).andExpect(status().isNotFound());

		verify(studentService, times(1)).findById(student.getId());
		verifyNoMoreInteractions(studentService);
	}

	// =========================================== Delete Student
	// ============================================

	@Test
	public void test_delete_student_success() throws Exception {
		Student student = new Student(1, "Arya Stark");

		when(studentService.findById(student.getId())).thenReturn(student);
		doNothing().when(studentService).delete(student.getId());

		mockMvc.perform(delete("/students/delete/{id}", student.getId())).andExpect(status().isOk());

		verify(studentService, times(1)).findById(student.getId());
		verify(studentService, times(1)).delete(student.getId());
		verifyNoMoreInteractions(studentService);
	}

	@Test
	public void test_delete_student_fail_404_not_found() throws Exception {
		Student student = new Student(UNKNOWN_ID, "student not found");

		when(studentService.findById(student.getId())).thenReturn(null);

		mockMvc.perform(delete("/students/delete/{id}", student.getId())).andExpect(status().isNotFound());

		verify(studentService, times(1)).findById(student.getId());
		verifyNoMoreInteractions(studentService);
	}

	// =========================================== CORS Headers
	// ===========================================

	@Test
	public void test_cors_headers() throws Exception {
		mockMvc.perform(get("/students/")).andExpect(header().string("Access-Control-Allow-Origin", "*"))
				.andExpect(header().string("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE"))
				.andExpect(header().string("Access-Control-Allow-Headers", "*"))
				.andExpect(header().string("Access-Control-Max-Age", "3600"));
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
