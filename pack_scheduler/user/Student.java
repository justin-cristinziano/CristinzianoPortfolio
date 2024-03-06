package edu.ncsu.csc216.pack_scheduler.user;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * creates Student objects with name, id, email, password, and max credits.
 * contains getters and setter methods, hash and equals, toString, and compareTo
 * has Comparable interface implemented 
 * 
 * @author Morgan Rivera
 * @author Stuti Gautam
 * @author Andrew Steinl
 */
public class Student extends User implements Comparable<Student> {
	
	/** Student's max credits */
	private int maxCredits;
	/** Maximum number of credits */
	public static final int MAX_CREDITS = 18;
	/** student's schedule */
	private Schedule schedule = new Schedule();
	
	/**
	 * Constructs a Student object with values for all fields.
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id id of student
	 * @param email email of student
	 * @param hashPW password of student
	 * @param maxCredits max credits of student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(hashPW);
		setMaxCredits(maxCredits);
	}
	
	/**
	 * Constructs a Student object with values for all fields.
	 * @param firstName first name of student
	 * @param lastName last name of student
	 * @param id id of student
	 * @param email email of student
	 * @param hashPW password of student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		super(firstName, lastName, id, email, hashPW);
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(hashPW);
		setMaxCredits(18);
	}
	
	/**
	 * Returns max credits
	 * @return the maxCredits
	 */
	public int getMaxCredits() {
		return maxCredits;
	}
	
	/**
	 * Sets max credits. Throws IllegalArgumentException less than 3 or greater than 18
	 * @param maxCredits to set maxCredits
	 * @throws IllegalArgumentException if null or empty
	 */
	public void setMaxCredits(int maxCredits) {
		if(maxCredits < 3 || maxCredits > MAX_CREDITS)
			throw new IllegalArgumentException("Invalid max credits");
		this.maxCredits = maxCredits;
	}
	
	
	
	/**
	 * Returns a comma separated value String of all the Student fields.
	 * @return String representation of Student
	 */
	@Override
	public String toString() {
		return getFirstName() + "," + getLastName()  + "," +  getId() + "," + getEmail() + "," +  getPassword()  + "," +  maxCredits;
	}
	
	/**
	 * Returns a negative number if student s is alphabetically before this student, it 
	 * returns a 0 if they are alphabetically the same, and a positive number if student s comes
	 * after this student alphabetically
	 * @param s student being compared with 
	 * @return result showing which student comes first alphabetically 
	 * @throws NullPointerException if the given student is null
	 * @throws ClassCastException if the given student is not a student object
	 */
	@Override
	public int compareTo(Student s) {
		
		
		// throws a null pointer exception if the given student is null
		if (s == null) {
			throw new NullPointerException();
		}
		// throws a ClassCastException if the given student is not a student object
		if (!(s instanceof Student)) {
			throw new ClassCastException(); 
		}
		// takes last names from students
		
		
		
		if (this.getLastName().compareTo(s.getLastName()) == 0) {
			if (this.getFirstName().compareTo(s.getFirstName()) == 0) {
				if (this.getId().compareTo(s.getId()) == 0) {
					return 0;
				} else {
					return this.getId().compareTo(s.getId());
				}
			} else {
				return this.getFirstName().compareTo(s.getFirstName());
			}
		} else {
			return this.getLastName().compareTo(s.getLastName());
		}
			
	}

	/**
	 * generates a hash value for a student's credits 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + maxCredits;
		return result;
	}

	/**
	 * Determines if two objects are equal to one another.
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
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
		
	}
	
	/**
	 * returns the Student's schedule
	 * @return student schedule
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	/**
	 * returns true if the Course can be added to the Student’s Schedule. 
	 * If the Course is null, if the Course is already in the schedule, if there is a conflict, 
	 * or if the Student has no more room in their schedule for the course, return false
	 * @param course course to be checked
	 * @return boolean based on if course can be added or not
	 */
	public boolean canAdd(Course course) {
    	if (course == null) {
    		return false;
    	}
    	if (schedule.canAdd(course)) {
    		int totalCredits = schedule.getScheduleCredits() + course.getCredits();
    		return totalCredits <= maxCredits;
    	}
    	return false;
	}
}
