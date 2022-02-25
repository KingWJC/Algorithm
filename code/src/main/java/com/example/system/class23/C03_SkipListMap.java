/**
 * 跳表的实现：多级链表（链表加索引链表）
 */
package com.example.system.class23;

import java.util.ArrayList;

public class C03_SkipListMap {
    /**
     * 跳表节点
     */
    public static class SkipListNode<K extends Comparable<K>, V> {
        public K key;
        public V val;
        // 层级节点
        public ArrayList<SkipListNode<K, V>> nextNodes;

        public SkipListNode(K k, V v) {
            key = k;
            val = v;
            nextNodes = new ArrayList<>();
        }

        /**
         * 往右遍历到的null或比当前节点key小，遍历结束。（向下层跳）
         */
        public boolean isKeyLess(K otherK) {
            return otherK != null && (key == null || key.compareTo(otherK) < 0);
        }

        public boolean isKeyEqual(K otherK) {
            return (key == null && otherK == null) || (key != null && otherK != null && key.compareTo(otherK) == 0);
        }
    }

    /**
     * 跳表
     */
    public static class SkipListMap<K extends Comparable<K>, V> {
        // 随机函数，维护平衡性 < 0.5 继续做，>=0.5 停
        private static final double PROBABILITY = 0.5;
        private SkipListNode<K, V> head;
        private int size;
        private int maxLevel;

        public SkipListMap() {
            head = new SkipListNode<K, V>(null, null);
            head.nextNodes.add(null);
            size = 0;
            maxLevel = 0;
        }

        /**
         * 在level层，从当前节点cur，往后找到小于key的最后一个节点并返回
         */
        private SkipListNode<K, V> mostRightLessNodeInLevel(K key, SkipListNode<K, V> cur, int level) {
            SkipListNode<K, V> next = cur.nextNodes.get(level);
            // cur不会为null，因为为空就到了链表结尾，遍历结束。
            while (next != null && next.isKeyLess(key)) {
                cur = next;
                next = cur.nextNodes.get(level);
            }
            return cur;
        }

        /**
         * 从最高层开始，一路找下去，
         * 最终，找到第0层的<key的最右的节点
         */
        private SkipListNode<K, V> mostRightLessNodeInTree(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> cur = head;
            int level = maxLevel;
            // 从上层跳下层
            while (level >= 0) {
                cur = mostRightLessNodeInLevel(key, cur, level--);
            }
            return cur;
        }

        public int size() {
            return size;
        }

        public boolean containKey(K key) {
            if (key == null) {
                return false;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key);
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.val : null;
        }

        /**
         * 新增、改value
         */
        public void put(K key, V value) {
            if (key == null) {
                return;
            }

            // 找到最底层0层上，最右一个< key 的Node
            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> find = less.nextNodes.get(0);
            if (find != null && find.isKeyEqual(key)) {
                find.val = value;
            } else {
                size++;
                // 计算索引随机层数
                int newNodeLevel = 0;
                while (Math.random() < PROBABILITY) {
                    newNodeLevel++;
                }
                // 头节点增加新层节点
                while (newNodeLevel > maxLevel) {
                    head.nextNodes.add(null);
                    maxLevel++;
                }
                // 初始化新节点
                SkipListNode<K, V> newNode = new SkipListNode<K, V>(key, value);
                for (int i = 0; i <= newNodeLevel; i++) {
                    // 下级节点预设为null
                    newNode.nextNodes.add(null);
                }

                SkipListNode<K, V> cur = head;
                while (newNodeLevel >= 0) {
                    // 获取要增加节点的前驱节点
                    cur = mostRightLessNodeInLevel(key, cur, newNodeLevel);
                    newNode.nextNodes.set(newNodeLevel, cur.nextNodes.get(newNodeLevel));
                    cur.nextNodes.set(newNodeLevel, newNode);
                    newNodeLevel--;
                }
            }
        }

        public void remove(K key) {
            if (containKey(key)) {
                size--;

                int level = maxLevel;
                SkipListNode<K, V> cur = head;
                while (level >= 0) {
                    cur = mostRightLessNodeInLevel(key, cur, level);
                    SkipListNode<K, V> next = cur.nextNodes.get(level);
                    // 在这一层中，pre下一个就是key,或者是>要删除key
                    if (next != null && next.isKeyEqual(key)) {
                        cur.nextNodes.set(level, next.nextNodes.get(level));
                    }
                    // 在level层只有一个节点了，就是默认节点head,消减层数
                    if (level != 0 && cur == head && cur.nextNodes.get(level) == null) {
                        head.nextNodes.remove(level);
                        maxLevel--;
                    }
                    level--;
                }
            }
        }

        public K firstKey() {
            return head.nextNodes.get(0).key;
        }

        public K lastKey() {
            int level = maxLevel;
            SkipListNode<K, V> cur = head;
            while (level >= 0) {
                SkipListNode<K, V> next = cur.nextNodes.get(level);
                while (next != null) {
                    cur = next;
                    next = cur.nextNodes.get(level);
                }
                level--;
            }
            return cur.key;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null && next.isKeyEqual(key) ? next.key : less.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }

            SkipListNode<K, V> less = mostRightLessNodeInTree(key);
            SkipListNode<K, V> next = less.nextNodes.get(0);
            return next != null ? next.key : null;
        }
    }

    public static void main(String[] args) {
        SkipListMap<String, String> skipList = new SkipListMap<>();
        printAll(skipList);
        skipList.put("A", "10");
        printAll(skipList);
        skipList.remove("A");
        printAll(skipList);

        System.out.println("======================");
		skipList.put("E", "E");
		skipList.put("B", "B");
		skipList.put("A", "A");
		skipList.put("F", "F");
		skipList.put("C", "C");
		skipList.put("D", "D");
        printAll(skipList);

        System.out.println("======================");
        System.out.println(skipList.containKey("B"));
		System.out.println(skipList.containKey("Z"));
		System.out.println(skipList.firstKey());
		System.out.println(skipList.lastKey());
		System.out.println(skipList.floorKey("D"));
		System.out.println(skipList.ceilingKey("D"));

        System.out.println("======================");
        skipList.remove("D");
		printAll(skipList);
		System.out.println(skipList.floorKey("D"));
		System.out.println(skipList.ceilingKey("D"));
    }

    private static void printAll(SkipListMap<String, String> skipList) {
        for (int i = skipList.maxLevel; i >= 0; i--) {
            System.out.println("level " + i + " ");
            SkipListNode<String, String> cur = skipList.head.nextNodes.get(i);
            while (cur != null) {
                System.out.print("(" + cur.key + "," + cur.val + ")");
                cur = cur.nextNodes.get(i);
            }
            System.out.println();
        }
    }
}
