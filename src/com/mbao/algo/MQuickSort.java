package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MQuickSort {

  private MQuickSort() {}

  public static void sort(Comparable[] a) {
//    StdRandom.shuffle(a);
    sort(a, 0, a.length - 1);
  }

  public static Comparable select(Comparable[] a, int k) {
    return "-1";
  }

  private static void sort(Comparable[] a, int lo, int hi) {
    if (hi <= lo) return; // "==" wouldn't work!!!!
    int k = partition(a, lo, hi); // after this step, a[k] of array is in the correct position
    sort(a, lo, k - 1);
    sort(a, k + 1, hi);
  }

  private static int partition(Comparable[] a, int lo, int hi) {
    int i = lo;
    int j = hi + 1;
    while (true) {
      while (i < hi && less(a[++i], a[lo])) {
//        if (i > hi) break;
//        i++;
      }
      while (j > lo && less(a[lo], a[--j])) {
//        if (j < (lo + 1)) break;
//        j--;
      }
      if (i >= j) break;
      exch(a, i, j);
    }
    exch(a, lo, j);
    return j;

//    int i = lo;
//    int j = hi + 1;
//    while (true) {
//      while (less(a[++i], a[lo])) {
//        if (i == hi) break;
//      }
//      while (less(a[lo], a[--j])) {
//        if (j == lo) break;
//      }
//      if (i >= j) break;
//      exch(a, i, j);
//    }
//    exch(a, lo, j);
//    return j;
  }


  private static boolean less(Comparable v, Comparable w) {
    return v.compareTo(w) < 0;
  }

  private static void exch(Object[] a, int i, int j) {
    Object c = a[i];
    a[i] = a[j];
    a[j] = c;
  }




  /**
   * Reads in a sequence of strings from standard input; quicksorts them;
   * and prints them to standard output in ascending order.
   * Shuffles the array and then prints the strings again to
   * standard output, but this time, using the select method.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "MQuicksortTest.txt");
    String[] a = in.readAllStrings();

    Quick.sort(a);
    show(a);

    // shuffle
    StdRandom.shuffle(a);
    // display results again using select
    StdOut.println();
    for (int i = 0; i < a.length; i++) {
      String ith = (String) Quick.select(a, i);
      StdOut.print(ith + "  ");
    }
    StdOut.println();

    ///////////////////////////////////////////////////////////
    StdOut.println();

    In mIn = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "MQuicksortTest.txt");
    String[] mA = mIn.readAllStrings();

    MQuickSort.sort(mA);
    show(mA);

    // shuffle
    StdRandom.shuffle(mA);
    // display results again using select
    StdOut.println();
    for (int i = 0; i < mA.length; i++) {
      String ith = (String) MQuickSort.select(mA, i);
      StdOut.print(ith + "  ");
    }
    StdOut.println();

  }

  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.print(a[i] + "  ");
    }
    StdOut.println();
  }
}
