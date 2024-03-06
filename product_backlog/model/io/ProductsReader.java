package edu.ncsu.csc216.product_backlog.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * This handles the IO of the project
 */
public class ProductsReader {

	/**
	 * Reads the list of products from a file and puts them into a list of products
	 * 
	 * @param fileName is the name of the file
	 * @return an ArrayList of the products in the file
	 * @throws IllegalArgumentException if unable to load from file
	 */
	public static ArrayList<Product> readProductsFile(String fileName) throws IllegalArgumentException {
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
			ArrayList<Product> productList = new ArrayList<Product>();

			String productLine = "";
			fileReader.useDelimiter("\\r?\\n?[#]");
			
			while(fileReader.hasNext()) {
				productLine = fileReader.next();
				
				if(processProduct(productLine) != null && processProduct(productLine).getTasks().size() != 0) {
					productList.add(processProduct(productLine));
				}
			}
			fileReader.close();
			return productList;
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
	}

	/**
	 * Processes the next line of the file into a product
	 * 
	 * @param productLine is the next like from the file to be processed
	 * @return Product a new Product
	 */
	private static Product processProduct(String productLine) {
		String productName = "";
		String taskLine = "";
		
		Scanner productScanner = new Scanner(productLine);
		productName = productScanner.nextLine().trim();
		Product newProduct = new Product(productName);
		
		productScanner.useDelimiter("\\r?\\n?[*]");
		
		while(productScanner.hasNext()) {
			taskLine = productScanner.next();
			
			try {
				newProduct.addTask(processTask(taskLine));
			} catch (Exception e) {
				//Stops this iteration (task line) and moves to the next
				continue;
			}
		}
		
		productScanner.close();
		
		if(productName.contains(",")) {
			return null;
		} else {
			return newProduct;
		}
	}

	/**
	 * This method processes a task from a string read from the file
	 * 
	 * @param taskLine is the line that the task is on
	 * @return a task
	 */
	private static Task processTask(String taskLine) {
		Scanner taskScanner = new Scanner(taskLine);

		String taskInfo = taskScanner.nextLine();

		Scanner taskScanner2 = new Scanner(taskInfo);
		taskScanner2.useDelimiter(",");
		String idString = taskScanner2.next();
		int id = Integer.parseInt(idString.trim());
		String state = taskScanner2.next().trim();
		String title = taskScanner2.next().trim();
		String type = taskScanner2.next().trim();
		String creator = taskScanner2.next().trim();
		String owner = taskScanner2.next().trim();
		String verified = taskScanner2.next().trim();

		taskScanner2.close();

		String subNote = "";
		ArrayList<String> notes = new ArrayList<String>();
		taskScanner.useDelimiter("\\r?\\n?[-]");
		while (taskScanner.hasNext()) {
			String fullNote = taskScanner.next();
			subNote = fullNote.substring(1);
			notes.add(subNote.trim());
		}

		taskScanner.close();

		if (notes.size() < 1) {
			id = -1;
		}

		//id set to negative so this should throw an exception if no notes
		Task task = new Task(id, state, title, type, creator, owner, verified, notes);
		return task;

	}
}
