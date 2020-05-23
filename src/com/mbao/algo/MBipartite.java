package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.Deque;
import java.util.LinkedList;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MBipartite {
  private boolean[] marked;
  private boolean[] color;
  private boolean isBipartite;
  private Deque<Integer> cycle;
  private int[] edgeTo;

  public MBipartite(Graph G) {
    marked = new boolean[G.V()];
    color = new boolean[G.V()];
    edgeTo = new int[G.V()];
    isBipartite = true;
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v]) {
        dfs(G, v);
      }
    }
  }

  public boolean isBipartite() {
    return isBipartite;
  }

  public boolean color(int v) {
    return color[v];
  }

  public Iterable<Integer> oddCycle() {
    return cycle;
  }

  private void dfs(Graph G, int v) {
    marked[v] = true;
    for (int w : G.adj(v)) {
      if (cycle != null) return;
      if (!marked[w]) {
        color[w] = !color[v];
        edgeTo[w] = v;
        dfs(G, w);
      } else if (color[w] == color[v]) {
        isBipartite = false;
        cycle = new LinkedList<>();
        for (int x = v; x != w; x = edgeTo[x]) {
          cycle.push(x);
        }
        cycle.push(w);
        cycle.push(v);
      }
    }
  }


  public static void main(String[] args) {
    int V1 = 4;
    int V2 = 5;
    int E  = 12;
    int F  = 3;
    // create random bipartite graph with V1 vertices on left side,
    // V2 vertices on right side, and E edges; then add F random edges
    Graph G = GraphGenerator.bipartite(V1, V2, E);
    for (int i = 0; i < F; i++) {
      int v = StdRandom.uniform(V1 + V2);
      int w = StdRandom.uniform(V1 + V2);
      G.addEdge(v, w);
    }
//    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "testBipartite.txt");
//    Graph G = new Graph(in);


    StdOut.println(G);

    Bipartite b = new Bipartite(G);
    if (b.isBipartite()) {
      StdOut.println("Graph is bipartite");
      for (int v = 0; v < G.V(); v++) {
        StdOut.println(v + ": " + b.color(v));
      }
    }
    else {
      StdOut.print("Graph has an odd-length cycle: ");
      for (int x : b.oddCycle()) {
        StdOut.print(x + " ");
      }
      StdOut.println();
    }

    //////////////////////////////////////////////////////

    StdOut.println();
    MBipartite mb = new MBipartite(G);
    if (mb.isBipartite()) {
      StdOut.println("Graph is bipartite");
      for (int v = 0; v < G.V(); v++) {
        StdOut.println(v + ": " + mb.color(v));
      }
    }
    else {
      StdOut.print("Graph has an odd-length cycle: ");
      for (int x : mb.oddCycle()) {
        StdOut.print(x + " ");
      }
      StdOut.println();
    }
  }
}
