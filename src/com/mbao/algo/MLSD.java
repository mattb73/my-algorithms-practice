package com.mbao.algo;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LSD;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MLSD {

  private MLSD() {}

  /**
   * Rearranges the array of w-character strings in ascending order.
   *
   * @param a the array to be sorted
   * @param w the number of characters per string
   */
  public static void sort(String[] a, int w) {
    int R = 256; // ASCII
    String[] aux = new String[a.length];
    for (int k = w - 1; k >= 0; k--) {
      int[] count = new int[R + 1];
      for (int i = 0; i < a.length; i++) {
        count[a[i].charAt(k) + 1]++;
      }
      for (int i = 1; i < count.length; i++) {
        count[i] += count[i - 1];
      }
      for (int i = 0; i < a.length; i++) {
        aux[count[a[i].charAt(k)]++] = a[i];
      }
      for (int i = 0; i < a.length; i++) {
        a[i] = aux[i];
      }
    }
  }

  /**
   * Rearranges the array of 32-bit integers in ascending order.
   * This is about 2-3x faster than Arrays.sort().
   *
   * @param a the array to be sorted
   */
  public static void sort(int[] a) {
    int R = 2; // 0 and 1
    int w = 32; // 32 bits
    int[] aux = new int[a.length];
    for (int d = 0; d < w; d++) {
      int[] count = new int[R + 1];
      for (int i = 0; i < a.length; i++) {
        count[bit(a[i], d) + 1]++;
      }
      for (int i = 1; i < count.length; i++) {
        count[i] += count[i - 1];
      }

      // The most significant bit represents the sign where 0 means positive and 1 means negative;
      // negative should come first therefore need to flip the starting points for 0 and 1
      if (d == w - 1) {
        int shift1 = count[R] - count[R / 2]; // number of 1s
        int shift2 = count[R / 2]; // number of 0s
        for (int i = 0; i < R/2; i++) {
          count[i] += shift1;
        }
        for (int i = R/2; i < R; i++) {
          count[i] -= shift2;
        }
      }

      for (int i = 0; i < a.length; i++) {
        aux[count[bit(a[i], d)]++] = a[i];
      }
      for (int i = 0; i < a.length; i++) {
        a[i] = aux[i];
      }
    }
  }

  // Returns the dth bit of n from the right side (least significant side)
  private static int bit(int n, int d) {
    int mask = 1 << d;
    return (n & mask) == 0 ? 0 : 1;
  }


  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "words3.txt");
    String[] a = in.readAllStrings();
    String[] ma = Arrays.copyOf(a, a.length);

    In inInt = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "integers.txt");
    int[] aInt = inInt.readAllInts();
    int[] maInt = Arrays.copyOf(aInt, aInt.length);

    int n = a.length;
    // check that strings have fixed length
    int w = a[0].length();
    for (int i = 0; i < n; i++)
      assert a[i].length() == w : "Strings must have fixed length";

    // sort the strings
    LSD.sort(a, w);
    MLSD.sort(ma, w);

    // check if my sort result is the same as answer
    StdOut.println(Arrays.equals(a, ma));

    // print results
    for (int i = 0; i < n; i++)
      StdOut.println(a[i] + "   " + ma[i]);


    StdOut.println();
    // sort the integers
    LSD.sort(aInt);
    MLSD.sort(maInt);

    // check if my sort result is the same as answer
    StdOut.println(Arrays.equals(aInt, maInt));

    // print results
    for (int i = 0; i < aInt.length; i++)
      StdOut.println(aInt[i] + "   " + maInt[i]);
  }
}
