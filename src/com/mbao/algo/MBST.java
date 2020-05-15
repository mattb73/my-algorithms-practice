package com.mbao.algo;

import edu.princeton.cs.algs4.StdOut;

/**
 * Binary Search Tree.
 * left <= this < right
 */
public class MBST<T extends Comparable> {
  private T data;
  private MBST<T> left;
  private MBST<T> right;

  public MBST(T data) {
    this.data = data;
  }

  public void insert(T data) {
    if (data.compareTo(this.data) <= 0) {
      if (left == null) {
        left = new MBST<>(data);
      } else {
        left.insert(data);
      }
    } else {
      if (right == null) {
        right = new MBST<>(data);
      } else {
        right.insert(data);
      }
    }
  }

  public boolean contains(T data) {
    if (this.data.compareTo(data) == 0) return true;
    if (data.compareTo(this.data) < 0) {
      if (left == null) return false;
      return left.contains(data);
    } else {
      if (right == null) return false;
      return right.contains(data);
    }
  }

  public String toString() {
    if (data == null) return null;
    StringBuilder sb = null;
    if (left != null) {
      sb = new StringBuilder(left.toString());
    }
    if (sb == null) {
      sb = new StringBuilder();
    }
    sb.append(data).append(" ");
    if (right != null) {
      sb.append(right.toString());
    }
    return sb.toString();
  }

  public static void main(String[] args) {
    MBST<Integer> mBst = new MBST<>(8);
    StdOut.println(mBst.toString()); // 8
    StdOut.println(mBst.contains(8)); // true
    StdOut.println(mBst.contains(11)); // false

    mBst.insert(4);
    StdOut.println(mBst.toString()); // 4 8
    StdOut.println(mBst.contains(8)); // true
    StdOut.println(mBst.contains(5)); // false
    StdOut.println(mBst.contains(4)); // true

    mBst.insert(6);
    mBst.insert(2);
    StdOut.println(mBst.toString()); // 2 4 6 8
    StdOut.println(mBst.contains(6)); // true
    StdOut.println(mBst.contains(2)); // true
    StdOut.println(mBst.contains(4)); // true
    StdOut.println(mBst.contains(8)); // true
    StdOut.println(mBst.contains(5)); // false
    StdOut.println(mBst.contains(10)); // false

    mBst.insert(10);
    mBst.insert(20);
    StdOut.println(mBst.toString()); // 2 4 6 8 10 20
    StdOut.println(mBst.contains(6)); // true
    StdOut.println(mBst.contains(8)); // true
    StdOut.println(mBst.contains(10)); // true
    StdOut.println(mBst.contains(20)); // true
    StdOut.println(mBst.contains(5)); // false
    StdOut.println(mBst.contains(23)); // false

    mBst.insert(5);
    mBst.insert(24);
    mBst.insert(0);
    mBst.insert(11);
    StdOut.println(mBst.toString()); // 0 2 4 5 6 8 10 11 20 24
  }
}
