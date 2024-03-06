/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * LinkedAbstractList implementation for Stack that allows the addition,
 * removal, checks for an empty list, checks the size of the list, and set the
 * capacity of the list
 * 
 * @param <E> a generic object
 * 
 * @author Connor Young
 * @author Justin Cristinziano
 * @author Ashwini Jha
 */
public class LinkedStack<E> implements Stack<E> {

	/**
	 * A LinkedAbstractList
	 */
	private LinkedAbstractList<E> list;

	/**
	 * The constructor for the class
	 * 
	 * @param capacity is the capacity for the stack
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}

	/**
	 * This pushes an element to the top of the stack
	 * 
	 * @param element is the element to push to the top
	 */
	@Override
	public void push(E element) {
		list.add(element);
	}

	/**
	 * This takes the element off the top of the stack and returns its value
	 * 
	 * @return element is the element on top of the stack before the removal
	 */
	@Override
	public E pop() {
		if (list.size() == 0) {
			throw new EmptyStackException();
		}

		return list.remove(list.size() - 1);
	}

	@Override
	public boolean isEmpty() {
		return list.size() == 0;
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public void setCapacity(int capacity) {
		list.setCapacity(capacity);
	}
}
