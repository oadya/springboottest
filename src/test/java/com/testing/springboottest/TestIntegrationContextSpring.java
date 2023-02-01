package com.testing.springboottest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.testing.springboottest.controller.StudentController;

/**
 * Test intégration pour tester l'injection des dépendances
 *
 */
@SpringBootTest // Pertmet de créer un contexte spring avec tous les objets nécéssaires aux
				// tests
class TestIntegrationContextSpring {

	@Autowired
	private StudentController controller;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull(); // vérifier que UserController a été créé
		assertThat(controller.getAll()).isNotNull(); // vérifier que Userservice a été créé
	}

}
