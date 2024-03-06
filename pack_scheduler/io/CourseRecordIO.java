package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads a sets of courses from a file and writes them to another file
 *
 * @author AndrewSteinl
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of only the valid
	 * Courses. If the file to read cannot be found or the permissions are incorrect
	 * a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the ArrayList!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the ArrayList with all the courses we read!
		return courses;
	}

	/**
	 * returns the course listed on the line if it is valid Sets the instructor id
	 * to the id specified in the file if there exists a faculty member with that id
	 * 
	 * @param nextLine the line containing the course information
	 * @return course listed on the line if valid
	 * @throws IllegalArgumentException if NoSuchElementException is caught
	 */
	private static Course readCourse(String nextLine) {

		// initializes scanner and sets delimiter
		Scanner scanner = new Scanner(nextLine);
		scanner.useDelimiter(",");

		// declares variable used in getting course info
		String name;
		String title;
		String section;
		int credits;
		String instructor;
		int enrollmentCap;
		String meetingDays;
		int startTime;
		int endTime;

		// pulls course information from the line
		try {
			name = scanner.next();
			title = scanner.next();
			section = scanner.next();
			credits = scanner.nextInt();
			instructor = scanner.next();
			enrollmentCap = scanner.nextInt();
			meetingDays = scanner.next();
			// tests to make sure that if meeting days is arranged there's no further
			// information
			if ("A".equals(meetingDays)) {
				if (scanner.hasNext()) {
					scanner.close();
					throw new IllegalArgumentException();
				} else {
					Course course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
					scanner.close();
					if(RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructor) != null) {
						RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructor).getSchedule().addCourseToSchedule(course);
					}
					return course;
				}
			} else {
				startTime = scanner.nextInt();
				endTime = scanner.nextInt();
				if (scanner.hasNext()) {
					scanner.close();
					throw new IllegalArgumentException();
				}
				// creates and returns the course from the given line
				Course course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime,
						endTime);
				scanner.close();
				if(RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructor) != null) {
					RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructor).getSchedule().addCourseToSchedule(course);
				}
				return course;
			}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Writes the given list of Courses to a given file
	 * 
	 * @param fileName file to write schedule of Courses to
	 * @param courses  list of Courses to write
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
	}

}