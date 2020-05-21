package com.mbao.algo;

import edu.princeton.cs.algs4.StdOut;

public class MStack<T> {
  private Node<T> head;
  int size;

  public MStack() {
    size = 0;
  }

  public void push(T data) {
    Node<T> newHead = new Node<>(data);
    newHead.next = head;
    head = newHead;
    size++;
  }

  public T pop() {
    if (head == null) return null;
    T data = head.data;
    head = head.next;
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
    if (head == null) return null;
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
    MStack<Integer> stack = new MStack<>();
    StdOut.println(stack.size() + ": " + stack.toString()); // 0: null

    StdOut.println("pop(): " + stack.pop()); // null
    StdOut.println(stack.size() + ": " + stack.toString()); // 0: null

    stack.push(1);
    stack.push(2);
    stack.push(3);
    StdOut.println(stack.size() + ": " + stack.toString()); // 3: 3-2-1

    StdOut.println("pop(): " + stack.pop()); // 3
    StdOut.println("pop(): " + stack.pop()); // 2
    stack.push(8);
    stack.push(9);
    StdOut.println("pop(): " + stack.pop()); // 9
    stack.push(5);
    StdOut.println("pop(): " + stack.pop()); // 5
    StdOut.println(stack.size() + ": " + stack.toString()); // 2: 8-1

    StdOut.println("pop(): " + stack.pop()); // 8
    StdOut.println("pop(): " + stack.pop()); // 1
    StdOut.println("pop(): " + stack.pop()); // null
    StdOut.println(stack.size() + ": " + stack.toString()); // 0: null
  }
}
