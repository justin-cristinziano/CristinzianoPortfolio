package edu.ncsu.csc216.product_backlog.model.backlog;

import java.util.ArrayList;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.io.ProductsReader;
import edu.ncsu.csc216.product_backlog.model.io.ProductsWriter;
import edu.ncsu.csc216.product_backlog.model.product.Product;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;

/**
 * Implements the Singleton method to manage the backlog
 */
public class BacklogManager {

	/**
	 * This is the current Product instance that this class is working with
	 */
	private Product currentProduct;

	/**
	 * This is the single instance of the BacklogManagers
	 */
	private static BacklogManager singleton;

	/**
	 * This field holds all the products
	 */
	private ArrayList<Product> products = new ArrayList<Product>();

	/**
	 * The constructor for BacklogManager (only one instance of it b/c of singleton)
	 */
	private BacklogManager() {

	}

	/**
	 * Retrieves the singleton instance of the BacklogManager. This method follows
	 * the Singleton design pattern.
	 *
	 * @return The singleton instance of the BacklogManager.
	 */
	public static BacklogManager getInstance() {
		if (singleton == null) {
			singleton = new BacklogManager();
		}

		return singleton;
	}

	/**
	 * Saves the current state of the BacklogManager to a file with the given file
	 * name.
	 *
	 * @param fileName The name of the file to save to.
	 */
	public void saveToFile(String fileName) {
		if (currentProduct == null || currentProduct.getTasks().size() < 1) {
			throw new IllegalArgumentException("Unable to save file.");
		} else {
			ProductsWriter.writeProductsToFile(fileName, products);
		}
	}

	/**
	 * Loads the state of the BacklogManager from a file with the given file name.
	 *
	 * @param fileName The name of the file to load from.
	 */
	public void loadFromFile(String fileName) {
		ArrayList<Product> newProducts = new ArrayList<Product>();

		newProducts = ProductsReader.readProductsFile(fileName);

		for (int i = 0; i < newProducts.size(); i++) {
			products.add(newProducts.get(i));
		}

		loadProduct(newProducts.get(0).getProductName());
		currentProduct = newProducts.get(0);
	}

	/**
	 * Loads the product with the given name as the current product.
	 *
	 * @param productName The name of the product to load.
	 * @throws IllegalArgumentException If the requested product is not available.
	 */
	public void loadProduct(String productName) {
		boolean foundProduct = false;

		for (int i = 0; i < products.size(); i++) {
			if (products.get(i).getProductName().equals(productName)) {
				currentProduct = products.get(i);
				foundProduct = true;
			}
		}

		if (!foundProduct) {
			throw new IllegalArgumentException("Product not available.");
		}
	}

	/**
	 * Retrieves the tasks in the BacklogManager as a 2D string array.
	 *
	 * @return A 2D string array containing task information.
	 */
	public String[][] getTasksAsArray() {

		if (currentProduct == null) {
			return null;
		}

		String[][] tasksArray = new String[currentProduct.getTasks().size()][4];
		

		for (int i = 0; i < currentProduct.getTasks().size(); i++) {
			tasksArray[i][0] = Integer.toString(currentProduct.getTasks().get(i).getTaskId());
			tasksArray[i][1] = currentProduct.getTasks().get(i).getStateName();
			tasksArray[i][2] = currentProduct.getTasks().get(i).getTypeLongName();
			tasksArray[i][3] = currentProduct.getTasks().get(i).getTitle();
		}

		return tasksArray;
	}

	/**
	 * Retrieves a task from the current product by its ID.
	 *
	 * @param id The ID of the task to retrieve.
	 * @return The task with the given ID, or null if no task with that ID exists in
	 *         the current product.
	 */
	public Task getTaskById(int id) {

		return currentProduct.getTaskById(id);

	}

	/**
	 * Executes a command on a task with the given ID in the current product.
	 *
	 * @param id The ID of the task to update.
	 * @param c  The command to execute on the task.
	 */
	public void executeCommand(int id, Command c) {
		currentProduct.executeCommand(id, c);
	}

	/**
	 * Deletes a task with the given ID from the current product.
	 *
	 * @param id The ID of the task to delete.
	 */
	public void deleteTaskById(int id) {
		currentProduct.deleteTaskById(id);
	}

	/**
	 * Adds a new task to the current product.
	 *
	 * @param title   The title of the task.
	 * @param type    The type of the task.
	 * @param creator The creator of the task.
	 * @param note    Additional notes for the task.
	 */
	public void addTaskToProduct(String title, Type type, String creator, String note) {
		currentProduct.addTask(title, type, creator, note);
	}

	/**
	 * Retrieves the name of the current product.
	 *
	 * @return The name of the current product.
	 */
	public String getProductName() {
		if (currentProduct == null) {
			return null;
		}

		return currentProduct.getProductName();
	}

	/**
	 * Retrieves a list of product names in the order they are listed in the
	 * products list.
	 *
	 * @return An array of product names.
	 */
	public String[] getProductList() {
		if(products == null) {
			return null;
		}
		
		String[] productList = new String[products.size()];

		for (int i = 0; i < products.size(); i++) {
			productList[i] = products.get(i).getProductName();
		}

		return productList;
	}

	/**
	 * Resets the list of products and sets the current product to null.
	 */
	public void clearProducts() {
		products.clear();
	}

	/**
	 * Updates the name of the current product to the given value.
	 *
	 * @param updateName The new name for the current product.
	 * @throws IllegalArgumentException If the new name is a duplicate, null, or an
	 *                                  empty string, or if no product is selected.
	 */
	public void editProduct(String updateName) {

		boolean productExists = false;
		if (currentProduct == null) {
			throw new IllegalArgumentException("No product selected.”");
		} else if (updateName != null && !("".equals(updateName))) {
			for (int i = 0; i < products.size(); i++) {
				if (products.get(i).getProductName().equals(updateName)) {
					productExists = true;
				}
			}

			if (!productExists) {
				currentProduct.setProductName(updateName);
			}
		}
	}

	/**
	 * Adds a new product to the backlog
	 * 
	 * @param productName is the name of the product
	 */
	public void addProduct(String productName) {
		for(int i = 0; i < products.size(); i++) {
			if(products.get(i).getProductName().toLowerCase().equals(productName.trim().toLowerCase())) {
				throw new IllegalArgumentException("Invalid product name.");
			}
		}
		
		this.products.add(new Product(productName.trim()));
		loadProduct(productName.trim());
	}

	/**
	 * Deletes the current product and updates the current product to the first
	 * product in the list.
	 *
	 * @throws IllegalArgumentException If no product is selected.
	 */
	public void deleteProduct() {
		
		if(currentProduct == null) {
			throw new IllegalArgumentException("No product selected.");
		}
		
		products.remove(currentProduct);
	}

	/**
	 * Resets the BacklogManager by setting the singleton instance to null. This
	 * method is intended for testing the BacklogManager class.
	 */
	protected void resetManager() {
		singleton = null;
	}
}
