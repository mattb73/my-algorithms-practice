package com.mbao.algo;

import edu.princeton.cs.algs4.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import static com.mbao.algo.MainClient.HOME_DIR;
import static com.mbao.algo.MainClient.TEST_DATA_RELATIVE_DIR;

public class MCycle {
  private boolean[] marked;
  private int[] edgeTo;
  Deque<Integer> cycle;
  boolean hasCycle;

  public MCycle(Graph G) {
    marked = new boolean[G.V()];
    edgeTo = new int[G.V()];
    for (int v = 0; v < G.V(); v++) {
      if (!marked[v] && !hasCycle) {
        dfsUG(G, v, -1);
      }
    }
  }

  public MCycle(Digraph DG, boolean dfs) {
    if (dfs) {
      initDGCycleDFS(DG);
    } else {
      initDGCycleNormal(DG);
    }
  }

  private void initDGCycleDFS(Digraph DG) {
    marked = new boolean[DG.V()];
    edgeTo = new int[DG.V()];
    hasCycle = false;
    boolean[] onStack = new boolean[DG.V()];

    for (int v = 0; v < DG.V(); v++) {
      if (!marked[v] && !hasCycle) {
        dfsDG(DG, v, onStack);
      }
    }
  }

  private void initDGCycleNormal(Digraph DG) {
    int[] indegree = new int[DG.V()];
    List<Integer> order = new ArrayList<>();
    Deque<Integer> next = new ArrayDeque<>();
    for (int v = 0; v < DG.V(); v++) {
      indegree[v] = DG.indegree(v);
    }
    for (int v = 0; v < DG.V(); v++) {
      if (indegree[v] == 0) {
        next.offer(v);
      }
    }
    while(!next.isEmpty()) {
      int v = next.poll();
      order.add(v);
      for (int w : DG.adj(v)) {
        indegree[w]--;
        if (indegree[w] == 0) {
          next.offer(w);
        }
      }
    }
    if (order.size() == DG.V()) {
      hasCycle = false;
    } else {
      hasCycle = true;
      // below part is sketch and unnecessary to understand.
      // there is a directed cycle in subgraph of vertices with indegree >= 1.
      int[] edgeTo = new int[DG.V()];
      int root = -1;  // any vertex with indegree >= -1
      for (int v = 0; v < DG.V(); v++) {
        if (indegree[v] == 0) continue;
        else root = v;
        for (int w : DG.adj(v)) {
          if (indegree[w] > 0) {
            edgeTo[w] = v;
          }
        }
      }
      if (root != -1) {
        // find any vertex on cycle
        boolean[] visited = new boolean[DG.V()];
        while (!visited[root]) {
          visited[root] = true;
          root = edgeTo[root];
        }

        // extract cycle
        cycle = new ArrayDeque<>();
        int v = root;
        do {
          cycle.push(v);
          v = edgeTo[v];
        } while (v != root);
        cycle.push(root);
      }
    }
  }

  private void dfsUG(Graph G, int v, int parent) {
    marked[v] = true;
    for (int w : G.adj(v)) {
      if (hasCycle) return;
      if (!marked[w]) {
        edgeTo[w] = v;
        dfsUG(G, w, v);
      } else if (w != parent) {
        hasCycle = true;
        cycle = new ArrayDeque<>();
        for (int x = v; x != w; x = edgeTo[x]) {
          cycle.push(x);
        }
        cycle.push(w);
        cycle.push(v);
      }
    }
  }

  private void dfsDG(Digraph DG, int v, boolean[] onStack) {
    onStack[v] = true;
    marked[v] = true;
    for (int w : DG.adj(v)) {
      if (hasCycle) return;
      if (!marked[w]) {
        edgeTo[w] = v;
        dfsDG(DG, w, onStack);
      } else if (onStack[w]) {
        hasCycle = true;
        cycle = new ArrayDeque<>();
        for (int x = v; x != w; x = edgeTo[x]) {
          cycle.push(x);
        }
        cycle.push(w);
        cycle.push(v);
      }
    }
    onStack[v] = false;
  }

  public boolean hasCycle() {
    return cycle != null;
  }

  public Iterable<Integer> cycle() {
    return cycle;
  }


  public static void main(String[] args) {
    Graph G = new Graph(new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyG.txt"));
    Digraph DG = new Digraph(new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyDG.txt"));
    Digraph DAG = new Digraph(new In(HOME_DIR + TEST_DATA_RELATIVE_DIR + "tinyDAG.txt"));

    Cycle finder = new Cycle(G);
    if (finder.hasCycle()) {
      for (int v : finder.cycle()) {
        StdOut.print(v + " ");
      }
      StdOut.println();
    } else {
      StdOut.println("Graph is acyclic");
    }
    StdOut.println();

    DirectedCycle dFinder = new DirectedCycle(DG);
    if (dFinder.hasCycle()) {
      StdOut.print("Directed cycle: ");
      for (int v : dFinder.cycle()) {
        StdOut.print(v + " ");
      }
      StdOut.println();
    } else {
      StdOut.println("No directed cycle");
    }
    StdOut.println();

    DirectedCycleX dFinderX = new DirectedCycleX(DG);
    if (dFinderX.hasCycle()) {
      StdOut.print("Directed cycle: ");
      for (int v : dFinderX.cycle()) {
        StdOut.print(v + " ");
      }
      StdOut.println();
    } else {
      StdOut.println("No directed cycle");
    }
    StdOut.println();

    //////////////////////////////////////////////////////////////
    StdOut.println();
    List<MCycle> list = new ArrayList<>();
    MCycle mCycle = new MCycle(G); list.add(mCycle);
    MCycle mDCycleDfs = new MCycle(DG, true); list.add(mDCycleDfs);
    MCycle mDCycleNormal = new MCycle(DG, false); list.add(mDCycleNormal);
    MCycle mDAGCycleDfs = new MCycle(DAG, true); list.add(mDAGCycleDfs);
    MCycle mDAGCycleNormal = new MCycle(DAG, false); list.add(mDAGCycleNormal);

    for (MCycle test : list) {
      if (test.hasCycle()) {
        StdOut.print("Cycle: ");
        for (int v : test.cycle()) {
          StdOut.print(v + " ");
        }
        StdOut.println();
      } else {
        StdOut.println("No cycle.");
      }
      StdOut.println();
    }
  }
}
