package edu.ncsu.csc216.stp.model.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;


import edu.ncsu.csc216.stp.model.test_plans.AbstractTestPlan;
import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.SortedList;

/**
 * This class reads a file containing test plans and their test cases and returns 
 * a sorted list of the test plans
 */
public class TestPlanReader {

	
	
	/**
	 * Reads a file containing test plans information and turns it into
	 * a sorted list of TestPlans.
	 * @param file file being pulled from
	 * @return SortedList of test plans
	 */
	public static ISortedList<TestPlan> readTestPlansFile(File file) {
		try {
	    	Scanner fileReader = new Scanner(new FileInputStream(file));  //Create a file scanner to read the file
	    	ISortedList<TestPlan> testPlans = new SortedList<TestPlan>();
	    	String fileInfo = "";
	    	
	    	
		    while(fileReader.hasNextLine()) {
		    	fileInfo += fileReader.nextLine() + "\n";
		    }
		    
		    fileReader.close();
		    
		    if (fileInfo.charAt(0) != '!') {
		    	throw new IllegalArgumentException("Unable to load file.");
		    }
		    
		    Scanner read = new Scanner(fileInfo);
		    read.useDelimiter("\\r?\\n?[!]");
		    String planToken = "";
		    while(read.hasNext()) {
		    	planToken = read.next();
		    	
		    	testPlans.add(processTestPlan(planToken));
		    }
		    read.close();
		    if (testPlans.size() == 0) {
		    	throw new IllegalArgumentException("No test plans");
		    }
		    
			return testPlans;
			
	    } catch (FileNotFoundException e) {
	    	throw new IllegalArgumentException("Unable to load file.");
	    }
	}
	
	
	/**
	 * Creates a test plan based on a given string of test plan information.
	 * @param testPlan string containing a test plans information
	 * @return TestPlan that was created from the test plan information
	 */
	private static TestPlan processTestPlan(String testPlan) {
		
		String testPlanName = "";
	    String testToken = "";
		Scanner testPlanScanner = new Scanner(testPlan);

    	testPlanName = testPlanScanner.nextLine().trim();
    	TestPlan plan = new TestPlan(testPlanName);

    	// gets the products task information and creates adds tasks from the information
    	testPlanScanner.useDelimiter("\\r?\\n?[#]");
    	while(testPlanScanner.hasNext()) {
    		testToken = testPlanScanner.next();
    		try {
    			plan.addTestCase(processTest(plan, testToken));
    		} catch(Exception e) {
    			continue;
    		}
 
    	}
    	testPlanScanner.close();
    	
    	return plan;
	}
	
	/**
	 * Creates a test case based on a given test case string an the test plan it belongs tp.
	 * @param testPlan Test plan the test case belongs to
	 * @param testCase string containing a test case's information
	 * @return Test Case based on the given string and test plan
	 */
	private static TestCase processTest(AbstractTestPlan testPlan, String testCase) {
		
		String idType = "";
		String description = "";
		String expected = "";
		String id = "";
		String type = "";
		String expectedDesc = "";
		String actualResults = "";
		
		Scanner testScanner = new Scanner(testCase);
		idType = testScanner.nextLine().trim();
		Scanner info = new Scanner(idType);
		info.useDelimiter(",");
		id = info.next();
		type = info.next();
		info.close();
		
		testScanner.useDelimiter("\\r?\\n?[-]");
		expectedDesc = testScanner.next().trim();
		
		Scanner expectedScnr = new Scanner(expectedDesc);
		expectedScnr.useDelimiter("\\r?\\n?[*]");
		//expectedScnr.next();
		description = expectedScnr.next().trim();
		expected = expectedScnr.next().trim();
		
		expectedScnr.close();
		TestCase test = new TestCase(id, type, description, expected);
		Boolean pass;
		String[] actual;
		
		while (testScanner.hasNext()){
			
			actualResults = testScanner.next().trim();
			if (actualResults.contains("PASS")) {
				pass = true;
			} else if (actualResults.contains("FAIL")) {
				pass = false;
			} else {
				testScanner.close();
				throw new IllegalArgumentException("Must be passing or failing");
			}
			
			actual = actualResults.split(" ");
			actualResults = "";
			for (int i = 1; i < actual.length; i++) {
				if (i == 1) {
					actualResults += actual[i];
				} else {
					actualResults += " " + actual[i];
				}
			}
			test.addTestResult(pass, actualResults);
			
			
			
		}
		
		testScanner.close();
		

		return test;
		
		
		
	}
	
}
