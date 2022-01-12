/**
 * (广）宽度优先遍历 Breadth first search
 */
package com.example.system.class10;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import com.example.utility.entity.Vertex;

public class C01_BFS {
    /**
     * 从start出发，进行宽度优先遍历（自已实现）
     */
    public static void bfs(Vertex start) {
        if (start == null)
            return;

        Queue<Vertex> queue = new LinkedList<>();
        HashSet<Vertex> cache = new HashSet<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Vertex cur = queue.poll();
            if (cache.contains(cur)) {
                continue;
            }

            System.out.print(cur.value + ",");
            cache.add(cur);
            for (Vertex v : cur.nexts) {
                queue.add(v);
            }
        }
    }

    /**
     * 样例版本
     */
    public static void bfs2(Vertex start) {
        if (start == null)
            return;

        Queue<Vertex> queue = new LinkedList<>();
        HashSet<Vertex> cache = new HashSet<>();
        queue.add(start);
        cache.add(start);
        while (!queue.isEmpty()) {
            Vertex cur = queue.poll();
            System.out.print(cur.value + ",");
            for (Vertex v : cur.nexts) {
                if (!cache.contains(v)) {
                    queue.add(v);
                    cache.add(v);
                }
            }
        }
    }
}
