package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;
/**
 * This class writes to and reads files
 */
public class FacultyRecordIO {

	/**
	 * Reads file and makes faculty objects. FileNotFoundException if can't be read or found.
	 * @param fileName file with faculty fields
	 * @return list of faculty
	 * @throws FileNotFoundException if can't be read or found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException{
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); 
		LinkedList<Faculty> facultyList = new LinkedList<Faculty>(); 
	    while (fileReader.hasNextLine()) {
	        try {
	        	facultyList.add(processFacultyMember(fileReader.nextLine()));
	        } 
	        catch (Exception e) {
	        	System.out.print("");
	        }
	    }
	    fileReader.close();
	    return facultyList;
	}
	
	/**
	 * Reads student file line and sets students fields. If student can't be created
	 * an IllegalArgumentException is thrown.
	 * @param line that is read
	 * @return course created
	 * @throws IllegalArgumentException if course can't be created
	 */
	private static Faculty processFacultyMember(String line) {
		Scanner scan = new Scanner(line);
		scan.useDelimiter(",");
		try {
			String firstName = scan.next();
			String lastName = scan.next();
			String id = scan.next();
			String email = scan.next();
			String password = scan.next();
			int maxCourses = scan.nextInt();
			
			scan.close();
			return new Faculty(firstName, lastName, id, email,  password,  maxCourses);
		} 
		catch (NoSuchElementException e) {
			scan.close();
			throw new IllegalArgumentException("");
		}
	}
	

	/**
	 * This method writes the faculty directory to a file
	 * 
	 * @param fileName         is the name the user wants to print to
	 * @param facultyDirectory is the directory of faculty members
	 * @throws IOException if there is an error while writing to the file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		try {
			PrintStream fileWriter = new PrintStream(new File(fileName));
			for (int i = 0; i < facultyDirectory.size(); i++) {
				fileWriter.println(facultyDirectory.get(i).toString());
			}
			fileWriter.close();
		} catch (Exception e) {
			throw new IOException();
		}
	}
}
