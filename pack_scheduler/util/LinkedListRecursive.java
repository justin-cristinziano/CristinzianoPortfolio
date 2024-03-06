package edu.ncsu.csc216.pack_scheduler.util;

/**
 * Created a LinkedList utilizing recursive methods
 * 
 * @param <E> a generic object
 * 
 * @author Connor Young
 * @author Ashwini Jha
 * @author Justin Cristinziano
 */
public class LinkedListRecursive<E> {

	/** the size of the list */
	private int size;

	/** the front of the list */
	private ListNode front;

	/** constructs a new list with a size of zero and a null front */
	public LinkedListRecursive() {
		this.front = null;
		this.size = 0;
	}

	/**
	 * checks if the list is empty
	 * 
	 * @return true if front is null
	 */
	public boolean isEmpty() {
		return front == null;
	}

	/**
	 * the size of the list
	 * 
	 * @return the size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Handles adding an element to an empty list. Otherwise it hands off the data
	 * to the second add method with size as the index it's to be added to
	 * 
	 * @param data the data to be added
	 * @return true if the data was successfully added
	 * @throws IllegalArgumentException if the data already exists in the list.
	 */
	public boolean add(E data) {
		if (front == null) {
			front = new ListNode(data, null);
			size++;
			return true;
		}

		if (contains(data)) {
			throw new IllegalArgumentException();
		}

		front.add(size, data);
		size++;
		return true;
	}

	/**
	 * Adds the provided data to a given index
	 * 
	 * @param idx  the index to add the data
	 * @param data the data to be added
	 * @throws IllegalArgumentException if data already exists within the list
	 * @throws IndexOutOfBounds         if idx is less than 0 or greater than the
	 *                                  size of the list.
	 * @throws NullPointerException     if data is null
	 * 
	 */
	public void add(int idx, E data) {

		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException();
		}
		if (data == null) {
			throw new NullPointerException();
		}

		if (size != 0) {
			if (contains(data)) {
				throw new IllegalArgumentException();
			} else if (idx == 0) {
				ListNode nodeToBeAdded = new ListNode(data, front);
				front = nodeToBeAdded;
				size++;
			} else {
				front.add(idx, data);
				size++;
			}
		} else {
			add(data);
		}

	}

	/**
	 * Retrieves the data at the provided index of the LinkedList
	 * 
	 * @param idx the index of the desired data
	 * @return the data at the provided index
	 * @throws IndexOutOfBoundsException if the index is less than zero or greater
	 *                                   than the list size
	 */
	public E get(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		} else {
			return front.get(idx);
		}

	}

	/**
	 * Removes the provided data from the list
	 * 
	 * @param data the data that will be removed
	 * @return true if the data was successfully removed
	 */
	public boolean remove(E data) {
//		if (data == null) {
//			throw new IllegalArgumentException();
//		}
//		if (isEmpty()) {
//			throw new IllegalArgumentException();
//		}
		if (data != null && !isEmpty()) {
			if (front.data.equals(data)) {
				if (size == 1) {
					front = null;
					size--;
					return true;
				} else {
					front.data = null;
					front = front.next;
					size--;
					return true;
				}
			} else {
				if (front.remove(data)) {
					size--;
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
		
	}

	/**
	 * Remove an element at the given index
	 * 
	 * @param idx the index to remove an element
	 * @return the element that was removed
	 */
	public E remove(int idx) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (idx == 0) {
			if (size == 1) {
				E rtn = front.data;
				front = null;
				size--;
				return rtn;
			} else {
				E rtn = front.data;
				front.data = null;
				front = front.next;
				size--;
				return rtn;
			}
		} else {
			size--;
			return front.remove(idx);

		}

	}

	/**
	 * Replaces the data at the given index with the given data.
	 * 
	 * @param idx  the index to replace data
	 * @param data the data to exchange
	 * @return the data that was changed
	 * @throws IndexOutOfBoundsException if idx is less than zero or greater than
	 *                                   size.
	 */
	public E set(int idx, E data) {
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException();
		}
		if (data == null) {
			throw new NullPointerException();
		}
		if (contains(data)) {
			throw new IllegalArgumentException();
		}
		

		return front.set(idx, data);

	}

	/**
	 * Checks if the list contains the given data
	 * 
	 * @param data the data that is being checked if it's in the list.
	 * @return true if the data exists within the list.
	 * @throws IllegalArgumentException if the list is empty.
	 */
	public boolean contains(E data) {
		if (isEmpty()) {
			throw new IllegalArgumentException();
		} else {
			return front.contains(data);
		}
	}

	/**
	 * Class that constructs ListNodes to be added, removed, or edited in the Linked
	 * List
	 */
	private class ListNode {

		/** the data stored in a list node */
		public E data;

		/** the next list node in the linked list */
		public ListNode next;

		/**
		 * Recursive method that cycles through the linked list until the idx parameter
		 * is 1. Once idx equals 1, a new list node is created with the provided data.
		 * 
		 * @param idx  the desired index to add the list node. Is decremented until 0 is
		 *             reached
		 * @param data the data that will be in the list node.
		 */
		public void add(int idx, E data) {
			if (idx == 1) {
				ListNode n1 = new ListNode(data, next);
				next = n1;
			} else if (next == null) {
				ListNode n2 = new ListNode(data, null);
				next = n2;
			}

			else {
				next.add(idx - 1, data);
			}
		}

		/**
		 * Recursive method that cycles through the linked list until idx reaches zero.
		 * Once idx equals zero, the data of the next node is returned.
		 * 
		 * @param idx the desired index to pull the data from. Is decremented until 0 is
		 *            reached
		 * @return the data at the provided index.
		 */
		public E get(int idx) {
			if (idx == 0) {
				return data;
			} else {
				return next.get(idx - 1);
			}
		}

		/**
		 * Recursive method that cycles through the linked list until idx equals 1. Once
		 * Idx equals 1, the data of the removed list node is return and the next
		 * reference is changed to the next's next node.
		 * 
		 * @param idx the desired index of the desired node to remove. Is decremented
		 *            until 1 is reached
		 * @return the data of the node that was removed.
		 */
		public E remove(int idx) {
			if (idx == 1) {
				E rtn = next.data;
				next = next.next;
				return rtn;
			} else {
				return next.remove(idx - 1);
			}

		}

		/**
		 * Recursive method that cycles through the data of each link node in the list
		 * and removes a node if its data matches the provided data.
		 * 
		 * @param data the data of the node to be removed
		 * @return true if the node was successfully removed
		 */
		public boolean remove(E data) {
			if (next == null) {
				return false;
			} else if (next.data.equals(data)) {
				if (next.next != null) {
					next.data = next.next.data;
					return true;
				} else {
					next.data = null;
					return true;
				}

			} else {
				return next.remove(data);
			}

		}

		/**
		 * Recursive method that cycles through the list until idx is 1. Once idx equals
		 * 1, the next nodes data is overwritten with the desired data. The replaced
		 * data is returned.
		 * 
		 * @param idx  the index to replace the data
		 * @param data the data to replace the old data with
		 * @return the data that was replaced
		 */
		public E set(int idx, E data) {
			if (idx == 0) {
				E rtn = this.data;
				this.data = data;
				return rtn;
			} else {
				return next.set(idx - 1, data);
			}
		}

		/**
		 * Recursive method that checks if the list contains the given data
		 * 
		 * @param data the data that is being checked if it's in the list.
		 * @return true if the data exists within the list.
		 */
		public boolean contains(E data) {
			if (next == null) {
				return false;
			} else if (this.data.equals(data)) {
				return true;
			}

			else {
				next.contains(data);
			}
			return false;
		}

		/**
		 * Constructor that creates new ListNodes with the given data and next node
		 * parameters
		 * 
		 * @param data the data for a list node
		 * @param next the next node in the list. 
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

}
