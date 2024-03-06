package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Registration manager handles the logging in and logging out of the
 * Packscheduler program while also keeping track of the current user of the
 * system.
 * 
 * @author Connor Young
 * @author Ananya Rao
 * @author Stuti Gautam
 */
public class RegistrationManager {

	/** an instance of RegistrationManager */
	private static RegistrationManager instance;

	/** course catalog */
	private CourseCatalog courseCatalog = new CourseCatalog();;

	/** student directory */
	private StudentDirectory studentDirectory = new StudentDirectory();

	/** faculty directory */
	private FacultyDirectory facultyDirectory = new FacultyDirectory();

	/** a user who is a University official */
	private User registrar;

	/** the current user of the program */
	private User currentUser;

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";

	/** file containing information about the user */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Constructor for RegistrationManager that calls the createRegistrar method to
	 * create a new Registrar.
	 */
	private RegistrationManager() {
		createRegistrar();
	}

	/**
	 * creates a new registrar with the provided fields from the
	 * registrar.properties file.
	 * 
	 * @throws IllegalArgumentException if registrar cannot be created
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Hashes a password with the SHA 256 algorithm to protect the password.
	 * 
	 * @param pw String representing a password
	 * @return String representing a scrambled version of the given password
	 * @throws IllegalArgumentException if password cannot be hashed
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * gets the current instance of Registration Manager
	 * 
	 * @return instance which is the current instance of RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Gets the course catalog
	 * 
	 * @return a sorted list of courses
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * gets the student Directory
	 * 
	 * @return a sort list of students
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * gets the faculty Directory
	 * 
	 * @return a sort list of students
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * handles the login functionality of the PackScheduler program. Compares the
	 * given id and password to those found within the registrar.properties file.
	 * 
	 * @param id       a String representing a person's id
	 * @param password a String representing a person's password
	 * @return true if the user's credentials matched and false if not.
	 * @throws IllegalArgumentException if user does not exist
	 */
	public boolean login(String id, String password) {
		if (currentUser != null) {
			return false;
		}
		String localHashPW = hashPW(password);
		Student s = studentDirectory.getStudentById(id);
		Faculty f = facultyDirectory.getFacultyById(id);

		if (registrar.getId().equals(id)) {
			if (registrar.getPassword().equals(localHashPW)) {
				currentUser = registrar;
				return true;
			} else {
				return false;
			}
		}
		if (s == null && f == null) {
			throw new IllegalArgumentException("User doesn't exist.");
		} else if (s != null && s.getId().equals(id)) {
			if (s.getPassword().equals(localHashPW) && !(currentUser instanceof Student)) {
				currentUser = s;
				return true;
			} else {
				return false;
			}
		} else if (f != null && f.getId().equals(id)) {
			if (f.getPassword().equals(localHashPW) && !(currentUser instanceof Faculty)) {
				currentUser = f;
				return true;
			} else {
				return false;
			}
		} else {
			throw new IllegalArgumentException("User doesn't exist.");
		}
	}

	/**
	 * handles the logout functionality of the program by setting the current user
	 * of the program to null.
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * gets the current user
	 * 
	 * @return an instance of the User class that represents the current user of the
	 *         program.
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * resets the course catalog and student directory.
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * class in charge of creating a new registrar (a university official that
	 * maintains the directory of enrolled students)
	 */
	private static class Registrar extends User {
		/**
		 * constructs a new Registrar instance based on the given parameters.
		 * 
		 * @param firstName the university official's first name
		 * @param lastName  the university official's last name
		 * @param id        the university official's id
		 * @param email     the university official's email
		 * @param hashPW    the university official's hashed password
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * method that updates the faculty schedule to include the course
	 * 
	 * @param c course to add to faculty schedule
	 * @param f faculty to add course to
	 * @return true if successfully added/ false otherwise.
	 */
	public boolean addFacultyToCourse(Course c, Faculty f) {
		if (!(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		} else {
			f.getSchedule().addCourseToSchedule(c);
			return true;
		}

	}

	/**
	 * method that updates the faculty schedule to remove the course
	 * 
	 * @param c course to remove from faculty schedule
	 * @param f faculty to remove course from
	 * @return true if successfully removed/ false otherwise.
	 */
	public boolean removeFacultyFromCourse(Course c, Faculty f) {
		if (!(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		} else {
			f.getSchedule().removeCourseFromSchedule(c);
			return true;
		}
	}

	/**
	 * method that resets the faculty schedule
	 * 
	 * @param f faculty whose schedule is to be reset
	 */
	public void resetFacultySchedule(Faculty f) {
		if (!(currentUser instanceof Registrar)) {
			throw new IllegalArgumentException();
		} else {
			f.getSchedule().resetSchedule();
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}
}