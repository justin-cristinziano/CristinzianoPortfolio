package edu.ncsu.csc216.pack_scheduler.directory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import edu.ncsu.csc216.pack_scheduler.io.FacultyRecordIO;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;
import edu.ncsu.csc216.pack_scheduler.user.User;

/**
 * Represents a directory of faculty
 */
public class FacultyDirectory {

	/** The hash algorithm constant */
	private static final String HASH_ALGORITHM = "SHA-256";

	/** This is the list of faculty members */
	private LinkedList<Faculty> facultyDirectory;

	/** This is the constructor for the class */
	public FacultyDirectory() {
		newFacultyDirectory();
	}

	/** This sets the directory to an empty LinkedList of Faculty objects */
	public void newFacultyDirectory() {
		facultyDirectory = new LinkedList<Faculty>();
	}

	/**
	 * This loads a list of faculty members to the directory from a file
	 * 
	 * @param fileName is the name of the file the user wants to pull from
	 */
	public void loadFacultyFromFile(String fileName) {
		try {
			facultyDirectory = FacultyRecordIO.readFacultyRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds a new faculty member to the directory
	 * 
	 * @param firstName  is the faculty member's first name
	 * @param lastName   is the faculty member's last name
	 * @param id         is the faculty member's id
	 * @param email      is the faculty member's email
	 * @param pass       is the faculty member's password
	 * @param repeatPass is the faculty member's password repeated
	 * @param maxCourses is the faculty member's max course count
	 * @return true if successful and false if the id already exists in directory
	 */
	public boolean addFaculty(String firstName, String lastName, String id, String email, String pass,
			String repeatPass, int maxCourses) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}

		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}

		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}

		if (!checkEmail(email)) {
			throw new IllegalArgumentException("Invalid email");
		}

		if (pass == null || "".equals(pass) || repeatPass == null || "".equals(repeatPass)) {
			throw new IllegalArgumentException("Invalid password");
		}

		if (repeatPass != pass) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		if (maxCourses > 3 || maxCourses < 1) {
			throw new IllegalArgumentException("Invalid max courses");
		}

		for (Faculty f : facultyDirectory) {
			if (f.getId().equals(id)) {
				return false;
			}
		}
		String hashPW = "";
		String repeatHashPW = "";
		
		hashPW = hashString(pass);
		repeatHashPW = hashString(repeatPass);
		// ensures the password and repeat password match after hashing
		if (!hashPW.equals(repeatHashPW)) {
			throw new IllegalArgumentException("Passwords do not match");
		}

		facultyDirectory.add(new Faculty(firstName, lastName, id, email, hashPW, maxCourses));
		return true;
	}
	
	/**
	 * Hashes a String according to the SHA-256 algorithm, and outputs the digest in base64 encoding.
	 * This allows the encoded digest to be safely copied, as it only uses [a-zA-Z0-9+/=].
	 * 
	 * @param toHash the String to hash 
	 * @return the encoded digest of the hash algorithm in base64
	 * @throws IllegalArgumentException if password cannot be hashed 
	 */
	private static String hashString(String toHash) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(toHash.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * method that checks the email to ensure it is valid
	 * @param email to check
	 * @return true if valid, false otherwise
	 */
	private boolean checkEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		}
		boolean containsAtSign = false;
		int atSignIndex = 0;
		boolean containsPeriod = false;
		int periodIndex = 0;
		for (int i = 0; i < email.length(); i++) {
			if (email.charAt(i) == '@') {
				if (!containsAtSign) {
					atSignIndex = i;
				}
				containsAtSign = true;
			} 
			if (email.charAt(i) == '.') {
				containsPeriod = true;
				periodIndex = i;
			}
		}

		if (!containsAtSign || !containsPeriod) {
			return false;
		}

		return atSignIndex < periodIndex;
	}

	/**
	 * This method removes a faculty member from the directory
	 * 
	 * @param id is the id of the faculty member the user wants to remove
	 * @return true if the removal was successful and false if the faculty member
	 *         doesn't exist
	 */
	public boolean removeFaculty(String id) {
		for (int i = 0; i < facultyDirectory.size(); i++) {
			if (id.equals(facultyDirectory.get(i).getId())) {
				facultyDirectory.remove(i);
				return true;
			}
		}
		return false;

	}

	/**
	 * This returns a 2D array of the directory for the GUI
	 * 
	 * @return a 2D representation of the directory for the GUI
	 */
	public String[][] getFacultyDirectory() {
		String[][] directory = new String[facultyDirectory.size()][3];

		for (int i = 0; i < facultyDirectory.size(); i++) {
			directory[i][0] = facultyDirectory.get(i).getFirstName();
			directory[i][1] = facultyDirectory.get(i).getLastName();
			directory[i][2] = facultyDirectory.get(i).getId();

		}

		return directory;
	}

	/**
	 * This saves the directory to a specified file
	 * 
	 * @param fileName is the name of the file the user wants to save to
	 */
	public void saveFacultyDirectory(String fileName) {
		try {
			FacultyRecordIO.writeFacultyRecords(fileName, facultyDirectory);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + fileName);
		}
	}

	/**
	 * This method retrieves a faculty member by id
	 * 
	 * @param id the the id of the faculty member
	 * @return the faculty member that shares the id parameter
	 */
	public Faculty getFacultyById(String id) {
		Faculty faculty = null;
		for (int i = 0; i < facultyDirectory.size(); i++) {
			User s = facultyDirectory.get(i);
			if (id.equals(s.getId())) {
				faculty = facultyDirectory.get(i);
			}
		}
		return faculty;
	}
}
