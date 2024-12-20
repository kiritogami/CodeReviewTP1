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


    assertNotNull(checker, "L'instance ne doit pas être nulle");
  }

  /**
   * Teste la méthode {@link AwesomePasswordChecker#maskAff(String)} pour un mot de passe donné.
   * Ce test vérifie le masque généré pour différentes catégories de caractères dans le mot de passe.
   */
  @Test
  void testMaskAff() throws IOException {
    String password = "Isim@07";
    int[] expectedMask = {3, 1, 1, 2,6,5,5};

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
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
    int[] expectedMask = new int[28];

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
    int[] result = checker.maskAff(password);


    assertArrayEquals(expectedMask, result, "Le masque pour un mot de passe vide ne doit contenir que des zéros.");
  }

  /**
   * Teste un mot de passe qui contient un seul caractère.
   * Ce test vérifie que la méthode masque correctement un seul caractère du mot de passe.
   */
  @Test
  void testMaskAffSingleCharacterPassword() throws IOException {
    String password = "k";
    int[] expectedMask = {2};

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
    String password = ">!?@";
    int[] expectedMask = {6, 6, 6, 6};

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
    String password = "12345";
    int[] expectedMask = {5, 5, 5, 5, 5};

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
    String password = "HBK";
    int[] expectedMask = {4, 4, 4};

    AwesomePasswordChecker checker = AwesomePasswordChecker.getInstance();
    int[] result = checker.maskAff(password);


    for (int i = 0; i < expectedMask.length; i++) {
      assertEquals(expectedMask[i], result[i], "Le masque généré ne correspond pas à l'attendu à l'indice " + i);
    }
  }

  /**
   * Teste la méthode {@link AwesomePasswordChecker#getDistance(String)}.
   * Ce test vérifie la distance calculée entre un mot de passe donné et la ressource de référence.
   */
  @Test
  void testGetDistance() throws IOException {
    String a = "Isim@_Ariri07";
    double result = AwesomePasswordChecker.getInstance().getDistance(a);



    assertEquals(9.208554713544617, result);
  }

  /**
   * Teste la Md5().
   * Ce test vérifie que la méthode fonctionne correctement en vérifiant si un mot de passe est un hash MD5.
   */
  @Test
  void testIsMd5() {

    String input = "Isim@_Ariri07";
    String result = AwesomePasswordChecker.computeMd5(input);
    assertNotNull(result);
  }

  /**
   * Teste la méthode {@link AwesomePasswordChecker#computeMd5(String)}.
   * Ce test vérifie que le calcul du MD5 pour un mot de passe donné génère le bon hash.
   */
  @Test
  void testComputeMd5() {

    String input = "Ariri";


    String expectedMd5 = "7c97c23d629468570ee3637d15e0eb30";


    String result = AwesomePasswordChecker.computeMd5(input);


    assertEquals(expectedMd5, result, "Le hash MD5 calculé ne correspond pas à l'attendu.");
  }

  /**
   * Teste la performance de la méthode {@link AwesomePasswordChecker#computeMd5(String)}.
   * Ce test mesure le temps d'exécution de la méthode pour plusieurs itérations et vérifie
   * que la performance est acceptable.
   */
  @Test
  public void testComputeMd5Performance() {

    String input = "Isima@_Ariri07";
    int numIterations = 1000;


    StopWatch stopWatch = new LoggingStopWatch();


    stopWatch.start();

    for (int i = 0; i < numIterations; i++) {

      String result = AwesomePasswordChecker.computeMd5(input);
    }


    stopWatch.stop();


    long totalExecutionTime = stopWatch.getElapsedTime();
    double averageExecutionTime = (double) totalExecutionTime / numIterations;


    System.out.println("Temps total d'exécution : " + totalExecutionTime + " ms");
    System.out.println("Temps moyen par itération : " + averageExecutionTime + " ms");

    assertTrue(averageExecutionTime < 1.0, "La méthode computeMd5 est trop lente.");
  }

}






