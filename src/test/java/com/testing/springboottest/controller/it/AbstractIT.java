package com.testing.springboottest.controller.it;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.testing.springboottest.client.StudentClient;
import com.testing.springboottest.entity.Student;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // Permet de créer un contexte spring avec tous les objets nécéssaires aux tests
@AutoConfigureMockMvc
@ComponentScan(basePackages = { "com.testing.springboottest.controller.it" }) // limiter les packages à scanner
@TestPropertySource(locations = "classpath:application-it.properties")
public abstract class AbstractIT {

	@LocalServerPort // récupération du port aléatoire généré
	protected int port;

	@MockBean
	protected StudentClient studentClient;

	private static final AtomicInteger counter = new AtomicInteger();

	static List<Student> users = new ArrayList<Student>(
			Arrays.asList(new Student(counter.incrementAndGet(), "student1", "student1@test.com", 12),
					new Student(counter.incrementAndGet(), "student2", "student2@test.com", 15)));

	@BeforeClass
	public static void start() {

	}

	@AfterClass
	public static void stop() {

	}

	@Before
	public void init() {

		// Mocker tous les appels externes pour éviter les vrais appels API lors de
		// l'exécution d'un test IT

		when(studentClient.getAllStudents()).thenReturn(users);
	}

}
