package com.testing.springboottest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.testing.springboottest.entity.Student;
import com.testing.springboottest.service.StudentService;

@SpringBootTest
@AutoConfigureMockMvc
class TestIntegrationAvecMockMVCEtMockBean {

	@Autowired
	private MockMvc mockMvc;

	@MockBean // pour Mocker le bean Userservice
	private StudentService service;

	@Test
	void hello_doit_retourner_bonjour_du_service() throws Exception {

		when(service.findById(0)).thenReturn(new Student());

		this.mockMvc.perform(get("/students/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Hello World"));
	}

}
