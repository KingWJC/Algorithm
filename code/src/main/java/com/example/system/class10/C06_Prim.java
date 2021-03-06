/**
 * 最小生成树: Prim
 */
package com.example.system.class10;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import com.example.utility.entity.Edge;
import com.example.utility.entity.Graph;
import com.example.utility.entity.Vertex;

public class C06_Prim {
    public static Set<Edge> primMST(Graph graph) {
        // 哪些点被解锁出来了
        HashSet<Vertex> vertexs = new HashSet<>();
        // 解锁的边进入小根堆
        PriorityQueue<Edge> queue = new PriorityQueue<>(new MyComparator());
        // 已解锁的边集合，过滤重复加入队列的边
        HashSet<Edge> edges = new HashSet<>();

        Set<Edge> ans = new HashSet<>();
        // for循环是应对多子树的情况
        for (Vertex node : graph.vertexes.values()) {
            // 第一次随便挑了一个点,做为开始点,并解锁
            if (!vertexs.contains(node)) {
                vertexs.add(node);
                for (Edge edge : node.edges) {
                    if (!edges.contains(edge)) {
                        edges.add(edge);
                        queue.offer(edge);
                    }
                }

                // 顶点解锁 -》边解锁 -》顶点解锁 -》边解锁 。。在所有解锁的边中选最小的边。
                while (!queue.isEmpty()) {
                    Edge edge = queue.poll();
                    Vertex to = edge.to;
                    if (!vertexs.contains(to)) {
                        // 顶点解锁
                        vertexs.add(to);
                        ans.add(edge);
                        // 边解锁
                        for (Edge nextEdge : to.edges) {
                            if (!edges.contains(nextEdge)) {
                                edges.add(nextEdge);
                                queue.offer(nextEdge);
                            }
                        }
                    }
                }

            }
        }
        return ans;
    }

    /**
     * 根据权重排序
     */
    public static class MyComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }
}
