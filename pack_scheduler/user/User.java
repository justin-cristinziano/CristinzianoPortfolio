package edu.ncsu.csc216.pack_scheduler.user;

/**
 * Abstract class that contains shared methods between the two types of users,
 * Students and registrars.
 */
public abstract class User {

	/** Student's first name */
	private String firstName;
	/** Student's last name */
	private String lastName;
	/** Student's id */
	private String id;
	/** Student's email */
	private String email;
	/** Student's password */
	private String password;

	/**
	 * Constructor for a user of the PackScheduler program
	 * 
	 * @param firstName the user's first name
	 * @param lastName  the user's last name
	 * @param id        the user's id
	 * @param email     the user's email
	 * @param hashPW    the user's hashed password
	 */
	public User(String firstName, String lastName, String id, String email, String hashPW) {
		super();
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(hashPW);
	}

	/**
	 * Returns first name
	 * 
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets first name. Throws IllegalArgumentException if null or empty
	 * 
	 * @param firstName the firstName to set
	 * @throws IllegalArgumentException if null or empty
	 */
	public void setFirstName(String firstName) {
		if ("".equals(firstName) || firstName == null)
			throw new IllegalArgumentException("Invalid first name");
		this.firstName = firstName;
	}

	/**
	 * Returns last name
	 * 
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets last name. Throws IllegalArgumentException if null or empty
	 * 
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if null or empty
	 */
	public void setLastName(String lastName) {
		if ("".equals(lastName) || lastName == null)
			throw new IllegalArgumentException("Invalid last name");
		this.lastName = lastName;
	}

	/**
	 * Returns id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets id. Throws IllegalArgumentException if null or empty
	 * 
	 * @param id the id to set
	 * @throws IllegalArgumentException if null or empty
	 */
	protected void setId(String id) {
		if ("".equals(id) || id == null)
			throw new IllegalArgumentException("Invalid id");
		this.id = id;
	}

	/**
	 * Returns email
	 * 
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets email. Throws IllegalArgumentException if null or empty, if doesn't
	 * contain @ or . , and if the . comes before the @
	 * 
	 * @param email the email to set
	 * @throws IllegalArgumentException if null or empty, if doesn't contain @ or .
	 *                                  , and if the . comes before the @
	 */
	public void setEmail(String email) {
		if ("".equals(email) || email == null)
			throw new IllegalArgumentException("Invalid email");
		if (!email.contains("@") || !email.contains("."))
			throw new IllegalArgumentException("Invalid email");
		if (email.indexOf('@') > email.lastIndexOf('.'))
			throw new IllegalArgumentException("Invalid email");
		this.email = email;
	}

	/**
	 * Returns password
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets password. Throws IllegalArgumentException if null or empty
	 * 
	 * @param password the password to set
	 * @throws IllegalArgumentException if null or empty
	 */
	public void setPassword(String password) {
		if ("".equals(password) || password == null)
			throw new IllegalArgumentException("Invalid password");
		this.password = password;
	}

	/**
	 * generates a hash value for the user object
	 * 
	 * @return result of hashcode as int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}
	
	/**
	 * Determines if two objects are equal to one another
	 * 
	 * @param obj to compare 
	 * @return boolean based on if objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}