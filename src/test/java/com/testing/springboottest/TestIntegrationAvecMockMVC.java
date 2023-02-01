package com.testing.springboottest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * Si on veut économiser le coût du démarrage du serveur un utilise MockMvc
 * MockMvc est un bean spring test qui permet d'intercepter les requêtes et les
 * transfert aux contrôlleurs permete de tester le controlleur dans démarrer le
 * serveur
 *
 */
@SpringBootTest
@AutoConfigureMockMvc // Ajouter autoconfiguration por mock MockMvc
class TestIntegrationAvecMockMVC {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void hello_doit_retourner_hello_world() throws Exception {
		this.mockMvc.perform(get("/students/")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$").value("Hello World"));
	}

}
