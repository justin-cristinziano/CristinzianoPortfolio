package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Validates a Course name
 * Contains the state abstract class which contains 
 * @author Ananya Rao
 * @author Stuti Gautam
 * @author Connor Young
 */
public class CourseNameValidator {
	
	/** Boolean for if the end state is valid or not for the course name */
    private boolean validEndState;
    
	/** count for number of letters */
    private int letterCount;
    
	/** count for number of digits */
    private int digitCount;
    
    /** current state the course name is in */
    private State currentState;
    
	/**
	 * Identifies the next character of input and then calls the appropriate method on the currentState
	 * @param courseName name of the course being validated
	 * @return the final state of the course
	 * @throws InvalidTransitionException if course name has less than 3 digits or
	 * more than required letters, or name starts with non letter
	 */
	public boolean isValid(String courseName) throws InvalidTransitionException {
        currentState = new InitialState();
        letterCount = 0;
        digitCount = 0;
        validEndState = false;
        
        for (char c : courseName.toCharArray()) {
            if (Character.isLetter(c)) {
                currentState.onLetter();
            } else if (Character.isDigit(c)) {
                currentState.onDigit();
            } else {
				currentState.onOther();
            }
        }
        
		if (digitCount != 3) {
			validEndState = false;
			return validEndState;
		}
		
        validEndState = true;
        return validEndState;

	}
	
	/**
	 * Abstract inner class of CourseNameValidator. The abstract class will contain 4 inner
	 * classes which represent the states a course name can be in.
	 * @author Ananya Rao
	 * @author Stuti Gautam
	 * @author Connor Young
	 */
	public abstract class State extends InvalidTransitionException {
		/** ID used for serialization. */
		private static final long serialVersionUID = 1L;

		/**
		 * Abstract method for handling a letter input
		 */
        abstract void onLetter() throws InvalidTransitionException;
        
        /**
         * Abstract method for handling a digit input
         */
        abstract void onDigit() throws InvalidTransitionException;

        /**
         * Concrete method for handling any other input. 
         * @throws InvalidTransitionException if the course name contains inputs that are not letters or digits
         */
        void onOther() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only contain letters and digits.");
        }
    }
	
	/**
	 * Represents the initial state a course name is in
	 * Inner class which implements its abstract class State
	 * @author Ananya Rao
	 * @author Stuti Gautam
	 * @author Connor Young
	 */
	private class InitialState extends State {

		/** ID used for serialization. */
		private static final long serialVersionUID = 1L;

		/**
		 * Method for initial state being a letter
		 * sets the current state to letterState
		 */
		@Override
		void onLetter() {
            letterCount++;
            currentState = new LetterState();
		}

		/**
		 * handles the first character being a digit in the course name
		 * @throws InvalidTransitionException if course name doesn't start with a letter
		 */
		@Override
		void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}	
	}
	
	/**
	 * Represents the letter state a course name is in
	 * Inner class which implements its abstract class State
	 * @author Ananya Rao
	 * @author Stuti Gautam
	 * @author Connor Young
	 */
	private class LetterState extends State {

		/** ID used for serialization. */
		private static final long serialVersionUID = 1L;
		/** constant for max number of letters in a course name */
		private static final int MAX_PREFIX_LETTERS = 4;

		/**
		 * Method for transitioning from letter state to another letter state
		 * @throws InvalidTransitionException if course name has more than 4 letters
		 */
		@Override
		void onLetter() throws InvalidTransitionException {
            if (letterCount < MAX_PREFIX_LETTERS) {
                letterCount++;
                currentState = new LetterState();
            } else {
                throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
            }
		}

		/**
		 * Method for transitioning from letter state to digit state 
		 */
		@Override
		void onDigit() {
			digitCount++;
            currentState = new NumberState();
		}	
	}
	
	/**
	 * Represents the number state a course name is in
	 * Inner class which implements its abstract class State
	 * @author Ananya Rao
	 * @author Stuti Gautam
	 * @author Connor Young
	 */
	private class NumberState extends State {

		/** ID used for serialization. */
		private static final long serialVersionUID = 1L;
		
		/** constant for number of digits in a course name */
		private static final int COURSE_NUMBER_LENGTH = 3;

		/**
		 * Method for transitioning from a digit state to a letter state
		 * @throws InvalidTransitionException if course name doesn't have 3 digits
		 */
		@Override
		void onLetter() throws InvalidTransitionException {
            if (digitCount == COURSE_NUMBER_LENGTH) {
                currentState = new SuffixState();
            } else {
                throw new InvalidTransitionException("Course name must have 3 digits.");
            }
		}

		/**
		 * Method for transitioning from a digit state to another digit state 
		 * @throws InvalidTransitionException if course name has more than 3 digits
		 */
		@Override
		void onDigit() throws InvalidTransitionException {
            if (digitCount < COURSE_NUMBER_LENGTH) {
                digitCount++;
                currentState = new NumberState();
            } else {
                throw new InvalidTransitionException("Course name can only have 3 digits.");
            }
		}
	}
	
	/**
	 * Represents the suffix state a course name is in
	 * Inner class which implements its abstract class State
	 * @author Ananya Rao
	 * @author Stuti Gautam
	 * @author Connor Young
	 */
	private class SuffixState extends State {

		/** ID used for serialization. */
		private static final long serialVersionUID = 1L;

		/**
		 * Method for having a letter after an ending suffix in a name
		 * @throws InvalidTransitionException if course name has more than 1 letter suffix
		 */
		@Override
		void onLetter() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * Method for having a digit after an ending suffix in a name 
		 * @throws InvalidTransitionException if course name has digits after suffix
		 */
		@Override
		void onDigit() throws InvalidTransitionException {
            throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}
}