/**
 * 
 */
package edu.ncsu.csc216.wolf_scheduler.scheduler;

import java.util.ArrayList;
import edu.ncsu.csc216.wolf_scheduler.course.Course;
import edu.ncsu.csc216.wolf_scheduler.io.CourseRecordIO;

/**
 * Sets schedule and catalog
 * @author Justin Cristinziano
 */
public class WolfScheduler {

	/** A catalog of the courses */
	private ArrayList<Course> catalog = new ArrayList<Course>();
	/** The schedule with all of the user's courses */
	private ArrayList<Course> schedule = new ArrayList<Course>();
	/** The schedule's title */
	private String title;
	/** The number of columns in schedule */
	private static final int COLUMN_COUNT = 3;
	/** Full schedule column count */
	private static final int FULL_COLUMN_COUNT = 6;
/**
 * Constructor that sets the schedule and title fields (and catalog
 * 	from a file name provided)
 * @param fileName the name of the file
 */
	public WolfScheduler(String fileName) {
		this.schedule = new ArrayList<>();
		this.title = "My Schedule";

		try {
			catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot find file.");
		}
	}
/**
 * Returns the catalog's courses (their name, section, and title)
 * @return catalogArray the Course catalog
 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[catalog.size()][COLUMN_COUNT];

		for (int i = 0; i < catalog.size(); i++) {
			catalogArray[i][0] = catalog.get(i).getName();
			catalogArray[i][1] = catalog.get(i).getSection();
			catalogArray[i][2] = catalog.get(i).getTitle();
		}
		return catalogArray;
	}
/**
 * provides the schedule (each courses name, section, and title)
 * @return scheduleArray a 2D array of all the courses in schedule
 */
	public String[][] getScheduledCourses() {
		String[][] scheduleArray = new String[schedule.size()][COLUMN_COUNT];

		for (int i = 0; i < schedule.size(); i++) {
			scheduleArray[i][0] = schedule.get(i).getName();
			scheduleArray[i][1] = schedule.get(i).getSection();
			scheduleArray[i][2] = schedule.get(i).getTitle();
		}
		return scheduleArray;
	}
/**
 * Returns a 2D array of the schedule courses (their name, section, title,
 * 	credits, instructorID, and meetingString)
 * @return fullScheduleArray is a 2D array of all the schedule courses and
 * 	their fields
 */
	public String[][] getFullScheduledCourses() {
		String[][] fullScheduleArray = new String[schedule.size()][FULL_COLUMN_COUNT];

		for (int i = 0; i < schedule.size(); i++) {
			fullScheduleArray[i][0] = schedule.get(i).getName();
			fullScheduleArray[i][1] = schedule.get(i).getSection();
			fullScheduleArray[i][2] = schedule.get(i).getTitle();
			fullScheduleArray[i][3] = Integer.toString(schedule.get(i).getCredits());
			fullScheduleArray[i][4] = schedule.get(i).getInstructorId();
			fullScheduleArray[i][5] = schedule.get(i).getMeetingString();
		}
		return fullScheduleArray;
	}
/**
 * Returns the schedule title
 * @return title the Schedule's title
 */
	public String getScheduleTitle() {
		return title;
	}
/**
 * provides the course from the catalog
 * @param name the name of the course you want returned
 * @param section the section of the course you want returned
 * @return the course you specified or null if it doesn't exist in catalog
 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				return catalog.get(i);
			}
		}

		return null;
	}
/**
 * adds a course to the schedule and returns true if successful (false if not)
 * @param name the name of the Course
 * @param section the section number of Course
 * @return true if addition was successful and false if not
 */
	public boolean addCourseToSchedule(String name, String section) {
		// The number that correlates to the requested course (will change)
		if(catalog.isEmpty()) {
			return false;
		}
		
		for (int i = 0; i < catalog.size(); i++) {
			// if course exists in catalog
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				// check to see if it's in schedule arrayList
				for (int j = 0; j < schedule.size(); j++) {
					// if it does exist in schedule arrayList, throw exception.
					if (name.equals(schedule.get(j).getName())) {
						throw new IllegalArgumentException("You are already enrolled in " + name);
					}
				}
				// if schedule doesn't contain catalog(i), add it to schedule arrayList
				schedule.add(catalog.get(i));
				return true;
			} 
		} return false;
	}
/**
 * Removes a course from the schedule
 * @param name is the name of the course you want to remove
 * @param section is the section of the course you want to remove
 * @return true if removal was successful and false if not
 */
	public boolean removeCourseFromSchedule(String name, String section) {
		for(int i = 0; i < schedule.size(); i++) {
			if(name.equals(schedule.get(i).getName()) && section.equals(schedule.get(i).getSection())) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}
/**
 * Resets schedule to an empty ArrayList
 */
	public void resetSchedule() {
		schedule = new ArrayList<Course>();
	}
/**
 * Provides the schedule's title
 * @param title the schedule's title
 */
	public void setScheduleTitle(String title) {
		if(title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		} else {
			this.title = title;
		}

	}
/**
 * Exports the schedule to a specified file
 * @param fileName the file you want to export the schedule to
 */
	public void exportSchedule(String fileName) {
		try {
			CourseRecordIO.writeCourseRecords(fileName, schedule);
		} catch (Exception e) {
			throw new IllegalArgumentException("The file cannot be saved.");
		}
	}

}
