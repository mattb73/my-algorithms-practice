package com.mbao.algo;

import edu.princeton.cs.algs4.DepthFirstOrder;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

/**
 * Topological order is the reverse-post order of dfs.
 * Use a stack to get the "reverse" part.
 */
public class MTopologicalDFS {
  private boolean[] marked;
  private List<Integer> preOrder;
  private List<Integer> postOrder;
  private Deque<Integer> reversePostOrder;

  public MTopologicalDFS(Digraph G) {
    marked = new boolean[G.V()];
    preOrder = new ArrayList<>();
    postOrder = new ArrayList<>();
    reversePostOrder = new LinkedList<>();

    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) {
        dfs(G, v);
      }
    }

    // If we do dfs in a different order, we'll get a different
    // but also valid topological order.
//    for (int v = G.V() - 1 ; v >= 0; v--) {
//      if (!marked[v]) {
//        dfs(G, v);
//      }
//    }
  }

  private void dfs(Digraph G, int v) {
    preOrder.add(v);
    marked[v] = true;
    for (int w : G.adj(v)) {
      if (!marked[w]) {
        dfs(G, w);
      }
    }
    postOrder.add(v);
    reversePostOrder.push(v);
  }

  public int pre(int v) {
    return preOrder.indexOf(v);
  }

  public int post(int v) {
    return postOrder.indexOf(v);
  }

  public Iterable<Integer> pre() {
    return preOrder;
  }

  public Iterable<Integer> post() {
    return postOrder;
  }

  public Iterable<Integer> reversePost() {
    return reversePostOrder;
  }


  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyDAG.txt");
    Digraph G = new Digraph(in);

    DepthFirstOrder dfs = new DepthFirstOrder(G);
    StdOut.println("   v  pre post");
    StdOut.println("--------------");
    for (int v = 0; v < G.V(); v++) {
      StdOut.printf("%4d %4d %4d\n", v, dfs.pre(v), dfs.post(v));
    }

    StdOut.print("Preorder:  ");
    for (int v : dfs.pre()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Postorder: ");
    for (int v : dfs.post()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Reverse postorder: ");
    for (int v : dfs.reversePost()) {
      StdOut.print(v + " ");
    }
    StdOut.println();


///////////////////////
    StdOut.println();

    MTopologicalDFS mTopologicalDFS = new MTopologicalDFS(G);
    StdOut.println("   v  pre post");
    StdOut.println("--------------");
    for (int v = 0; v < G.V(); v++) {
      StdOut.printf("%4d %4d %4d\n", v, mTopologicalDFS.pre(v), mTopologicalDFS.post(v));
    }

    StdOut.print("Preorder:  ");
    for (int v : mTopologicalDFS.pre()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Postorder: ");
    for (int v : mTopologicalDFS.post()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

    StdOut.print("Reverse postorder: ");
    for (int v : mTopologicalDFS.reversePost()) {
      StdOut.print(v + " ");
    }
    StdOut.println();

  }
}
