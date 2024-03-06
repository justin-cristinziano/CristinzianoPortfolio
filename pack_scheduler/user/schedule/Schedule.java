/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * The class creates a schedule of courses. It checks for duplicate courses and checks if there are any conflicts.
 * It allows user to add or remove a course from the schedule, as well as reset the schedule
 * @author Ananya Rao
 * @author Stuti Gautam
 * @author Connor Young
 */
public class Schedule {

	/** Private field for schedule title */
	private String title;
	
	/** Private field of custom array list of courses in schedule */
    private ArrayList<Course> schedule;

    /**
     * Constructor for schedule class. Sets a default title and creates an empty array list of courses
     */
    public Schedule() {
        title = "My Schedule";
        schedule = new ArrayList<Course>();
    }
    
    /**
     * Getter method for title
     * @return title of schedule
     */
    public String getTitle() {
    	return title;
    }
    
    /**
     * Method for adding a course to a schedule. Checks for duplicate courses and conflicts in the schedule
     * @param course course being added
     * @return true if course successfully added
     * @throws IllegalArgumentException if there is a duplicate course or if there is a schedule conflict
     */
    public boolean addCourseToSchedule(Course course) {
    	
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isDuplicate(course)) {
                throw new IllegalArgumentException("You are already enrolled in " + course.getName());
            }
             
            try {
              schedule.get(i).checkConflict(course);
            } catch (ConflictException e) {
                throw new IllegalArgumentException("The course cannot be added due to a conflict.");
            }
        }
        schedule.add(course);
        return true;
    }
    
    /**
     * This method removes a course from the schedule
     * @param course course being removed
     * @return true if course successfully removed and false if no course is selected
     */
    public boolean removeCourseFromSchedule(Course course) { 

    	if (schedule == null || schedule.isEmpty()) {
            return false;
        }
    	
    	if (course == null) {
    		return false;
    	}
    	
       
    	for (int i = 0; i < schedule.size(); i++) {
    		if (schedule.get(i) == null) {
    			return false;
    		}
    		if (course.equals(schedule.get(i))) {
    			schedule.remove(i);
                return true;
            }
        }
        return false;
    }
    
    /**
     * Resets the schedule to default title and empty list
     */
    public void resetSchedule() {
        title = "My Schedule";
        schedule = new ArrayList<Course>();
    }
    
    /**
     * Returns a 2D array of Course information. 
     * Each row should be a Course and the columns are name, section, title, and the meeting string
     * @return 2D array of course information
     */
    public String[][] getScheduledCourses() {
    	if (schedule == null || schedule.isEmpty()) {
            return new String[0][0];
        } else {
            String[][] scheduledCoursesArr = new String[schedule.size()][5];
            
            for (int i = 0; i < schedule.size(); i++) {
                Course course = schedule.get(i);
                scheduledCoursesArr[i] = course.getShortDisplayArray();
            }
            return scheduledCoursesArr;
        }
    }
    
    /**
     * Sets the title of schedule
     * @param title title of schedule
     * @throws IllegalArgumentException if title is null
     */
    public void setTitle(String title) {
        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null.");
        }
        this.title = title;
    }
    
    /**
     * cumulative sum that returns the total credits in the Schedule
     * @return total credits in schedule
     */
    public int getScheduleCredits() {
    	int totalCredits = 0;
        for (Course course : schedule) {
            totalCredits += course.getCredits();
        }
        return totalCredits;
    }
    
    /**
     * returns true if the Course can be added to the schedule. If the Course is null, 
     * if the Course is already in the schedule, or if there is a conflict, returns false.
     * @param course course to be checked 
     * @return boolean based on if course can be added or not
     */
    public boolean canAdd(Course course) {
        if (course == null) {
            return false;
        }
        for (Course c : schedule) {
            if (c.isDuplicate(course)) {
                return false; 
            }
            try {
                c.checkConflict(course);
            } catch (ConflictException e) {
                return false;
            }
        }
        return true;
    }
}
