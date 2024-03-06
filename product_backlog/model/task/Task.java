package edu.ncsu.csc216.product_backlog.model.task;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.command.Command.CommandValue;

/**
 * This class controls the task information. Tasks will be placed under Product
 * objects and handled in BacklogManager
 */
public class Task {

	/**
	 * The backlog state
	 */
	private final BacklogState backlogState = new BacklogState();

	/**
	 * The owned state
	 */
	private final OwnedState ownedState = new OwnedState();

	/**
	 * The processing state
	 */
	private final ProcessingState processingState = new ProcessingState();

	/**
	 * The verifying state
	 */
	private final VerifyingState verifyingState = new VerifyingState();

	/**
	 * The done state
	 */
	private final DoneState doneState = new DoneState();

	/**
	 * The rejected state
	 */
	private final RejectedState rejectedState = new RejectedState();

	/** The task's ID */
	private int taskId;

	/** The task's title */
	private String title;

	/** The current TaskState of the Task */
	private TaskState currentState;

	/** The type of Task */
	private Type type;

	/** The task's creator */
	private String creator;

	/** The owner of the task */
	private String owner;

	/** A boolean representing whether the task is verified */
	private boolean isVerified;

	/** An Arraylist of all the task's notes */
	private ArrayList<String> notes = new ArrayList<String>();

	/** Represents the "Backlog" status. */
	public static final String BACKLOG_NAME = "Backlog";

	/** Represents the "Owned" status. */
	public static final String OWNED_NAME = "Owned";

	/** Represents the "Processing" status. */
	public static final String PROCESSING_NAME = "Processing";

	/** Represents the "Verifying" status. */
	public static final String VERIFYING_NAME = "Verifying";

	/** Represents the "Done" status. */
	public static final String DONE_NAME = "Done";

	/** Represents the "Rejected" status. */
	public static final String REJECTED_NAME = "Rejected";

	/** Represents a task of type "Feature." */
	public static final String FEATURE_NAME = "Feature";

	/** Represents a task of type "Bug." */
	public static final String BUG_NAME = "Bug";

	/** Represents a task of type "Technical Work." */
	public static final String TECHNICAL_WORK_NAME = "Technical Work";

	/** Represents a task of type "Knowledge Acquisition." */
	public static final String KNOWLEDGE_ACQUISITION_NAME = "Knowledge Acquisition";

	/** Represents the type code for "Feature" tasks. */
	public static final String T_FEATURE = "F";

	/** Represents "Bug" tasks. */
	public static final String T_BUG = "B";

	/** Represents "Technical Work" tasks. */
	public static final String T_TECHNICAL_WORK = "TW";

	/** Represents "Knowledge Acquisition" tasks. */
	public static final String T_KNOWLEDGE_ACQUISITION = "KA";

	/** Represents an "Unowned" status. */
	public static final String UNOWNED = "unowned";

	/**
	 * Constructs a Task from the provided id, title, creator, and note
	 * 
	 * @param id      is the task's id
	 * @param title   is the task's title
	 * @param type    is the task's type
	 * @param creator is the creator of the task
	 * @param note    is the note you want to add to the task
	 */
	public Task(int id, String title, Type type, String creator, String note) {
		if (id <= 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (type == null) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (creator == null || "".equals(creator)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		this.currentState = backlogState;

		setTaskId(id);

		setTitle(title);

		setType(type);

		setCreator(creator);

		addNoteToList(note);

		setOwner(UNOWNED);

	}

	/**
	 * Constructs a Task by providing all field values
	 * 
	 * @param id       is the task's id
	 * @param state    is the task's current state
	 * @param title    is the title of the task
	 * @param type     is the type of task
	 * @param creator  is the creator of the task
	 * @param owner    is the current owner of the the task
	 * @param verified is a boolean representing whether the task is verified or not
	 * @param notes    are any previous notes of the task
	 */
	public Task(int id, String state, String title, String type, String creator, String owner, String verified,
			ArrayList<String> notes) {

		if (type == null || "".equals(type)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (id <= 0) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (title == null || "".equals(title)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		// Throw exception if state isn't a valid type of state

		if (state == null || !(state.equals(BACKLOG_NAME) || (state.equals(OWNED_NAME) || state.equals(PROCESSING_NAME)
				|| state.equals(VERIFYING_NAME) || state.equals(DONE_NAME) || state.equals(REJECTED_NAME)))) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		// Throw if invalid type information
		if (!(type.equals(T_FEATURE) || type.equals(T_BUG) || type.equals(T_TECHNICAL_WORK)
				|| type.equals(T_KNOWLEDGE_ACQUISITION))) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		// If unowned and not in rejected or backlog state
		if ((state.equals(OWNED_NAME) || state.equals(PROCESSING_NAME) || state.equals(VERIFYING_NAME)
				|| state.equals(DONE_NAME)) && owner.equals(UNOWNED)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		// Owner can't be null or "" (
		if (owner == null || "".equals(owner)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (state.equals(DONE_NAME) && ((type.equals(T_FEATURE) || type.equals(T_BUG) || type.equals(T_TECHNICAL_WORK))
				&& "false".equals(verified))) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (state.equals(DONE_NAME) && "true".equals(verified) && type.equals(T_KNOWLEDGE_ACQUISITION)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (creator == null || "".equals(creator)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		if (verified == null || "".equals(creator)) {
			throw new IllegalArgumentException("Invalid task information.");
		}

		setTaskId(id);
		setState(state);
		setTitle(title);
		setTypeFromString(type);
		setCreator(creator);
		setOwner(owner);
		setNotes(notes);
		
		if("true".equals(verified)) {
			setVerified(true);
		}
		
		if("false".equals(verified)) {
			setVerified(false);
		}

	}

	/**
	 * The getter method for TaskID (returns TaskId)
	 * 
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * The setter for TaskID
	 * 
	 * @param taskId the taskId to set
	 */
	private void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	/**
	 * The getter method for title (returns title)
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * The setter for title
	 * 
	 * @param title the title to set
	 */
	private void setTitle(String title) {
		this.title = title;
	}

	/**
	 * The getter method for creator (returns creator)
	 * 
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * The setter for creator
	 * 
	 * @param creator the creator to set
	 */
	private void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * The getter method for owner (returns owner)
	 * 
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * The setter for owner
	 * 
	 * @param owner the owner to set
	 */
	private void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * The getter for isVerified (returns isVerified = true if it is verified)
	 * 
	 * @return isVerified (representing whether it is verified or not)
	 */
	public boolean isVerified() {
		return isVerified;
	}

	/**
	 * The setter for isVerified
	 * 
	 * @param isVerified the verification state
	 */
	private void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	/**
	 * The getter method for notes (returns notes)
	 * 
	 * @return the notes
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}

	/**
	 * The setter for notes
	 * 
	 * @param notes the notes to set
	 */
	private void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	/**
	 * Sets the type of task.
	 *
	 * @param type The type to set.
	 */
	private void setType(Type type) {
		this.type = type;
	}

	/**
	 * Adds a note to the list and returns the index of the added note.
	 *
	 * @param note The note to add.
	 * @return The index of the added note.
	 */
	public int addNoteToList(String note) {
		if (note == null || "".equals(note)) {
			throw new IllegalArgumentException("Invalid task information.");
		} else {
			if (note.charAt(0) == '[') {
				notes.add(note);
				return notes.size() - 1;
			} else {
				notes.add("[" + getStateName() + "] " + note);
				return notes.size() - 1;
			}
		}
	}

	/**
	 * Retrieves the name of the state.
	 *
	 * @return The name of the state.
	 */
	public String getStateName() {
		if (currentState == backlogState) {
			return BACKLOG_NAME;
		} else if (currentState == ownedState) {
			return OWNED_NAME;
		} else if (currentState == processingState) {
			return PROCESSING_NAME;
		} else if (currentState == verifyingState) {
			return VERIFYING_NAME;
		} else if (currentState == doneState) {
			return DONE_NAME;
		} else if (currentState == rejectedState) {
			return REJECTED_NAME;
		} else {
			return null;
		}

	}

	/**
	 * Sets the state of this object.
	 *
	 * @param state The state to set.
	 */
	private void setState(String state) {

		if (state.equals(DONE_NAME)) {
			currentState = doneState;
		}

		if (state.equals(BACKLOG_NAME)) {
			currentState = backlogState;
		}

		if (state.equals(OWNED_NAME)) {
			currentState = ownedState;
		}

		if (state.equals(PROCESSING_NAME)) {
			currentState = processingState;
		}

		if (state.equals(VERIFYING_NAME)) {
			currentState = verifyingState;
		}

	}

	/**
	 * Sets the type based on a string representation.
	 *
	 * @param typeString The string representation of the type.
	 */
	private void setTypeFromString(String typeString) {
		if (typeString.equals(T_BUG)) {
			setType(Type.BUG);
		}

		if (typeString.equals(T_FEATURE)) {
			setType(Type.FEATURE);
		}

		if (typeString.equals(T_TECHNICAL_WORK)) {
			setType(Type.TECHNICAL_WORK);
		}

		if (typeString.equals(T_KNOWLEDGE_ACQUISITION)) {
			setType(Type.KNOWLEDGE_ACQUISITION);
		}
	}

	/**
	 * Retrieves the type of this object.
	 *
	 * @return The type of this object.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Retrieves the short name of the type.
	 *
	 * @return The short name of the type.
	 */
	public String getTypeShortName() {
		if (type == Type.BUG) {
			return T_BUG;
		}
		if (type == Type.FEATURE) {
			return T_FEATURE;
		}
		if (type == Type.KNOWLEDGE_ACQUISITION) {
			return T_KNOWLEDGE_ACQUISITION;
		}
		if (type == Type.TECHNICAL_WORK) {
			return T_TECHNICAL_WORK;
		}

		else {
			throw new IllegalArgumentException("Can't get short name");
		}
	}

	/**
	 * Retrieves the long name of the type.
	 *
	 * @return The long name of the type.
	 */
	public String getTypeLongName() {
		if (type == Type.BUG) {
			return BUG_NAME;
		}
		if (type == Type.FEATURE) {
			return FEATURE_NAME;
		}
		if (type == Type.KNOWLEDGE_ACQUISITION) {
			return KNOWLEDGE_ACQUISITION_NAME;
		}
		if (type == Type.TECHNICAL_WORK) {
			return TECHNICAL_WORK_NAME;
		}

		else {
			throw new IllegalArgumentException("Can't get long name");
		}
	}

	/**
	 * Retrieves the list of notes as a string.
	 *
	 * @return The list of notes as a string.
	 */
	public String getNotesList() {
		String notesList = "";

		for (String note : notes) {
			notesList += "- " + note + '\n';
		}

		return notesList;
	}

	/**
	 * Returns a string representation of this object.
	 *
	 * @return A string representation of this object.
	 */
	public String toString() {
		String taskString = "";

		taskString = "* " + this.getTaskId() + "," + this.getStateName() + "," + this.getTitle() + ","
				+ this.getTypeShortName() + "," + this.getCreator() + "," + this.getOwner() + "," + this.isVerified()
				+ '\n' + getNotesList();

		return taskString;
	}

	/**
	 * Updates this object based on a given command.
	 *
	 * @param c The command used to update this object.
	 * @throws UnsupportedOperationException if transition is not a valid transition
	 *                                       between states
	 */
	public void update(Command c) throws UnsupportedOperationException {
		if (this.currentState == backlogState && c.getCommand() != CommandValue.CLAIM
				&& c.getCommand() != CommandValue.REJECT) {
			throw new UnsupportedOperationException("Illegal state change");
		}

		if (this.currentState == ownedState && c.getCommand() != CommandValue.PROCESS
				&& c.getCommand() != CommandValue.REJECT && c.getCommand() != CommandValue.BACKLOG) {
			throw new UnsupportedOperationException("Illegal state change");
		}

		if (this.currentState == processingState && c.getCommand() != CommandValue.PROCESS
				&& c.getCommand() != CommandValue.VERIFY && c.getCommand() != CommandValue.COMPLETE
				&& c.getCommand() != CommandValue.BACKLOG) {
			throw new UnsupportedOperationException("Illegal state change");
		}

		if (this.currentState == verifyingState && c.getCommand() != CommandValue.COMPLETE
				&& c.getCommand() != CommandValue.PROCESS) {
			throw new UnsupportedOperationException("Illegal state change");
		}

		if (this.currentState == doneState && c.getCommand() != CommandValue.PROCESS
				&& c.getCommand() != CommandValue.BACKLOG) {
			throw new UnsupportedOperationException("Illegal state change");
		}

		if (this.currentState == rejectedState && c.getCommand() != CommandValue.BACKLOG) {
			throw new UnsupportedOperationException("Illegal state change");
		}

		if (c.getCommand() == CommandValue.CLAIM) {
			this.currentState = ownedState;
			currentState.updateState(c);
			setState(currentState.getStateName());
		}

		if (c.getCommand() == CommandValue.BACKLOG) {
			this.currentState = backlogState;
			currentState.updateState(c);
			setState(currentState.getStateName());
		}

		if (c.getCommand() == CommandValue.PROCESS) {
			this.currentState = processingState;
			currentState.updateState(c);
			setState(currentState.getStateName());
		}

		if (c.getCommand() == CommandValue.VERIFY) {
			this.currentState = verifyingState;
			currentState.updateState(c);
			setState(currentState.getStateName());
		}

		if (c.getCommand() == CommandValue.COMPLETE) {
			this.currentState = doneState;
			currentState.updateState(c);
			setState(currentState.getStateName());
		}

		if (c.getCommand() == CommandValue.REJECT) {
			this.currentState = rejectedState;
			currentState.updateState(c);
			setState(currentState.getStateName());
		}

	}

	/**
	 * Retrieves an array of notes for the task.
	 *
	 * @return An array of notes.
	 */
	public String[] getNotesArray() {
		String[] notesArray = new String[notes.size()];

		for (int i = 0; i < notes.size(); i++) {
			notesArray[i] = notes.get(i);
		}

		return notesArray;
	}

	/**
	 * Interface for states in the Task State Pattern. All concrete task states must
	 * implement the TaskState interface.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private interface TaskState {

		/**
		 * Update the Task based on the given Command An UnsupportedOperationException
		 * is thrown if the Command is not a is not a valid action for the given state.
		 * 
		 * @param c Command describing the action that will update the Task state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		void updateState(Command c);

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		public String getStateName();

	}

	/**
	 * This class controls the task state when the user wants the state to be
	 * "Backlog"
	 */
	private final class BacklogState implements TaskState {

		/**
		 * This method updates the state for task
		 * 
		 * @param c is the command used to change the state
		 */
		public void updateState(Command c) {
			currentState = backlogState;
			setOwner(UNOWNED);
			addNoteToList(c.getNoteText());
		}

		/**
		 * Retrieves the the name of the state for whatever class calls it
		 * 
		 * @return state
		 */
		public String getStateName() {
			return BACKLOG_NAME;
		}
	}

	/**
	 * This class controls the task state when the user wants the state to be
	 * "Owned"
	 */
	private final class OwnedState implements TaskState {

		/**
		 * This method updates the state for task
		 * 
		 * @param c is the command used to change the state
		 */
		public void updateState(Command c) {
			currentState = ownedState;
			setOwner(c.getOwner());
			addNoteToList(c.getNoteText());
		}

		/**
		 * Retrieves the the name of the state for whatever class calls it
		 * 
		 * @return state
		 */
		public String getStateName() {
			return OWNED_NAME;
		}
	}

	/**
	 * This class controls the task state when the user wants the state to be
	 * "Verifying"
	 */
	private final class VerifyingState implements TaskState {

		/**
		 * This method updates the state for task
		 * 
		 * @param c is the command used to change the state
		 */
		public void updateState(Command c) {
			currentState = verifyingState;
			addNoteToList(c.getNoteText());
		}

		/**
		 * Retrieves the the name of the state for whatever class calls it
		 * 
		 * @return state
		 */
		public String getStateName() {
			return VERIFYING_NAME;
		}
	}

	/**
	 * This class controls the task state when the user wants the state to be
	 * "Processing"
	 */
	private final class ProcessingState implements TaskState {

		/**
		 * This method updates the state for task
		 * 
		 * @param c is the command used to change the state
		 */
		public void updateState(Command c) {
			currentState = processingState;
			addNoteToList(c.getNoteText());

		}

		/**
		 * Retrieves the the name of the state for whatever class calls it
		 * 
		 * @return state
		 */
		public String getStateName() {
			return PROCESSING_NAME;
		}
	}

	/**
	 * This class controls the task state when the user wants the state to be "Done"
	 */
	private final class DoneState implements TaskState {

		/**
		 * This method updates the state for task
		 * 
		 * @param c is the command used to change the state
		 */
		public void updateState(Command c) {
			currentState = doneState;
			addNoteToList(c.getNoteText());
		}

		/**
		 * Retrieves the the name of the state for whatever class calls it
		 * 
		 * @return state
		 */
		public String getStateName() {
			return DONE_NAME;
		}
	}

	/**
	 * This class controls the task state when the user wants the state to be
	 * "Rejected"
	 */
	private final class RejectedState implements TaskState {

		/**
		 * This method updates the state for task
		 * 
		 * @param c is the command used to change the state
		 */
		public void updateState(Command c) {
			currentState = rejectedState;
			setOwner(UNOWNED);
			addNoteToList(c.getNoteText());
		}

		/**
		 * Retrieves the the name of the state for whatever class calls it
		 * 
		 * @return state
		 */
		public String getStateName() {
			return REJECTED_NAME;
		}
	}

	/** The possible types of tasks */
	public enum Type {

		/**
		 * This is feature type of task
		 */
		FEATURE,
		/**
		 * This is bug type of task
		 */
		BUG,
		/**
		 * This is technicalWork type of task
		 */
		TECHNICAL_WORK,
		/**
		 * This is knowledgeAcquisition type of task
		 */
		KNOWLEDGE_ACQUISITION
	}

}
