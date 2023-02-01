package com.testing.springboottest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.testing.springboottest.controller.StudentController;
import com.testing.springboottest.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerUnitTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private StudentService userservice;

	@InjectMocks
	private StudentController userController;

	@BeforeEach
	public void init() {
		// MockitoAnnotations.openMocks(this);
		// MockitoAnnotations.initMocks(this);
		// mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

	}

	@Test
	public void return_helloworld_when_get_hello() throws Exception {

		this.mockMvc.perform(get("/students/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Hello World"));
	}

}
