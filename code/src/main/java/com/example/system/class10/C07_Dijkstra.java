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
            // 原始点 -> minNode(跳转点) 最小距离distance
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

    /**
     * 第二种:加强堆
     */
    public static HashMap<Vertex, Integer> dijkstra2(Vertex from) {
        VertexHeap heap = new VertexHeap(0);
        heap.update(from, 0);
        HashMap<Vertex, Integer> result = new HashMap<>();
        while (!heap.isEmpaty()) {
            VertexRecord record = heap.pop();
            for (Edge edge : record.node.edges) {
                heap.update(edge.to, edge.weight + record.distance);
            }
            result.put(record.node, record.distance);
        }
        return result;
    }

    public static class VertexRecord {
        int distance;
        Vertex node;

        public VertexRecord(int distance, Vertex node) {
            this.distance = distance;
            this.node = node;
        }
    }

    public static class VertexHeap {
        // 实际的堆结构
        Vertex[] heap;
        // 反向索引表
        HashMap<Vertex, Integer> indexMap;
        // key 某一个节点， value 从源节点出发到该节点的目前最小距离
        HashMap<Vertex, Integer> distanceMap;
        int size;

        public VertexHeap(int size) {
            this.size = size;
            heap = new Vertex[size];
            indexMap = new HashMap<>();
            distanceMap = new HashMap<>();
        }

        public boolean isEmpaty() {
            return size == 0;
        }

        public boolean isEntered(Vertex node) {
            return indexMap.containsKey(node);
        }

        public boolean isHeap(Vertex node) {
            return isEntered(node) && indexMap.get(node) != -1;
        }

        public VertexRecord pop() {
            if (isEmpaty()) {
                return null;
            }
            VertexRecord ans = new VertexRecord(distanceMap.get(heap[0]), heap[0]);
            swap(0, size - 1);
            indexMap.put(heap[size - 1], -1);
            distanceMap.remove(heap[size - 1]);
            
            heap[size - 1] = null;
            size--;
            heapify(0);
            return ans;
        }

        /**
         * 有一个点叫node，现在发现了一个从源节点出发到达node的距离为distance
         * 添加，更新或忽略。
         */
        public void update(Vertex node, int distance) {
            // 更新
            if (isHeap(node)) {
                distanceMap.put(node, Math.min(distance, distanceMap.get(node)));
                heapInsert(indexMap.get(node));
            }
            // 添加
            if (!isEntered(node)) {
                heap[size] = node;
                distanceMap.put(node, distance);
                indexMap.put(node, size);
                size++;
                heapInsert(size - 1);
            }
            // ignore
        }

        private void heapInsert(int index) {
            int parentIndex = (index - 1) / 2;
            while (distanceMap.get(heap[index]) < distanceMap.get(heap[parentIndex])) {
                swap(index, parentIndex);
                index = (index - 1) / 2;
                parentIndex = (index - 1) / 2;
            }
        }

        private void heapify(int index) {
            int left = index * 2 + 1;
            while (left < size) {
                int smallest = left + 1 < size && distanceMap.get(heap[left + 1]) < distanceMap.get(heap[left])
                        ? left + 1
                        : left;
                if (distanceMap.get(heap[index]) <= distanceMap.get(heap[smallest])) {
                    break;
                }
                swap(index, smallest);
                index = smallest;
                left = index * 2 + 1;
            }
        }

        private void swap(int a, int b) {
            Vertex temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;

            indexMap.put(heap[a], b);
            indexMap.put(heap[b], a);
        }
    }
}
