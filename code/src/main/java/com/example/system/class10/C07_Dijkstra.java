/**
 * Dijkstra算法：单源的最短路径。
 * 两种方法
 */
package com.example.system.class10;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import com.example.utility.entity.Edge;
import com.example.utility.entity.Vertex;

public class C07_Dijkstra {
    /**
     * 第一种方法
     */
    public static HashMap<Vertex, Integer> dijkstra1(Vertex from) {
        HashMap<Vertex, Integer> distanceMap = new HashMap<>();
        distanceMap.put(from, 0);
        // 打过对号的点
        HashSet<Vertex> selected = new HashSet<>();
        Vertex min = findMinVertex(distanceMap, selected);
        while (min != null) {
            //  原始点  ->  minNode(跳转点)   最小距离distance
            int distance = distanceMap.get(min);
            for (Edge edge : min.edges) {
                if (!distanceMap.containsKey(edge.to)) {
                    distanceMap.put(edge.to, edge.weight + distance);
                } else {
                    distanceMap.put(edge.to, Math.min(distanceMap.get(edge.to), edge.weight + distance));
                }
            }
            selected.add(min);
            min = findMinVertex(distanceMap, selected);
        }
        return distanceMap;
    }

    /**
     * 获取没被选择的最小路径的顶点
     */
    private static Vertex findMinVertex(HashMap<Vertex, Integer> distanceMap, HashSet<Vertex> selected) {
        Vertex ans = null;
        int minDistance = Integer.MAX_VALUE;
        for (Entry<Vertex, Integer> entry : distanceMap.entrySet()) {
            int distance = entry.getValue();
            Vertex node = entry.getKey();
            if (!selected.contains(node) && entry.getValue() < minDistance) {
                minDistance = distance;
                ans = node;
            }
        }
        return ans;
    }

}
