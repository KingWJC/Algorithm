/**
 * 最小生成树: Kruskal
 */
package com.example.system.class10;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

import com.example.utility.entity.*;

public class C05_Kcuskal {
    public static Set<Edge> kruskalMST(Graph graph) {
        // 从小的边到大的边，依次弹出，小根堆！
        PriorityQueue<Edge> queue = new PriorityQueue<>(new MyComparator());
        for (Edge e : graph.edges) {// M 条边
            queue.add(e);// O(logM)
        }
        UnionFind uFind = new UnionFind(graph.vertexes.values());
        Set<Edge> ans = new HashSet<>();
        while (!queue.isEmpty()) {
            Edge edge = queue.poll();
            if (!uFind.isSameSet(edge.from, edge.to)) {
                ans.add(edge);
                uFind.union(edge.from, edge.to);
            }
        }
        return ans;
    }

    public static class UnionFind {
        HashMap<Vertex, Vertex> parents;
        HashMap<Vertex, Integer> sizes;

        public UnionFind(Collection<Vertex> data) {
            parents = new HashMap<>();
            sizes = new HashMap<>();

            for (Vertex v : data) {
                parents.put(v, v);
                sizes.put(v, 1);
            }
        }

        private Vertex findBiggest(Vertex v) {
            Stack<Vertex> help = new Stack<>();
            while (parents.get(v) != v) {
                help.add(v);
                v = parents.get(v);
            }

            while (!help.isEmpty()) {
                parents.put(help.pop(), v);
            }
            help.clear();
            return v;
        }

        public void union(Vertex a, Vertex b) {
            Vertex v1 = findBiggest(a);
            Vertex v2 = findBiggest(b);
            if (v1 != v2) {
                int size1 = sizes.get(v1);
                int size2 = sizes.get(v2);
                Vertex bigger = size1 >= size2 ? v1 : v2;
                Vertex small = bigger == v1 ? v2 : v1;
                parents.put(small, bigger);
                sizes.put(bigger, size1 + size2);
                sizes.remove(small);
            }
        }

        public boolean isSameSet(Vertex a, Vertex b) {
            return findBiggest(a) == findBiggest(b);
        }

        public int size() {
            return sizes.size();
        }
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
