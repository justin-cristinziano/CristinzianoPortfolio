/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Handles conflicts between courses and events, 
 * contains method that checks conflicts using an Activity parameter and 
 * throws a checked exception if a conflict is found
 * 
 * @author Andrew Steinl
 */
public interface Conflict {
	/**
	 * checks if a course or event potentially conflicts with another course or event
	 * by seeing if either the times overlap on the same day
	 * @param possibleConflictingActivity activity that might be conflicting
	 * @throws ConflictException if there is a conflict with the activities
	 */
	void checkConflict(Activity possibleConflictingActivity) throws ConflictException;
}
