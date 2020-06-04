package com.mbao.algo;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MSD;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MMSD {
  private static final int R = 256;

  private MMSD() {}

  public static void sort(String[] a) {
    String[] aux = new String[a.length];
    sort(a, 0, a.length - 1, 0, aux);
  }

  private static void sort(String[] a, int lo, int hi, int d, String[] aux) {
    if (hi <= lo) return;
    int[] count = new int[R + 2];
    for (int i = lo; i <= hi; i++) {
      count[index(a[i], d) + 1]++;
    }
    for (int i = 1; i < count.length; i++) {
      count[i] += count[i - 1];
    }
    for (int i = lo; i <= hi; i++) {
      aux[count[index(a[i], d)]++] = a[i];
    }
    for (int i = lo; i <= hi; i++) {
      a[i] = aux[i - lo];
    }

    int i = 0;
    while (i < count.length) {
      int j = i + 1;
      while (j < count.length && count[j] == count[i]) {
        j++;
      }
      if (j < count.length) {
        sort(a, lo + count[i], lo + count[j] - 1, d + 1, aux);
      }
      i = j;
    }
  }

  // 0 <-> EOS, 1 <-> 1st char in the alphabet, 2 <-> 2nd char in the alphabet, and so on.
  private static int index(String s, int d) {
    if (d >= s.length()) return 0;
    return s.charAt(d) + 1;
  }


  /**
   * Reads in a sequence of extended ASCII strings from standard input;
   * MSD radix sorts them;
   * and prints them to standard output in ascending order.
   *
   * @param args the command-line arguments
   */
  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "MSDTest.txt");
    String[] a = in.readAllStrings();
    String[] ma = Arrays.copyOf(a, a.length);
    int n = a.length;
    int maxL = 0;
    for (String s : a) {
      if (s.length() > maxL) {
        maxL = s.length();
      }
    }

    MSD.sort(a);
    MMSD.sort(ma);

    StdOut.println(Arrays.equals(a, ma));
    String format = "%-" + maxL + "s  %s\n";
    for (int i = 0; i < n; i++) {
      StdOut.printf(format, a[i], ma[i]);
    }
  }
}
