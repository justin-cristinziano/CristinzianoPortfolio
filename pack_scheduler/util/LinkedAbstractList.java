package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * Creates and contains the methods for a custom LinkedList
 * 
 * @param <E> generic Object
 * 
 * @author Connor Young
 * @author Stuti Gautam
 * @author Ananya Rao
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	/** The size of the list */
	private int size;

	/** The capacity of the list */
	private int capacity;

	/** The first node in the list */
	private ListNode front;
	
	/** The last node in the list */
	private ListNode back;

	/**
	 * Constructs a new LinkedList with the given capacity. The front field is set
	 * to null and size to zero.
	 * 
	 * @param capacity the capacity of the list.
	 * @throws IllegalArgumentException if the capacity is less than 0 or if it is
	 *                                  less than the size of the list.
	 */
	public LinkedAbstractList(int capacity) {
		this.front = null;
		this.size = 0;
		setCapacity(capacity);

		if (capacity < size) {
			throw new IllegalArgumentException();
		}
	}


	/**
	 * Gets the data at a given index
	 * 
	 * @param idx the desired index to get the element from
	 * @throws IndexOutOfBoundsException if idx is less than zero or greater than or
	 *                                   equal to the size of the list.
	 */
	@Override
	public E get(int idx) {

		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}
		ListNode current = front;
		int counter = 0;
		while (counter != idx) {
			current = current.next;
			counter++;
		}

		return current.data;

	}

	/**
	 * Replaces the data at a given index with the provided data.
	 * 
	 * @param idx     the index to replace data
	 * @param element the data to replace the old data
	 * 
	 * @throws NullPointerException      if element is null
	 * @throws IllegalArgumentException  if element already exists within the list
	 * @throws IndexOutOfBoundsException if idx is less than zero or greater than or
	 *                                   equal to the size of the list
	 */
	@Override
	public E set(int idx, E element) {

		if (element == null) {
			throw new NullPointerException();
		}

		ListNode n1 = front;
		for (int i = 0; i < size; i++) {
			if (n1.data.equals(element)) {
				throw new IllegalArgumentException();
			}
			n1 = n1.next;
		}
		
		if (idx < 0 || idx >= size()) {
			throw new IndexOutOfBoundsException();
		}

		ListNode n = front;
		E removed = null;

		if (idx == 0) {
			removed = front.data;
			front.data = element;
		} else {
			for (int i = 0; i < idx - 1; i++) {
				n = n.next;
			}
			removed = n.next.data;
			n.next.data = element;
		}
		return removed;
	}

	/**
	 * Adds a new node to the linked list at the provided index with the provided
	 * data
	 * 
	 * @param idx     the index to add the data
	 * @param element the data to add to the list
	 * 
	 * @throws NullPointerException      if element is null
	 * @throws IllegalArgumentException  if element already exists within the list
	 * @throws IndexOutOfBoundsException if idx is less than zero or greater than
	 *                                   the size of the list
	 */
	@Override
	public void add(int idx, E element) {

		if (element == null) { 
			throw new NullPointerException();
		}
		
		ListNode n1 = front;
		for (int i = 0; i < size; i++) {
			if (n1.data.equals(element)) {
				throw new IllegalArgumentException();
			}
			n1 = n1.next;
		}
		
		if (idx < 0 || idx > size()) {
			throw new IndexOutOfBoundsException();
		}
		
		if (size == capacity) {
			throw new IllegalArgumentException();
		}

		ListNode nodeToBeAdded = new ListNode(element); 
		ListNode n = front;

		if (size() == 0) {
			front = nodeToBeAdded;
			back = nodeToBeAdded;
			size++;
		} else {
			if (idx == 0) {
				nodeToBeAdded.next = front;
				front = nodeToBeAdded;
				size++;

			} else if (idx == size) {
				back.next = nodeToBeAdded;
				back = nodeToBeAdded;
				size++;
			}
			else {
				for (int i = 0; i < idx - 1; i++) {
					n = n.next;
				}
				nodeToBeAdded.next = n.next;
				n.next = nodeToBeAdded;
				size++; 
			}
		}
		
	}

	/**
	 * Removes the data at the given index
	 * 
	 * @param idx the index to remove the data
	 * @return the removed data
	 * @throws IndexOutOfBoundsException if idx is less than zero or greater than or
	 *                                   equal to the size of the list
	 */
	@Override
	public E remove(int idx) {
	    if (idx < 0 || idx >= size) {
	        throw new IndexOutOfBoundsException();
	    }
	    if (front == null) {
	    	throw new IndexOutOfBoundsException();
	    }

	    E removed = null;
	    if (idx == 0) {
	        removed = front.data;
	        front = front.next;
	        if (size == 1) {
	        	back = null;
	        }
	    } else {
	        ListNode n = front;
	        for (int i = 0; i < idx - 1; i++) {
	            n = n.next;
	        }
	        removed = n.next.data;
	        if (idx == size - 1) {
	        	back = n;
	        	back.next = null;
	        } else {
	        	n.next = n.next.next;
	        }
	    }
	    size--;
	    return removed;
	}

	/**
	 * Returns the size of the list
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}
	
	/**
	 * Sets the capacity of the list.
	 * 
	 * @param capacity the capacity of the list.
	 * @throws IllegalArgumentException if the capacity is less than 0 or if it is
	 *                                  less than the size of the list.
	 */
	public void setCapacity(int capacity)  {
		if(capacity < 0 || capacity < this.size) {
			throw new IllegalArgumentException();
		} else {
			this.capacity = capacity;
		}
	}

	/**
	 * A single node of the linked list representing a piece of data
	 * 
	 * @author Connor Young
	 * @author Stuti Gautam
	 * @author Ananya Rao
	 */
	private class ListNode {

		/** The data of the node */
		private E data;

		/** The next node in the list */
		private ListNode next;

		/**
		 * Calls the second list node constructor with the given data and a null next
		 * parameter
		 * 
		 * @param data the data for the list node
		 */
		public ListNode(E data) {
			this(data, null);
		}

		/**
		 * Constructs a new ListNode with the given data and reference to the next
		 * ListNode
		 * 
		 * @param data the data for the list node
		 * @param next the next list node is the list.
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}
	

}
