package edu.ncsu.csc216.stp.model.test_plans;

import edu.ncsu.csc216.stp.model.tests.TestCase;

/**
 * This class defines the TestPlan object which is a representation of a system test plan.
 */
public class TestPlan extends AbstractTestPlan implements Comparable<TestPlan> {

	
	/** Failing tests list name */
	public static final String FAILING_TEST_LIST_NAME = "Failing Tests";
	
	/** String for a passing test case */
	public static final String PASSING_TEST = "PASS";
	
	/** String for a failing test case */
	public static final String FAILING_TEST = "FAIL";
	
	/**
	 * TestPlan's constructor.
	 * @param name test plans name
	 */
	public TestPlan(String name) {
		super(name);
		
		if(name.equalsIgnoreCase(FAILING_TEST_LIST_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}

	}

	
	/**
	 * Gets a 2d array of the test plans test cases.
	 * @return 2d array of test cases
	 */
	@Override
	public String[][] getTestCasesAsArray() {
		
		String[][] testCasesArray = new String[getTestCases().size()][3];
		for (int i = 0; i < getTestCases().size(); i++) {
			testCasesArray[i][0] = getTestCases().get(i).getTestCaseId();
			testCasesArray[i][1] = getTestCases().get(i).getTestType();
			if (getTestCases().get(i).isTestCasePassing()) {
				testCasesArray[i][2] = PASSING_TEST;
			} else {
				testCasesArray[i][2] = FAILING_TEST;
			}
			
		}
		
		return testCasesArray;
		
	}
	
	/**
	 * Adds a test case. 
	 * @param test added test case
	 */
	@Override
	public void addTestCase(TestCase test) {
		super.addTestCase(test);
		test.setTestPlan(this);
		
	}



	@Override
	public int compareTo(TestPlan o) {
		return this.getTestPlanName().compareToIgnoreCase(o.getTestPlanName());
	}
	
	
	
	
}
