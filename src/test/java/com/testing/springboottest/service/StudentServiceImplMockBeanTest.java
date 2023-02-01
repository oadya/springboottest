package com.testing.springboottest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import com.testing.springboottest.entity.Student;
import com.testing.springboottest.repository.StudentRepository;

@SpringBootTest
@ContextConfiguration(classes = { StudentServiceImpl.class }) // limiter the number of bean initialize
//@RunWith(SpringRunner.class)
class StudentServiceImplMockBeanTest {

	@Autowired // to create real bean
	private StudentService studentService;

	@MockBean
	private StudentRepository studentRepository;

	private static final AtomicInteger counter = new AtomicInteger();

	static List<Student> users = new ArrayList<Student>(
			Arrays.asList(new Student(counter.incrementAndGet(), "student1", "student1@test.com", 12),
					new Student(counter.incrementAndGet(), "student2", "student2@test.com", 15)));

	@BeforeEach
	public void setUp() {
	}

	@Test
	void testGetMention() {

		final String mention = studentService.getMention(14);

		assertThat(mention).isEqualTo("Bien");
	}

	@Test
	void testGetAllStudents() {

		// When
		when(studentRepository.findAll()).thenReturn(users);

		final List<Student> resp = studentService.getAll();

		// Then
		assertThat(resp).hasSize(2);
		assertThat(resp.stream().map(x -> x.getNote()).collect(Collectors.toList())).contains(Double.valueOf(15),
				Double.valueOf(12));

		verify(studentRepository, times(1)).findAll();
		verifyNoMoreInteractions(studentRepository);

	}

	@Test
	void testGettudent() {

		// Given
		Student user = new Student(counter.incrementAndGet(), "student1", "student1@test.com", 12);

		// When
		when(studentRepository.findByUsername(any(String.class))).thenReturn(user);

		final Student resp = studentService.findByName(user.getUsername());

		// Then
		assertThat(resp.getUsername()).isEqualTo(user.getUsername());

		verify(studentRepository, times(1)).findByUsername(eq("student1"));
		verifyNoMoreInteractions(studentRepository);

	}

}
