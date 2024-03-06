package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A custom implementation of Linked list that doesn’t allow for null elements
 * or duplicate elements as defined by the equals() method. Implements methods
 * from AbstractSequentialList.
 * 
 * @param <E> A generic object
 * @author Connor Young
 * @author Justin Cristinziano
 * @author Ashwini Jha
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** the size of the list */
	private int size;

	/** the front of the list */
	private ListNode front;

	/** the back of the list */
	private ListNode back;

	/**
	 * Creates a new LinkedList with a size initialized as 0.
	 */
	public LinkedList() {
		size = 0;
		front = new ListNode(null);
		back = new ListNode(null);
		front.next = back;
		back.prev = front;
	}

	/**
	 * Calls the LinkedListIterator inner class to create a new list iterator and
	 * set it at the given index
	 * 
	 * @param idx the index at which the iterator is to be set
	 * @return a new ListIterator set at the given index
	 */
	public ListIterator<E> listIterator(int idx) {
		return new LinkedListIterator(idx);
	}

	/**
	 * gets the size of the list
	 * 
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Sets the data at the given index to the given element
	 * 
	 * @param index   the index to set the new data
	 * @param element the data to set
	 * @return the data that was replaced
	 */
	@Override
	public E set(int index, E element) {
		if (contains(element)) {
			throw new IllegalArgumentException();
		}
		return super.set(index, element);
	}

	/**
	 * Adds a new ListNode at the given index with the given data
	 * 
	 * @param idx  the index to add the data
	 * @param data the data to add
	 */
	@Override
	public void add(int idx, E data) {
		if (contains(data)) {
			throw new IllegalArgumentException();
		}
		super.add(idx, data);
	}

	/**
	 * Class responsible for the construction of ListNodes for the LinkedList class
	 * 
	 * @author Connor Young
	 * @author Justin Cristinziano
	 * @author Ashwini Jha
	 */
	private class ListNode {

		/** the data for a ListNode */
		public E data;

		/** the next ListNode in the list */
		public ListNode next;

		/** the previous node in the list */
		public ListNode prev;

		/**
		 * Constructor for a ListNode with that has only been given the data to add.
		 * Calls second constructor with remaining parameters set as null.
		 * 
		 * @param data the data for a ListNode
		 */
		public ListNode(E data) {
			this(data, null, null);
		}

		/**
		 * Secondary constructor for ListNodes with additional parameters that keep
		 * track of the node that comes before and after the node that is being
		 * constructed
		 * 
		 * @param data the data to add to the ListNode
		 * @param prev the previous node
		 * @param next the next node
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.prev = prev;
			this.next = next;
		}
	}

	/**
	 * Class responsible for the creation of the list iterator used by the
	 * LinkedList class. Handles the addition, removal, and setting of ListNodes
	 * 
	 * @author Connor Young
	 * @author Justin Cristinziano
	 * @author Ashwini Jha
	 */
	private class LinkedListIterator implements ListIterator<E> {

		/** the ListNode that would be returned on a call to previous() */
		public ListNode previous;

		/** the ListNode that would be returned on a call to next() */
		public ListNode next;

		/** the index that would be returned on a call to previousIndex() */
		public int nextIndex;

		/** the index that would be returned on a call to nextIndex() */
		public int prevIndex;

		/**
		 * represents the ListNode that was returned on the last call to either
		 * previous() or next() or null if a call to previous() or next() was not the
		 * last call on the ListIterator
		 */
		private ListNode lastRetrieved;

		/**
		 * Constructor for a list iterator that takes in an integer representing the
		 * index to set the iterator at.
		 * 
		 * @param index the index to set the iterator at.
		 * @throws IndexOutOfBounds if the index parameter is less than zero or larger
		 *                          than the size of the list
		 */
		public LinkedListIterator(int index) {
			if (index < 0 || index > size()) {
				throw new IndexOutOfBoundsException();
			}
			
			previous = front;
			next = front.next;
			for (int i = 0; i < index; i++) {
				next();
			}
			prevIndex = index - 1;
			nextIndex = index;
			lastRetrieved = null;
		}

		/**
		 * Checks if there is a next node in the list with non null data
		 * 
		 * @return true if the next node's data is not null
		 */
		public boolean hasNext() {
			return next.data != null;
		}

		/**
		 * Returns the next element in the list and advances the iterator position. This
		 * method may be called repeatedly to iterate through the list, or intermixed
		 * with calls to previous() to go back and forth.
		 * 
		 * @return the next element in the list
		 * @throws NoSuchElementException if the data in the next element is null.
		 */
		public E next() {
			if (hasNext()) {
				lastRetrieved = next;
				prevIndex++;
				nextIndex++;
				E rtn = next.data;
				next = next.next;
				previous = previous.next;
				return rtn;
			}

				throw new NoSuchElementException();
		}

		/**
		 * Returns true if this list iterator has more elements when traversing the list
		 * in the reverse direction.
		 * 
		 * @return true if the list iterator has more elements when traversing the list
		 *         in the reverse direction
		 */
		public boolean hasPrevious() {
			return previous.data != null;
		}

		/**
		 * Returns the previous element in the list and moves the cursor position
		 * backwards. This method may be called repeatedly to iterate through the list
		 * backwards, or intermixed with calls to next() to go back and forth.
		 * 
		 * @return the previous element in the list
		 * @throws NoSuchElementException if the iteration has no previous element
		 */
		public E previous() {
			if (hasPrevious()) {
				lastRetrieved = previous;
				nextIndex--;
				prevIndex--;
				E rtn = previous.data;
				next = next.prev;
				previous = previous.prev;
				return rtn;
			}
			throw new NoSuchElementException();
		}

		/**
		 * Returns the index of the element that would be returned by a subsequent call
		 * to next().
		 * 
		 * @return the index of the element that would be returned by a subsequent call
		 *         to next, or list size if the list iterator is at the end of the list
		 */
		public int nextIndex() {
			return nextIndex;
		}

		/**
		 * Returns the index of the element that would be returned by a subsequent call
		 * to previous(). (Returns -1 if the list iterator is at the beginning of the
		 * list.)
		 * 
		 * @return the index of the element that would be returned by a subsequent call
		 *         to previous, or -1 if the list iterator is at the beginning of the
		 *         list
		 * 
		 */
		public int previousIndex() {
			return prevIndex;
		}

		/**
		 * Removes from the list the last element that was returned by next() or
		 * previous(). This call can only be made once per call to next or previous. It
		 * can be made only if add(E) has not been called after the last call to next or
		 * previous.
		 * 
		 * @throws IllegalStateException if neither next nor previous have been called,
		 *                               or remove or add have been called after the
		 *                               last call to next or previous
		 */
		public void remove() {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			} 
			if (lastRetrieved == previous) {
				previous.prev.next = next;
			}
			if (lastRetrieved == next) {
				next.prev.next = next.next;
			}
			lastRetrieved = null;
			size--;
		}

		/**
		 * Replaces the last element returned by next() or previous() with the specified
		 * element. This call can be made only if neither remove() nor add(E) have been
		 * called after the last call to next or previous.
		 * 
		 * @param data the data to be set
		 * 
		 * @throws IllegalStateException if neither next nor previous have been called,
		 *                               or remove or add have been called after the
		 *                               last call to next or previous
		 * 
		 * @throws NullPointerException  if data is null
		 */
		public void set(E data) {
			if (lastRetrieved == null) {
				throw new IllegalStateException();
			}
			if (data == null) {
				throw new NullPointerException();
			}
			lastRetrieved.data = data;
			
		}

		/**
		 * Inserts the specified element into the list. The element is inserted
		 * immediately before the element that would be returned by next(), if any, and
		 * after the element that would be returned by previous(), if any. (If the list
		 * contains no elements, the new element becomes the sole element on the list.)
		 * The new element is inserted before the implicit cursor: a subsequent call to
		 * next would be unaffected, and a subsequent call to previous would return the
		 * new element.
		 * 
		 * @param data the element to insert
		 * 
		 * @throws NullPointerException if data is null;
		 * 
		 */
		@Override
		public void add(E data) {
			if (data == null) {
				throw new NullPointerException();
			}
			ListNode n1 = new ListNode(data);
			n1.next = next;
			n1.prev = previous;
			next.prev = n1;
			
			previous.next = n1;
			nextIndex++;
			prevIndex++;
			size++;
			lastRetrieved = null;

		}
	}
}
