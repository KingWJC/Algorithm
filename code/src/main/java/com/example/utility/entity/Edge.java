/**
 * 图的边
 */
package com.example.utility.entity;

public class Edge {
    // 权重
    public int weight;
    // 起点
    public Vertex from;
    // 终点
    public Vertex to;

    public Edge(int weight, Vertex from, Vertex to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }
}
