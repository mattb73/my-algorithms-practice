package com.mbao.algo;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Merge;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MMergeSort {

  private MMergeSort() {}

  public static void sort(Comparable[] a) {
    Comparable[] aux = Arrays.copyOf(a, a.length);
    sort(a, aux, 0, a.length - 1);
  }

  private static void sort(Comparable[] a, Comparable[] aux, int lo, int hi) {
    if (lo == hi) return; // or hi <= lo
    int mid = lo + (hi - lo) / 2;
    sort(a, aux, lo, mid);
    sort(a, aux, mid + 1, hi);
    merge(a, aux, lo, mid, hi);
  }

  private static void merge(Comparable[] a, Comparable[] aux, int lo, int mid, int hi) {
    int i = lo;
    int j = mid + 1;
    int k = lo;
    while (k <= hi) {
      if (j > hi) {
        a[k++] = aux[i++];
      } else if (i > mid) {
        a[k++] = aux[j++];
      } else if (less(aux[i], aux[j])) {
        a[k++] = aux[i++];
      } else {
        a[k++] = aux[j++];
      }
    }
  }

  private static boolean less(Comparable x, Comparable y) {
    return x.compareTo(y) < 0;
  }




  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "words3.txt");
    String[] a = in.readAllStrings();

    Merge.sort(a);
    show(a);

    /////////////////////////
    StdOut.println();

    MMergeSort.sort(a);
    show(a);
  }

  // print array to standard output
  private static void show(Comparable[] a) {
    for (int i = 0; i < a.length; i++) {
      StdOut.print(a[i] + "  ");
    }
    StdOut.println();
  }
}
