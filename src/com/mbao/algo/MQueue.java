package com.mbao.algo;

import edu.princeton.cs.algs4.StdOut;

public class MQueue<T> {
  private Node<T> head;
  private Node<T> tail;
  private int size;

  public MQueue() {
    size = 0;
  }

  public void offer(T data) {
    if (tail == null) { // queue is empty
      tail = new Node<>(data);
      head = tail;
    } else {
      tail.next = new Node<>(data);
      tail = tail.next;
    }
    size++;
  }

  public T poll() {
    if (head == null) return null;
    T data = head.data;
    head = head.next;
    if (head == null) {
      tail = null;
    }
    size--;
    return data;
  }

  public T peek() {
    if (head == null) return null;
    return head.data;
  }

  public int size() {
    return size;
  }

  public String toString() {
    if (size == 0) return "null";
    StringBuilder sb = new StringBuilder();
    Node p = head;
    while (p.next != null) {
      sb.append(p.data).append("-");
      p = p.next;
    }
    sb.append(p.data);
    return sb.toString();
  }


  private class Node<T> {
    T data;
    Node<T> next;
    public Node(T data) {
      this.data = data;
    }
  }



  public static void main(String[] args) {
    MQueue<Integer> q = new MQueue<>();
    StdOut.println(q.size() + ": " + q.toString()); // "0: null"

    q.poll();
    StdOut.println(q.size() + ": " + q.toString()); // "0: null"

    q.offer(0);
    StdOut.println(q.size() + ": " + q.toString()); // "1: 0"

    StdOut.println("poll(): " + q.poll()); // "0"
    StdOut.println(q.size() + ": " + q.toString()); // "0: null"

    q.offer(1);
    q.offer(2);
    q.offer(3);
    StdOut.println(q.size() + ": " + q.toString()); // "3: 1-2-3"

    StdOut.println("poll(): " + q.poll()); // "1"
    StdOut.println(q.size() + ": " + q.toString()); // "2: 2-3"

    q.offer(0);
    q.offer(4);
    StdOut.println("poll(): " + q.poll()); // 2
    StdOut.println("poll(): " + q.poll()); // 3
    StdOut.println(q.size() + ": " + q.toString()); // "2: 0-4"

    StdOut.println("poll(): " + q.poll()); // 0
    StdOut.println("poll(): " + q.poll()); // 4
    StdOut.println("poll(): " + q.poll()); // null
    StdOut.println("poll(): " + q.poll()); // null
    q.offer(8);
    q.offer(9);
    q.offer(2);
    q.offer(3);
    StdOut.println(q.size() + ": " + q.toString()); // "4: 8-9-2-3"

    q.poll();
    q.poll();
    q.poll();
    q.poll();
    q.poll();
    StdOut.println(q.size() + ": " + q.toString()); // "0: null"
  }
}
