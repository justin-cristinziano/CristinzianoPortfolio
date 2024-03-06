package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Represents the checked exception that is thrown when activities are conflicted
 * and must be handled by developer when thrown 
 * Contains 2 constructors (with and without parameter) that pass message to Exception's constructor and 
 * interact with each other and contains constant for ID used for serialization
 */
public class ConflictException extends Exception {

	/** ID used for serialization. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Constructor that passes message for the Exception object
	 * to Exception's constructor
	 * @param message message to be passed 
	 */
	public ConflictException(String message) {
		super(message);
	}
	
	/**
	 * ConflictExceptions parameterless constructor;
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}

}

