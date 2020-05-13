package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

/**
 * Use max heap and relax edge if there's a *longer* path.
 */
public class MAcyclicLP2 {
  private double[] distTo;
  private DirectedEdge[] edgeTo;

  public MAcyclicLP2(EdgeWeightedDigraph G, int s) {
    distTo = new double[G.V()];
    edgeTo = new DirectedEdge[G.V()];
    for (int v = 0; v < G.V(); v++) {
      distTo[v] = Double.NEGATIVE_INFINITY;
    }
    PriorityQueue<Integer> maxQ = new PriorityQueue<>((o1, o2) -> -Double.compare(distTo[o1], distTo[o2]));
    distTo[s] = 0.0;
    maxQ.offer(s);
    while (!maxQ.isEmpty()) {
      int v = maxQ.poll();
      for (DirectedEdge e : G.adj(v)) {
        int w = e.to();
        if (distTo[w] < distTo[v] + e.weight()) {
          distTo[w] = distTo[v] + e.weight();
          edgeTo[w] = e;
          if (maxQ.contains(w)) {
            maxQ.remove(w);
            maxQ.offer(w);
          } else {
            maxQ.offer(w);
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
    Deque<DirectedEdge> path = new ArrayDeque<>();
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

    MAcyclicLP2 mlp = new MAcyclicLP2(G, s);

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
