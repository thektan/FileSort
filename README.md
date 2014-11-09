FileSort
========

For personal uses.

Sorts files to the correct folder on the specified external hard drive.

#Preconditions:
- Source files must be in the directory "~/Downloads/Sort" (Can be changed using the final SOURCE variable)
- Files in "Sort" folder must be in the format of "[Name] xxxxxx.xxx"

#Process:
1. Takes user input of the path on the external hard drive where the files will move to.
2. Creates a list of files that need to be moved.
3. Removes any hidden files on the list.
4. Gets the destination folder by removing the last 11 characters of the file name.
5. Creates the destination folder if one does not already exist.
6. Move the file to the destination.
7. Repeat steps 4-6 for each file on the list.
 
#Postconditions:
- If folder does not exist in the destination path, one will be created.
- Gives a summary of time elapsed and how many files moved successfully or failed.