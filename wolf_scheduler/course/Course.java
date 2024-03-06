/**


 * 
 */
package edu.ncsu.csc216.wolf_scheduler.course;

import java.util.Objects;
/**
 * The Course object Justin Cristinziano
 */
public class Course {

	/** Course's name. */
	private String name;
	/** Course's title. */
	private String title;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/** Minimum length of setName's parameter, Name */
	private static final int MIN_NAME_LENGTH = 5;
	/** Maximum length of setName's parameter, Name */
	private static final int MAX_NAME_LENGTH = 8;
	/** Minimum length of the Course's letter count */
	private static final int MIN_LETTER_COUNT = 1;
	/** Maximum length of the Course's letter count */
	private static final int MAX_LETTER_COUNT = 4;
	/** The number of digit chars the name must have */
	private static final int DIGIT_COUNT = 3;
	/** The number of digits for a section */
	private static final int SECTION_LENGTH = 3;
	/** Minimum credits for a course */
	private static final int MIN_CREDITS = 1;
	/** Maximum credits for a course */
	private static final int MAX_CREDITS = 5;
	/** Highest hour for a course's starting or ending time */
	private static final int UPPER_HOUR = 23;
	/** Highest minutes for a course's starting or ending time */
	private static final int UPPER_MINUTE = 59;

	/**
	 * Constructs the object (sets all of the fields based on the parameters passed)
	 * 
	 * @param name         name of the Course
	 * @param title        title of the Course
	 * @param section      section of the Course
	 * @param credits      credit hours for this Course
	 * @param instructorId Instructor's unityID
	 * @param meetingDays  days the Course meets on
	 * @param startTime    start time of the Course
	 * @param endTime      end time of the Course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays,
			int startTime, int endTime) {
		setName(name);
		setTitle(title);
		setSection(section);
		setCredits(credits);
		setInstructorId(instructorId);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}

	/**
	 * Calls the Course constructor with eight parameters where params startTime and
	 * endTime are zero
	 * 
	 * @param name         name of the Course
	 * @param title        title of the Course
	 * @param section      section of the Course
	 * @param credits      credit hours for this Course
	 * @param instructorId Instructor's unityID
	 * @param meetingDays  days the Course meets on
	 */
	public Course(String name, String title, String section, int credits, String instructorId, String meetingDays) {
		this(name, title, section, credits, instructorId, meetingDays, 0, 0);
	}

	/**
	 * Returns the name of the Course
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the Course's name
	 * 
	 * @param name the name to set
	 */
	private void setName(String name) {
		// Throw exception if name is null
		if (name == null) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Throw exception if name is greater than MAX_NAME_LENGTH or MIN_NAME_LENGTH
		if (name.length() < MIN_NAME_LENGTH || name.length() > MAX_NAME_LENGTH) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		// Check pattern for L[LLL] NNN
		int letterCount = 0;
		int digitCount = 0;
		boolean spaceFound = false;

		for (int i = 0; i < name.length(); i++) {
			if (!spaceFound) {
				if (Character.isLetter(name.charAt(i))) {
					letterCount++;
				} else if (name.charAt(i) == ' ') {
					spaceFound = true;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			} else if (spaceFound) {
				if (Character.isDigit(name.charAt(i))) {
					digitCount++;
				} else {
					throw new IllegalArgumentException("Invalid course name.");
				}
			}
		}

		if (letterCount < MIN_LETTER_COUNT || letterCount > MAX_LETTER_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		if (digitCount != DIGIT_COUNT) {
			throw new IllegalArgumentException("Invalid course name.");
		}

		this.name = name;
	}

	/**
	 * Returns the Course's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the Course's title
	 * 
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		if (title == null || title.length() == 0) {
			throw new IllegalArgumentException("Invalid title.");
		} else {
			this.title = title;
		}
	}

	/**
	 * Returns the Course's section
	 * 
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Sets the section of the Course
	 * 
	 * @param section the section to set
	 */
	public void setSection(String section) {
		if (section == null) {
			throw new IllegalArgumentException("Invalid section.");
		}

		if (section.length() != SECTION_LENGTH) {
			throw new IllegalArgumentException("Invalid section.");
		}

		for (int i = 0; i < section.length(); i++) {
			if (!(Character.isDigit(section.charAt(i)))) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}

		this.section = section;
	}

	/**
	 * Returns the Courses credit hours
	 * 
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Sets the amount of credits earned for this Course
	 * 
	 * @param credits the credits to set
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}

		this.credits = credits;
	}

	/**
	 * Returns the Course's instructorId
	 * 
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Sets the Course's instructor ID
	 * 
	 * @param instructorId the instructorId to set
	 */
	public void setInstructorId(String instructorId) {
		if (instructorId == null || "".equals(instructorId)) {
			throw new IllegalArgumentException("Invalid instructor id.");
		}

		this.instructorId = instructorId;
	}

	/**
	 * Return the Course's meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
		return meetingDays;
	}

	/**
	 * Returns the Course's start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
		return startTime;
	}

	/**
	 * Returns the Course's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
		return endTime;
	}

	/**
	 * Generates a hashCode for Course using all fields
	 * 
	 * @return hashCode for Course
	 */
	@Override
	public int hashCode() {
		return Objects.hash(credits, endTime, instructorId, meetingDays, name, section, startTime, title);
	}

	/**
	 * Compares a given object to this object for equality on all fields.
	 * 
	 * @param obj the Object to compare
	 * @return true if the objects are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return credits == other.credits && endTime == other.endTime && Objects.equals(instructorId, other.instructorId)
				&& Objects.equals(meetingDays, other.meetingDays) && Objects.equals(name, other.name)
				&& Objects.equals(section, other.section) && startTime == other.startTime
				&& Objects.equals(title, other.title);
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * 
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
		if ("A".equals(meetingDays)) {
			return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays;
		}
		return name + "," + title + "," + section + "," + credits + "," + instructorId + "," + meetingDays + ","
				+ startTime + "," + endTime;
	}

	/**
	 * Sets the course's meeting dates and times
	 * 
	 * @param meetingDays for the day the Course meets on
	 * @param startTime   for the meeting's startTime
	 * @param endTime     for the meeting's end time
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		int startHour = 0;
		int startMin = 0;
		int endHour = 0;
		int endMin = 0;
		int totalDays = 0;
		boolean isArranged = false;
		if (startTime > endTime) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		if (meetingDays == null || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		for (int i = 0; i < meetingDays.length(); i++) {
			if (meetingDays.charAt(i) == 'A' && meetingDays.length() > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}

		if (startTime > 2359 || startTime < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if (endTime > 2359 || endTime < 0) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}

		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			this.meetingDays = meetingDays;

			this.startTime = 0;
			this.endTime = 0;

		} else {
			int aCount = 0;
			int mCount = 0;
			int tCount = 0;
			int wCount = 0;
			int hCount = 0;
			int fCount = 0;

			for (int i = 0; i < meetingDays.length(); i++) {
				if (meetingDays.charAt(i) == 'A') {
					aCount++;
				} else if (meetingDays.charAt(i) == 'M') {
					mCount++;
				} else if (meetingDays.charAt(i) == 'T') {
					tCount++;
				} else if (meetingDays.charAt(i) == 'W') {
					wCount++;
				} else if (meetingDays.charAt(i) == 'H') {
					hCount++;
				} else if (meetingDays.charAt(i) == 'F') {
					fCount++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
			}

			if (aCount > 1 || mCount > 1 || tCount > 1 || wCount > 1 || hCount > 1 || fCount > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			startHour = startTime / 100;
			startMin = startTime % 100;
			endHour = endTime / 100;
			endMin = endTime % 100;

			if (startHour < 0 || startHour > UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (startMin < 0 || startMin > UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endHour < 0 || endHour > UPPER_HOUR) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			if (endMin < 0 || endMin > UPPER_MINUTE) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}

			this.startTime = startTime;
			this.endTime = endTime;
			this.meetingDays = meetingDays;
		}
	}

	/**
	 * Gets the meeting string for whatever class calls it
	 * 
	 * @return the Course's meeting days, start time, and end time
	 */
	public String getMeetingString() {
		String meetingOnDays = getMeetingDays();
		int startingTime = this.startTime;
		int endingTime = this.endTime;
		if ("A".equals(meetingDays)) {
			return "Arranged";
		} else {
			return "" + meetingOnDays + " " + getTimeString(startingTime) + "-" + getTimeString(endingTime);

		}
	}

	private String getTimeString(int time) {
		int hours = 0;
		int min = 0;
		boolean addLeadingZero = false;

		String timeTypeString = "AM";

		hours = time / 100;
		min = time % 100;

		if (hours > 12) {
			hours = hours - 12;
			timeTypeString = "PM";
		} else if (hours == 0) {
			hours = 12;
		} else if (hours == 12) {
			timeTypeString = "PM";
		}

		if (min < 10) {
			return "" + hours + ":" + "0" + min + timeTypeString;
		} else {
			return "" + hours + ":" + min + timeTypeString;
		}

	}
}
