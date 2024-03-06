package edu.ncsu.csc216.stp.model.tests;

import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.util.ILog;
import edu.ncsu.csc216.stp.model.util.Log;

/**
 * This class represents a specific test case within a test plan.
 */
public class TestCase {

	/** Test cases id */
	private String testCaseId;

	/** Test cases type */
	private String testType;

	/** Test cases description */
	private String testDescription;

	/** Test cases expected results */
	private String expectedResults;

	/** Test cases test plan */
	private TestPlan testPlan;

	/** Test results for a test case */
	private ILog<TestResult> testResults;

	/**
	 * Constructs a test case.
	 * 
	 * @param testCaseId      test cases id
	 * @param testType        test cases type
	 * @param testDescription test cases description
	 * @param expectedResults test cases expected results
	 */
	public TestCase(String testCaseId, String testType, String testDescription, String expectedResults) {
		setTestCaseId(testCaseId);
		setTestType(testType);
		setTestDescription(testDescription);
		setExpectedResults(expectedResults);
		testPlan = null;
		testResults = new Log<TestResult>();

	}

	/**
	 * Gets the test cases id.
	 * 
	 * @return the testCaseId
	 */
	public String getTestCaseId() {
		return testCaseId;
	}

	/**
	 * Sets the test cases id.
	 * 
	 * @param testCaseId the testCaseId to set
	 */
	private void setTestCaseId(String testCaseId) {
		if (testCaseId == null || "".equals(testCaseId)) {
			throw new IllegalArgumentException("Invalid test information.");
		}

		this.testCaseId = testCaseId;
	}

	/**
	 * Gets the test cases type.
	 * 
	 * @return the testType
	 */
	public String getTestType() {
		return testType;
	}

	/**
	 * Sets the test cases type.
	 * 
	 * @param testType the testType to set
	 */
	private void setTestType(String testType) {
		if (testType == null || "".equals(testType)) {
			throw new IllegalArgumentException("Invalid test information.");
		}

		this.testType = testType;
	}

	/**
	 * Gets the test cases description.
	 * 
	 * @return the testDescription
	 */
	public String getTestDescription() {
		return testDescription;
	}

	/**
	 * Sets the test cases description
	 * 
	 * @param testDescription the testDescription to set
	 */
	private void setTestDescription(String testDescription) {
		if (testDescription == null || "".equals(testDescription)) {
			throw new IllegalArgumentException("Invalid test information.");
		}

		this.testDescription = testDescription;
	}

	/**
	 * Gets the test cases expected results.
	 * 
	 * @return the expectedResults
	 */
	public String getExpectedResults() {
		return expectedResults;
	}

	/**
	 * Sets the test cases expected results.
	 * 
	 * @param expectedResults the expectedResults to set
	 */
	private void setExpectedResults(String expectedResults) {
		if (expectedResults == null || "".equals(expectedResults)) {
			throw new IllegalArgumentException("Invalid test information.");
		}

		this.expectedResults = expectedResults;
	}

	/**
	 * Adds a test result.
	 * 
	 * @param pass whether its passing
	 * @param test test result information
	 */
	public void addTestResult(boolean pass, String test) {
		try {
			testResults.add(new TestResult(pass, test));
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid test results.");
		}
	}

	/**
	 * Gets if the test case is passing.
	 * 
	 * @return passing
	 */
	public boolean isTestCasePassing() {
		if (testResults.size() == 0) {
			return false;
		} else {
			return testResults.get(testResults.size() - 1).isPassing();
		}
	}

	/**
	 * Gets the test cases status.
	 * 
	 * @return status
	 */
	public String getStatus() {
		if (isTestCasePassing()) {
			return TestResult.PASS;
		} else {
			return TestResult.FAIL;
		}
	}

	/**
	 * Gets the test cases actual results log.
	 * 
	 * @return log
	 */
	public String getActualResultsLog() {
		String actualResultsLog = "";

		for (int i = 0; i < testResults.size(); i++) {
			actualResultsLog += "- " + testResults.get(i).toString() + "\n";
		}

		return actualResultsLog;
	}

	/**
	 * Sets the test cases test plan.
	 * 
	 * @param testPlan desired test plan
	 */
	public void setTestPlan(TestPlan testPlan) {
		if (testPlan == null) {
			throw new IllegalArgumentException("Invalid test plan.");
		}

		this.testPlan = testPlan;
	}

	/**
	 * Gets the current test plan.
	 * 
	 * @return test plan
	 */
	public TestPlan getTestPlan() {
		return this.testPlan;
	}

	/**
	 * Changes test case to a string.
	 * 
	 * @return string
	 */
	public String toString() {
		String testCaseString = "";

		testCaseString += "# " + getTestCaseId() + "," + getTestType() + "\n";
		testCaseString += "* " + getTestDescription() + "\n";
		
		//Test Description
		//...
		//...
		
		testCaseString += "* " + getExpectedResults() + "\n";
		testCaseString += getActualResultsLog();
		
		return testCaseString;
		
	}

}
