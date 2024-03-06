package edu.ncsu.csc216.pack_scheduler.course;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Represents a course in a schedule/catalog
 * subclass of Activity, has fields and methods specific to Course (not Event)
 * also has setters, getters, hash(), equals(), toString() methods
 * 
 * @author AndrewSteinl
 */
public class Course extends Activity implements Comparable<Course> {

	/** Course's name. */
	private String name;
	/** Course's section. */
	private String section;
	/** Course's credit hours */
	private int credits;
	/** Course's instructor */
	private String instructorId;
	/** Course's roll */
	private CourseRoll roll;
	/** Course section length */
	private static final int SECTION_LENGTH = 3;
	/** Course's max credits */
	private static final int MAX_CREDITS = 5;
	/** Course's minimum credits */
	private static final int MIN_CREDITS = 1;
	
	/**
	 * Constructs a Course object with values for all the fields.
	 * @param name Course's name
	 * @param title Course's title
	 * @param section Course's section
	 * @param credits number of credit hours
	 * @param instructorId Course's instructor
	 * @param meetingDays Days the course meets
	 * @param startTime Time the course starts
	 * @param endTime Time the course ends
	 * @param enrollmentCap enrollment cap of a course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
            int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
        setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
        if (enrollmentCap < 10 || enrollmentCap > 250) {
            throw new IllegalArgumentException("Invalid enrollment cap");
        }
        roll = new CourseRoll(this, enrollmentCap);
    }

	/**
	 * Creates a Course with the given name, title, section, credits, enrollmentCap, instructorId, and meetingDays for
	 * courses that are arranged. 
	 * @param name Course's name
	 * @param title Course's title
	 * @param section Course's section
	 * @param credits number of credit hours
	 * @param instructorId Course's instructor
	 * @param meetingDays Days the course meets
	 * @param enrollmentCap enrollment cap of a course
	 */
	public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
		this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
	}

	/**
	 * Returns the Course's name.
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Checks to see if the given name is valid and sets the Course's 
	 * name to the new name.
	 * @param name the new name thats replacing the old one
	 * @throws IllegalArgumentException if the given name is invalid
	 */
	private void setName(String name) {
		
		
		// throws an exception if name is null
		if (null == name) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		CourseNameValidator c = new CourseNameValidator();
		try {
			if (c.isValid(name)) {
				this.name = name;
			} else {
				throw new IllegalArgumentException("Invalid course name.");
			}
		} catch (InvalidTransitionException e) {
			throw new IllegalArgumentException("Invalid course name.");
		}
		
	}

	/**
	 * Returns the Course's section.
	 * @return the section
	 */
	public String getSection() {
		return section;
	}

	/**
	 * Checks to see if a given section is valid and sets the Course's section 
	 * to the new section.
	 * @param section the new section thats replacing the old one
	 * @throws IllegalArgumentException if the given section if invalid
	 */
	public void setSection(String section) {
		// tests to make sure section is the right size and isn't null
		if (null == section || section.length() != SECTION_LENGTH){
			throw new IllegalArgumentException("Invalid section.");
		}
		
		// makes sure section is only digits
		char character[] = new char[section.length()];
		character = section.toCharArray();
		for (int i = 0; i < SECTION_LENGTH; i++) {
			if(character[i] < 48 || character[i] > 57) {
				throw new IllegalArgumentException("Invalid section.");
			}
		}
		
		this.section = section;
	}

	/**
	 * Returns the Course's credits.
	 * @return the credits
	 */
	public int getCredits() {
		return credits;
	}

	/**
	 * Checks to see if a given credits number is valid and if so sets 
	 * the Course's credits to the given credits.
	 * @param credits the new credits number thats replacing the old one
	 * @throws IllegalArgumentException if the given credits are invalid
	 */
	public void setCredits(int credits) {
		if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
			throw new IllegalArgumentException("Invalid credits.");
		}
		this.credits = credits;
	}

	/**
	 * Returns the Course's instructorId.
	 * @return the instructorId
	 */
	public String getInstructorId() {
		return instructorId;
	}

	/**
	 * Checks to see if a given instructor Id is valid and if so
	 * sets the Course's instructorId to the given instructorId.
	 * @param instructorId the new instructorId to that the old one is being replaced with
	 * @throws IllegalArgumentException if the given instructorId if null or blank
	 */
	public void setInstructorId(String instructorId) {
		if ("".equals(instructorId)){
			throw new IllegalArgumentException("Invalid instructor id.");
		}
		this.instructorId = instructorId;
	}
	
	/**
	 * Determines if a given course is a duplicate of another course by
	 * checking if the two courses have the same name.
	 * @param activity being compared
	 * @return boolean if a course is a duplicate
	 */
	public boolean isDuplicate(Activity activity) {
		
		boolean equals = false;
		if(activity instanceof Course) {
			Course course = (Course) activity;
			if (course.getName().equals(this.getName())) {
				equals = true;
				return equals;
			} else {
				return equals;
			}
		} else {
			return equals;
		}
		
		
		
	}
	
	/**
	 * Sets the Course's meeting days and times to a new meeting days and times.
	 * @param meetingDays the days the course will be meeting
	 * @param startTime the course's start time
	 * @param endTime the course's end time
	 * @throws IllegalArgumentException if the new meeting days and times are invalid
	 */
	@Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) {
		if (null == meetingDays || "".equals(meetingDays)) {
			throw new IllegalArgumentException("Invalid meeting days and times.");
		}
		char character[] = new char[meetingDays.length()];
		character = meetingDays.toCharArray();
		int countM = 0;
		int countT = 0;
		int countW = 0;
		int countH = 0;
		int countF = 0;
		String days = "";
		int start = 0;
		int end = 0;
		
		if ("A".equals(meetingDays)) {
			if (startTime != 0 || endTime != 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			
		} else {
			// figures out what meeting days are recorded and ensures not are repeated
			if (startTime == 0 || endTime == 0) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
			for (int i = 0; i < meetingDays.length(); i++) {
				if (character[i] == 77) {
					countM++;
				} else if (character[i] == 84) {
					countT++;
				} else if (character[i] == 87) {
					countW++;
				} else if (character[i] == 72) {
					countH++;
				} else if (character[i] == 70) {
					countF++;
				} else {
					throw new IllegalArgumentException("Invalid meeting days and times.");
				}
				}
			if (countM > 1 || countT > 1 || countW > 1 || countH > 1 || countF > 1) {
				throw new IllegalArgumentException("Invalid meeting days and times.");
			}
		}
		
		days = meetingDays;
		start = startTime;
		end = endTime;
		super.setMeetingDaysAndTime(days, start, end);
	}
	

	/**
	 * Generates a hash value for an course's meeting day, time, and title which 
	 * is then stored for later use.
	 * @return the hash value for meeting day, time, and title
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + credits;
		result = prime * result + ((instructorId == null) ? 0 : instructorId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((section == null) ? 0 : section.hashCode());
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
		Course other = (Course) obj;
		if (credits != other.credits)
			return false;
		if (instructorId == null) {
			if (other.instructorId != null)
				return false;
		} else if (!instructorId.equals(other.instructorId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (section == null) {
			if (other.section != null)
				return false;
		} else if (!section.equals(other.section))
			return false;
		return true;
	}

	/**
	 * Returns a comma separated value String of all Course fields.
	 * @return String representation of Course
	 */
	@Override
	public String toString() {
	    if ("A".equals(getMeetingDays())) {
	        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays();
	    }
	    return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + roll.getEnrollmentCap() + "," + getMeetingDays() + "," + getStartTime() + "," + getEndTime(); 
	}

	/** 
	 * Populates the rows of the course catalog and the student schedule
	 * @return String array of the course catalog and the student schedule
	 */
	@Override
	public String[] getShortDisplayArray() {
		String[] shortDisplay = new String[5];
		shortDisplay[0] = name;
		shortDisplay[1] = section;
		shortDisplay[2] = getTitle();
		shortDisplay[3] = getMeetingString();
		shortDisplay[4] = Integer.toString(roll.getOpenSeats());
		return shortDisplay;
	}

	/**
	 * Displays course information for the full final schedule
	 * @return String array of the final schedule
	 */
	@Override
	public String[] getLongDisplayArray() {
		String[] longDisplay = new String[7];
		String strCredits = "";
		strCredits += credits;
		longDisplay[0] = name;
		longDisplay[1] = section;
		longDisplay[2] = getTitle();
		longDisplay[3] = strCredits;
		longDisplay[4] = instructorId;
		longDisplay[5] = getMeetingString();
		longDisplay[6] = "";
		return longDisplay;
	}

	/**
	 * Compares this course to the given parameter course lexicographically
	 * @return 0 if courses are equal, -1 or 1 based on comparison if not equal
	 */
	@Override
	public int compareTo(Course c) {
		
		if (this.getName().compareTo(c.getName()) == 0) {
			return this.getSection().compareTo(c.getSection());
		} else {
			return this.getName().compareTo(c.getName());
		}
	}
	
	/**
	 * returns a CourseRoll object
	 * @return roll course roll
	 */
	public CourseRoll getCourseRoll() {
		return roll;
	}
}
