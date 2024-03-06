package edu.ncsu.csc216.stp.model.util;



/**
 * This class swaps the position of elements in the list
 * @param <E> allows for no casting between classes
 */
public class SwapList<E> implements ISwapList<E> {

	/**
	 * This is the starting capacity of the list
	 */
	private static final int INITIAL_CAPACITY = 10;
	
	
	/**
	 * This is the list itself. It's used to retrieve data for the client (using the indices)
	 */
	private E[] list;
	
	/** This is the number of elements currently in the list */
	private int size;
	
	
	/**
	 * SwapList constructors
	 */
	@SuppressWarnings("unchecked")
	public SwapList() {
		list = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}
	
	/**
	 * This method adds an element to the end of list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(E element) {
		
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		
		try {
			list[size()] = element;
			size++;
		} catch (Exception e) {
			E[] temp = (E[]) new Object[size() * 2];
			for (int i = 0; i < size(); i++) {
				temp[i] = list[i];
			}
			list = temp;
			list[size()] = element;
			size++;
		}
		
		
		
		
	}

	/**
	 * This removes the element at index idx
	 * 
	 * @param idx is the index the client wants to remove from the list
	 * @return the object removed
	 */
	@Override
	public E remove(int idx) {
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException("Invalid index.");
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
	 * This moves the item up the list array at the index idx
	 * 
	 * @param idx is the index of the object to move
	 */
	@Override
	public void moveUp(int idx) {
		
		if (idx >= size() || idx < 0){
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		if (idx != 0) {
			E upper = null;
			E lower = null;
			lower = list[idx - 1];
			upper = list[idx];
			list[idx] = lower;
			list[idx - 1] = upper;
		}
	
		
	}
	/**
	 * This moves the item down the list array at the index idx
	 * 
	 * @param idx is the index of the object to move
	 */
	@Override
	public void moveDown(int idx) {
		
		if (idx >= size || idx < 0){
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		if (idx != size - 1) {
			E upper = null;
			E lower = null;
			upper = list[idx + 1];
			lower = list[idx];
			list[idx] = upper;
			list[idx + 1] = lower;
		}
		
		
	}

	/**
	 * This moves the item at index idx to the front of the list array
	 * 
	 * @param idx is the index of the object to move
	 */
	@Override
	public void moveToFront(int idx) {
		
		if (idx >= size() || idx < 0){
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		if (idx != 0) {
			E element = get(idx);
			for (int i = idx; i > 0; i--) {
				list[i] = list[i - 1];
			}
			list[0] = element;
		}
		
		
	}

	/**
	 * This moves the item at index idx
	 * 
	 * @param idx is the index of the object to move
	 */
	@Override
	public void moveToBack(int idx) {

		if (idx >= size() || idx < 0){
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		if (idx != size() - 1) {
			E element = get(idx);
			for (int i = idx; i < list.length - idx - 1; i++) {
				list[i] = list[i + 1];
			}
			list[size() - 1] = element;
		}
		
		
	}

	/**
	 * This retrieves the object in list at index idx
	 * 
	 * @return object at index idx in list
	 */
	@Override
	public E get(int idx) {
		
		if (idx >= size() || idx < 0){
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		return list[idx];
		
	}

	/**
	 * This returns the size of the list
	 * 
	 * @return size the number of elements in the list
	 */
	@Override
	public int size() {
		
		return size;
	}

}
