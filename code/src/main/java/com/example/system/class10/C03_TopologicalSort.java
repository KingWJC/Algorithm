/**
 * 拓朴排序: 实现步骤
 */
package com.example.system.class10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.example.utility.entity.Graph;
import com.example.utility.entity.Vertex;

public class C03_TopologicalSort {
    /**
     * 修改图结构中顶点的入度
     */
    public static List<Vertex> sortedTopology(Graph graph) {
        // 顶点的入度为0的队列
        Queue<Vertex> zeroQueue = new LinkedList<>();
        for (Vertex v : graph.vertexes.values()) {
            if (v.in == 0) {
                zeroQueue.add(v);
            }
        }

        List<Vertex> ans = new ArrayList<>();
        while (!zeroQueue.isEmpty()) {
            Vertex cur = zeroQueue.poll();
            for (Vertex v : cur.nexts) {
                v.in--;
                if (v.in == 0) {
                    zeroQueue.add(v);
                }
            }
            ans.add(cur);
        }
        return ans;
    }

    /**
     * 不修改图结构的数据，使用HashMap存储入度
     */
    public static List<Vertex> sortedTopology1(Graph graph) {
        HashMap<Vertex, Integer> inMap = new HashMap<>();
        Queue<Vertex> zeroQueue = new LinkedList<>();
        for (Vertex node : graph.vertexes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroQueue.add(node);
            }
        }

        List<Vertex> ans = new ArrayList<Vertex>();
        while (!zeroQueue.isEmpty()) {
            Vertex node = zeroQueue.poll();
            ans.add(node);
            for (Vertex next : node.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroQueue.add(next);
                }
            }
        }

        return ans;
    }

    public static void main(String[] args) {
        // 课程表的拓朴序
        int[][] matrix = { { 5, 1, 8 }, { 4, 1, 3 }, { 9, 2, 3 }, { 7, 2, 5 }, { 0, 8, 9 }, { 2, 3, 4 }, { 4, 5, 4 },
                { 9, 5, 6 }, { 7, 9, 7 }, { 0, 4, 7 }, { 2, 4, 6 } };
        Graph graph = new Graph(matrix);
        List<Vertex> result = sortedTopology(graph);
        for (Vertex v : result) {
            System.out.print(v.value + ",");
        }
    }
}
