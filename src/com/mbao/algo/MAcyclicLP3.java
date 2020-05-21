package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.Deque;
import java.util.LinkedList;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

/**
 * Relax edges in topological order since we know it's a DAG. O(E + V).
 * Don't need any priority queues since the order is determined from topological sort.
 */
public class MAcyclicLP3 {
  private double[] distTo;
  private DirectedEdge[] edgeTo;

  public MAcyclicLP3(EdgeWeightedDigraph G, int s) {
    distTo = new double[G.V()];
    edgeTo = new DirectedEdge[G.V()];
    for (int v = 0; v < G.V(); v++) {
      distTo[v] = Double.NEGATIVE_INFINITY;
    }
    distTo[s] = 0.0;
    Topological topological = new Topological(G);
    if (topological.hasOrder()) {
      for (int v : topological.order()) {
        for (DirectedEdge e : G.adj(v)) {
          int w = e.to();
          if (distTo[w] < distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
          }
        }
      }
    }
  }

  public double distTo(int v) {
    return distTo[v];
  }

  public boolean hasPathTo(int v) {
    return distTo[v] != Double.NEGATIVE_INFINITY;
  }

  public Iterable<DirectedEdge> pathTo(int v) {
    if (!hasPathTo(v)) return null;
    Deque<DirectedEdge> path = new LinkedList<>();
    for (int x = v; edgeTo[x] != null; x = edgeTo[x].from()) {
      path.push(edgeTo[x]);
    }
    return path;
  }


  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyEWDAG.txt");
    int s = 5;
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

    ////////////////////////////////////////
    StdOut.println();

    MAcyclicLP3 mlp = new MAcyclicLP3(G, s);

    for (int v = 0; v < G.V(); v++) {
      if (mlp.hasPathTo(v)) {
        StdOut.printf("%d to %d (%.2f)  ", s, v, mlp.distTo(v));
        for (DirectedEdge e : mlp.pathTo(v)) {
          StdOut.print(e + "   ");
        }
        StdOut.println();
      }
      else {
        StdOut.printf("%d to %d         no path\n", s, v);
      }
    }
  }
}
