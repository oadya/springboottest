package com.testing.springboottest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

/**
 * 
 * Test d'intégraton avec démarrage du serveur Tomcat pour tester le controller
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) // WebEnvironment.RANDOM_PORT: lancer le server tomcat et
																// choisir un port aléatoire
																// pour les tests
class TestIntegrationContextSpringEmbeddedTomcat {

	@LocalServerPort // récupération du port aléatoire généré
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void hello_doit_retourner_hello_world() {
		String response = this.restTemplate.getForObject("http://localhost:" + port + "/students/", String.class);
		assertThat(response).isEqualTo("Hello World");
	}

}
