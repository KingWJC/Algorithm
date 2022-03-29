/**
 * 哈夫曼树的实现
 * 不牵扯任何byte类型的转化，转byte需要自己实现
 * 字符串为空的时候，自己处理边界吧
 */
package com.example.system.class31;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Map.Entry;

public class HuffmanTree {
    /**
     * 二叉树节点（表示一个字符出现的次数）
     */
    private class Node {
        public int count;
        public Node left;
        public Node right;

        public Node(int c) {
            count = c;
        }
    }

    /**
     * Node用count属性从小到大排序。
     */
    private class NodeCompare implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.count - o2.count;
        }
    }

    /**
     * 根据文章str, 生成词频统计表
     */
    public HashMap<Character, Integer> getCountMap(String str) {
        HashMap<Character, Integer> ans = new HashMap<>();
        char[] c = str.toCharArray();
        for (char v : c) {
            if (!ans.containsKey(v)) {
                ans.put(v, 1);
            } else {
                ans.put(v, ans.get(v) + 1);
            }
        }
        return ans;
    }

    /**
     * 根据词频表countMap，生成哈夫曼编码表
     */
    public HashMap<Character, String> getHuffmanForm(HashMap<Character, Integer> countMap) {
        HashMap<Character, String> ans = new HashMap<>();

        // 整篇文章都是同一个字符，词频表只有一条数据。需要特殊处理，编码为0
        if (countMap.size() == 1) {
            for (char key : countMap.keySet()) {
                ans.put(key, "0");
            }
            return ans;
        }

        // 存储节点Node和它代表的字符,就是二叉树的叶子节点
        HashMap<Node, Character> nodes = new HashMap<>();
        // 小根堆
        PriorityQueue<Node> heap = new PriorityQueue<>(new NodeCompare());
        for (Entry<Character, Integer> entry : countMap.entrySet()) {
            Node cur = new Node(entry.getValue());
            char c = entry.getKey();
            nodes.put(cur, c);
            heap.add(cur);
        }

        // 从下往上组装二叉树，节点都二叉树的叶子节点。
        while (heap.size() != 1) {
            Node a = heap.poll();
            Node b = heap.poll();
            Node h = new Node(a.count + b.count);
            h.left = a;
            h.right = b;
            heap.add(h);
        }
        Node head = heap.poll();
        fillForm(head, "", nodes, ans);
        return ans;
    }

    /**
     * 递归遍历二叉树，直到叶子节点。
     */
    private void fillForm(Node head, String pre, HashMap<Node, Character> nodes, HashMap<Character, String> ans) {
        if (nodes.containsKey(head)) {
            ans.put(nodes.get(head), pre);
        } else {
            fillForm(head.left, pre + "0", nodes, ans);
            fillForm(head.right, pre + "1", nodes, ans);
        }
    }

    /**
     * 原始字符串str，根据哈夫曼编码表，转译成哈夫曼编码返回
     */
    public String huffmanEncode(String str, HashMap<Character, String> huffmanForm) {
        char[] s = str.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c : s) {
            builder.append(huffmanForm.get(c));
        }
        return builder.toString();
    }

    /**
     * 哈夫曼编码huffmanEncode，根据哈夫曼编码表，还原成原始字符串
     */
    public String huffmanDecode(String huffmanEncode, HashMap<Character, String> huffmanForm) {
        TrieNode root = createTrie(huffmanForm);
        TrieNode cur = root;
        char[] encode = huffmanEncode.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < encode.length; i++) {
            int index = encode[i] == '0' ? 0 : 1;
            cur = cur.nexts[index];
            // 前缀树的结束位置
            if (cur.nexts[0] == null && cur.nexts[1] == null) {
                builder.append(cur.value);
                cur = root;
            }
        }
        return builder.toString();
    }

    /**
     * 前缀树节点
     */
    private class TrieNode {
        public char value;
        public TrieNode[] nexts;

        public TrieNode() {
            value = 0;
            // 只有0和1两个选择。
            nexts = new TrieNode[2];
        }
    }

    /**
     * 根据哈夫曼编码表，生成前缀树
     */
    private TrieNode createTrie(HashMap<Character, String> huffmanForm) {
        TrieNode root = new TrieNode();
        for (char key : huffmanForm.keySet()) {
            char[] path = huffmanForm.get(key).toCharArray();
            TrieNode cur = root;
            for (int i = 0; i < path.length; i++) {
                int index = path[i] == '0' ? 0 : 1;
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new TrieNode();
                }
                cur = cur.nexts[index];
            }
            // 路径结尾的节点，就是代表的字符。
            cur.value = key;
        }
        return root;
    }
}