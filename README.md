filesort
========

This was created for personal uses. Originally written in Java in a way to practice Java while creating something useful.

Takes the files from the given source path folder and moves them into a folder in the given destination folder. The folder it will be moved into is determined by the file name format "[Folder To Be Moved Into] xxxxxx.xxx".

So if the file name is `Bananas 000001.jpg`, that file will be moved into a folder named `Bananas`.

## Usage
First compile `FileSort.java` by running `javac FileSort.java`.

Then call the following command:
```
java FileSort [source path] [destination path]
```

## Process
1. Takes user input of the path on the external hard drive where the files will move to.
2. Creates a list of files that need to be moved.
3. Removes any hidden files on the list.
4. Gets the destination folder by removing the last 11 characters of the file name.
5. Creates the destination folder if one does not already exist.
6. Move the file to the destination.
7. Repeat steps 4-6 for each file on the list.

## Postconditions
- If folder does not exist in the destination path, one will be created.
- A summary will be given of time elapsed and how many files moved successfully or failed.
