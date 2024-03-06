package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;
/**
 * Reads, writes, and processes student objects from a file using the sortedList type class. 
 * @author Morgan Rivera
 * @author Stuti Gautam
 * @author Andrew Steinl
 */
public class StudentRecordIO {
	/**
	 * Reads file and makes student objects. FileNotFoundException if can't be read or found.
	 * @param fileName file with student fields
	 * @return list of students
	 * @throws FileNotFoundException if can't be read or found
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException{
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); 
		SortedList<Student> students = new SortedList<Student>(); 
	    while (fileReader.hasNextLine()) {
	        try {
	        	students.add(processStudent(fileReader.nextLine()));
	        } 
	        catch (Exception e) {
	        	System.out.print("");
	        }
	    }
	    fileReader.close();
	    return students;
	}
	/**
	 * Writes the list of students and saves in file given
	 * @param fileName file to write student directory
	 * @param studentDirectory list to write list
	 * @throws IOException if cannot write to file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < studentDirectory.size(); i++) {
    		fileWriter.println(studentDirectory.get(i).toString());
		}
		fileWriter.close();
		
	}
	/**
	 * Reads student file line and sets students fields. If student can't be created
	 * an IllegalArgumentException is thrown.
	 * @param line that is read
	 * @return course created
	 * @throws IllegalArgumentException if course can't be created
	 */
	@SuppressWarnings("resource")
	private static Student processStudent(String line) {
		Scanner scan = new Scanner(line);
		scan.useDelimiter(",");
		try {
			String firstName = scan.next();
			String lastName = scan.next();
			String id = scan.next();
			String email = scan.next();
			String password = scan.next();
			int maxCredits = scan.nextInt();
			return new Student(firstName, lastName, id, email,  password,  maxCredits);
		} 
		catch (NoSuchElementException e) {
			scan.close();
			throw new IllegalArgumentException("");
		}
	}
}