package com.mbao.algo;

import edu.princeton.cs.algs4.BinarySearch;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MBinarySearch {
  
  private MBinarySearch() {}

  // Assumes a sorted.
  public static int indexOf(int[] a, int key) {
    int lo = 0;
    int hi = a.length - 1;
    while (lo <= hi) {
      int mid = lo + (hi - lo) / 2;
      if (a[mid] == key) return mid;
      if (a[mid] > key) {
        hi = mid - 1;
      } else {
        lo = mid + 1;
      }
    }
    return -1;
  }

  public static void main(String[] args) {
    // read the integers from a file
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyW.txt");
    int[] whitelist = in.readAllInts();
    In check = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyT.txt");
    int[] checklist =  check.readAllInts();

    // sort the array
    Arrays.sort(whitelist);

    // read integer key from checklist; print if not in whitelist
    for (int key : checklist) {
      if (BinarySearch.indexOf(whitelist, key) == -1)
        StdOut.print(key + " ");
    }

    //////////////////////////////////////////////////
    
    StdOut.println();
    for (int key : checklist) {
      if (MBinarySearch.indexOf(whitelist, key) == -1)
        StdOut.print(key + " ");
    }
  }
}
