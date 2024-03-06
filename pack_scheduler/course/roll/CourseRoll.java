/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * Creates a course roll of students. Sets the enrollment cap and gets the roll
 * size of a certain course. Allows the user to enroll, drop, set an emrollment
 * cap, and check for open seats in a course. Works with the linked abstract
 * list class in order to add or drop a student from a course
 * 
 * @author Ananya Rao
 * @author Stuti Gautam
 * @author Connor Young
 */
public class CourseRoll {

	/**
	 * a course
	 */
	private Course course;
	/**
	 * a custom LinkedAbstractList of Students
	 */
	private LinkedAbstractList<Student> roll;

	/**
	 * Private instance field for the enrollment cap
	 */
	private int enrollmentCap;

	/**
	 * Class constant for the minimum number of students needed for an enrollment
	 * cap
	 */
	private static final int MIN_ENROLLMENT = 10;

	/**
	 * Class constant for the max number of students needed for an enrollment cap
	 */
	private static final int MAX_ENROLLMENT = 250;

	/** The size of a waitlist */
	private static final int WAITLIST_SIZE = 10;

	/**
	 * An field representing a waitlist
	 */
	private LinkedQueue<Student> waitlist;

	/**
	 * Constructor for the courseRoll class
	 * 
	 * @param c        the course
	 * @param capacity of the class
	 */
	public CourseRoll(Course c, int capacity) {
		if (c == null) {
			throw new IllegalArgumentException();
		}
		this.course = c;
		roll = new LinkedAbstractList<Student>(capacity);
		setEnrollmentCap(capacity);
		waitlist = new LinkedQueue<Student>(WAITLIST_SIZE);
	}

	/**
	 * Sets the requirements in order to set an enrollment cap. The cap cannot be
	 * less than 10 or greater than 250. The cap also has to be less than the size
	 * of the roll.
	 * 
	 * @param cap enrollment cap
	 * @throws IllegalArgumentException is the enrollment capacity is invalid
	 */
	public void setEnrollmentCap(int cap) {
		if (cap < MIN_ENROLLMENT || cap > MAX_ENROLLMENT || cap < roll.size()) {
			throw new IllegalArgumentException("Invalid enrollment capacity");
		}
		this.enrollmentCap = cap;
		roll.setCapacity(cap);
	}

	/**
	 * Gets the number of open seats in a course by subtracting the roll size from
	 * the enrollment cap
	 * 
	 * @return the number of open seats
	 */
	public int getOpenSeats() {
		return enrollmentCap - roll.size();
	}

	/**
	 * Getter method for the enrollment cap
	 * 
	 * @return the enrollment cap
	 */
	public int getEnrollmentCap() {
		return enrollmentCap;
	}

	/**
	 * Enrolls a student into the class if the student is valid. Adds the student to
	 * the end of the roll. Student is invalid if the student is already enrolled,
	 * is null, or if there is not more roam in the class.
	 * 
	 * @param s student being enrolled
	 * @throws IllegalArgumentException if the student does not meet enrollment
	 *                                  requirements
	 */
	public void enroll(Student s) {
		if (s == null) {
			throw new IllegalArgumentException("Student cannot be null");
		}
		if (isStudentEnrolled(s)) {
			throw new IllegalArgumentException("Student is already enrolled");
		}
		if (getOpenSeats() <= 0) {
			if (waitlist.size() == WAITLIST_SIZE) {
				throw new IllegalArgumentException();
			}
			waitlist.enqueue(s);
		} else {
			try {
				roll.add(roll.size(), s);
			} catch (Exception e) {
				throw new IllegalArgumentException("Error adding student: " + e.getMessage(), e);
			}
		}

	}

	/**
	 * Private helper method to help check if a student is already enrolled in a
	 * course
	 * 
	 * @param s student being checked
	 * @return true if the student is enrolled and false otherwise
	 */
	private boolean isStudentEnrolled(Student s) {
		for (int i = 0; i < roll.size(); i++) {
			if (roll.get(i).equals(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Drops the selected student. The student cannot be dropped if the selected
	 * student is null or is not enrolled in the course.
	 * 
	 * @param student student being dropped
	 * @throws IllegalArgumentException if the student cannot be dropped
	 */
	public void drop(Student student) {
		if (student == null) {
			throw new IllegalArgumentException("Student cannot be null.");
		}
		if (roll.contains(student)) {
			roll.remove(student);
			if (getNumberOnWaitlist() > 0) {
				Student waitingStudent = waitlist.dequeue();
				roll.add(waitingStudent);
				waitingStudent.getSchedule().addCourseToSchedule(course);
			}
		} else if (waitlist.contains(student)) {
			for (int i = 0; i < waitlist.size() - 1; i++) {
				Student temp = waitlist.dequeue();
				if (student != temp) {
					waitlist.enqueue(temp);
				} else {
					i++;
				}
			}
		}

	}

	/**
	 * Checks if the selected student can or cannot be enrolled in a certain course
	 * 
	 * @param student student being considered for enrollment
	 * @return false if they cannot be enrolled and true otherwise
	 */
	public boolean canEnroll(Student student) {
		if (getOpenSeats() <= 0 || roll.contains(student)) {
			return false;
		} else {
			return !(waitlist.contains(student) || waitlist.size() == WAITLIST_SIZE);
		}
	}

	/**
	 * gets the number of students on the wait list
	 * 
	 * @return the number of students on the wait list
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

}
