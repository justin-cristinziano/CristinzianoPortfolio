/**
 * 
 */
package edu.ncsu.csc216.product_backlog.model.product;

import java.util.ArrayList;

import java.util.List;

import edu.ncsu.csc216.product_backlog.model.command.Command;
import edu.ncsu.csc216.product_backlog.model.task.Task;
import edu.ncsu.csc216.product_backlog.model.task.Task.Type;



/**
 * This class manages the products that contain lists of tasks
 */
public class Product {

	/**
	 * This is the field responsible for holding the list of tasks for a product
	 */
	private ArrayList<Task> tasks;

	/**
	 * This field is responsible for holding the product name
	 */
	private String productName;

	/**
	 * This field is responsible for holding the number of tasks there are for a
	 * product
	 */
	private int counter;

	/**
	 * This is Product's constructor. It sets the product name
	 * 
	 * @param name is the name of the product
	 */
	public Product(String name) {
		setProductName(name);
		this.tasks = new ArrayList<Task>();
		this.counter = 1;

	}

	/**
	 * Sets the product name.
	 *
	 * @param productName The product name to set. Must not be null or an empty
	 *                    string.
	 * @throws IllegalArgumentException If the provided product name is null or an
	 *                                  empty string.
	 */
	public void setProductName(String productName) {
		if ("".equals(productName) || productName == null) {
			throw new IllegalArgumentException("Invalid product name.");
		} else {
			this.productName = productName.trim();
		}
	}

	/**
	 * Retrieves the product name.
	 *
	 * @return The product name.
	 */
	public String getProductName() {
		return this.productName;
	}

	/**
	 * Adds a task to the list in sorted order by task ID.
	 *
	 * @param task The task to add.
	 * @throws IllegalArgumentException If a task with the same ID already exists in
	 *                                  the list.
	 */
	public void addTask(Task task) {
		int newTaskId = task.getTaskId();

		for (int i = 0; i < tasks.size(); i++) {
			if (newTaskId == tasks.get(i).getTaskId()) {
				throw new IllegalArgumentException("Task cannot be added.");
			}
		}

		if (tasks.size() == 0) {
			tasks.add(task);
		} else {
			for (int i = 0; i < tasks.size(); i++) {
				if (newTaskId < tasks.get(i).getTaskId()) {
					tasks.add(i, task);
					break;

					// if you reach the end its not smaller than any of the tasks
				} else if (i == tasks.size() - 1) {
					tasks.add(task);
					break;
				}
			}
		}
	}

	/**
	 * Creates and adds a new task using the provided information. The task ID will
	 * be generated based on the current counter value.
	 *
	 * @param title   The title of the task.
	 * @param type    The type of the task.
	 * @param creator The creator of the task.
	 * @param note    Additional notes for the task.
	 * @throws IllegalArgumentException If a task with the same ID already exists in
	 *                                  the list.
	 */
	public void addTask(String title, Type type, String creator, String note) {
		setTaskCounter();

		Task newTask = new Task(counter, title, type, creator, note);

		addTask(newTask);
	}

	/**
	 * Retrieves the list of tasks.
	 *
	 * @return The list of tasks.
	 */
	public List<Task> getTasks() {
		return tasks;
	}

	/**
	 * Retrieves a task from the list by its ID.
	 *
	 * @param id The ID of the task to retrieve.
	 * @return The task with the given ID, or null if no task with that ID exists.
	 */
	public Task getTaskById(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskId() == id) {
				return tasks.get(i);
			}
		}

		return null;
	}

	/**
	 * Deletes a task from the list by its ID.
	 *
	 * @param id The ID of the task to delete.
	 */
	public void deleteTaskById(int id) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskId() == id) {
				tasks.remove(i);
			}
		}
	}

	/**
	 * Executes a command on a task with the given ID.
	 *
	 * @param id The ID of the task to update.
	 * @param c  The command to execute on the task.
	 */
	public void executeCommand(int id, Command c) {
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskId() == id) {
				tasks.get(i).update(c);
				
			}
		}
	}

	/**
	 * Finds the highest id and sets the task to (highest + 1)
	 */
	private void setTaskCounter() {
		int highestId = 0;

		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).getTaskId() > highestId) {
				highestId = tasks.get(i).getTaskId();
			}
		}

		counter = highestId + 1;
	}
}
