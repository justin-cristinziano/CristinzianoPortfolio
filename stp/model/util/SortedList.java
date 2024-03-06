package edu.ncsu.csc216.stp.model.util;

/**
 * Implementation of the ISortedList interface using a sortedList. The elements
 * are kept in sorted order using the Comparable interface.
 *
 * @param <E> type for ISortedList; must implement Comparable
 */
public class SortedList<E extends Comparable<E>> implements ISortedList<E> {

	/** Front list node */
	private ListNode front;

	/** Sorted Lists size */
	private int size;

	/**
	 * Sorted Lists constructor.
	 */
	public SortedList() {
		this.size = 0;
		front = null;
	}

	/**
	 * This method adds an element to the sorted list.
	 *
	 * @param element is the element to add to the end of the list
	 */
	@Override
	public void add(E element) {
		
		
		if (element == null) {
			throw new NullPointerException("Cannot add null element.");
		}

		
		if (contains(element)) {
			throw new IllegalArgumentException("Cannot add duplicate element.");
		}
		
		if (front == null || element.compareTo(front.data) < 0) {
			front = new ListNode(element, front);
		} else {
			ListNode current = front;
			while (current.next != null && current.next.data.compareTo(element) < 0) {
				current = current.next;
			}
			current.next = new ListNode(element, current.next);
		}
		
		size++;
		
		

		

	}

	/**
	 * This method removes an element from the sorted list.
	 *
	 * @param idx index of the removed element
	 */
	@Override
	public E remove(int idx) {
		checkIndex(idx);

		if (idx == 0 && size > 1) {
			ListNode previousFront = front;
			front = front.next;
			size--;
			return previousFront.data;
		} else if (idx == 0 && size == 1) {
			ListNode previousFront = front;
			front = null;
			size--;
			return previousFront.data;
		}

		ListNode current = front;
		E removedNodeData;

		for (int i = 0; i < idx - 1; i++) {
			current = current.next;
		}

		removedNodeData = current.next.data;

		current.next.data = null;

		current.next = current.next.next;

		size--;

		return removedNodeData;

	}

	/**
	 * Gets the element at a given index.
	 * 
	 * @param idx the given index
	 * @return E the element at said index
	 */
	@Override
	public E get(int idx) {
		checkIndex(idx);

		ListNode current = front;
		

		for (int i = 0; i < idx; i++) {
			current = current.next;
		}

		return current.data;
	}

	/**
	 * The sorted lists size.
	 * 
	 * @return the sorted lists size
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Determines whether the list contains a specific element.
	 * 
	 * @param element desired element
	 * @return whether or not it is in the list
	 */
	@Override
	public boolean contains(E element) {
		
		if(front == null) {
			return false;
		}
		
		ListNode current = front;

		if (current.data.equals(element)) {
			return true;
		}

		while (current.next != null) {
			current = current.next;

			if (current.data.equals(element)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks the current index.
	 * 
	 * @param idx index
	 */
	private void checkIndex(int idx) {
		if(front == null) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
		
		int finalIndex = 0;

		ListNode current = front;

		while (current.next != null) {
			current = current.next;

			finalIndex++;
		}

		if (idx < 0 || idx > finalIndex) {
			throw new IndexOutOfBoundsException("Invalid index.");
		}
	}

	/**
	 * Defines the list nodes for the list.
	 */
	private class ListNode {
		/**
		 * This is the data that a node holds
		 */
		public E data;

		/**
		 * This is the next node in the list
		 */
		public ListNode next;

		/**
		 * This is the LinkedNode that is used for the SortedList
		 *
		 * @param data is the data of the node
		 * @param next is the next node
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}

	}
}
