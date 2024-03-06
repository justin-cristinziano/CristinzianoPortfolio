package edu.ncsu.csc216.pack_scheduler.course;

/**
 * Represents an activity in a schedule/catalog, 
 * parent class of Course and Event (has common methods such as isDuplicate and 
 * has checkConflict interacting with Conflict interface)
 * includes common fields title, meetingDays, startTime, endTime
 * also has setters, getters, hash(), equals() methods
 * 
 * @author AndrewSteinl
 */
public abstract class Activity implements Conflict {

	/** Activity's title. */
	private String title;
	/** Activity's meeting days */
	private String meetingDays;
	/** Activity's starting time */
	private int startTime;
	/** Activity's ending time */
	protected int endTime;
	/** Number of hours in a day */
	private static final int UPPER_HOUR = 24;
	/** Number of Minutes in an hour */
	private static final int UPPER_MINUTE = 60;
	
	
	/**
	 * determines if the event or course is a duplicate of another event or course
	 * @param activity the activity in question
	 * @return boolean if the activity is a duplicate
	 */
	public abstract boolean isDuplicate(Activity activity);
	
	/** 
	 * Populates the rows of the course catalog and the student schedule
	 * @return String array of the course catalog and the student schedule
	 */
	public abstract String[] getShortDisplayArray();
	
	/**
	 * Displays course and event information for the full final schedule
	 * @return String array of the final schedule
	 */
	public abstract String[] getLongDisplayArray();

	
	/**
	 * Constructs a Activity object with values for all the fields.
	 * @param title Activity's title
	 * @param meetingDays Activity's meeting days
	 * @param startTime Activity's startTime
	 * @param endTime Activity's endTime
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
        super();
        setTitle(title);
        setMeetingDaysAndTime(meetingDays, startTime, endTime);
    }

	/**
	 * Returns the Activity's title.
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Activitys's title to a given title.
	 * @param title the title to set
	 * @throws IllegalArgumentException if the given title is invalid
	 */
	public void setTitle(String title) {
		if (null == title || "".equals(title)){
			throw new IllegalArgumentException("Invalid title.");
		}
		this.title = title;
	}

	/**
	 * Returns the Activity's meetingDays.
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Activity's startTime.
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Activity's endTime.
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Returns the Activity's meeting time
	 * @return the meetingTime
	 */
	public String getMeetingString() {
		
		// declared variables used to show time
		String meeting = "";
		int startMin;
		int startHour;
		int endMin;
		int endHour;
		String startPM = "AM";
		String endPM = "AM";
		String endMinS = "";
		String startMinS = "";
		
		// checks to see if meeting day is arranged
		if ("A".equals(meetingDays)) {
			meeting = "Arranged";
		} else {
			// calculated minutes and hours
			startMin = startTime % 100;
			startHour = startTime / 100;
			endMin = endTime % 100;
			endHour = endTime / 100;
			
			// determines if times are AM or PM for startTime
			if (startHour >= 12) {
				if (startHour != 12) {
					startHour = startHour - 12;
				}
				startPM = "PM";
			} else if (startHour == 0) {
				startHour = 12;
			}
			
			// adds starting 0 to minute counts below 10
			if (startMin < 10) {
				startMinS = "0";
				startMinS += startMin;
			} else {
				startMinS += startMin;
			}
			if (endMin < 10) {
				endMinS = "0";
				endMinS += endMin;
			} else {
				endMinS += endMin;
			}
			
			// determines if times are AM or PM for endTime
			if (endHour >= 12) {
				if (endHour != 12) {
					endHour = endHour - 12;
				}
				endPM = "PM";
			} else if (endHour == 0) {
				endHour = 12;
			}
			meeting = meetingDays + " " + startHour + ":" + startMinS + startPM + "-" + endHour + ":" + endMinS + endPM;
		}
		
		return meeting;
	}

	/** 
	 * checks if a course or event potentially conflicts with another course or event
	 * by seeing if the meeting days and the times on the meeting days overlap parsing through the 
	 * meetingDays and meeting days of parameter's strings 
	 * @throws ConflictException if there is a conflict with the day/time of the activities
	 */	
	@Override
	public void checkConflict(Activity possibleConflictingActivity) throws ConflictException {
		// creates variables used to compare activities meeting days
		String meetingDays1 = possibleConflictingActivity.getMeetingDays();
		String meetingDays2 = this.getMeetingDays();
		char characters1[] = new char[meetingDays1.length()];
		characters1 = meetingDays1.toCharArray();
		char characters2[] = new char[meetingDays2.length()];
		characters2 = meetingDays2.toCharArray();
		boolean matchingDay = false;
		
		
		// checks to see if activities meeting days match
		for(int i = 0; i < characters1.length; i++) {
			for(int j = 0; j < characters2.length; j++) {
				if(characters1[i] == characters2[j] && characters1[i] != 65) {
					matchingDay = true;
				}
			}
		}
		
		if(matchingDay) {
			// checks to see if 
			if(possibleConflictingActivity.getEndTime() == this.getStartTime() || possibleConflictingActivity.getStartTime() == this.getEndTime()) {
				throw new ConflictException();
			}
			// checks to see if the activities times overlap
			if(possibleConflictingActivity.getStartTime() > this.getStartTime()) {
				if(possibleConflictingActivity.getStartTime() < this.getEndTime() || 
					possibleConflictingActivity.getEndTime() == this.getEndTime()){
					throw new ConflictException();
					}
			} else if (possibleConflictingActivity.getStartTime() < this.getStartTime()) {
				if(possibleConflictingActivity.getEndTime() > this.getStartTime() || 
					possibleConflictingActivity.getStartTime() == this.getStartTime()){
					throw new ConflictException();
					}
			} else {
				throw new ConflictException();
			}
		}
	}

	/**
	 * Checks to see if a given meeting days and times re valid and sets 
	 * the Activity's meeting days and times to the new given meeting days and times
	 * @param meetingDays the days the activity will be meeting
	 * @param startTime the activity's start time
	 * @param endTime the activity's end time
	 * @throws IllegalArgumentException if the meeting days and times are invalid
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		
		int startMin = 0;
		int startHour = 0;
		int endMin = 0;
		int endHour = 0;
		
		// finds the times minutes and hours
		startMin = startTime % 100;
		startHour = startTime / 100;
		endMin = endTime % 100;
		endHour = endTime / 100;
		
		// tests to make sure start and end times are possible times
		if (startMin >= UPPER_MINUTE || startHour >= UPPER_HOUR || startTime < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (endMin >= UPPER_MINUTE || endHour >= UPPER_HOUR || endTime < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		// tests to make sure the start time is before the end time
		if (endTime < startTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		this.meetingDays = meetingDays;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	/**
	 * Generates hashCode for activity using the fields.
	 * Overridden for specific checks in Course for name, instructor id, credits, and section 
	 * @return hashCode for activity
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + endTime;
		result = prime * result + ((meetingDays == null) ? 0 : meetingDays.hashCode());
		result = prime * result + startTime;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Compares given activity to this activity for equality of fields
	 * Overridden for specific checks in Course for name, instructor id, credits, and section 
	 * @param obj the object being compared to
	 * @return boolean of whether the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (endTime != other.endTime)
			return false;
		if (meetingDays == null) {
			if (other.meetingDays != null)
				return false;
		} else if (!meetingDays.equals(other.meetingDays))
			return false;
		if (startTime != other.startTime)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	

}