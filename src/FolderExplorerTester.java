//////////////// FILE HEADER (INCLUDE IN EVERY FILE) //////////////////////////
//
// Title: P07 Folder Content Explorer
// Course: CS 300 Fall 2021
//
// Author: Surya Somayyajula
// Email: somayyajula@wisc.edu
// Lecturer: Mouna Kacem
//
//////////////////// PAIR PROGRAMMERS COMPLETE THIS SECTION ///////////////////
//
// Partner Name: (name of your pair programming partner)
// Partner Email: (email address of your programming partner)
// Partner Lecturer's Name: (name of your partner's lecturer)
//
// VERIFY THE FOLLOWING BY PLACING AN X NEXT TO EACH TRUE STATEMENT:
// ___ Write-up states that pair programming is allowed for this assignment.
// ___ We have both read and understand the course Pair Programming Policy.
// ___ We have registered our team prior to the team registration deadline.
//
///////////////////////// ALWAYS CREDIT OUTSIDE HELP //////////////////////////
//
// Persons: NONE
// Online Sources: NONE
//
///////////////////////////////////////////////////////////////////////////////
/**
 * Tester class for making sure that the FolderExplorer methods work properly in different scenarios
 */
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.nio.file.NotDirectoryException;

public class FolderExplorerTester {
  /**
   * Testing different scenarios for the the getContents method
   * 
   * @param folder that has to be searched through
   * @return whether all tests pass or not
   */
  public static boolean testGetContents(File folder) {
    try {
      // Scenario 1
      // list the basic contents of the cs300 folder
      ArrayList<String> listContent = FolderExplorer.getContents(folder);
      // expected output must contain "exams preparation", "grades",
      // "lecture notes", "programs", "reading notes", "syllabus.txt",
      // and "todo.txt" only.
      String[] contents = new String[] {"exams preparation", "grades", "lecture notes", "programs",
          "reading notes", "syllabus.txt", "todo.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // check the size and the contents of the output
      if (listContent.size() != 7) {
        System.out.println("Problem detected: cs300 folder must contain 7 elements.");
        return false;
      }
      for (int i = 0; i < expectedList.size(); i++) {
        if (!listContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of cs300 folder.");
          return false;
        }
      }
      // Scenario 2 - list the contents of the grades folder
      File f = new File(folder.getPath() + File.separator + "grades");
      listContent = FolderExplorer.getContents(f);
      if (listContent.size() != 0) {
        System.out.println("Problem detected: grades folder must be empty.");
        return false;
      }
      // Scenario 3 - list the contents of the p02 folder
      f = new File(folder.getPath() + File.separator + "programs" + File.separator + "p02");
      listContent = FolderExplorer.getContents(f);
      if (listContent.size() != 1 || !listContent.contains("FishTank.java")) {
        System.out.println(
            "Problem detected: p02 folder must contain only " + "one file named FishTank.java.");
        return false;
      }
      // Scenario 4 - List the contents of a file
      f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        listContent = FolderExplorer.getContents(f);
        System.out.println("Problem detected: Your FolderExplorer.getContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // no problem detected
      }
      // Scenario 5 - List the contents of not found directory/file
      f = new File(folder.getPath() + File.separator + "music.txt");
      try {
        listContent = FolderExplorer.getContents(f);
        System.out.println("Problem detected: Your FolderExplorer.getContents() must "
            + "throw a NotDirectoryException if the provided File does not exist.");
        return false;
      } catch (NotDirectoryException e) {
        // behavior expected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FolderExplorer.getContents() has thrown"
          + " a non expected exception.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Testing different scenarios for the deepGetContents
   * 
   * @param folder to has to be searched through including all files, but not subfolders in this
   *               case
   * @return whether deepGetContents pass all the tests
   */
  public static boolean testDeepGetContentsBaseCase(File folder) {
    try {
      // File with directory of reading notes
      File file = new File(folder.getPath() + File.separator + "reading notes");
      // Gets all files in the directory and added to listContent
      ArrayList<String> listContent = FolderExplorer.getDeepContents(file);
      // Expected list contents
      String[] contents =
          new String[] {"zyBooksCh1.txt", "zyBooksCh2.txt", "zyBooksCh3.txt", "zyBooksCh4.txt"};
      List<String> expectedList = Arrays.asList(contents);
      // Checks if arraylist has correct size
      if (listContent.size() != 4) {
        System.out.println("Problem detected: readingNotes folder must contain 4 elements.");
        return false;
      }
      // Checks if arraylist has the correct elements
      for (int i = 0; i < expectedList.size(); i++) {
        if (!listContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of readingNotes folder.");
          return false;
        }
      }
      // Scenario 5 - List the contents of not found directory/file
      File f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        listContent = FolderExplorer.getDeepContents(f);
        System.out.println("Problem detected: Your FolderExplorer.getDeepContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // no problem detected
      }
    } catch (Exception e) {
      System.out.println("Problem detected: Your FolderExplorer.getDeepContents() has thrown"
          + " a non expected exception.");
      e.printStackTrace();
      return false;
    }

    return true;

  }

  /**
   * Testing different scenarios for the deepGetContents
   * 
   * @param folder to has to be searched through including all files and subfolders in that folder
   * @return whether deepGetContents pass all the tests
   */
  public static boolean testDeepListRecursiveCase(File folder) {
    try {
      // Checks if the deepGetContents method gets all the files from the programs folder
      File file = new File(folder.getPath() + File.separator + "programs");
      ArrayList<String> listContent = FolderExplorer.getDeepContents(file);
      String[] contents = new String[] {"ClimbingTracker.java", "ClimbingTrackerTester.java",
          "FishTank.java", "ExceptionalClimbing.java", "ExceptionalClimbingTester.java",
          "Program01.pdf", "Program02.pdf", "Program03.pdf"};
      List<String> expectedList = Arrays.asList(contents);
      // Checks if list size is correct
      if (listContent.size() != 8) {
        System.out.println("Problem detected: programs folder must contain 8 elements.");
        return false;
      }
      // Checks if all the methods are correct
      for (int i = 0; i < expectedList.size(); i++) {
        if (!listContent.contains(expectedList.get(i))) {
          System.out.println("Problem detected: " + expectedList.get(i)
              + " is missing from the output of the list contents of programs folder.");
          return false;
        }
      }
      // Scenario 5 - List the contents of not found directory/file
      File f = new File(folder.getPath() + File.separator + "todo.txt");
      try {
        listContent = FolderExplorer.getDeepContents(f);
        System.out.println("Problem detected: Your FolderExplorer.getDeepContents() must "
            + "throw a NotDirectoryException if it is provided an input which is not"
            + "a directory.");
        return false;
      } catch (NotDirectoryException e) { // catch only the expected exception
        // no problem detected
      }
      // Catches exception and prints stacktrace
    } catch (Exception e) {
      System.out.println("Problem detected: Your FolderExplorer.getDeepContents() has thrown"
          + " a non expected exception.");
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Test for whether lookUpByFileName works correctly
   * 
   * @param folder that has to be searched through, including all files and subfolders in that
   *               folder
   * @return whether it finds the correct path of the given filename
   */
  public static boolean testLookupByFileName(File folder) {
    // Creates file with cs300 directory
    // Trys to see if the lookUpByName method finds the correct path, if it doesn't then return
    // false, and exception is caught then return false
    try {
      File f = new File(folder.getPath());
      if (!"cs300\\programs\\writeUps\\Program01.pdf"
          .equals(FolderExplorer.lookupByName(f, "Program01.pdf"))) {
        return false;
      }
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Tests for whether lookByKey works correctly
   * 
   * @param folder that has to be searched through, including all files and subfolders in that
   *               folder
   * @return whether it finds the correct filenames with the particular key that was given as input
   */
  public static boolean testLookupByKeyBaseCase(File folder) {
    // Creates file with reading notes directory
    boolean val = false;
    try {
      File f = new File(folder.getPath() + File.separator + "reading notes");
      // Contains all the matching filenames in the arraylist
      ArrayList<String> match = FolderExplorer.lookupByKey(f, ".txt");
      // correctlist that contains the expected filenames
      ArrayList<String> correctlist = new ArrayList<String>();
      correctlist.add("zyBooksCh1.txt");
      correctlist.add("zyBooksCh2.txt");
      correctlist.add("zyBooksCh3.txt");
      correctlist.add("zyBooksCh4.txt");
      // checks if the arraylists contain the same elements
      val = correctlist.equals(match);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return val;

  }

  /**
   * Tests for whether lookBySize works correctly
   * 
   * @param folder that has to be searched through, including all files and subfolders in that
   *               folder
   * @return whether it finds the correct files that fall within the size range
   */
  public static boolean testLookupBySize(File folder) {
    boolean val = false;
    try {
      // sizes arraylist has all the files that fall within the size range
      ArrayList<String> sizes = new ArrayList<String>();
      sizes = FolderExplorer.lookupBySize(folder, 100, 5000);
      // correctlist that contains the expected filenames
      ArrayList<String> correctlist = new ArrayList<String>();
      correctlist.add("Inheritance.txt");
      correctlist.add("Recursion.txt");
      correctlist.add("todo.txt");
      // checks if the arraylists contain the same elements
      val = sizes.equals(correctlist);
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return val;

  }

  /**
   * Calls all the test methods with the cs300 folder
   * 
   * @param args system args
   */
  public static void main(String[] args) {
    // Prints the results of all the test methods with the cs300 folder
    System.out.println("testGetContents: " + testGetContents(new File("cs300")));
    System.out
        .println("testDeepGetContentsBaseCase: " + testDeepGetContentsBaseCase(new File("cs300")));
    System.out
        .println("testDeepListRecursiveCase: " + testDeepListRecursiveCase(new File("cs300")));
    System.out.println("testLookUpByFileName: " + testLookupByFileName(new File("cs300")));
    System.out.println("testLookUpByKeyBaseCase: " + testLookupByKeyBaseCase(new File("cs300")));
    System.out.println("testLookUpBySize: " + testLookupBySize(new File("cs300")));
  }
}
