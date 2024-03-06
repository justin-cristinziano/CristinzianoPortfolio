package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Interface for Queue that allows the addition, removal, checks for an empty
 * list, checks the size of the list, and set the capacity of the list
 * 
 * @param <E> a generic object
 * 
 * @author Connor Young
 * @author Justin Cristinziano
 * @author Ashwini Jha
 */
public interface Queue<E> {

	/**
	 * Adds the element to the back of the Queue
	 * 
	 * @param element the element to be added to the queue
	 * @throws IllegalArgumentException if there is no room in the list (capacity
	 *                                  has been reached)
	 */
	void enqueue(E element);

	/**
	 * Removes and returns the element at the front of the Queue
	 * 
	 * @return the element that was removed
	 * @throws NoSuchElementException if the Queue is empty
	 */
	E dequeue();

	/**
	 * Checks if the Queue is empty
	 * 
	 * @return true if the Queue is empty
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return the number of elements in the Queue
	 */
	int size();

	/**
	 * Sets the Queue Capacity
	 * 
	 * @param capacity the desired capacity of the queue
	 * @throws IllegalArgumentException if the capacity is negative or less than the
	 *                                  number of elements in the Queue
	 */
	void setCapacity(int capacity);

}
