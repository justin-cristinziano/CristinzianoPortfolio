package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * Faculty class that creates Faculty objects with name, id, email, password,
 * and max courses. contains getters and setter methods, hash and equals,
 * toString. Extends User
 */
public class Faculty extends User {

	/** num of courses faculty is willing to teach */
	private int maxCourses;
	/** min courses taught by faculty */
	public static final int MIN_COURSES = 1;
	/** max courses taught by faculty */
	public static final int MAX_COURSES = 3;
	/** The schedule of classes that this faculty member teaches*/
	private FacultySchedule schedule;

	/**
	 * constructor method that uses the superclass
	 * 
	 * @param firstName of the faculty
	 * @param lastName  of the faculty
	 * @param id        of the faculty
	 * @param email     of the faculty
	 * @param hashPW    of the faculty
	 * @param cap       of number of courses willing to be taught by the faculty
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int cap) {
		super(firstName, lastName, id, email, hashPW);
		setMaxCourses(cap);
		schedule = new FacultySchedule(id);
	}

	/**
	 * method that returns the faculty's course count
	 * 
	 * @return the maxCourses
	 */
	public int getMaxCourses() {
		return maxCourses;
	}

	/**
	 * method that sets the faculty course count to a given integer
	 * 
	 * @param maxCourses the number of courses being taught to set
	 */
	public void setMaxCourses(int maxCourses) {
		if (maxCourses > MAX_COURSES || maxCourses < MIN_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}
		this.maxCourses = maxCourses;
	}

	/**
	 * generates a hash value for a faculty's maxCourses
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCourses;
		return result;
	}

	/**
	 * Determines if two objects are equal to one another.
	 * 
	 * @param obj the object being compared to
	 * @return boolean of whether the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.maxCourses;
	}

	/**
	 * Returns a comma separated value String of all the faculty's fields.
	 * 
	 * @return String representation of Faculty
	 */
	@Override
	public String toString() {
		String r = "";
		r += this.getFirstName() + ",";
		r += this.getLastName() + ",";
		r += this.getId() + ",";
		r += this.getEmail() + ",";
		r += this.getPassword() + ",";
		r += this.getMaxCourses();
		return r;
	}

	/**
	 * This returns the schedule for the user
	 * 
	 * @return schedule the schedule of classes for this faculty member
	 */
	public FacultySchedule getSchedule() {
		return schedule;
	}

	/**
	 * Returns true if the number of courses is more than the maxCourses for the
	 * faculty member
	 * 
	 * @return true if numScheduledCourses is greater than the number of maxCourses
	 *         for the faculty member
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > maxCourses;
	}

}
