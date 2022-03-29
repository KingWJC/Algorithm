/**
 * Dinic算法的实现
 */
package com.example.system.class31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Dinic {
    /**
     * 无向图的边
     */
    private class Edge {
        private int from;
        public int to;
        // 承载量
        public int available;

        public Edge(int from, int to, int available) {
            this.from = from;
            this.to = to;
            this.available = available;
        }
    }

    private int N;
    // 每个点的每个边的索引。
    private ArrayList<ArrayList<Integer>> nexts;
    private ArrayList<Edge> edges;
    // 高度数组
    private int[] height;
    // 支路数组（当前的支路是那个边）
    private int[] cur;

    public Dinic(int nums) {
        N = nums + 1;
        nexts = new ArrayList<>();
        for (int i = 0; i <= N; i++) {
            nexts.add(new ArrayList<>());
        }
        edges = new ArrayList<>();
        height = new int[N];
        cur = new int[N];
    }

    /**
     * 增加边
     */
    public void addEdge(int from, int to, int v) {
        int m = edges.size();
        edges.add(new Edge(from, to, v));
        nexts.get(from).add(m);
        // 增加反向边
        edges.add(new Edge(to, from, 0));
        nexts.get(to).add(m + 1);
    }

    /**
     * 从start到end的最大网络流
     */
    public int maxFlow(int start, int end) {
        int flow = 0;
        while (bfs(start, end)) {
            Arrays.fill(cur, 0);
            flow += dfs(start, end, Integer.MAX_VALUE);
            // bfs每次路线重新计算高度。
            Arrays.fill(height, 0);
        }
        return flow;
    }

    /**
     * 宽度优先遍历，判断是否有可用路径。
     * bfs的start永远是第一层，height[start]=0
     */
    private boolean bfs(int start, int end) {
        LinkedList<Integer> queue = new LinkedList<>();
        queue.addFirst(start);
        // 队列中，路线已经走过的边，不需要重复经过。
        boolean[] visited = new boolean[N];
        visited[start] = true;
        while (!queue.isEmpty()) {
            int parent = queue.pollLast();
            for (int i = 0; i < nexts.get(parent).size(); i++) {
                Edge e = edges.get(nexts.get(parent).get(i));
                int to = e.to;
                if (!visited[to] && e.available > 0) {
                    visited[to] = true;
                    height[to] = height[parent] + 1;
                    if (to == end) {
                        break;
                    }
                    queue.addFirst(to);
                }
            }
        }
        return visited[end];
    }

    /**
     * 深度优先遍历，递归
     * start可变参数，end是固定参数,v是任务流量
     * 收集到的流，作为结果返回，ans <= v
     */
    private int dfs(int start, int end, int v) {
        if (start == end || v == 0) {
            return v;
        }

        int flow = 0;
        // start点从哪条边开始试 -> cur[start]，初始都是0.
        for (; cur[start] < nexts.get(start).size(); cur[start]++) {
            int edgeIndex = nexts.get(start).get(cur[start]);
            Edge e = edges.get(edgeIndex);
            // 获取反向边，0的反向是1，反向1的原始是0.
            Edge o = edges.get(edgeIndex ^ 1);
            if (height[e.to] == height[start] + 1) {
                int f = dfs(e.to, end, Math.min(e.available, v));
                // 之后步骤收集到的流。
                if (f != 0) {
                    e.available -= f;
                    o.available += f;
                    flow += f;
                    v -= f;
                    if (v <= 0) {
                        break;
                    }
                }
            }
        }
        return flow;
    }
}
