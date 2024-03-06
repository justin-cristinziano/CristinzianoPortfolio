package edu.ncsu.csc216.stp.model.manager;

import java.io.File;

import edu.ncsu.csc216.stp.model.io.TestPlanReader;
import edu.ncsu.csc216.stp.model.io.TestPlanWriter;
import edu.ncsu.csc216.stp.model.test_plans.AbstractTestPlan;
import edu.ncsu.csc216.stp.model.test_plans.FailingTestList;
import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.ISwapList;
import edu.ncsu.csc216.stp.model.util.SortedList;
import edu.ncsu.csc216.stp.model.util.SwapList;

/**
 * This class manages all of the other objects. It works directly with the GUI
 * to display, arrange, and manipulate data between all of the classes of this
 * project
 */
public class TestPlanManager {

	/** Name for the failing test list */
	public static final String FAILING_TEST_LIST_NAME = "Failing Tests";
	
	/**
	 * This is the sorted list of test plans
	 */
	private ISortedList<TestPlan> testPlans;

	/**
	 * This is the list of failing tests
	 */
	private FailingTestList failingTestList;

	/**
	 * This is the currentTestPlan (used by the GUI to change the current plan)
	 */
	private AbstractTestPlan currentTestPlan;

	/**
	 * This boolean represents whether TestPlanManager has been changed since the
	 * last save.
	 */
	private boolean isChanged;

	/**
	 * This constructs an empty testPlanManager
	 */
	public TestPlanManager() {
		// Initialize the fields as described
		this.testPlans = new SortedList<TestPlan>();
		this.failingTestList = new FailingTestList();
		this.currentTestPlan = failingTestList;
		this.isChanged = false;
		clearTestPlans();
	}

	/**
	 * This method loads a list of test plans and their data from the file
	 * testPlanFile
	 * 
	 * @param testPlanFile is the name of the file to load the info from
	 */
	public void loadTestPlans(File testPlanFile) {

		ISortedList<TestPlan> newPlans = TestPlanReader.readTestPlansFile(testPlanFile);
		for (int i = 0; i < newPlans.size(); i++) {
			try {
				testPlans.add(newPlans.get(i));
			} catch (IllegalArgumentException e) {
				continue;
			}
			
		}
		setCurrentTestPlan("Failing Tests");
		isChanged = true;
	}

	/**
	 * This method saves the list of test plans and their data to a specified file,
	 * testPlanFile
	 * 
	 * @param testPlanFile is the name of the file to load the data to
	 */
	public void saveTestPlans(File testPlanFile) {
		TestPlanWriter.writeTestPlanFile(testPlanFile, testPlans);
		isChanged = false;
	}

	/**
	 * This method adds a new test plan to the list of test plans
	 * 
	 * @param testPlanName is the name of the new testPlan
	 */
	public void addTestPlan(String testPlanName) {
		
		if (testPlanName.equalsIgnoreCase(FAILING_TEST_LIST_NAME) ) {
			throw new IllegalArgumentException("Invalid name.");
		}
		
		for (int i = 0; i < testPlans.size(); i++) {
			if (testPlans.get(i).getTestPlanName().equalsIgnoreCase(testPlanName)){
				throw new IllegalArgumentException("Invalid name.");
			}
		}
		
		
		TestPlan newPlan = new TestPlan(testPlanName);
		testPlans.add(newPlan);
		setCurrentTestPlan(testPlanName);
		isChanged = true;
		
		
	}

	/**
	 * This checks if a change has been made since the last save. If there was a
	 * change, it returns true.
	 * 
	 * @return a boolean that is true if its been changed since the last save and
	 *         false if not
	 */
	public boolean isChanged() {
		return isChanged;
	}

	/**
	 * This retrieves a string array of all the testPlan names for the GUI
	 * 
	 * @return a string array of all the testplan names
	 */
	public String[] getTestPlanNames() {
		String[] plans = new String[testPlans.size() + 1];
		plans[0] = failingTestList.getTestPlanName();
		for (int i = 0; i < testPlans.size(); i++) {
			plans[i + 1] = testPlans.get(i).getTestPlanName();
		}
		return plans;
	}

	/**
	 * This retrieves a string array of all the failing tests in the list. It is a
	 * helper method to help control the FailingTestList
	 */
	private void getFailingTests() {
		failingTestList.clearTests();
		TestPlan temp = null;
		ISwapList<TestCase> tests = new SwapList<TestCase>();
		for (int i = 0; i < testPlans.size(); i++) {
			temp = testPlans.get(i);
			tests = temp.getTestCases();
			for (int j = 0; j < tests.size(); j++) {
				if (!(tests.get(j).isTestCasePassing())) {
					failingTestList.addTestCase(tests.get(j));
				}
			}
		}
	}
			

	/**
	 * This method sets the current test plan in the GUI
	 * 
	 * @param testPlanName is the name of the test plan 
	 */
	public void setCurrentTestPlan(String testPlanName) {
		getFailingTests();
		boolean exists = false;
		for (int i = 0; i < testPlans.size(); i++) {
			if (testPlans.get(i).getTestPlanName().equals(testPlanName)){
				exists = true;
				this.currentTestPlan = testPlans.get(i);
			}
		}
		
		if (!exists) {
			this.currentTestPlan = failingTestList;
		}
	}

	/**
	 * Retrieves the current test plan that is selected in the GUI
	 * @return currentTestPlan is the AbstractTestPlan currently selected in the GUI
	 */
	public AbstractTestPlan getCurrentTestPlan() {
		return currentTestPlan;
	}

	/**
	 * This method edits the specified test name (testPlanName)
	 * @param testPlanName is the name of the test plan the client wants to edit
	 */
	public void editTestPlan(String testPlanName) {
		if (getCurrentTestPlan().equals(failingTestList)){
			throw new IllegalArgumentException("The Failing Tests list may not be edited.");
		}
		
		if (testPlanName.equalsIgnoreCase(FAILING_TEST_LIST_NAME)) {
			throw new IllegalArgumentException("Invalid name.");
		}
		
		for (int i = 0; i < testPlans.size(); i++) {
			if (testPlans.get(i).getTestPlanName().equalsIgnoreCase(testPlanName)) {
				throw new IllegalArgumentException("Invalid name.");
			}
		}
		
		currentTestPlan.setTestPlanName(testPlanName);
		isChanged = true;
		
	}

	/**
	 * This method removes the test plan from the list of test plans
	 */
	public void removeTestPlan() {
		if (getCurrentTestPlan().equals(failingTestList)){
			throw new IllegalArgumentException("The Failing Tests list may not be deleted.");
		}
		
		for(int i = 0; i < testPlans.size(); i++) {
			if (testPlans.get(i).equals(getCurrentTestPlan())) {
				testPlans.remove(i);
				break;
			}
		}
		
		this.currentTestPlan = failingTestList;
		isChanged = true;

		
	}

	/**
	 * This method adds a test to the current test plan
	 * @param t is the Test Case to add
	 */
	public void addTestCase(TestCase t) {
		if (!(getCurrentTestPlan().equals(failingTestList) || getCurrentTestPlan() == null)) {
			getCurrentTestPlan().addTestCase(t);
			if (!(t.isTestCasePassing())) {
				getFailingTests();
			}
			isChanged = true;
		}
	}

	/**
	 * This adds a new test result to the test case
	 * 
	 * @param idx is the test case's index to add the result to
	 * @param passing is a boolean representing whether the test is passing or not
	 * @param actualResult is the actual result of the test case
	 */
	public void addTestResult(int idx, boolean passing, String actualResult) {
		if (!(getCurrentTestPlan().equals(failingTestList) || getCurrentTestPlan() == null)) {
			TestCase test = getCurrentTestPlan().getTestCase(idx);
			test.addTestResult(passing, actualResult);
			if (!(test.isTestCasePassing())) {
				getFailingTests();
			}
		}
	}

	/**
	 *  This method clears all the test plans from the list
	 */
	public void clearTestPlans() {
		testPlans = new SortedList<TestPlan>();
		failingTestList = new FailingTestList();
		this.currentTestPlan = failingTestList;
		isChanged = false;
	}

}
