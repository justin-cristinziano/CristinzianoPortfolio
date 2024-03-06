package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * This class keeps track of all the failing tests of a test plan.
 */
public class FailingTestList extends AbstractTestPlan {

	/** Failing tests list name */
	public static final String FAILING_TEST_LIST_NAME = "Failing Tests";
	
	
	/**
	 * Failing Test List constructor.
	 */
	public FailingTestList() {
		super(FAILING_TEST_LIST_NAME);
		
	}

	
	/**
	 * Adds a test case.
	 * @param test test case being added
	 */
	@Override
	public void addTestCase(TestCase test) {
		if (test.isTestCasePassing()) {
			throw new IllegalArgumentException("Cannot add passing test case.");
		} else {
			super.addTestCase(test);
		}
	}
	
	/**
	 * Sets the test plan name to a new name. 
	 * @param name new test plans name
	 */
	public void setTestPlanName(String name) {
		if (name.equalsIgnoreCase(FAILING_TEST_LIST_NAME)) {
			super.setTestPlanName(name);
		} else {
			throw new IllegalArgumentException("The Failing Tests list cannot be edited.");
		}
	}
	
	@Override
	public String[][] getTestCasesAsArray() {
		String[][] failingCasesArray = new String[getTestCases().size()][3];
		for (int i = 0; i < getTestCases().size(); i++) {
			failingCasesArray[i][0] = getTestCases().get(i).getTestCaseId();
			failingCasesArray[i][1] = getTestCases().get(i).getTestType();
			if (this.getTestCase(i).getTestPlan() == null) {
				failingCasesArray[i][2] = "";
			} else {
				failingCasesArray[i][2] = this.getTestCase(i).getTestPlan().getTestPlanName();
			}
		
			
			
		}
		
		return failingCasesArray;
	}
	
	/**
	 * Clears all the tests from the failing tests.
	 */
	public void clearTests() {
		int size = super.getTestCases().size();
		for (int i = 0; i < size; i++) {
			super.removeTestCase(0);
		}
	}
	
	

}
