package edu.ncsu.csc216.pack_scheduler.course.validator;
/**
 * Class that creates the custom exception "ConflictExeption".
 * @author Connor Young
 */
public class InvalidTransitionException extends Exception {

	/**
	 * ID used for serialization
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for InvalidTransitionException that throws a custom message
	 * 
	 * @param message (String) the specific message that is going to be displayed
	 *                when exception is thrown.
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Constructor for InvalidTransitionException that displays the default message
	 * "Invalid FSM Transition." with exception is thrown
	 */
	public InvalidTransitionException() {
		this("Invalid FSM Transition.");
	}

}
