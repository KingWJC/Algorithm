/**
 * 图
 */
package com.example.utility.entity;

import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    public HashMap<Integer, Vertex> vertexes;
    public HashSet<Edge> edges;

    public Graph() {
        vertexes = new HashMap<>();
        edges = new HashSet<>();
    }

    /**
     * 使用matrix初始化图
     * matrix 包含所有的边，是N*3的矩阵[weight, from节点上面的值，to节点上面的值]
     */
    public Graph(int[][] matrix) {
        vertexes = new HashMap<>();
        edges = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            int weight = matrix[i][0];
            int from = matrix[i][1];
            int to = matrix[i][2];
            
            vertexes.computeIfAbsent(from, Vertex::new);
            if (!vertexes.containsKey(to)) {
                vertexes.put(to, new Vertex(to));
            }

            Vertex v1 = vertexes.get(from);
            Vertex v2 = vertexes.get(to);
            Edge newEdge = new Edge(weight, v1, v2);
            v1.out++;
            v2.in++;
            v1.edges.add(newEdge);
            v1.nexts.add(v2);

            edges.add(newEdge);
        }
    }
}
