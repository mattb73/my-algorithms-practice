package com.mbao.algo;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Deque;
import java.util.LinkedList;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MBFS {
  private boolean[] visited;
  private int[] edgeTo;
  private int[] distTo;

  public MBFS(Graph G, int s) {
    visited = new boolean[G.V()];
    edgeTo = new int[G.V()];
    distTo = new int[G.V()];
    for (int v = 0; v < G.V(); v++) {
      edgeTo[v] = -1;
      distTo[v] = Integer.MAX_VALUE;
    }
    edgeTo[s] = s;
    distTo[s] = 0;
    bfs(G, s);
  }

  public boolean hasPathTo(int v) {
    return visited[v];
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v)) return null;
    Deque<Integer> path = new LinkedList<>();
    int x = v;
    while (edgeTo[x] != x) {
      path.push(x);
      x = edgeTo[x];
    }
    path.push(x);
    return path;
  }

  public int distTo(int v) {
    return distTo[v];
  }

  private void bfs(Graph G, int s) {
    Deque<Integer> toProcess = new LinkedList<>();
    visited[s] = true;
    toProcess.offer(s);
    while (!toProcess.isEmpty()) {
      int v = toProcess.poll();
      for (int w : G.adj(v)) {
        if (!visited[w]) {
          visited[w] = true;
          edgeTo[w] = v;
          distTo[w] = distTo[v] + 1;
          toProcess.offer(w);
        }
      }
    }
  }


  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyCG.txt");
    Graph G = new Graph(in);
    // StdOut.println(G);

    int s = 5;
    BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);

    for (int v = 0; v < G.V(); v++) {
      if (bfs.hasPathTo(v)) {
        StdOut.printf("%d to %d (%d):  ", s, v, bfs.distTo(v));
        for (int x : bfs.pathTo(v)) {
          if (x == s) StdOut.print(x);
          else        StdOut.print("-" + x);
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d (-):  not connected\n", s, v);
      }
    }

    ////////////////////////////////////////////////////
    StdOut.println();
    MBFS mbfs = new MBFS(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (mbfs.hasPathTo(v)) {
        StdOut.printf("%d to %d (%d):  ", s, v, mbfs.distTo(v));
        for (int x : mbfs.pathTo(v)) {
          if (x == s) StdOut.print(x);
          else        StdOut.print("-" + x);
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d (-):  not connected\n", s, v);
      }
    }
  }
}
