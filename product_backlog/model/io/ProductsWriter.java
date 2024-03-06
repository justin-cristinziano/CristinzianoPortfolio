package edu.ncsu.csc216.product_backlog.model.io;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;

/**
 * Writes the list of products to a specified file
 */
public class ProductsWriter {

	/**
	 * The constructor for ProductsWriter
	 */
	public ProductsWriter() {

	}

	/**
	 * Writes the products list to a specified file
	 * 
	 * @param fileName is the name of the file
	 * @param products is the ArrayList of products
	 * @throws IllegalArgumentException if there are any errors or exceptions
	 */
	public static void writeProductsToFile(String fileName, ArrayList<Product> products)
			throws IllegalArgumentException {

		try {
			PrintStream fileWriter = new PrintStream(new File(fileName));

			for (Product a : products) {
				fileWriter.println("# " + a.getProductName());
				for(Task b: a.getTasks()) {
					fileWriter.print(b.toString());
				}
			}

			fileWriter.close();

		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to read file");
		}

	}
}
