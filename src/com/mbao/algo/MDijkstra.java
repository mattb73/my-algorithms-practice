package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.*;

public class MDijkstra {
  private double[] distTo;
  private DirectedEdge[] edgeTo;

  /**
   * Computes a shortest-paths tree from the source vertex {@code s} to every other
   * vertex in the edge-weighted digraph {@code G}.
   *
   * @param  G the edge-weighted digraph
   * @param  s the source vertex
   * @throws IllegalArgumentException if an edge weight is negative
   * @throws IllegalArgumentException unless {@code 0 <= s < V}
   */
  public MDijkstra(EdgeWeightedDigraph G, int s) {
    edgeTo = new DirectedEdge[G.V()];
    distTo = new double[G.V()];
    for (int i = 0; i < G.V(); i++) {
      distTo[i] = Double.POSITIVE_INFINITY;
    }
    distTo[s] = 0.0;
    PriorityQueue<Integer> minQ = new PriorityQueue<>((o1, o2) -> Double.compare(distTo[o1], distTo[o2]));

    minQ.offer(s);
    while(!minQ.isEmpty()) {
      int v = minQ.poll();
      for (DirectedEdge e : G.adj(v)) {
        int w = e.to();
        if (distTo[w] > distTo[v] + e.weight()) {
          distTo[w] = distTo[v] + e.weight();
          edgeTo[w] = e;
          if (minQ.contains(w)) {
            minQ.remove(w);
            minQ.offer(w);
          } else {
            minQ.offer(w);
          }
        }
      }
    }
  }

  /**
   * Returns the length of a shortest path from the source vertex {@code s} to vertex {@code v}.
   * @param  v the destination vertex
   * @return the length of a shortest path from the source vertex {@code s} to vertex {@code v};
   *         {@code Double.POSITIVE_INFINITY} if no such path
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public double distTo(int v) {
    if (v < 0 || v >= distTo.length) {
      throw new IllegalArgumentException();
    }
    return distTo[v];
  }

  /**
   * Returns true if there is a path from the source vertex {@code s} to vertex {@code v}.
   *
   * @param  v the destination vertex
   * @return {@code true} if there is a path from the source vertex
   *         {@code s} to vertex {@code v}; {@code false} otherwise
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public boolean hasPathTo(int v) {
    if (v < 0 || v >= distTo.length) {
      throw new IllegalArgumentException();
    }
    return distTo[v] != Double.POSITIVE_INFINITY;
  }

  /**
   * Returns a shortest path from the source vertex {@code s} to vertex {@code v}.
   *
   * @param  v the destination vertex
   * @return a shortest path from the source vertex {@code s} to vertex {@code v}
   *         as an iterable of edges, and {@code null} if no such path
   * @throws IllegalArgumentException unless {@code 0 <= v < V}
   */
  public Iterable<DirectedEdge> pathTo(int v) {
    if (v < 0 || v >= distTo.length) {
      throw new IllegalArgumentException();
    }
    if (!hasPathTo(v)) return null;
    LinkedList<DirectedEdge> path = new LinkedList<>();
    for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
      path.push(e);
    }
    return path;
  }


  public static void main(String[] args) {
    In in = new In("/Users/mbao/Workspace/Algorithms4/algs4-data/tinyEWD.txt");
    EdgeWeightedDigraph G = new EdgeWeightedDigraph(in);
    int s = 4;

    // compute shortest paths
    DijkstraSP sp = new DijkstraSP(G, s);
    MDijkstra msp = new MDijkstra(G, s);


    // print shortest path
    for (int t = 0; t < G.V(); t++) {
      if (sp.hasPathTo(t)) {
        StdOut.printf("%d to %d (%.2f)  ", s, t, sp.distTo(t));
        for (DirectedEdge e : sp.pathTo(t)) {
          StdOut.print(e + "   ");
        }
        StdOut.println();
      }
      else {
        StdOut.printf("%d to %d         no path\n", s, t);
      }
    }

    StdOut.println();

    // print shortest path
    for (int t = 0; t < G.V(); t++) {
      if (msp.hasPathTo(t)) {
        StdOut.printf("%d to %d (%.2f)  ", s, t, msp.distTo(t));
        for (DirectedEdge e : msp.pathTo(t)) {
          StdOut.print(e + "   ");
        }
        StdOut.println();
      }
      else {
        StdOut.printf("%d to %d         no path\n", s, t);
      }
    }
  }
}
