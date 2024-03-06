package edu.ncsu.csc216.stp.model.util;


/**
 * Class representing a log list.
 * @param <E> lists generic type.
 */
public class Log<E> implements ILog<E> {
	
	/**
	 * This is the array of elements that represent the log
	 */
	private E[] log;
	
	/**
	 * This is the size of the log
	 */
	private int size;
	
	/** Log arrays capacity */
	public static final int INIT_CAPACITY = 10;
	

	/**
	 * This is the constructor for the class. It builds an instance of a log
	 */
	@SuppressWarnings("unchecked")
	public Log() {
		log = (E[]) new Object[INIT_CAPACITY];
		size = 0;
	}

	/**
	 * This method adds an element to the log
	 * 
	 * @param element is the element to add to the end of the list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void add(E element) {
		
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}
		
		try {
			log[size()] = element;
			size++;
		} catch (Exception e) {
			E[] temp = (E[]) new Object[size() * 2];
			for (int i = 0; i < size(); i++) {
				temp[i] = log[i];
			}
			log = temp;
			log[size()] = element;
			size++;
		}
		
		
		

	}

	/**
	 * This method retrieves a message from the log (using the index, idx)
	 * 
	 * @param idx is the index to retrieve from
	 */
	@Override
	public E get(int idx) {
		if (idx >= size() || idx < 0) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		return log[idx];
	}

	/**
	 * This retrieves the size of the log for the client
	 * 
	 * @return size, the number of elements in the list
	 */
	@Override
	public int size() {
		
		return size;
		
	}

}
