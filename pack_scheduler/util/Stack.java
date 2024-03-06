package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Interface for Stack that allows the addition, removal, checks for an empty
 * list, checks the size of the list, and set the capacity of the list
 * 
 * @param <E> a generic object
 * 
 * @author Connor Young
 * @author Justin Cristinziano
 * @author Ashwini Jha
 */
public interface Stack<E> {

	/**
	 * Add an element to the front of the list
	 * 
	 * @param element a generic objects
	 */
	void push(E element);

	/**
	 * Removes an element from the from front of the list
	 * 
	 * @return the object removed
	 */
	E pop();

	/**
	 * Checks if the stack is empty
	 * 
	 * @return true if the stack is empty
	 */
	boolean isEmpty();

	/**
	 * gets the size of the stack
	 * 
	 * @return the size of the stack represented as an integer
	 */
	int size();

	/**
	 * sets the capacity for the stack
	 * 
	 * @param capacity the desired capacity of the stack
	 */
	void setCapacity(int capacity);
}
