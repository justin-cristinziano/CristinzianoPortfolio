package edu.ncsu.csc216.product_backlog.model.command;

/**
 * This class encapsulates the information about a user command that would lead
 * to a transition
 */
public class Command {

	/** This is the note when the command is run */
	private String note;
	
	private CommandValue c;
	
	/** This is the owner of the command (whoever is doing it) */
	private String owner;

	/** Error message for an invalid command */
	private static final String COMMAND_ERROR_MESSAGE = "Invalid command";

	/**
	 * This is an enumeration of all the values that a command could be
	 */
	public enum CommandValue {
		/**
		 * This sends the task to backlog state
		 */
		BACKLOG,
		/**
		 * This sends the task to claimed state
		 */
		CLAIM,
		/**
		 * This sends the task to processed state
		 */
		PROCESS,
		/**
		 * This sends the task to verifying state
		 */
		VERIFY,
		/**
		 * This sends the task to done state
		 */
		COMPLETE,
		/**
		 * This sends the task to rejected state
		 */
		REJECT
	}

	/**
	 * This is the constructor for the Command class
	 * 
	 * @param c        is the command the user wants to execute
	 * @param owner    is the owner of the command
	 * @param noteText is the note attached to the command
	 * @throws IllegalArgumentException if the command is null
	 */
	public Command(CommandValue c, String owner, String noteText) throws IllegalArgumentException {
		if(c == null) {
			throw new IllegalArgumentException("Command can't be null");
		}
		
		if(noteText == null || "".equals(noteText)) {
			throw new IllegalArgumentException("Invalid note text");
		}
		
		if(c == CommandValue.CLAIM && (owner == null || "".equals(owner))) {
			throw new IllegalArgumentException("Claimed tasks need owners");
		}
		
		if(c != CommandValue.CLAIM && owner != null) {
			throw new IllegalArgumentException("Only claimed tasks can have owners");
		}
		
		this.c = c;
		
		this.owner = owner;
		
		this.note = noteText;
		
	}
	
	/**
	 * Retrieves the command the users wanting to use
	 * @return the command
	 */
	public CommandValue getCommand() {
		return this.c;
	}
	
	/**
	 * Gets the note text
	 * @return the note string
	 */
	public String getNoteText() {
		return note;
	}
	
	/**
	 * Retrieves the owner of whoever is using the command
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

}
