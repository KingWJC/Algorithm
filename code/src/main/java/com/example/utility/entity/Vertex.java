/**
 * 图的顶点
 */
package com.example.utility.entity;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    // 值
    public int value;
    // 入度
    public int in;
    // 出度
    public int out;
    // 从当前顶点出发找到的邻居顶点
    public List<Vertex> nexts;
    // 从当前顶点出发找到的边
    public List<Edge> edges;

    public Vertex(int value) {
        this.value = value;
        in = 0;
        out = 0;
        nexts = new ArrayList<>();
        edges = new ArrayList<>();
    }
}
