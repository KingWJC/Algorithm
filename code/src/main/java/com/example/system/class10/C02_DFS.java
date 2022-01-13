/**
 * 深度优先遍历 Depth first search
 */
package com.example.system.class10;

import java.util.HashSet;
import java.util.Stack;

import com.example.utility.entity.Graph;
import com.example.utility.entity.Vertex;

public class C02_DFS {
    /**
     * 递归后序遍历
     */
    public static void dfs(Vertex start) {
        HashSet<Vertex> cache = new HashSet<>();
        process(start, cache);
    }

    private static void process(Vertex start, HashSet<Vertex> cache) {
        if (start == null) {
            return;
        }

        System.out.print(start.value + ",");
        cache.add(start);
        for (Vertex v : start.nexts) {
            if (!cache.contains(v)) {
                process(v, cache);
            }
        }
    }

    /**
     * 迭代栈遍历
     */
    public static void dfsUseStack(Vertex start) {
        if (start == null) {
            return;
        }

        Stack<Vertex> stack = new Stack<>();
        HashSet<Vertex> set = new HashSet<>();
        stack.add(start);
        set.add(start);
        System.out.print(start.value + ",");
        while (!stack.isEmpty()) {
            Vertex cur = stack.pop();
            for (Vertex next : cur.nexts) {
                if (!set.contains(next)) {
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.print(next.value + ",");
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 5, 0, 5 }, { 4, 0, 1 }, { 9, 0, 2 }, { 7, 5, 3 }, { 0, 3, 4 }, { 2, 3, 2 } };
        // , { 5, 1, 0 }, { 4, 2, 0 }, { 9, 5, 0 }, { 7, 3, 5 }, { 0, 4, 3 }, { 2, 2, 3}
        // 完全有向图。
        Graph graph = new Graph(matrix);
        dfs(graph.vertexes.get(0));
        System.out.println();
        dfsUseStack(graph.vertexes.get(0));
    }
}
