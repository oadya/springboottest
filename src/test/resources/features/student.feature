@student
Feature: Gestion des notes des étudiants d'une classe

  # tableau commun exécutée pour chaque scenario
  Background: 
    Given Etant donné une classe contenant cette liste d'étudiants
      | username | email             | note | mention    |
      | student1 | student1@test.com |    8 | Echec      |
      | student2 | student2@test.com |   11 | Passable   |
      | student3 | student3@test.com |   12 | Assez bien |
      | student4 | student4@test.com |   14 | Bien       |
      | student5 | student5@test.com |   16 | Tres bien  |
      
      
  # Vérifier les notes et les mentions des étudiants
  @important
  Scenario: Vérifier les notes et les mentions des étudiants
    When Quand on récupère les données de l'étudiant "student1"
    Then Vérifier que sa note est égale à "8" et on sa mention est égale à "Echec"
    
    
  # Scenario de mise à jour des notes d'un etudiant
  @important @regression
  Scenario: Mise à jour des notes d'un étudiant
    Given Etant donné un professeur
    And Le professeur a les habilitations de modifier les notes
    When Le professeur modifie la note d'un étudiant avec les données ci-dessus suite au rattrapage
      | username | rattrapage |
      | student1 |         14 |
    Then Verifier que les informations suivantes sont affichées
      | username | email             | note | mention |
      | student1 | student1@test.com |   14 | Bien    |


  # Scenario de mise à jour des notes des étudiants
  @important @regression
  Scenario Outline: Mise à jour des notes des étudiants suite à un ratrappage
    When on modifie la note de l étudiant "<username>" avec la note "<rattrapage>"
    Then verifier que la nouvelle mention est "<mention>"
  
    Examples: 
      | username | rattrapage | mention   |
      | student1 |         14 | Bien      |
      | student2 |         16 | Tres bien |
      
      
  # Scenario de mise à jour des notes des etudiants à partir d'un fichier excel
  @important @Regression
  Scenario Outline: Mise à jour es notes des étudiants suite ratrappage à partir du tableau excel
    When on modifie la note de l étudiant avec la colonne ratrappage du tableau excel "<row_index>"
    Then vérifier que la nouvelle note de cet étudiant corresponde à celle indiquée dans la colonne nouvelle-note tableau excel

    Examples: 
      | row_index |
      |         1 | 
      |         2 |


  # Scenario pour lister tous les étudiants
  @important
  Scenario: Récupérer tous les étudiants d'une classe
    When Quand on requete l'api de récu^ération des tous les étudiants
    Then Tous les étudiants en base doivent être remontés et contient "5" elements
    
