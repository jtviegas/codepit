package org.aprestos.labs.snippets.impl.algo.assorted.topn;

import java.util.Arrays;

public class Sorter {

	private static final int ROOT_INDEX = 1;
	private static final int ARRAY_DEFAULT_VALUE = -1;
	
	private int maxHeapIndex = 10;
	private int maxHeapSize = 1 + maxHeapIndex;
	

	
	private int[] heap;
	private int index;

	public Sorter() {
		
	}

	private void createHeap(int itemsSize) {
		
		this.maxHeapIndex = itemsSize;
		this.maxHeapSize = maxHeapIndex + 1;
		this.heap = new int[maxHeapSize];
		Arrays.fill(this.heap, ARRAY_DEFAULT_VALUE);
		this.index = 1;
	}

	public LinkedList heapsort(int[] items) throws Exception {

		int val = -1;
		LinkedList result = new LinkedList();
		LinkedList currentNode = result;
		
		if (null == heap)
			createHeap(items.length);

		for (int i = 0; i < items.length; i++)
			insert(items[i]);

		for (int j = 0; j < items.length; j++) {
			// get the root value
			val = this.heap[ROOT_INDEX];

			if(currentNode.value != val)
				currentNode = currentNode.add(val);
			
			// move the last element to the heap root
			this.heap[ROOT_INDEX] = this.heap[--this.index];
			this.heap[this.index] = ARRAY_DEFAULT_VALUE;

			bubbleDown(ROOT_INDEX);
		}

		return result;
	}

	private void bubbleDown(int position) {

		if (position >= maxHeapIndex)
			return;

		int strongerChildPosition = -1;
		int firstValue = ARRAY_DEFAULT_VALUE, secondValue = ARRAY_DEFAULT_VALUE;

		int parentValue = this.heap[position];
		int firstChildPosition = getFirstChildIndex(position);
		int secondChildPosition = firstChildPosition + 1;

		if (firstChildPosition <= maxHeapIndex) {
			firstValue = this.heap[firstChildPosition];
			if (secondChildPosition <= maxHeapIndex) {
				secondValue = this.heap[secondChildPosition];
			}
		}
		// get the bigger of the children
		if (firstValue > ARRAY_DEFAULT_VALUE) {

			if ((secondValue > ARRAY_DEFAULT_VALUE)
					&& (firstValue < secondValue))
				strongerChildPosition = secondChildPosition;
			else
				strongerChildPosition = firstChildPosition;

		}

		// if children bigger, do a swap and resume the
		// down bubbling from there
		if (-1 != strongerChildPosition
				&& parentValue < this.heap[strongerChildPosition]) {
			swap(position, strongerChildPosition);
			bubbleDown(strongerChildPosition);
		}

	}

	private int getFirstChildIndex(int position) {
		return (2 * position);
	}

	private void swap(int a, int b) {

		int aValue = heap[a];
		int bValue = heap[b];

		heap[a] = bValue;
		heap[b] = aValue;

	}

	private void bubbleUp(int position) {

		if (ROOT_INDEX >= position)
			return;

		int parent = getParentIndex(position);
		int sonValue = heap[position];
		int parentValue = heap[parent];

		if (parentValue < sonValue) {
			swap(parent, position);
			bubbleUp(parent);
		}
	}

	private int getParentIndex(int position) {
		int result = 0;

		if (1 == position)
			result = -1;
		else
			result = ((int) position / 2);

		return result;
	}

	private void insert(int value) throws Exception {

		if (index > maxHeapIndex)
			throw new Exception("HEAP_IS_FULL");

		this.heap[index] = value;

		if (index > ROOT_INDEX)
			bubbleUp(index);

		index++;

	}

}
