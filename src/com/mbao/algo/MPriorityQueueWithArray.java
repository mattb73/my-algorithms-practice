package com.mbao.algo;

import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Implemented the traditional way: with auto-resizing array.
 */
public class MPriorityQueueWithArray<T> {
  private int capacity = 10;
  private int size = 0;
  private T[] items;
  private Comparator<T> comparator = (o1, o2) -> {
    if (o1 instanceof Comparable) {
      return ((Comparable) o1).compareTo(o2);
    }
    return o1.toString().compareTo(o2.toString());
  };

  public MPriorityQueueWithArray() {
    items = (T[]) new Object[capacity];
  }

  public MPriorityQueueWithArray(Comparator<T> comparator) {
    this.comparator = comparator;
    items = (T[]) new Object[capacity];
  }

  public void offer(T item) {
    if (size == capacity) {
      increaseCapacity();
    }
    items[size++] = item;
    heapifyUp(size -1);
  }

  public T poll() {
    if (size == 0) return null;
    T item = items[0];
    items[0] = items[size - 1];
    size--;
    heapifyDown(0);
    return item;
  }

  public T peek() {
    return items[0];
  }

  private void heapifyUp(int i) {
    while (hasParent(i) && comparator.compare(items[i], parent(i)) < 0) {
      swap(i, parentIndex(i));
      i = parentIndex(i);
    }
  }

  // note: only need to compare with the SMALLER of the 2 children
  private void heapifyDown(int i) {
    while (hasLeftChild(i)) {
      int smallerChildIndex = leftChildIndex(i);
      if (hasRightChild(i) && comparator.compare(rightChild(i), leftChild(i)) < 0) {
        smallerChildIndex = rightChildIndex(i);
      }
      if (comparator.compare(items[i], items[smallerChildIndex]) < 0) {
        break;
      } else {
        swap(i, smallerChildIndex);
        i = smallerChildIndex;
      }
    }
  }

  public boolean isEmpty() {
    return size == 0;
  }

  private void increaseCapacity() {
    capacity *= 2;
    items = Arrays.copyOf(items, capacity);
  }

  private void swap(int i, int j) {
    T temp = items[i];
    items[i] = items[j];
    items[j] = temp;
  }

  private int parentIndex(int i) {
    return (i - 1) / 2;
  }

  private int leftChildIndex(int i) {
    return i * 2 + 1;
  }

  private int rightChildIndex(int i) {
    return i * 2 + 2;
  }

  private T parent(int i) {
    return items[parentIndex(i)];
  }

  private T leftChild(int i) {
    return items[leftChildIndex(i)];
  }

  private T rightChild(int i) {
    return items[rightChildIndex(i)];
  }

  private boolean hasParent(int i) {
    return parentIndex(i) >= 0;
  }

  private boolean hasLeftChild(int i) {
    return leftChildIndex(i) < size;
  }

  private boolean hasRightChild(int i) {
    return rightChildIndex(i) < size;
  }



  public static void main(String[] args) {
    MPriorityQueueWithArray<Integer> minPQ = new MPriorityQueueWithArray<>();
    MPriorityQueueWithArray<Integer> maxPQ = new MPriorityQueueWithArray<>(new Comparator<Integer>() {
      @Override
      public int compare(Integer o1, Integer o2) {
        return -o1.compareTo(o2);
      }
    });

    int[] array = new int[] {2, 8, 4, -1, 0, 10, 25, 5, 24, 9, 1, 30};
    for (int value : array) {
      minPQ.offer(value);
      maxPQ.offer(value);
    }

    StdOut.print("Min pq: ");
    while (!minPQ.isEmpty()) {
      StdOut.print(minPQ.poll() + " ");
    }
    StdOut.println();

    StdOut.print("Max pq: ");
    while (!maxPQ.isEmpty()) {
      StdOut.print(maxPQ.poll() + " ");
    }
    StdOut.println();
  }
}
