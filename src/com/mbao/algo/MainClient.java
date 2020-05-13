package com.mbao.algo;

import edu.princeton.cs.algs4.*;

public class MainClient {

  public static void main(String[] args) {
    In in = new In("/Users/mbao/Workspace/Algorithms4/algs4-data/tinyEWDAG.txt");
    int s = 4;
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);

    AcyclicLP lp = new AcyclicLP(G, s);

    for (int v = 0; v < G.V(); v++) {
      if (lp.hasPathTo(v)) {
        StdOut.printf("%d to %d (%.2f)  ", s, v, lp.distTo(v));
        for (DirectedEdge e : lp.pathTo(v)) {
          StdOut.print(e + "   ");
        }
        StdOut.println();
      }
      else {
        StdOut.printf("%d to %d         no path\n", s, v);
      }
    }

    StdOut.println();
  }
}
