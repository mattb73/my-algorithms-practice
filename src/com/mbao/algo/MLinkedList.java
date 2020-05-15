package com.mbao.algo;

import edu.princeton.cs.algs4.StdOut;

public class MLinkedList {
  Node head;

  public MLinkedList() {}

  public void append(int data) {
    if (head == null) {
      head = new Node(data);
    } else {
      Node p = head;
      while (p.next != null) {
        p = p.next;
      }
      p.next = new Node(data);
    }
  }

  public void prepend(int data) {
    if (head == null) {
      head = new Node(data);
    } else {
      Node newHead = new Node(data);
      newHead.next = head;
      head = newHead;
    }
  }

  public void delete(int data) {
    if (head == null) return;
    if (head.data == data) {
      head = head.next;
      return;
    }
    Node p = head;
    while (p.next != null) {
      if (p.next.data == data) {
        p.next = p.next.next;
        return;
      } else {
        p = p.next;
      }
    }
  }

  public void print() {
    if (head == null) {
      StdOut.println("null");
      return;
    }
    StringBuilder sb = new StringBuilder();
    Node p = head;
    while (p.next != null) {
      sb.append(p.data).append(" -> ");
      p = p.next;
    }
    sb.append(p.data);
    StdOut.println(sb.toString());
  }


  private class Node {
    int data;
    Node next;

    public Node(int data) {
      this.data = data;
    }
  }


  public static void main(String[] args) {
    MLinkedList mLinkedList = new MLinkedList();
    mLinkedList.print(); // "null"

    mLinkedList.append(0);
    mLinkedList.print(); // "0"

    mLinkedList.append(1);
    mLinkedList.append(2);
    mLinkedList.print(); // "0 -> 1 -> 2"

    mLinkedList.prepend(3);
    mLinkedList.prepend(4);
    mLinkedList.print(); // "4 -> 3 -> 0 -> 1 -> 2"

    mLinkedList.delete(4);
    mLinkedList.print(); // "3 -> 0 -> 1 -> 2"

    mLinkedList.delete(2);
    mLinkedList.print(); // "3 -> 0 -> 1"

    mLinkedList.delete(1);
    mLinkedList.delete(3);
    mLinkedList.delete(4);
    mLinkedList.delete(0);
    mLinkedList.delete(2);
    mLinkedList.print(); // "null"

    mLinkedList.prepend(5);
    mLinkedList.print(); // "5"

    mLinkedList.prepend(6);
    mLinkedList.append(7);
    mLinkedList.print(); // "6 -> 5 -> 7"
  }
}
