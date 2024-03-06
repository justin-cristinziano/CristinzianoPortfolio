/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * This is the Stack class. You can add to the end and remove from the end. You
 * can also get the size and set a capacity (a limit on the size).
 * 
 * @param <E> is an object of any type
 */
public class ArrayStack<E> implements Stack<E> {

	/**
	 * This field is the Array List that we are going to be manipulating. In other
	 * words, this is the "stack".
	 */
	private ArrayList<E> list;

	/**
	 * This is the capacity for the stack
	 */
	private int capacity;

	/**
	 * This constructs the ArrayStack (sets list and capacity fields)
	 * 
	 * @param capacity is the stacks max size
	 */
	public ArrayStack(int capacity) {
		list = new ArrayList<E>();
		setCapacity(capacity);
	}

	/**
	 * This method adds an element to the end of the list
	 * 
	 * @param element is the element to add
	 */
	@Override
	public void push(E element) {
		if(list.size() == capacity) {
			throw new IllegalArgumentException("List is already at capacity");
		}
		
		list.add(element);
	}

	/**
	 * This method takes the last element of the stack, removes it from the stack,
	 * and returns it to the client that calls it.
	 * 
	 * @return removed the element that is removed
	 */
	@Override
	public E pop() {

		if (list.size() == 0) {
			throw new EmptyStackException();
		}

		E removed = list.get(list.size() - 1);

		list.remove(list.size() - 1);

		return removed;
	}

	/**
	 * Returns true if the stack is empty (size = 0) and false if not
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	/**
	 * Returns the size of the stack for the client that calls it
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return list.size();
	}

	/**
	 * Sets the capacity of the stack
	 * 
	 * @param capacity is the desired stack capacity
	 */
	@Override
	public void setCapacity(int capacity) {
		if (capacity < 0 || capacity < size()) {
			throw new IllegalArgumentException("Can't set capacity smaller than list size");
		}

		this.capacity = capacity;
	}

}
