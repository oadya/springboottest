package com.testing.springboottest.cucumber.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.testing.springboottest.entity.Student;
import com.testing.springboottest.repository.StudentRepository;
import com.testing.springboottest.util.DataReader;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StudentStep {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private StudentRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private List<Student> actual;
	private List<Student> expected;

	private Student actualStudent;

	private List<HashMap<String, String>> datamap;

	private static final AtomicInteger counter = new AtomicInteger();

	@Before(value = "@student", order = 1)
	public void setup1() {
		System.out.println("Execute before each Scenario");

		actual = new ArrayList<Student>();
		expected = new ArrayList<Student>();
		userRepository.deleteAll();

	}

	@Before(order = 0)
	public void setup2() {
		System.out.println("Execute before each Scenario");
	}

	@After(order = 1)
	public void tearDown() {
		System.out.println("Execute after each Scenario");
	}

	@After(order = 2)
	public void tearDown2() {
		System.out.println("Execute after each Scenario");
	}

	@BeforeStep
	public void beforeStep() {
		// System.out.println("Execute before each Step");
	}

	@AfterStep
	public void afterStep() {
		// System.out.println("Execute after each Step");
	}

	@Given("Etant donné une classe contenant cette liste d'étudiants")
	public void etant_donné_une_classe_content_cette_liste_d_étudiants(DataTable dataTable) {

		// read datatable
		List<Map<String, String>> studentsList = dataTable.asMaps(String.class, String.class);

		for (Map<String, String> studentsMap : studentsList) {

			Student student = new Student();

			student.setId(counter.incrementAndGet());
			student.setUsername(studentsMap.get("username"));
			student.setEmail(studentsMap.get("email"));
			student.setNote(Double.parseDouble(studentsMap.get("note")));
			student.setMention(studentsMap.get("mention"));

			// save students
			expected.add(student);
		}
		userRepository.saveAll(expected);

	}

//	@Given("Etant donné une classe content cette liste d'étudiants")
//	public void etant_donné_une_classe_content_cette_liste_d_étudiants(List<User> students) {
//
//		// set id
//		students.forEach(x -> x.setId(counter.incrementAndGet()));
//
//		// save students
//		expected.addAll(students);
//		userRepository.saveAll(expected);
//	}

	@When("Quand on récupère les données de l'étudiant {string}")
	public void quand_on_récupère_les_données_de_l_étudiant(String student) {

		actualStudent = userRepository.findByUsername(student);
	}

	@Then("Vérifier que sa note est égale à {string} et on sa mention est égale à {string}")
	public void vérifier_que_sa_note_est_égale_à_et_on_sa_mention_est_égale_à(String note, String mention) {

		Assertions.assertEquals(Double.valueOf(note), actualStudent.getNote());
		Assertions.assertEquals(mention, actualStudent.getMention());

	}

	@Given("Etant donné un professeur")
	public void etant_donné_un_professeur_habilité() {
		// vérifier que l'utilisateur est un professeur
	}

	@Given("Le professeur a les habilitations de modifier les notes")
	public void le_professeur_a_les_habilitations_de_modifier_les_notes() {
		// vérifier que le professeur a les habilitation
	}

	@When("Le professeur modifie la note d'un étudiant avec les données ci-dessus suite au rattrapage")
	public void le_professeur_modifie_la_note_d_un_étudiant_avec_les_données_ci_dessus_suite_au_rattrapage(
			DataTable dataTable) {

		updateNoteFromDataTable(dataTable);
	}

	@Then("Verifier que les informations suivantes sont affichées")
	public void verifier_que_les_informations_suivantes_sont_affcichées(DataTable dataTable) {

		validateDataTableData(dataTable);

	}

	@When("Quand on requete l'api de récu^ération des tous les étudiants")
	public void quand_on_requete_l_api_de_récu_ération_des_tous_les_étudiants()
			throws JsonMappingException, JsonProcessingException {

		actual.addAll(Arrays.asList(objectMapper
				.readValue(testRestTemplate.getForEntity("/students/all", String.class).getBody(), Student[].class)));

	}

	@Then("Tous les étudiants en base doivent être remontés et contient {string} elements")
	public void tous_les_étudiants_en_base_doivent_être_remontés_et_contient_elements(String nombeElemens) {
		Assertions.assertEquals(Integer.parseInt(nombeElemens), actual.size());
		validate();
	}

	@When("on modifie la note de l étudiant {string} avec la note {string}")
	public void on_modifie_la_note_de_l_étudiant_avec_la_note(String student, String ratrapage) {

		userRepository.updateNoteByUserName(student, Double.valueOf(ratrapage), getMention(Double.valueOf(ratrapage)));

		actualStudent = userRepository.findByUsername(student);
	}

	@Then("verifier que la nouvelle mention est {string}")
	public void la_nouvelle_note_de_l_étudiant_est(String mention) {

		Student std = userRepository.findByUsername(actualStudent.getUsername());

		Assertions.assertEquals(mention.trim(), std.getMention().trim());
	}

	@When("on modifie la note de l étudiant avec la colonne ratrappage du tableau excel {string}")
	public void on_modifie_la_note_de_l_étudiant_avec_la_colonne_nouvelle_note_du_tableau_excel(String rows) {

		datamap = DataReader.data("src/test/resources/students.xlsx", "Sheet1");

		int index = Integer.parseInt(rows) - 1;

		String username = datamap.get(index).get("username");
		String rattrapage = datamap.get(index).get("rattrapage");

		actualStudent = new Student();
		actualStudent.setUsername(username);
		actualStudent.setNote(Double.valueOf(rattrapage));

		userRepository.updateNoteByUserName(username, Double.valueOf(rattrapage),
				getMention(Double.valueOf(rattrapage)));

	}

	@Then("vérifier que la nouvelle note de cet étudiant corresponde à celle indiquée dans la colonne nouvelle-note tableau excel")
	public void vérifier_que_la_nouvelle_note_de_cet_étudiant_corresponde_à_celle_indiquée_dans_la_colonne_nouvelle_note_tableau_excel() {

		Student user = userRepository.findByUsername(actualStudent.getUsername());

		Assertions.assertEquals(actualStudent.getUsername(), user.getUsername());
		Assertions.assertEquals(actualStudent.getNote(), user.getNote());

	}

	private void updateNoteFromDataTable(DataTable dataTable) {

		List<Map<String, String>> studentsList = dataTable.asMaps(String.class, String.class);

		expected.clear();

		for (Map<String, String> studentsMap : studentsList) {

			userRepository.updateNoteByUserName(studentsMap.get("username"),
					Double.parseDouble(studentsMap.get("rattrapage")),
					getMention(Double.valueOf(studentsMap.get("rattrapage"))));

			expected.add(userRepository.findByUsername(studentsMap.get("username")));

		}
	}

	private void validateDataTableData(DataTable dataTable) {

		List<Map<String, String>> studentsList = dataTable.asMaps(String.class, String.class);

		for (Map<String, String> studentsMap : studentsList) {

			Student user = new Student();

			user.setUsername(studentsMap.get("username"));
			user.setEmail(studentsMap.get("email"));
			user.setNote(Double.valueOf(studentsMap.get("note")));
			user.setMention(studentsMap.get("mention"));

			actual.add(user);
		}

		validate();
	}

	private String getMention(double ratrapage) {

		String result = "";

		if (ratrapage < 10) {
			result = "Echec";
		} else if (ratrapage >= 10 && ratrapage < 12) {
			result = "Passable";
		} else if (ratrapage >= 12 && ratrapage < 14) {
			result = "Assez bien";
		} else if (ratrapage >= 14 && ratrapage < 16) {
			result = "Bien";
		} else if (ratrapage >= 16) {
			result = " Tres bien";
		}

		return result;

	}

	private void validate() {

		Assertions.assertEquals(expected.size(), actual.size());

		IntStream.range(0, actual.size()).forEach(index -> validate(expected.get(index), actual.get(index)));
	}

	private void validate(final Student expected, final Student actual) {

		Assertions.assertEquals(expected.getUsername(), actual.getUsername());
		Assertions.assertEquals(expected.getEmail(), actual.getEmail());
		Assertions.assertEquals(expected.getNote(), actual.getNote());
		Assertions.assertEquals(expected.getMention(), actual.getMention());

	}

}
