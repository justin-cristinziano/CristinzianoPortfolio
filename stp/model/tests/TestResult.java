package edu.ncsu.csc216.stp.model.tests;

/**
 * This class represents a test result of a test case.
 */
public class TestResult {
	
	/** String that represents a passing result */
	public static final String PASS = "PASS";
	
	/** String that represents a failing result */
	public static final String FAIL = "FAIL";
	
	/** Whether or not the test result is passing */
	private boolean passing;
	
	/** The test results actual results */
	private String actualResults;
	
	
	/** 
	 * Test Results constructor.
	 * @param passing whether its passing
	 * @param actualResults its actual results
	 */
	public TestResult(boolean passing, String actualResults) {
		setPassing(passing);
		setActualResults(actualResults);
	}

	/**
	 * Gets test result is passing or not.
	 * @return passing
	 */
	public boolean isPassing() {
		return passing;
	}

	/**
	 * Sets whether the test result is passing.
	 * @param passing the passing to set
	 */
	private void setPassing(boolean passing) {
		this.passing = passing;
	}

	/**
	 * Gets the actual results.
	 * @return the actualResults
	 */
	public String getActualResults() {
		return actualResults;
	}

	/**
	 * Sets the actual results.
	 * @param actualResults the actualResults to set
	 */
	private void setActualResults(String actualResults) {
		if(actualResults == null || "".equals(actualResults)) {
			throw new IllegalArgumentException("Invalid test results.");
		}
		
		this.actualResults = actualResults;
	}
	
	/**
	 * Turns the test results into a string.
	 * @return String
	 */
	public String toString() {
		if(isPassing()) {
			return PASS + ": " + getActualResults();
		} else {
			return FAIL + ": " + getActualResults();
		}
	}
	
	
}
