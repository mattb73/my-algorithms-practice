package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.ArrayDeque;
import java.util.Deque;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MDFS {
  private boolean[] visited;
  private int[] pathTo;

  public MDFS(Graph G, int s) {
    visited = new boolean[G.V()];
    pathTo = new int[G.V()];
    for (int v = 0; v < G.V(); v++) {
      pathTo[v] = -1;
    }
    pathTo[s] = s;
    dfs(G, s);
  }

  public boolean hasPathTo(int v) {
    return visited[v];
  }

  public Iterable<Integer> pathTo(int v) {
    if (!hasPathTo(v)) return null;
    Deque<Integer> path = new ArrayDeque<>();
    int x = v;
    while (pathTo[x] != x) {
      path.push(x);
      x = pathTo[x];
    }
    path.push(x);
    return path;
  }

  private void dfs(Graph G, int v) {
    visited[v] = true;
    for (int w : G.adj(v)) {
      if (!visited[w]) {
        pathTo[w] = v;
        dfs(G, w);
      }
    }
  }


  public static void main(String[] args) {
    In in = new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyCG.txt");
    Graph G = new Graph(in);
    int s = 5;
    DepthFirstPaths dfs = new DepthFirstPaths(G, s);

    for (int v = 0; v < G.V(); v++) {
      if (dfs.hasPathTo(v)) {
        StdOut.printf("%d to %d:  ", s, v);
        for (int x : dfs.pathTo(v)) {
          if (x == s) StdOut.print(x);
          else        StdOut.print("-" + x);
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d:  not connected\n", s, v);
      }
    }

    //////////////////////////////////////////////
    StdOut.println();

    MDFS mdfs = new MDFS(G, s);
    for (int v = 0; v < G.V(); v++) {
      if (mdfs.hasPathTo(v)) {
        StdOut.printf("%d to %d:  ", s, v);
        for (int x : mdfs.pathTo(v)) {
          if (x == s) StdOut.print(x);
          else        StdOut.print("-" + x);
        }
        StdOut.println();
      } else {
        StdOut.printf("%d to %d:  not connected\n", s, v);
      }
    }
  }
}
