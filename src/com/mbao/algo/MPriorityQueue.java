package com.mbao.algo;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Implemented with ArrayList, which does the array resizing for us.
 *
 * Added ability to:
 * - remove(any item)
 * - update(item, new item)
 */
public class MPriorityQueue<T> {
  private ArrayList<T> items;
  private Comparator<T> comparator = (o1, o2) -> {
    if (o1 instanceof Comparable) {
      return ((Comparable) o1).compareTo(o2);
    }
    return o1.toString().compareTo(o2.toString());
  };

  public MPriorityQueue() {
    items = new ArrayList<>();
  }

  public MPriorityQueue(Comparator<T> comparator) {
    this.comparator = comparator;
    items = new ArrayList<>();
  }

  public void offer(T item) {
    items.add(item);
    heapifyUp(items.size() - 1);
  }

  public T poll() {
    if (items.size() == 0) return null;
    T item = items.get(0);
    items.set(0, items.get(items.size() - 1));
    items.remove(items.size() - 1);
    heapifyDown(0);
    return item;
  }

  public T peek() {
    return items.get(0);
  }

  public boolean contains(T item) {
    return items.contains(item);
  }

  public void remove(T item) {
    if (!items.contains(item)) return;
    int index = items.indexOf(item);
    items.set(index, items.get(items.size() - 1));
    items.remove(items.size() - 1);
    heapifyDown(index);
  }

  public void update(T item, T newItem) {
    if (!items.contains(item)) return;
    int index = items.indexOf(item);
    items.set(index, newItem);
    heapifyUp(index);
    heapifyDown(index);
  }

  private void heapifyUp(int i) {
    while (hasParent(i) && comparator.compare(items.get(i), parent(i)) < 0) {
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
      if (comparator.compare(items.get(i), items.get(smallerChildIndex)) < 0) {
        break;
      } else {
        swap(i, smallerChildIndex);
        i = smallerChildIndex;
      }
    }
  }

  public boolean isEmpty() {
    return items.isEmpty();
  }

  private void swap(int i, int j) {
    T temp = items.get(i);
    items.set(i, items.get(j));
    items.set(j, temp);
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
    return items.get(parentIndex(i));
  }

  private T leftChild(int i) {
    return items.get(leftChildIndex(i));
  }

  private T rightChild(int i) {
    return items.get(rightChildIndex(i));
  }

  private boolean hasParent(int i) {
    return parentIndex(i) >= 0;
  }

  private boolean hasLeftChild(int i) {
    return leftChildIndex(i) < items.size();
  }

  private boolean hasRightChild(int i) {
    return rightChildIndex(i) < items.size();
  }



  public static void main(String[] args) {
    MPriorityQueue<Integer> minPQ = new MPriorityQueue<>();
    MPriorityQueue<Integer> maxPQ = new MPriorityQueue<>((o1, o2) -> -o1.compareTo(o2));

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
    StdOut.println();


    //-----------------------------------------------------------------
    // test remove() method

    StdOut.println();
    for (int v : array) {
      minPQ.offer(v);
    }
    for (int value : array) {
      minPQ.remove(value);
      StdOut.print("Min pq: ");
      while (!minPQ.isEmpty()) {
        StdOut.print(minPQ.poll() + " ");
      }
      StdOut.println();

      for (int v : array) {
        minPQ.offer(v);
      }
    }



    //-----------------------------------------------------------------
    // Verify update(x, y) is equivalent to remove(x) then offer(y)

    StdOut.println();
    for (int value : array) {
      int random = StdRandom.uniform(-50, 50);
      minPQ.remove(value);
      minPQ.offer(random);
      StdOut.print("Min pq: ");
      while (!minPQ.isEmpty()) {
        StdOut.print(minPQ.poll() + " ");
      }
      StdOut.println();

      for (int v : array) {
        minPQ.offer(v);
      }
      minPQ.update(value, random);
      StdOut.print("Min pq: ");
      while (!minPQ.isEmpty()) {
        StdOut.print(minPQ.poll() + " ");
      }
      StdOut.println();

      for (int v : array) {
        minPQ.offer(v);
      }
    }
  }
}
