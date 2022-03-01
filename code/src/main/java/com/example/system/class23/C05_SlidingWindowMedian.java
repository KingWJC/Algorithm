/**
 * 滑动窗口中位数
 */
package com.example.system.class23;

import java.util.Arrays;

public class C05_SlidingWindowMedian {
    /**
     * 大小为k的窗口，在数组上滑动
     * 求出每次滑动的中位数。
     */
    public static double[] medianSlidingWindow(int[] nums, int k) {
        SBTreeMap<Node> treeMap = new SBTreeMap<>();
        for (int i = 0; i < k - 1; i++) {
            treeMap.add(new Node(i, nums[i]));
        }
        double[] ans = new double[nums.length - k + 1];
        int index = 0;
        for (int i = k - 1; i < nums.length; i++) {
            treeMap.add(new Node(i, nums[i]));
            if (treeMap.size() % 2 == 0) {
                Node upmid = treeMap.getIndexKey(treeMap.size() / 2 - 1);
                Node downmid = treeMap.getIndexKey(treeMap.size() / 2);
                ans[index++] = ((double) upmid.value + (double) downmid.value) / 2;
            } else {
                Node mid = treeMap.getIndexKey(treeMap.size() / 2);
                ans[index++] = (double) mid.value;
            }
            treeMap.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }

    /**
     * 处理重复值,相同值比较加入顺序号。
     */
    private static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Node o) {
            // 先比较值，相同的值再比较加入的索引
            return o.value != value ? Integer.valueOf(value).compareTo(o.value)
                    : Integer.valueOf(index).compareTo(o.index);
        }
    }

    /**
     * 只包含key的SB树节点，每个节点唯一且不包含重复值
     */
    private static class SBTNode<K extends Comparable<K>> {
        public K key;
        public SBTNode<K> left;
        public SBTNode<K> right;
        public int size;

        public SBTNode(K k) {
            key = k;
            size = 1;
        }
    }

    private static class SBTreeMap<K extends Comparable<K>> {
        private SBTNode<K> root;

        private SBTNode<K> rightRotate(SBTNode<K> cur) {
            SBTNode<K> leftNode = cur.left;
            cur.left = leftNode.right;
            leftNode.right = cur;
            leftNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return leftNode;
        }

        private SBTNode<K> leftRotate(SBTNode<K> cur) {
            SBTNode<K> rightNode = cur.right;
            cur.right = rightNode.left;
            rightNode.left = cur;
            rightNode.size = cur.size;
            cur.size = (cur.left != null ? cur.left.size : 0) + (cur.right != null ? cur.right.size : 0) + 1;
            return rightNode;
        }

        private SBTNode<K> maintain(SBTNode<K> cur) {
            if (cur == null) {
                return null;
            }

            int lSize = cur.left != null ? cur.left.size : 0;
            int llSize = cur.left != null && cur.left.left != null ? cur.left.left.size : 0;
            int lrSize = cur.left != null && cur.left.right != null ? cur.left.right.size : 0;
            int rSize = cur.right != null ? cur.right.size : 0;
            int rlSize = cur.right != null && cur.right.left != null ? cur.right.left.size : 0;
            int rrSize = cur.right != null && cur.right.right != null ? cur.right.right.size : 0;

            if (rSize < llSize) {
                cur = rightRotate(cur);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (rSize < lrSize) {
                cur.left = leftRotate(cur.left);
                cur = rightRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            } else if (lSize < rrSize) {
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur = maintain(cur);
            } else if (lSize < rlSize) {
                cur.right = rightRotate(cur.right);
                cur = leftRotate(cur);
                cur.left = maintain(cur.left);
                cur.right = maintain(cur.right);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode<K> add(SBTNode<K> cur, K key) {
            if (cur == null) {
                return new SBTNode<K>(key);
            } else {
                cur.size++;
                if (cur.key.compareTo(key) > 0) {
                    cur.left = add(cur.left, key);
                } else {
                    cur.right = add(cur.right, key);
                }
                return maintain(cur);
            }
        }

        private SBTNode<K> delete(SBTNode<K> cur, K key) {
            cur.size--;
            if (key.compareTo(cur.key) < 0) {
                cur.left = delete(cur.left, key);
            } else if (key.compareTo(cur.key) > 0) {
                cur.right = delete(cur.right, key);
            } else {
                if (cur.left == null && cur.right == null) {
                    cur = null;
                } else if (cur.left != null && cur.right == null) {
                    cur = cur.left;
                } else if (cur.left == null && cur.right != null) {
                    cur = cur.right;
                } else {
                    SBTNode<K> des = cur.right;
                    SBTNode<K> pre = null;
                    des.size--;
                    while (des.left != null) {
                        pre = des;
                        des = des.left;
                        des.size--;
                    }

                    if (pre != null) {
                        pre.left = des.right;
                        des.right = cur.right;
                    }
                    des.left = cur.left;

                    des.size = des.left.size + (cur.right != null ? cur.right.size : 0) + 1;
                    cur = des;
                }
            }
            return cur;
        }

        // kth 顺序号。
        private SBTNode<K> getIndex(SBTNode<K> cur, int kth) {
            // 左树的size+1，与kth相等，则当前节点的index和kth相等。
            if (kth == (cur.left != null ? cur.left.size : 0) + 1) {
                return cur;
            } else if (kth <= (cur.left != null ? cur.left.size : 0)) {
                return getIndex(cur.left, kth);
            } else {
                return getIndex(cur.right, kth - (cur.left != null ? cur.left.size : 0) - 1);
            }
        }

        // key左边最近的节点
        private SBTNode<K> findLastIndex(K key) {
            SBTNode<K> cur = root;
            SBTNode<K> pre = null;
            while (cur != null) {
                pre = cur;
                if (cur.key.compareTo(key) == 0) {
                    break;
                } else if (cur.key.compareTo(key) < 0) {
                    cur = cur.right;
                } else {
                    cur = cur.left;
                }
            }
            return pre;
        }

        public int size() {
            return root != null ? root.size : 0;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }

            SBTNode<K> lastNode = findLastIndex(key);
            return lastNode != null && lastNode.key.compareTo(key) == 0 ? true : false;
        }

        public void add(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SBTNode<K> lastNode = findLastIndex(key);
            if (lastNode == null || lastNode.key.compareTo(key) != 0) {
                root = add(root, key);
            }
        }

        public void remove(K key) {
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            // index+1的原因：将索引转换为size顺序。
            return getIndex(root, index + 1).key;
        }
    }

    public static void main(String[] args) {
        int[] nums = { 3, 1, 6, 4, 5, 9 };
        double[] ans = medianSlidingWindow(nums, 3);
        System.out.println(Arrays.toString(ans));
    }
}
