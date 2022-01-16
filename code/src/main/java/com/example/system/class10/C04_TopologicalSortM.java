/**
 * 拓朴排序: 针对给定的有向图找到任意一种拓扑排序的顺序.
 * https://www.lintcode.com/problem/127/
 * 3种解法
 */
package com.example.system.class10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class C04_TopologicalSortM {
    /**
     * 给定的有向图类型
     */
    public static class DirectedGraphNode {
        int label;
        List<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<>();
        }
    }

    /**
     * 第一种：宽度优先遍历
     */
    public static ArrayList<DirectedGraphNode> topSortBFS(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Integer> inMap = new HashMap<>();
        // HashMap必须先赋值，否则Integer返回null，无法参与运算。
        for (DirectedGraphNode cur : graph) {
            inMap.put(cur, 0);
        }
        for (int i = 0; i < graph.size(); i++) {
            for (DirectedGraphNode d : graph.get(i).neighbors) {
                inMap.put(d, inMap.get(d) + 1);
            }
        }

        Queue<DirectedGraphNode> zeroQueue = new LinkedList<>();
        inMap.forEach((key, value) -> {
            if (value == 0) {
                zeroQueue.add(key);
            }
        });

        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        while (!zeroQueue.isEmpty()) {
            DirectedGraphNode cur = zeroQueue.poll();
            ans.add(cur);
            for (DirectedGraphNode n : cur.neighbors) {
                inMap.put(n, inMap.get(n) - 1);
                if (inMap.get(n) == 0) {
                    zeroQueue.offer(n);
                }
            }
        }

        return ans;
    }

    /**
     * 第二种：深度优先遍历(顶点深度越深，拓朴序越小)
     */
    public static ArrayList<DirectedGraphNode> topSortDFS1(ArrayList<DirectedGraphNode> graph) {
        // 缓存每个节点的深度，节省递归次数
        HashMap<DirectedGraphNode, RecordDeep> order = new HashMap<>();
        List<RecordDeep> records = new ArrayList<>();
        for (DirectedGraphNode cur : graph) {
            records.add(process(cur, order));
        }

        records.sort(new DeepComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (RecordDeep r : records) {
            ans.add(r.node);
        }
        return ans;
    }

    /**
     * 统计每个节点的最大深度
     */
    public static class RecordDeep {
        DirectedGraphNode node;
        int deep;

        public RecordDeep(DirectedGraphNode node, int deep) {
            this.node = node;
            this.deep = deep;
        }
    }

    private static RecordDeep process(DirectedGraphNode node, HashMap<DirectedGraphNode, RecordDeep> order) {
        // 直接返回缓存的数据
        if (order.containsKey(node)) {
            return order.get(node);
        }

        int deep = 0;
        for (DirectedGraphNode next : node.neighbors) {
            deep = Math.max(deep, process(next, order).deep);
        }
        RecordDeep ans = new RecordDeep(node, deep + 1);
        order.put(node, ans);
        return ans;
    }

    /**
     * 按深度排序
     */
    public static class DeepComparator implements Comparator<RecordDeep> {
        @Override
        public int compare(RecordDeep o1, RecordDeep o2) {
            return o2.deep - o1.deep;
        }
    }

    /**
     * 第三种：深度优先遍历(点个数越多，拓朴序越小)
     */
    public static ArrayList<DirectedGraphNode> topSortDFS2(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, RecordCount> order = new HashMap<>();
        List<RecordCount> records = new ArrayList<>();
        for (DirectedGraphNode cur : graph) {
            records.add(processCount(cur, order));
        }
        records.sort(new CountComparator());

        ArrayList<DirectedGraphNode> ans = new ArrayList<>();
        for (RecordCount r : records) {
            ans.add(r.node);
        }
        return ans;
    }

    /**
     * 统计每个节点之后的所有点数
     */
    public static class RecordCount {
        DirectedGraphNode node;
        long count;

        public RecordCount(DirectedGraphNode cur, long count) {
            this.node = cur;
            this.count = count;
        }
    }

    private static RecordCount processCount(DirectedGraphNode cur, HashMap<DirectedGraphNode, RecordCount> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }

        long count = 1;
        for (DirectedGraphNode next : cur.neighbors) {
            count += processCount(next, order).count;
        }
        RecordCount ans = new RecordCount(cur, count);
        order.put(cur, ans);
        return ans;
    }

    /**
     * 按顶点数量排序
     */
    public static class CountComparator implements Comparator<RecordCount> {
        @Override
        public int compare(RecordCount o1, RecordCount o2) {
            if (o1.count == o2.count)
                return 0;
            return o2.count > o1.count ? 1 : -1;
        }
    }

    public static void main(String[] args) {
        HashMap<DirectedGraphNode, Integer> inMap = new HashMap<>();
        System.out.println(inMap.get(new DirectedGraphNode(1)));
    }
}
