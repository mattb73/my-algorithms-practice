package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

/**
 * By tracking nodes with 0 incoming edges.
 */
public class MTopologicalNormal {
  private int[] indegree;
  private List<Integer> order;

  public MTopologicalNormal(Digraph G) {
    indegree = new int[G.V()];
    order = new ArrayList<>();

    initializeIndegreeArray(G);

    Queue<Integer> nextToProcess = new LinkedList<>();
    for (int v = 0; v < G.V(); v++) {
      if (indegree[v] == 0) {
        nextToProcess.offer(v);
      }
    }

    while(!nextToProcess.isEmpty()) {
      int v = nextToProcess.poll();
      for (int w : G.adj(v)) {
        indegree[w]--;
        if (indegree[w] == 0) {
          nextToProcess.offer(w);
        }
      }
      order.add(v);
    }
  }

  private void initializeIndegreeArray(Digraph G) {
    for (int v = 0; v < G.V(); v++) {
      for (int w : G.adj(v)) {
        indegree[w]++;
      }
    }
  }

  public Iterable<Integer> order() {
    return order;
  }

  public boolean hasOrder() {
    return order.size() == indegree.length;
  }

  private int rank(int v) {
    return order.indexOf(v);
  }




  public boolean check(Digraph G) {
    // digraph is acyclic
    if (hasOrder()) {
      // check that ranks are a permutation of 0 to V-1
      boolean[] found = new boolean[G.V()];
      for (int i = 0; i < G.V(); i++) {
        found[rank(i)] = true;
      }
      for (int i = 0; i < G.V(); i++) {
        if (!found[i]) {
          System.err.println("No vertex with rank " + i);
          return false;
        }
      }

      // check that ranks provide a valid topological order
      for (int v = 0; v < G.V(); v++) {
        for (int w : G.adj(v)) {
          if (rank(v) > rank(w)) {
            System.err.printf("%d-%d: rank(%d) = %d, rank(%d) = %d\n",
                    v, w, v, rank(v), w, rank(w));
            return false;
          }
        }
      }

      // check that order() is consistent with rank()
      int r = 0;
      for (int v : order()) {
        if (rank(v) != r) {
          System.err.println("order() and rank() inconsistent");
          return false;
        }
        r++;
      }
    }


    return true;
  }


  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyDAG.txt");
    Digraph G = new Digraph(in);


    DepthFirstOrder dfs = new DepthFirstOrder(G);

    StdOut.print("Reverse postorder: ");
    for (int v : dfs.reversePost()) {
      StdOut.print(v + " ");
    }
    StdOut.println();


    ///////////////////////
    StdOut.println();

    MTopologicalNormal mTopologicalNormal = new MTopologicalNormal(G);

    StdOut.print("Reverse postorder: ");
    for (int v : mTopologicalNormal.order()) {
      StdOut.print(v + " ");
    }
    StdOut.println();
    StdOut.println("Valid order: " + mTopologicalNormal.check(G));

  }
}
