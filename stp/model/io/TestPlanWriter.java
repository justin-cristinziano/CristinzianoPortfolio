package edu.ncsu.csc216.stp.model.io;

import java.io.File;
import java.io.PrintStream;


import edu.ncsu.csc216.stp.model.test_plans.TestPlan;
import edu.ncsu.csc216.stp.model.tests.TestCase;
import edu.ncsu.csc216.stp.model.util.ISortedList;
import edu.ncsu.csc216.stp.model.util.ISwapList;
import edu.ncsu.csc216.stp.model.util.SwapList;

/**
 * This class in charge of printing the test plan information to a file. 
 */
public class TestPlanWriter {

	
	/**
	 * Writes all the test plans in the system to a given file.
	 * @param file File being exported to.
	 * @param testPlans Sorted List of the test plans being printed
	 */
	public static void writeTestPlanFile(File file, ISortedList<TestPlan> testPlans) {
		try {
			String fileName = file.toString();
			PrintStream fileWriter = new PrintStream(new File(fileName));
			String output = "";
			
			TestPlan tempPlan = null;
			TestCase tempTest = null;
			ISwapList<TestCase> testCases = new SwapList<TestCase>();
			
			for (int i = 0; i < testPlans.size(); i++) {
				tempPlan = testPlans.get(i);
				output += "! " + tempPlan.getTestPlanName() + "\n";
				testCases = tempPlan.getTestCases();
				for (int j = 0; j < testCases.size(); j++) {
					
					tempTest = testCases.get(j);
					output += tempTest.toString();
					
				}
				
			}
			
			fileWriter.print(output);
			fileWriter.close();
			
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
		
	}
	
	
}
