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
 * Contains methods for getting contents (either recursively or not) of directories based on search
 * parameters
 */
import java.io.File;
import java.nio.file.NotDirectoryException;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FolderExplorer {
  /**
   * Returns a list of the names of all files and directories in the given folder currentDirectory.
   * Throws NotDirectoryException with a description error message if the provided currentDirectory
   * does not exist or if it is not a directory
   * 
   * @param currentDirectory folder that will be searched through
   * @return ArrayList of all files and directories of the folder
   * @throws NotDirectoryException
   */
  public static ArrayList<String> getContents(File currentDirectory) throws NotDirectoryException {

    // Throws an exception if the directory given is not one or does not exist
    if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
      throw new NotDirectoryException("This directory either does not exist or is not a directory");
    }
    // Gets all filenames and directory names from the currentDirectory
    String[] pathnames = currentDirectory.list();
    // Adds all file names and directories from the pathnames array to the contents arraylist
    ArrayList<String> contents = new ArrayList<String>();
    for (String k : pathnames) {
      contents.add(k);
    }
    return contents;
  }

  /**
   * Recursive helper method for the deepGetContents method
   * 
   * @param currentDirectory folder that has to be searched through, including any files and
   *                         subfolders in that folder
   * @param contents         all filenames in that directory
   * @return contents arraylist with all filenames
   */
  private static ArrayList<String> deepHelp(File currentDirectory, ArrayList<String> contents) {
    // Iterates through currentDirectory list of files
    for (File k : currentDirectory.listFiles()) {
      // Base case: if k is a file, then add it to contents
      if (k.isFile()) {
        contents.add(k.getName());
      } else {
        // Recursive case: Recurses into the subdirectory, adds all files, then recurses back up,
        // then repeats for other subdirectories
        deepHelp(k, contents);

      }
    }
    return contents;
  }

  /**
   * Recursive helper method for the lookUp methods
   * 
   * @param currentDirectory folder that has to be searched through, including any files and
   *                         subfolders in that folder
   * @param contents         all files in that directory, and its subdirectories
   * @return contents arraylist with all files
   */
  private static ArrayList<File> lookUpHelp(File currentDirectory, ArrayList<File> files) {
    // Iterates through currentDirectory list of files
    for (File k : currentDirectory.listFiles()) {
      // Base case: if k is a file, then add it to contents
      if (k.isFile()) {
        files.add(k);
      } else {
        // Recursive case: Recurses into the subdirectory, adds all files, then recurses back up,
        // then repeats for other subdirectories
        lookUpHelp(k, files);
      }
    }
    return files;
  }

  /**
   * Recursive method that lists the names of all the files (not directories) in the given directory
   * and its sub-directories. Throws NotDirectoryException with a description error message if the
   * provided currentDirectory does not exist or if it is not a directory
   * 
   * @param currentDirectory folder that will be searched through
   * @return ArrayList of all the files of a directory and its sub-directories
   * @throws NotDirectoryException
   */
  public static ArrayList<String> getDeepContents(File currentDirectory)
      throws NotDirectoryException {
    // Throws an exception if the directory given is not one or does not exist
    if (!currentDirectory.exists() || !currentDirectory.isDirectory()) {
      throw new NotDirectoryException("This directory either does not exist or is not a directory");
    }
    // Contents arraylist is set to the deepHelp method call, which gets all files in that directory
    ArrayList<String> contents = new ArrayList<String>();
    contents = deepHelp(currentDirectory, contents);
    return contents;
  }


  /**
   * Searches the given directory and all of its sub-directories for an exact match to the provided
   * fileName. This method must be recursive or must use a recursive private helper method to
   * operate. This method returns a path to the file, if it exists. Throws NoSuchElementException
   * with a descriptive error message if the search operation returns with no results found
   * (including the case if fileName is null or currentDirectory does not exist, or was not a
   * directory)
   * 
   * @param currentDirectory folder that will be searched through
   * @param fileName         file you want to find
   * @return path of the file if it is found
   * @throws NoSuchElementException
   */
  public static String lookupByName(File currentDirectory, String fileName)
      throws NoSuchElementException {
    // Throws exception if fileName is null, directory is a directory, or if the directory exists
    if (fileName == null || !currentDirectory.isDirectory() || !currentDirectory.exists()) {
      throw new NoSuchElementException(
          "Either fileName is null, directory doesn't exist, or the directory is not a directory");
    }
    String filepath = "";
    // files arraylist, adds all files from the directory
    ArrayList<File> files = new ArrayList<File>();
    files = lookUpHelp(currentDirectory, files);
    // Trys to find filename, if found, then get path of file, else throw exception
    for (int i = 0; i < files.size(); i++) {
      if (fileName.equals(files.get(i).getName())) {
        String start = currentDirectory.getName();
        filepath = files.get(i).getPath();
        if (filepath.contains(start)) {
          filepath = filepath.substring(filepath.indexOf(start));
        }
        break;
      } else if (i == files.size() - 1) {
        throw new NoSuchElementException("Was not able to find file name");
      }
    }
    return filepath;
  }

  /**
   * Recursive method that searches the given folder and its sub-directories for ALL files that
   * contain the given key in part of their name. Returns An arraylist of all the names of files
   * that match and an empty arraylist when the operation returns with no results found (including
   * the case where currentDirectory is not a directory).
   * 
   * @param currentDirectory folder that will be searched through
   * @param key              String that has to be found in the file names
   * @return ArrayList of all the file names that contain the key
   */
  public static ArrayList<String> lookupByKey(File currentDirectory, String key) {
    // Arraylist that will have all the filenames that have the key
    ArrayList<String> matches = new ArrayList<String>();
    // files arraylist, adds all files from the directory
    ArrayList<File> files = new ArrayList<File>();
    files = lookUpHelp(currentDirectory, files);
    // Returns empty arraylist if key is null, directory is a directory, or if the directory exists
    if (!currentDirectory.isDirectory() || !currentDirectory.exists() || key == null) {
      return matches;
    }
    // Iterates through all the files, and if the filename contains the key, and adds the filename
    // to the matches arraylist
    for (File k : files) {
      if (k.getName().contains(key)) {
        matches.add(k.getName());
      }
    }
    return matches;
  }

  /**
   * Recursive method that searches the given folder and its sub-directories for ALL files whose
   * size is within the given max and min values, inclusive. Returns an array list of the names of
   * all files whose size are within the boundaries and an empty arraylist if the search operation
   * returns with no results found (including the case where currentDirectory is not a directory).
   * 
   * @param currentDirectory folder that will be searched through
   * @param sizeMin          min file size
   * @param sizeMax          max file size
   * @return ArrayList of all files that fall within the size range
   */
  public static ArrayList<String> lookupBySize(File currentDirectory, long sizeMin, long sizeMax) {
    // Arraylist that will have all the filenames that fall within the size range
    ArrayList<String> sizes = new ArrayList<String>();
    // Files that will have all the files in the currentDirectory
    ArrayList<File> files = new ArrayList<File>();
    // If the directory is not a directory, then an empty arraylist is returned
    if (!currentDirectory.isDirectory()) {
      return sizes;
    }
    // All files from the directory is added to the arraylist
    files = lookUpHelp(currentDirectory, files);
    // Iterates through files, if the file is within the size range, then the filenames is added to
    // the sizes arraylist
    for (File k : files) {
      if (k.length() >= sizeMin && k.length() <= sizeMax) {
        sizes.add(k.getName());
      }
    }
    return sizes;
  }
}
