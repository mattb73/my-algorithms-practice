package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MPrimMST {
  private Edge[] edgeTo;
  private double[] distToTree;
  private boolean[] marked;
  private PriorityQueue<Integer> minQ;
  private List<Edge> mst;
  private double weight;

  /**
   * Compute a minimum spanning tree (or forest) of an edge-weighted graph.
   * @param G the edge-weighted graph
   */
  public MPrimMST(EdgeWeightedGraph G) {
    mst = new ArrayList<>();
    weight = 0.0;
    edgeTo = new Edge[G.V()];
    marked = new boolean[G.V()];
    distToTree = new double[G.V()];
    for (int i = 0; i < G.V(); i++) {
      distToTree[i] = Double.POSITIVE_INFINITY;
    }
    minQ = new PriorityQueue<>((o1, o2) -> Double.compare(distToTree[o1], distToTree[o2]));

    minQ.offer(0);
    while (!minQ.isEmpty()) {
      int v = minQ.poll();
      process(G, v);
    }
  }

  private void process(EdgeWeightedGraph G, int v) {
    marked[v] = true;
    if (edgeTo[v] != null) { // edgeTo[0] == null;
      mst.add(edgeTo[v]);
      weight += edgeTo[v].weight();
    }
    for (Edge e : G.adj(v)) {
      int w = e.other(v);
      if (!marked[w] && distToTree[w] > e.weight()) {
        distToTree[w] = e.weight();
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

  /**
   * Returns the edges in a minimum spanning tree (or forest).
   * @return the edges in a minimum spanning tree (or forest) as
   *    an iterable of edges
   */
  public Iterable<Edge> edges() {
    return mst;
  }

  /**
   * Returns the sum of the edge weights in a minimum spanning tree (or forest).
   * @return the sum of the edge weights in a minimum spanning tree (or forest)
   */
  public double weight() {
    return weight;
  }


  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyEWG.txt");
    EdgeWeightedGraph G = new EdgeWeightedGraph(in);

    PrimMST mst = new PrimMST(G);
    for (Edge e : mst.edges()) {
      StdOut.println(e);
    }
    StdOut.printf("%.5f\n", mst.weight());
    StdOut.println();

    MPrimMST mmst = new MPrimMST(G);
    for (Edge e : mmst.edges()) {
      StdOut.println(e);
    }
    StdOut.printf("%.5f\n", mmst.weight());
    StdOut.println();
  }
}
