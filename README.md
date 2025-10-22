Assignment 3 – Minimum Spanning Tree Report

Student: Abushemenov Alisher

1. Summary of Input Data and Algorithm Results

This project focused on finding the Minimum Spanning Tree (MST) for several graphs of different sizes and densities using Prim’s and Kruskal’s algorithms. Each graph represents a transportation network, and the goal is to connect all vertices (districts) with minimum total cost.

The algorithms were implemented in Java using the following classes:

Graph.java

Edge.java

PrimMST.java

KruskalMST.java

UnionFind.java

MSTSolver.java

Graph data and results:

| Graph ID | Vertices | Edges | Prim Total Cost | Kruskal Total Cost | Prim Ops | Kruskal Ops | Prim Time (ms) | Kruskal Time (ms) |
| -------- | -------- | ----- | --------------- | ------------------ | -------- | ----------- | -------------- | ----------------- |
| 1        | 5        | 6     | 16.0            | 16.0               | 35       | 32          | 0.8            | 1.36              |
| 2        | 6        | 8     | 15.0            | 15.0               | 48       | 51          | 0.1            | 0.08              |
| 3        | 10       | 12    | 30.0            | 30.0               | 72       | 77          | 0.09           | 0.08              |
| 4        | 12       | 15    | 41.0            | 41.0               | 89       | 99          | 0.18           | 0.12              |
| 5        | 20       | 22    | 67.0            | 67.0               | 132      | 167         | 0.2            | 0.14              |
| 6        | 25       | 28    | 92.0            | 92.0               | 167      | 212         | 0.21           | 0.15              |


Observations:

Both algorithms produced the same MST total cost, confirming correctness.

Kruskal’s algorithm required fewer operations on small graphs, while Prim’s algorithm became more efficient as graphs grew denser.

2. Comparison Between Prim’s and Kruskal’s Algorithms
2.1 Theoretical Overview

Prim’s Algorithm:

Builds MST by starting from a single vertex and repeatedly adding the smallest edge connecting a new vertex.

Efficient for dense graphs due to the use of a priority queue (heap) to manage edges.

Kruskal’s Algorithm:

Sorts all edges by weight and adds them one by one, skipping edges that would form a cycle.

Uses Union-Find (Disjoint Set) structure to detect cycles, efficient for sparse graphs.

Comparison Table:

Aspect	Prim’s Algorithm	Kruskal’s Algorithm
Approach	Grows MST from one node	Adds smallest edges globally
Data Structure	Priority Queue	Union-Find (Disjoint Set)
Best for	Dense graphs	Sparse graphs
Time Complexity	O(E log V)	O(E log E) ≈ O(E log V)
Implementation	Slightly more complex	Easier to implement
2.2 Practical Observations

On small and medium graphs, both algorithms perform almost equally fast.

On larger, denser graphs, Prim’s algorithm requires fewer operations because it grows a single connected component.

Kruskal’s algorithm performs more Union-Find checks on dense graphs.

For sparse graphs, Kruskal remains simple and fast.

3. Conclusions

Both Prim’s and Kruskal’s algorithms produce the same MST cost and structure for all connected graphs.

Efficiency depends on graph density:

Prim’s algorithm: better for dense graphs.

Kruskal’s algorithm: better for sparse graphs.

In real-world transportation networks (usually sparse), Kruskal may be more practical; Prim’s scales better for dense networks.

Summary:
Both algorithms are correct and effective; choice depends on graph type and implementation details.

4. References

Princeton University – Algorithms, Part II: Minimum Spanning Trees
https://algs4.cs.princeton.edu/43mst/

Lecture 6 – Disjoint Set Union (Union-Find) and Minimum Spanning Trees (Kruskal / Prim)
