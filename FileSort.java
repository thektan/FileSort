/**
 * Sorts files to the correct folder on the specified external hard drive.
 * 
 * 1. Takes user input of the path on the external hard drive where the files will move to.
 * 2. Creates a list of files that need to be moved.
 * 3. Moves the files to the destination folder according to the file name.
 * 
 * Preconditions:
 * - Source files must be in the directory "~/Downloads/Sort"
 * - Files in "Sort" folder must be in the format of "[Name] xxxxxx.xxx"
 * 
 * @author Kevin Tan
 * @version 5.0
 * @date 05/03/2015
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileSort 
{
	// Define directories.
	// *************** DEFINE SOURCE AND EXTERNAL PATHS ***************
	public static final File SOURCE = new File(System.getProperty("user.home") + "/"); // Files to sort.
	public static final String EXTERNAL = "/"; // Destination of files.
	// ****************************************************************

	public static ArrayList<String> sourceList = new ArrayList<String>(); // List of files on computer.
	public static int successCounter = 0, failCounter = 0; // Keeps track of how many files were moved or failed.
	public static int newDirectoryCounter = 0; // Keeps track of the amount of new directories created.

	// Times how long it took to move all the files.
	public static long start, stop;
	
	/**
	 * Removes any folders of files that start with ".".
	 * @param list the list of files to filter out.
	 */
	public static void removeHidden(ArrayList<String> list)
	{
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i).substring(0,1).equals("."))
			{
				list.remove(i);
				i--; // Decrease index count when an item gets removed.
			}
		}
	}
	
	/**
	 * Gets the folder the file will move to by removing the last  characters.
	 * @param file the file that will be moved.
	 * @return folder that the file will move to.
	 */
	public static String getFolder(String file)
	{
		return file.substring(0, file.length() - 11);
	}
	
	/**
	 * Creates the lists and moves the files.
	 * @param args the destination path on the external.
	 */
	public static void main(String[] args) 
	{
		System.out.println("-----------------------------------------");
		System.out.println("---             File Sort             ---");
		System.out.println("---            Version 5.0            ---");
		System.out.println("-----------------------------------------\n");

		// Get the destination of where the folders are from user input.
		File destination = new File(EXTERNAL);
		
		// Get file names and add them to a list.
		File[] filesToSort = SOURCE.listFiles(); 
		
		// Add file names to a string list.
	    for (int i = 0; i < filesToSort.length; i++) 
		      sourceList.add(filesToSort[i].getName());
	    
	    // Removes the hidden folders.
		removeHidden(sourceList);
		
		// PRINT Source List
		System.out.println("Source files (to be sorted):");

	    for (int i = 0; i < sourceList.size(); i++) 
		      System.out.println(sourceList.get(i));
		    
	    // Move the files to the correct folder.
		System.out.println("\nBegin moving files:");
		
		start = System.nanoTime(); // Start timer.
		
		// Start loop of moving each file on the list.
	    for (int i = 0; i < sourceList.size(); i++)
	    {
	    	String destinationName = getFolder(sourceList.get(i)); // Get the folder name where the file will be moved to.
	    	
	    	// Get paths.
	    	Path sourcePath = Paths.get(SOURCE + "/" + sourceList.get(i));
	    	Path destinationPath = Paths.get(destination + "/" + destinationName + "/" + sourceList.get(i));
	    	
	    	// PRINT
	    	System.out.println("Moving:\t" + sourceList.get(i) + "\nTo:\t" + destination + "/" + destinationName); 

	    	try 
	    	{
	    		// Create a new folder if the folder doesn't exist.
	    		boolean result = new File(destination + "/" + destinationName).mkdir();
	    		if (result) 
	    		{
	    			System.out.println("* New directory created:\t" + destination + "/" + destinationName); // PRINT
	    			newDirectoryCounter++;
	    		}

	    		System.out.print("Status:\t");
				Files.move(sourcePath, destinationPath); // Move files to destination.
				successCounter++;
				System.out.print("Success!\n"); // PRINT
			} 
	    	catch (IOException e) 
	    	{
	    		failCounter++;
	    		System.out.print("*****FAIL*****\n" + e.toString() + "\n"); // PRINT
			}
	    	
	    	System.out.print("\n");
	    }
 
	    stop = System.nanoTime() - start; // Stop timer.
	    stop /= 1000000000; // Convert to seconds.
	    
	    // PRINT
	    System.out.println("\n-----------------------------------------");
	    System.out.println("---              Summary              ---");
	    System.out.println("-----------------------------------------");

	    // If more than 60 seconds, print out minutes and seconds separately.
	    if (stop >= 60)
	    {
	    	long min = stop/60;
	    	long rsec = stop - min*60;
	    	System.out.println("Time elapsed: ...................... " + min + "min " + rsec + "s");
	    }
	    else
	    {
	    	System.out.println("Time elapsed: ...................... " + stop + "s");
	    }

	    System.out.println("Files to be moved: ................. "	+ sourceList.size());
	    System.out.println("Files successfully moved: .......... "	+ successCounter); 
	    System.out.println("Files failed to moved: ............. "	+ failCounter);
	    System.out.println("New directories created: ........... " 	+ newDirectoryCounter);

	    if (failCounter == 0) System.out.println("\n***** File Sort Successful! *****\n");
	}

}
