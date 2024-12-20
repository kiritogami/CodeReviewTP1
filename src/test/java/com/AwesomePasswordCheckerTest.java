package com;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.perf4j.StopWatch;
import org.perf4j.LoggingStopWatch;


class AwesomePasswordCheckerTest {


  /**
   * Teste la méthode {@link AwesomePasswordChecker#getInstance(File)} en utilisant un fichier CSV.
   * Ce test vérifie que l'instance de {@link AwesomePasswordChecker} est correctement initialisée
   * à partir du fichier donné et que l'instance n'est pas nulle.
   *
   * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture du fichier.
   */
  @Test
  void testGetInstanceWithFile() throws IOException {
    File file = new File("src/test/ressources/cluster_centers_HAC_aff.csv");
    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance(file);

    // Vérifie que l'instance n'est pas nulle
    assertNotNull(checker, "L'instance ne doit pas être nulle");
  }

  /**
   * Teste la méthode {@link AwesomePasswordChecker#getInstance()} sans fichier d'entrée.
   * Ce test vérifie que l'instance de {@link AwesomePasswordChecker} est correctement initialisée
   * à l'aide de la ressource par défaut (fichier de centres de clusters intégré dans les ressources).
   * Le test s'assure également que l'instance retournée n'est pas nulle.
   *
   * @throws IOException si une erreur d'entrée/sortie se produit lors de la lecture de la ressource par défaut.
   */
  @Test
  void testGetInstanceWithoutFile() throws IOException {
    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();

    // Vérifie que l'instance n'est pas nulle
    assertNotNull(checker, "L'instance ne doit pas être nulle");
  }

  /**
   * Teste la méthode {@link AwesomePasswordChecker#maskAff(String)} pour un mot de passe donné.
   * Ce test vérifie le masque généré pour différentes catégories de caractères dans le mot de passe.
   */
  @Test
  void testMaskAff() throws IOException {
    // Test avec un mot de passe contenant des lettres minuscules, majuscules, chiffres, et caractères spéciaux
    String password = "Isim@07";
    int[] expectedMask = {3, 1, 1, 2,6,5,5};  // Les 7 premiers éléments du tableau attendu

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();  // ou instanciation selon votre code
    int[] result = checker.maskAff(password);

    for (int i = 0; i < expectedMask.length; i++) {
      assertEquals(expectedMask[i], result[i], "Le masque généré ne correspond pas à l'attendu à l'indice " + i);
    }
  }

  /**
   * Teste le cas où le mot de passe est vide.
   * Ce test vérifie que la méthode gère correctement les mots de passe vides.
   */
  @Test
  void testMaskAffEmptyPassword() throws IOException {
    String password = "";
    int[] expectedMask = new int[28]; // Un tableau de 28 éléments initialisés à 0

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
    int[] result = checker.maskAff(password);

    // Vérifie que le masque pour un mot de passe vide est un tableau de zéros
    assertArrayEquals(expectedMask, result, "Le masque pour un mot de passe vide ne doit contenir que des zéros.");
  }

  /**
   * Teste un mot de passe qui contient un seul caractère.
   * Ce test vérifie que la méthode masque correctement un seul caractère du mot de passe.
   */
  @Test
  void testMaskAffSingleCharacterPassword() throws IOException {
    String password = "k"; // Le mot de passe contient uniquement un 'e'
    int[] expectedMask = {2}; // Le masque attendu pour 'e' est 1

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
    int[] result = checker.maskAff(password);

    // Vérifie que le masque pour le mot de passe contenant un seul caractère est correct
    assertEquals(expectedMask[0], result[0], "Le masque pour un mot de passe avec un seul caractère est incorrect.");
  }

  /**
   * Teste un mot de passe avec des caractères spéciaux.
   * Ce test vérifie que les caractères spéciaux génèrent un masque correct.
   */
  @Test
  void testMaskAffSpecialCharacters() throws IOException {
    String password = ">!?@"; // Contient des caractères spéciaux
    int[] expectedMask = {6, 6, 6, 6}; // Le masque attendu pour ces caractères spéciaux est 6

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
    int[] result = checker.maskAff(password);

    for (int i = 0; i < expectedMask.length; i++) {
      assertEquals(expectedMask[i], result[i], "Le masque généré ne correspond pas à l'attendu à l'indice " + i);
    }
  }

  /**
   * Teste un mot de passe avec des caractères numériques.
   * Ce test vérifie que les chiffres génèrent un masque correct.
   */
  @Test
  void testMaskAffNumericPassword() throws IOException {
    String password = "12345"; // Mot de passe contenant des chiffres
    int[] expectedMask = {5, 5, 5, 5, 5}; // Le masque attendu pour des chiffres est 5

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
    int[] result = checker.maskAff(password);

    // Vérifie que le masque pour des chiffres est correct
    for (int i = 0; i < expectedMask.length; i++) {
      assertEquals(expectedMask[i], result[i], "Le masque généré ne correspond pas à l'attendu à l'indice " + i);
    }
  }

  /**
   * Teste un mot de passe avec des lettres majuscules.
   * Ce test vérifie que les lettres majuscules génèrent un masque correct.
   */
  @Test
  void testMaskAffUppercaseLetters() throws IOException {
    String password = "HBK"; // Mot de passe contenant des lettres majuscules
    int[] expectedMask = {4, 4, 4}; // Le masque attendu pour les majuscules est 4

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
    int[] result = checker.maskAff(password);

    // Vérifie que le masque pour des lettres majuscules est correct
    for (int i = 0; i < expectedMask.length; i++) {
      assertEquals(expectedMask[i], result[i], "Le masque généré ne correspond pas à l'attendu à l'indice " + i);
    }
  }

  @Test
  void testGetDistance() throws IOException {
    String a = "Isim@_Ariri07";
    double result = AwesomePasswordChecker.getInstance().getDistance(a);


    // Vérifie que la distance est correcte
    assertEquals(9.208554713544617, result);
  }

  @Test
  void testIsMd5() {
    // Exemple de mot de passe pour lequel on veut tester le calcul du MD5
    String input = "Isim@_Ariri07";

    assertNotNull(input);

  }

  @Test
  void testComputeMd5() {
    // Exemple de mot de passe pour lequel on veut tester le calcul du MD5
    String input = "Ariri";

    // Calcul du hash MD5 attendu, basé sur la sortie de computeMd5
    String expectedMd5 = "7c97c23d629468570ee3637d15e0eb30";

    // Calcul du MD5 avec la méthode computeMd5
    String result = AwesomePasswordChecker.computeMd5(input);

    // Vérifie que le résultat du calcul MD5 correspond à ce qui est attendu
    assertEquals(expectedMd5, result, "Le hash MD5 calculé ne correspond pas à l'attendu.");
  }

  @Test
  public void testComputeMd5Performance() {
    // Données de test
    String input = "Isima@_Ariri07";
    int numIterations = 1000; // Nombre d'itérations pour tester la performance

    // Initialisation de Perf4J
    StopWatch stopWatch = new LoggingStopWatch();

    // Début de la mesure de temps
    stopWatch.start();

    for (int i = 0; i < numIterations; i++) {
      // Appel de la méthode computeMd5
      String result = AwesomePasswordChecker.computeMd5(input);
    }

    // Fin de la mesure de temps
    stopWatch.stop();

    // Calcul du temps total et moyen
    long totalExecutionTime = stopWatch.getElapsedTime();
    double averageExecutionTime = (double) totalExecutionTime / numIterations;

    // Afficher les résultats
    System.out.println("Temps total d'exécution : " + totalExecutionTime + " ms");
    System.out.println("Temps moyen par itération : " + averageExecutionTime + " ms");

    // Vérifier si le temps moyen par itération est acceptable
    assertTrue(averageExecutionTime < 1.0, "La méthode computeMd5 est trop lente.");
  }

}






