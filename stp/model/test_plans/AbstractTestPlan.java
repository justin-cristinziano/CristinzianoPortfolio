package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISwapList;
import edu.ncsu.csc216.stp.model.util.SwapList;

/**
 * This is an abstract class that defines a object TestPlan that represents a system test plan.
 */
public abstract class AbstractTestPlan {
	
	/** The test plans name */
	private String testPlanName;
	
	/** Swap list of test cases */
	private ISwapList<TestCase> testCases;

	/**
	 * AbstractTestPlans constructor.
	 * @param name test plans name
	 */
	public AbstractTestPlan(String name) {
		setTestPlanName(name);
		testCases = new SwapList<TestCase>();
	}

	
	/**
	 * Gets the test plans name.
	 * @return the testPlanName
	 */
	public String getTestPlanName() {
		return testPlanName;
	}

	/**
	 * Sets the test plans name to a given name.
	 * @param testPlanName the testPlanName to set
	 */
	public void setTestPlanName(String testPlanName) {
		
		if (testPlanName == null || testPlanName.length() == 0) {
			throw new IllegalArgumentException("Invalid name.");
		}
		
		this.testPlanName = testPlanName;
	}
	
	/**
	 * Gets the test plans test cases.
	 * @return SwapList of the test plans test cases
	 */
	public ISwapList<TestCase> getTestCases(){
		return testCases;
	}
	
	/**
	 * Adds a test case to the test plan.
	 * @param test case being added
	 */
	public void addTestCase(TestCase test) {
		testCases.add(test);
	}
	
	/**
	 * Removes a test case based on a given id.
	 * @param id test cases id.
	 * @return null
	 */
	public TestCase removeTestCase(int id) {
		return testCases.remove(id);
	}
	
	/**
	 * Gets a test case by a given id. 
	 * @param idx desired test cases id
	 * @return the test case that matches the given id
	 */
	public TestCase getTestCase(int idx) {
		return testCases.get(idx);
	}
	
	/**
	 * Gets the number of failing tests.
	 * @return number of failing tests
	 */
	public int getNumberOfFailingTests() {
		int count = 0;
		for (int i = 0; i < testCases.size(); i++) {
			if (!(testCases.get(i).isTestCasePassing())) {
				count++;
			}
		}
		return count; 
	}
	
	/**
	 * Adds a test result.
	 * @param idx test cases id
	 * @param passing whether its passing or not
	 * @param info about the test result
	 */
	public void addTestResult(int idx, boolean passing, String info) {
		getTestCase(idx).addTestResult(passing, info);
	}
	
	/**
	 * Gets the test cases as an array
	 * @return 2d string array of the test cases
	 */
	public abstract String[][] getTestCasesAsArray();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((testPlanName == null) ? 0 : testPlanName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTestPlan other = (AbstractTestPlan) obj;
		if (testPlanName == null) {
			if (other.testPlanName != null)
				return false;
		} else if (!testPlanName.equalsIgnoreCase(other.testPlanName))
			return false;
		return true;
	}
	
	
	
}
