package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.IOException;


import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Object defining a catalog consisting of all the available courses
 * 
 * @author Morgan Rivera
 * @author Stuti Gautam
 * @author Andrew Steinl
 */
public class CourseCatalog {
	
	/** field that contains the course catalog */
	private SortedList<Course> catalog;
	
	
	/** 
	 * CourseCatalog constructor; constructs an empty catalog
	 */
	public CourseCatalog() {
		this.catalog = new SortedList<>();
	}
	
	/**
	 * Creates a new Course catalog
	 */
	public void newCourseCatalog() {
		this.catalog = new SortedList<>();
	}
	
	/**
	 * Loads a sortedList of courses into the catalog
	 * @param fileName the name of the file being pulled from
	 * @throws IllegalArgumentException if file not found
	 */
	public void loadCoursesFromFile(String fileName)  {
		
		catalog = new SortedList<>();
		
		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
	
	/**
	 * adds a Course with the following fields to the catalog and 
	 * returns true if the Course is added and false if the Course already exists in the catalog	 
	 * @param name of the course
	 * @param title of the course
	 * @param section of the course
	 * @param credits number of credits of the course
	 * @param instructorId of the course
	 * @param meetingDays of the course
	 * @param startTime of the course
	 * @param endTime of the course
	 * @param enrollmentCap enrollment cap of course
	 * @return true if the course was successfully added, false if not
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits,
			String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		
		Course course;
		Course newCourse = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		for (int i = 0; i < catalog.size(); i++) {
			course = catalog.get(i);
			if (name.equals(course.getName()) && section.equals(course.getSection())) {
				return false;
			}
		}
		
		catalog.add(newCourse);
		
		return true;
	}
	
	/**
	 * Removes a course from the catalog if it exists
	 * returns true if the Course is removed from the catalog and 
	 * false if the Course is not in the catalog
	 * @param name of the removed course
	 * @param section of the removed course
	 * @return true if the Course is removed from the catalog, false if not
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		
		Course course;
		boolean exists = false;
		for (int i = 0; i < catalog.size(); i++) {
			course = catalog.get(i);
			if (name.equals(course.getName()) && section.equals(course.getSection())) {
				exists = true;
				catalog.remove(i);
			}
		}
		return exists;
	}
	
	/**
	 * returns the Course from the catalog with the given name and section or 
	 * returns null if the Course isn’t in the catalog
	 * @param name of the desired course
	 * @param section of the desired course
	 * @return Course that matches the name and section, null if course doesn't exist
	 */
	public Course getCourseFromCatalog(String name, String section) {
		
		for (int i = 0; i < catalog.size(); i++) {
			Course course = catalog.get(i);
			if (name.equals(course.getName()) && section.equals(course.getSection())) {
				return course;
			}
		}
		return null;
	}
	
	/**
	 * returns the name, section, title, and meeting information for Courses in the catalog
	 * @return a 2d String array of the courses in the catalog
	 */
	public String[][] getCourseCatalog(){
        String[][] courseCatalog = new String[catalog.size()][5]; 

        for (int i = 0; i < catalog.size(); i++) {
            Course course = catalog.get(i);
            String[] courseInfo = course.getShortDisplayArray();
            courseCatalog[i] = courseInfo; 
        }

        return courseCatalog;
	}
	
	/**
	 * saves the catalog course records to the given file
	 * @param fileName the name of the file being exported to
	 * @throws IllegalArgumentException if any IOExceptions are caught
	 */
	public void saveCourseCatalog(String fileName)  {
		try {
			CourseRecordIO.writeCourseRecords(fileName, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}
}
