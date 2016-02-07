/**
 * Sorts files to the correct folder on the specified external hard drive.
 *
 * @author Kevin Tan
 * @version 5.0
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileSort
{
	public static ArrayList<String> sourceList = new ArrayList<String>(); // List of files on computer.
	public static int successCounter 		= 0; // Keeps track of how many files moved.
	public static int failCounter 			= 0; // Keeps track of how many files failed.
	public static int newDirectoryCounter 	= 0; // Keeps track of the amount of new directories created.

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
	 * Gets the folder the file will move to by removing the last	characters.
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

		// Check if arguments are present.
		if (args.length < 2)
		{
			System.out.println("Usage:\t java FileSort [source path] [destination path]\n");
			System.exit(1);
		}

		// Get source and destination directories from user input.
		File source = new File(args[0]);
		File destination = new File(args[1]);

		// Get file names and add them to a list.
		File[] filesToSort = source.listFiles();

		// Check if the directory has any files.
		if (filesToSort.length < 2)
		{
			System.out.println("Nothing to sort in: " + args[0] + "\n");
			System.exit(1);
		}

		// Add file names to a string list.
		for (int i = 0; i < filesToSort.length; i++)
			sourceList.add(filesToSort[i].getName());

		// Removes the hidden folders.
		removeHidden(sourceList);

		// Print Source List
		System.out.println("Source files (to be sorted):");

		for (int i = 0; i < sourceList.size(); i++)
			System.out.println(sourceList.get(i));

		// Move the files to the correct folder.
		System.out.println("\nBegin moving files:");

		start = System.nanoTime(); // Start timer.

		// Start loop of moving each file on the list.
		for (int i = 0; i < sourceList.size(); i++)
		{
			// Get the folder name where the file will be moved to.
			String destinationName = getFolder(sourceList.get(i));

			// Get paths.
			Path sourcePath = Paths.get(source + "/" + sourceList.get(i));
			Path destinationPath = Paths.get(destination + "/" + destinationName + "/" + sourceList.get(i));

			System.out.println("Moving:\t" + sourceList.get(i) + "\n");
			System.out.println("To:\t" + destination + "/" + destinationName);

			try
			{
				// Create a new folder if the folder doesn't exist.
				boolean result = new File(destination + "/" + destinationName).mkdir();
				if (result)
				{
					System.out.println("* New directory created:\t" + destination + "/" + destinationName);

					newDirectoryCounter++;
				}

				System.out.print("Status:\t");

				Files.move(sourcePath, destinationPath); // Move files to destination.
				successCounter++;

				System.out.print("Success!\n");
			}
			catch (IOException e)
			{
				failCounter++;

				System.out.print("*****FAIL*****\n");
				System.out.print(e.toString() + "\n");
			}

			System.out.print("\n");
		}

		stop = System.nanoTime() - start; // Stop timer.
		stop /= 1000000000; // Convert to seconds.

		System.out.print("\n");
		System.out.println("-----------------------------------------");
		System.out.println("---             Summary               ---");
		System.out.println("-----------------------------------------");

		// If more than 60 seconds, print out minutes and seconds separately.
		if (stop >= 60)
		{
			long min = stop / 60;
			long rsec = stop - min * 60;

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
