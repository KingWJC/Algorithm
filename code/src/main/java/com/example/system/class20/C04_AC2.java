/**
 * AC自动机：查找到的敏感词集合
 */
package com.example.system.class20;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class C04_AC2 {
    /**
     * 前缀树的节点
     */
    private static class Node {
        Node fail;
        Node[] nexts;
        // 如果end不为空，表示这个点是某个敏感词字符串的结尾，end的值就是这个敏感词
        String end;
        // 表示这个字符串之前有没有匹配，只有在上面的end变量不为空的时候，endUse才有意义
        boolean endUse;

        public Node() {
            endUse = false;
            nexts = new Node[26];
            fail = null;
            end = null;
        }
    }

    private static class ACAutomation {
        private Node root;

        public ACAutomation() {
            root = new Node();
        }

        public void insert(String word) {
            char[] chars = word.toCharArray();
            int index = 0;
            Node cur = root;
            for (int i = 0; i < chars.length; i++) {
                index = chars[i] - 'a';
                if (cur.nexts[index] == null) {
                    cur.nexts[index] = new Node();
                }
                cur = cur.nexts[index];
            }
            cur.end = word;
        }

        public void build() {
            Queue<Node> queue = new LinkedList<>();
            queue.add(root);
            Node cur = null;
            Node fnode = null;
            while (!queue.isEmpty()) {
                cur = queue.poll();
                for (int i = 0; i < 26; i++) {// 所有的路,子节点
                    // cur -> 父亲的i号儿子，必须把i号儿子的fail指针设置好！
                    if (cur.nexts[i] != null) {
                        cur.nexts[i].fail = root;
                        fnode = cur.fail;
                        // 当前节点的失配指针，其指向的节点的子节点中是否有同样的字母。
                        while (fnode != null) {
                            if (fnode.nexts[i] != null) {
                                cur.nexts[i].fail = fnode.nexts[i];
                                break;
                            }
                            fnode = fnode.fail;
                        }
                        queue.add(cur.nexts[i]);
                    }
                }
            }
        }

        public List<String> containWords(String context) {
            char[] str = context.toCharArray();
            Node cur = root;
            Node follow = null;
            int index = 0;
            List<String> ans = new ArrayList<>();
            for (int i = 0; i < str.length; i++) {
                index = str[i] - 'a';

                // 四种情况：
                // cur==root && cur.nexts[index] == null 跳出到下一个字符
                // cur==root && cur.nexts[index] != null
                // cur!=root && cur.nexts[index] == null 
                // cur!=root && cur.nexts[index] != null

                // 如果当前字符在这条路上没配出来，就随着fail方向走向下条路径
                while (cur.nexts[index] == null && cur != root) {
                    cur = cur.fail;
                }
                // 继续匹配的节点，为root时重新开始匹配。
                cur = cur.nexts[index] != null ? cur.nexts[index] : root;
                // 判断当前节点和其fail指针节点，是否有匹配的敏感词。
                follow = cur;
                while (follow != root) {
                    if (follow.endUse) {
                        break;
                    }
                    // 不同的需求，在这一段之间修改
                    if (follow.end != null) {
                        ans.add(follow.end);
                        follow.endUse = true;
                    }
                    // 不同的需求，在这一段之间修改
                    follow = follow.fail;
                }
            }
            return ans;
        }
    }

    public static void main(String[] args) {
        ACAutomation ac = new ACAutomation();
        ac.insert("dhe");
        ac.insert("he");
        ac.insert("abcdheks");
        // 设置fail指针
        ac.build();

        List<String> contains = ac.containWords("abcdhekskdjfafhasldkflskdjhwqaeruv");
        for (String word : contains) {
            System.out.println(word);
        }
    }
}
