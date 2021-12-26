/**
 * 前缀树
 */
package com.example.system.class05;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class C01_TrierTree {
    /**
     * 前缀树节点类型为数组.
     */
    public static class ArrayNode {
        public int pass;
        public int end;
        // 数组索引表示小写字母
        // 0 a
        // 1 b
        // 2 c
        // .. ..
        // 25 z
        // nexts[i] == null i方向的路不存在
        // nexts[i] != null i方向的路存在
        public ArrayNode[] nexts;

        public ArrayNode() {
            pass = 0;
            end = 0;
            // 字符串限制为26个小写字母.
            nexts = new ArrayNode[26];
        }
    }

    /**
     * 前缀树(固定数组实现前缀树)
     */
    public static class TrieTreeArray {
        ArrayNode root;

        public TrieTreeArray() {
            root = new ArrayNode();
        }

        /**
         * 插入字符串,只要形成路径, 节点pass+1.
         */
        public void insert(String word) {
            if (word == null)
                return;

            char[] data = word.toCharArray();
            ArrayNode curNode = root;
            curNode.pass++;
            int index = 0;
            // 从左往右遍历字符
            for (int i = 0; i < data.length; i++) {
                // 由字符，对应成走向哪条路
                index = data[i] - 'a';
                if (curNode.nexts[index] == null) {
                    curNode.nexts[index] = new ArrayNode();
                }

                curNode = curNode.nexts[index];
                curNode.pass++;
            }
            curNode.end++;
        }

        /**
         * 删除(多个和一个的情况)
         */
        public void delete(String word) {
            if (search(word) > 0) {
                char[] data = word.toCharArray();
                ArrayNode curNode = root;
                curNode.pass--;
                int index = 0;
                for (int i = 0; i < data.length; i++) {
                    index = data[i] - 'a';

                    // 删除后为pass为0,则置为空.
                    if (--curNode.nexts[index].pass == 0) {
                        curNode.nexts[index] = null;
                        return;
                    }
                    curNode = curNode.nexts[index];
                }
                curNode.end--;
            }
        }

        /**
         * 查询word这个单词之前加入过几次
         */
        public int search(String word) {
            if (word == null)
                return 0;

            char[] data = word.toCharArray();
            ArrayNode curNode = root;
            for (int i = 0; i < data.length; i++) {
                int index = data[i] - 'a';
                if (root.nexts[index] == null) {
                    return 0;
                }
                curNode = root.nexts[index];
            }

            return curNode.end;
        }

        /**
         * 包含前缀的个数
         */
        public int prefixNumber(String pre) {
            if (pre == null)
                return 0;

            char[] data = pre.toCharArray();
            ArrayNode curNode = root;
            int index = 0;
            for (int i = 0; i < data.length; i++) {
                index = data[i] - 'a';
                if (curNode.nexts[index] == null) {
                    return 0;
                }
                curNode = curNode.nexts[index];
            }
            return curNode.pass;
        }

    }

    /**
     * 前缀树节点类型为哈希表.
     */
    public static class HashNode {
        public int pass;
        public int end;
        public Map<Integer, HashNode> nexts;

        public HashNode() {
            pass = 0;
            end = 0;
            nexts = new HashMap<>();
        }
    }

    /**
     * 前缀树(哈希表实现前缀树)
     */
    public static class TrieTreeHash {
        HashNode root;

        public TrieTreeHash() {
            root = new HashNode();
        }

        public void insert(String word) {
            if (word == null) {
                return;
            }

            char[] data = word.toCharArray();
            HashNode curNode = root;
            root.pass++;
            int index = 0;
            for (int i = 0; i < data.length; i++) {
                index = (int) data[i];
                if (!curNode.nexts.containsKey(index)) {
                    curNode.nexts.put(index, new HashNode());
                }

                curNode = curNode.nexts.get(index);
                curNode.pass++;
            }
            curNode.end++;
        }

        public void delete(String word) {
            if (search(word) > 0) {
                char[] data = word.toCharArray();
                HashNode curNode = root;
                curNode.pass--;
                int index = 0;
                for (int i = 0; i < data.length; i++) {
                    index = (int) data[i];
                    if (--curNode.nexts.get(index).pass == 0) {
                        curNode.nexts.remove(index);
                        return;
                    }
                    curNode = curNode.nexts.get(index);
                }
                curNode.end--;
            }
        }

        public int search(String word) {
            if (word == null)
                return 0;

            char[] data = word.toCharArray();
            HashNode curNode = root;
            int index = 0;
            for (int i = 0; i < data.length; i++) {
                index = (int) data[i];
                if (!curNode.nexts.containsKey(index)) {
                    return 0;
                }
                curNode = curNode.nexts.get(index);
            }
            return curNode.end;
        }

        public int prefixNumber(String pre) {
            if (pre == null)
                return 0;

            char[] data = pre.toCharArray();
            HashNode curNode = root;
            int index = 0;
            for (int i = 0; i < data.length; i++) {
                index = (int) data[i];
                if (!curNode.nexts.containsKey(index)) {
                    return 0;
                }
                curNode = curNode.nexts.get(index);
            }
            return curNode.pass;
        }
    }

    /**
     * 字符串集合(对比测试)
     */
    public static class StringDataTest {
        // 字符串和其出现次数
        private HashMap<String, Integer> box;

        public StringDataTest() {
            box = new HashMap<>();
        }

        public void insert(String word) {
            if (box.containsKey(word)) {
                box.put(word, box.get(word) + 1);
            } else {
                box.put(word, 1);
            }
        }

        public void delete(String word) {
            if (box.containsKey(word)) {
                if (box.get(word) > 1) {
                    box.put(word, box.get(word) - 1);
                } else {
                    box.remove(word);
                }
            }
        }

        public int search(String word) {
            if (!box.containsKey(word)) {
                return 0;
            } else {
                return box.get(word);
            }
        }

        public int preNumber(String pre) {
            int count = 0;
            for (String cur : box.keySet()) {
                if (cur.startsWith(pre))
                    count++;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int arrLen = 10;
        int strLen = 6;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            String[] arr = generateRandomStringArray(arrLen, strLen);
            TrieTreeArray trie1 = new TrieTreeArray();
            TrieTreeHash trie2 = new TrieTreeHash();
            StringDataTest test3 = new StringDataTest();
            for (int j = 0; j < arr.length; j++) {
                double decide = Math.random();
                if (decide < 0.25) {
                    trie1.insert(arr[j]);
                    trie2.insert(arr[j]);
                    test3.insert(arr[j]);
                } else if (decide < 0.5) {
                    trie1.delete(arr[j]);
                    trie2.delete(arr[j]);
                    test3.delete(arr[j]);
                } else if (decide < 0.75) {
                    int ans1 = trie1.search(arr[j]);
                    int ans2 = trie2.search(arr[j]);
                    int ans3 = test3.search(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println(Arrays.toString(arr));
                        System.out.println("error");
                        break;
                    }
                } else {
                    int ans1 = trie1.prefixNumber(arr[j]);
                    int ans2 = trie2.prefixNumber(arr[j]);
                    int ans3 = test3.preNumber(arr[j]);
                    if (ans1 != ans2 || ans2 != ans3) {
                        System.out.println("error");
                        break;
                    }
                }
            }
        }
    }

    public static String[] generateRandomStringArray(int arrLength, int strLength) {
        String[] ans = new String[(int) (Math.random() * strLength) + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = generateRandomString(strLength);
        }
        return ans;
    }

    public static String generateRandomString(int strLength) {
        char[] ans = new char[(int) (Math.random() * strLength) + 1];
        for (int i = 0; i < ans.length; i++) {
            int value = (int) (Math.random() * 6);
            ans[i] = (char) (97 + value);
        }
        return String.valueOf(ans);
    }
}
