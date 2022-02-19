/**
 * AC自动机：查找到的敏感词的个数。
 */
package com.example.system.class20;

import java.util.LinkedList;
import java.util.Queue;

public class C03_AC1 {
    /**
     * 前缀树
     */
    private static class Node {
        // 有多少个字符串以该节点结束。
        int end;
        // 失配指针
        Node fail;
        // 子节点集合,索引代表字母
        Node[] nexts;

        public Node() {
            end = 0;
            fail = null;
            nexts = new Node[26];
        }
    }

    private static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        /**
         * 插入敏感词，生成前缀树
         */
        public void insert(String word) {
            char[] words = word.toCharArray();
            Node cur = root;
            int index = 0;
            for (int i = 0; i < words.length; i++) {
                index = words[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end++;
        }

        /**
         * 建立fail指针
         */
        public void build() {
            // bfs宽度优先遍历用队列
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            // 遍历中的当前节点
            Node cur = null;
            // 当前节点的失配指针
            Node failNode = null;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {// 下级所有的路
                    if (cur.nexts[i] != null) {// 该路下有子节点
                        cur.nexts[i].fail = root;// 初始时先设置一个值
                        failNode = cur.fail;
                        while (failNode != null) {// cur不是要节点
                            if (failNode.nexts[i] != null) {
                                cur.nexts[i].fail = failNode.nexts[i];
                                break;
                            }
                            failNode = failNode.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }

            }
        }

        /**
         * 返回匹配多少个敏感词
         */
        public int containNum(String content) {
            int ans = 0;
            Node cur = root;
            // fail看一圈
            Node follow = null;
            int index = 0;
            char[] str = content.toCharArray();
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';
                // 若字母匹配失败，跳到fail指针指向的节点
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }

                cur = cur.nexts[index] != null ? cur.nexts[index] : root;

                // 查一遍fail，试图找到其他被当前敏感词包含的敏感词
                follow = cur;
                while (follow != null) {
                    // 敏感词已统计过
                    if (follow.end == -1) {
                        break;
                    }

                    // 统计到结尾的敏感词
                    if (follow.end > 0) {
                        ans += follow.end;
                        follow.end = -1;
                    }

                    follow = follow.fail;
                }
            }

            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation ac=new ACAutomation();
        ac.insert("aab");
        ac.insert("bbc");
        ac.insert("ab");
        ac.build();
        System.out.println(ac.containNum("abbc"));
    }
}
