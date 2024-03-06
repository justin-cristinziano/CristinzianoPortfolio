package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Creates and contains the methods for a custom ArrayList
 * @param <E> generic Object
 * 
 * @author Connor Young
 * @author Stuti Gautam
 * @author Ananya Rao
 */
public class ArrayList<E> extends AbstractList<E> {
	/** The initial capacity of a new made array list */
	private static final int SIZE_INT = 10;

	/** an array that contains the contents of the arrayList */
	private E[] list;

	/** the size of the array list */
	private int size;

	/**
	 * Constructs a new, empty ArrayList with a capacity of 10
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		list = (E[]) new Object[SIZE_INT];
	}

	/**
	 * add an object to an ArrayList
	 * 
	 * @param idx         an integer representing the index that the element needs
	 *                    to be added to
	 * @param listElement an object that is to be added to an array list
	 * @throws NullPointerException      if the listElement is null
	 * @throws IllegalArgumentException  if the listElement doesn't already exist
	 *                                   within the list
	 * @throws IndexOutOfBoundsException if the idx parameter is less than zero or
	 *                                   greater than the size of the array
	 */
	@Override
	public void add(int idx, E listElement) {
		if (listElement == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < list.length; i++) {
			if (list[i] != null && list[i].equals(listElement)) {
				throw new IllegalArgumentException();
			}
		}

		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}

		if (idx != size) {
			for (int i = size; i > idx; i--) {
				list[i] = list[i - 1];
			}
			list[idx] = listElement;
			size++;
		} else {
			list[size] = listElement;
			size++;
		}

		if (size >= list.length) {
			growArray();
		}

	}

	/**
	 * Doubles the capacity of the ArrayList
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		E[] tempList = (E[]) new Object[list.length * 2];
		
		for (int i = 0; i < list.length; i++) {
			tempList[i] = list[i];
		}
		list = tempList;
	}

	/**
	 * removes the element at the index specified by idx and shifts the remaining
	 * elements to the left
	 * 
	 * @param idx an index
	 * @return E the object that was removed
	 * @throws IndexOutOfBoundsException if the idx parameter is less than zero or
	 *                                   greater than the size of the array
	 */
	@Override
	public E remove(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}

		E removedElement = list[idx];

		if (idx != size) {
			for (int i = idx; i < list.length - idx - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size() - 1] = null;
			size--;
		} else {
			list[size() - 1] = null;
			size--;
		}

		return removedElement;
	}

	/**
	 * replaces an object at a desired index with another element
	 * 
	 * @param idx         the index that is getting its element replaced
	 * @param listElement the object that is replacing the existing object in the
	 *                    array
	 * @return E the element that was replaced
	 * @throws NullPointerException      if the listElement is null
	 * @throws IllegalArgumentException  if the listElement already exists within
	 *                                   the list
	 * @throws IndexOutOfBoundsException if idx is less than 0 or is greater than or
	 *                                   equal to the size of the list
	 */
	@Override
	public E set(int idx, E listElement) {
		if (listElement == null) {
			throw new NullPointerException();
		}

		for (int i = 0; i < list.length; i++) {
			if (list[i] != null && listElement.equals(list[i])) {
				throw new IllegalArgumentException();
			}
		}

		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}

		E replacedElement = list[idx];
		list[idx] = listElement;

		return replacedElement;
	}

	/**
	 * gets the element at the desired index
	 * 
	 * @param idx the desire index to obtain the element from the list
	 * @return E the element at the desired index
	 * @throws IndexOutOfBoundsException if idx is less than 0 or is greater than or
	 *                                   equal to the size of the list
	 */
	@Override
	public E get(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		return list[idx];
	}

	/**
	 * returns the size of the list
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

}
