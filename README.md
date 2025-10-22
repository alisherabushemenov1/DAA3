# 🏙️ Assignment 3 – Minimum Spanning Tree Report  
**by Abushemenov Alisher**

---

## 1. Summary of Input Data and Algorithm Results

This project focused on finding the **Minimum Spanning Tree (MST)** for several graphs of different sizes and densities using **Prim’s** and **Kruskal’s** algorithms.  
Each graph represents a transportation network, and the goal is to connect all vertices (districts) with minimum total cost.

The algorithms were implemented in Java using the following classes:  
`Graph.java`, `Edge.java`, `PrimMST.java`, `KruskalMST.java`, `UnionFind.java`, and `MSTSolver.java`.

| Graph ID | Vertices | Edges | Prim Total Cost | Kruskal Total Cost | Prim Ops | Kruskal Ops | Prim Time (ms) | Kruskal Time (ms) |
|-----------|-----------|--------|------------------|--------------------|-----------|---------------|------------------|
| 1 | 5 | 6 | 16.0 | 16.0 | 35 | 32 | 0.8 | 1.36 |
| 2 | 6 | 8 | 15.0 | 15.0 | 48 | 51 | 0.1 | 0.08 |
| 3 | 10 | 12 | 30.0 | 30.0 | 72 | 77 | 0.09 | 0.08 |
| 4 | 12 | 15 | 41.0 | 41.0 | 89 | 99 | 0.18 | 0.12 |
| 5 | 20 | 22 | 67.0 | 67.0 | 132 | 167 | 0.20 | 0.14 |
| 6 | 25 | 28 | 92.0 | 92.0 | 167 | 212 | 0.21 | 0.15 |

For all graphs, both algorithms produced the same MST total cost, confirming that both are correct.  
The operation counts show that **Kruskal’s algorithm** required fewer operations on small graphs, while **Prim’s algorithm** became more efficient as the graphs grew denser.

---

## 2. Comparison Between Prim’s and Kruskal’s Algorithms

### **Theory**

- **Prim’s algorithm** builds the MST by starting from a single vertex and repeatedly adding the smallest edge that connects a new vertex.  
  It is efficient for **dense graphs** because it uses a priority queue (heap) to manage edges.

- **Kruskal’s algorithm** sorts all edges by weight and adds them one by one, skipping edges that would form a cycle.  
  It uses the **Union-Find** structure to detect cycles, making it efficient for **sparse graphs**.

| Aspect | Prim’s Algorithm | Kruskal’s Algorithm |
|--------|------------------|---------------------|
| Approach | Grows MST from one node | Adds smallest edges globally |
| Data Structure | Priority Queue | Union-Find (Disjoint Set) |
| Best for | Dense graphs | Sparse graphs |
| Time Complexity | O(E log V) | O(E log E) ≈ O(E log V) |
| Implementation | Slightly more complex | Easier to implement |

---

### **In Practice**

From the experiments:
- On small and medium graphs, both algorithms performed almost equally fast.
- On larger and denser graphs, **Prim’s algorithm** required fewer operations because it grows one connected component instead of sorting all edges.
- **Kruskal’s algorithm** required more operations on dense graphs due to additional Union-Find checks.

However, for sparse graphs with few edges, **Kruskal remains simple and fast.**

---

## 3. Conclusions

Both Prim’s and Kruskal’s algorithms produce the same MST cost and structure for all connected graphs.  
The difference lies in their efficiency depending on **graph density**:

- **Prim’s algorithm** is better for **dense graphs** because it doesn’t process all edges globally.  
- **Kruskal’s algorithm** is better for **sparse graphs** because sorting fewer edges is faster.  
- In real-world transportation networks (usually sparse), **Kruskal’s algorithm** may be slightly more practical,  
  while **Prim’s** scales better for dense, interconnected networks.

✅ **Conclusion:** Both algorithms are correct and effective, but their performance depends on the type of graph and implementation details.

---

## 4. References

- [Princeton University – Algorithms, Part II: Minimum Spanning Trees](https://algs4.cs.princeton.edu/43mst/)  
- **Lecture 6 – Disjoint Set Union (Union-Find) and Minimum Spanning Trees (Kruskal / Prim)**

