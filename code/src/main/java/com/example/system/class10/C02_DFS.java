/**
 * 深度优先遍历 Depth first search
 */
package com.example.system.class10;

import java.util.HashSet;
import java.util.Stack;

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
        if (start == null || cache.contains(start)) {
            return;
        }

        for (Vertex v : start.nexts) {
            cache.add(v);
            process(v, cache);
        }
        System.out.println(start.value);
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
        
    }
}
