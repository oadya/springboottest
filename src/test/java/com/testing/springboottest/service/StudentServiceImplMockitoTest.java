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

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.testing.springboottest.entity.Student;
import com.testing.springboottest.repository.StudentRepository;

public class StudentServiceImplMockitoTest {

	@InjectMocks
	private StudentService studentService = new StudentServiceImpl();

	@Mock
	private StudentRepository studentRepository;

	private static final AtomicInteger counter = new AtomicInteger();

	static List<Student> users = new ArrayList<Student>(
			Arrays.asList(new Student(counter.incrementAndGet(), "student1", "student1@test.com", 12),
					new Student(counter.incrementAndGet(), "student2", "student2@test.com", 15)));

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetMention() {

		final String mention = studentService.getMention(14);

		assertThat(mention).isEqualTo("Bien");

	}

	@Test
	public void testGetAllStudents() {

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
	public void testGettudent() {

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
