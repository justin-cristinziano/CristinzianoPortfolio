package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * * Array implementation of Queue that allows the addition, removal, checks for
 * an empty list, checks the size of the list, and set the capacity of the list
 * 
 * @param <E> a generic object
 * 
 * @author Connor Young
 * @author Justin Cristinziano
 * @author Ashwini Jha
 */
public class ArrayQueue<E> implements Queue<E> {

	/** A generic array list */
	private ArrayList<E> list;

	/** The capacity of the queue */
	private int capacity;

	/**
	 * Constructs a new ArrayQueue with the given capacity
	 * 
	 * @param capacity an integer representing the capacity of the ArrayQueue
	 */
	public ArrayQueue(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * Adds the element to the back of the Queue
	 * 
	 * @param element the element to be added to the queue
	 * @throws IllegalArgumentException if there is no room in the list (capacity
	 *                                  has been reached)
	 */
	@Override
	public void enqueue(E element) {
		if (capacity == list.size()) {
			throw new IllegalArgumentException("Capacity has been reached");
		} else {
			list.add(size(), element);
		}

	}

	/**
	 * Removes and returns the element at the front of the Queue
	 * 
	 * @return the element that was removed
	 * @throws NoSuchElementException if the Queue is empty
	 */
	@Override
	public E dequeue() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return list.remove(0);
	}

	/**
	 * Checks if the Queue is empty
	 * 
	 * @return true if the Queue is empty
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns the number of elements in the Queue
	 * 
	 * @return the number of elements in the Queue
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the Queue Capacity
	 * 
	 * @param capacity the desired capacity of the queue
	 * @throws IllegalArgumentException if the capacity is negative or less than the
	 *                                  number of elements in the Queue
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException();
		}
		this.capacity = capacity;

	}

}
