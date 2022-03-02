/**
 * 根据身高重建队列
 * 链接：https://leetcode.com/problems/queue-reconstruction-by-height/
 */
package com.example.system.class23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class C07_QueueReconstructByHeight {
    /**
     * ArrayList结构
     */
    public static int[][] reconstructQueue1(int[][] people) {
        int N = people.length;
        Unit[] units = new Unit[N];
        for (int i = 0; i < N; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, new UnitComparapor());
        ArrayList<Unit> list = new ArrayList<>();
        for (Unit u : units) {
            list.add(u.k, u);
        }

        int[][] ans = new int[N][2];
        int index = 0;
        for (Unit u : list) {
            ans[index][0] = u.h;
            ans[index][1] = u.k;
            index++;
        }
        return ans;
    }

    /**
     * 封装：身高h，前面有k个人大于等于h
     */
    private static class Unit {
        public int h;
        public int k;

        public Unit(int height, int greater) {
            h = height;
            k = greater;
        }

        @Override
        public String toString() {
            return "[" + h + "," + k + "]";
        }
    }

    /**
     * 比较器：先按h倒序，若h相同，则按k顺序。
     */
    private static class UnitComparapor implements Comparator<Unit> {
        @Override
        public int compare(Unit o1, Unit o2) {
            return o1.h != o2.h ? o2.h - o1.h : o1.k - o2.k;
        }
    }

    /**
     * SizeBlanlenceTree结构
     */
    public static int[][] reconstructQueue2(int[][] people) {
        int N = people.length;
        Unit[] units = new Unit[N];
        for (int i = 0; i < N; i++) {
            units[i] = new Unit(people[i][0], people[i][1]);
        }
        Arrays.sort(units, new UnitComparapor());
        System.out.println(Arrays.toString(units));

        SBTree tree = new SBTree();
        for (int i = 0; i < N; i++) {
            tree.insert(units[i].k, i);
        }

        LinkedList<Integer> allIndex = tree.allIndexes();
        int[][] ans = new int[N][2];
        int index = 0;
        for (Integer arri : allIndex) {
            ans[index][0] = units[arri].h;
            ans[index][1] = units[arri].k;
            index++;
        }

        return ans;
    }

    /**
     * key是时序，所以只用存值value（就是数组的索引）。
     */
    private static class SBTNode {
        public int value;
        public SBTNode l;
        public SBTNode r;
        public int size;

        public SBTNode(int v) {
            value = v;
            size = 1;
        }
    }

    /**
     * 用插入时序做key的有序表
     */
    private static class SBTree {
        private SBTNode root;

        private SBTNode rightRotate(SBTNode cur) {
            SBTNode left = cur.l;
            cur.l = left.r;
            left.r = cur;
            left.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return left;
        }

        private SBTNode leftRotate(SBTNode cur) {
            SBTNode right = cur.r;
            cur.r = right.l;
            right.l = cur;
            right.size = cur.size;
            cur.size = (cur.l != null ? cur.l.size : 0) + (cur.r != null ? cur.r.size : 0) + 1;
            return right;
        }

        private SBTNode maintain(SBTNode cur) {
            if (cur == null) {
                return null;
            }

            int lSize = cur.l != null ? cur.l.size : 0;
            int llSize = cur.l != null && cur.l.l != null ? cur.l.l.size : 0;
            int lrSize = cur.l != null && cur.l.r != null ? cur.l.r.size : 0;
            int rSize = cur.r != null ? cur.r.size : 0;
            int rlSize = cur.r != null && cur.r.l != null ? cur.r.l.size : 0;
            int rrSize = cur.r != null && cur.r.r != null ? cur.r.r.size : 0;

            if (rSize < llSize) {
                cur = rightRotate(cur);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (rSize < lrSize) {
                cur.l = leftRotate(cur.l);
                cur = rightRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            } else if (lSize < rrSize) {
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur = maintain(cur);
            } else if (lSize < rlSize) {
                cur.r = rightRotate(cur.r);
                cur = leftRotate(cur);
                cur.l = maintain(cur.l);
                cur.r = maintain(cur.r);
                cur = maintain(cur);
            }
            return cur;
        }

        private SBTNode add(SBTNode cur, int index, SBTNode val) {
            if (cur == null) {
                return val;
            }

            cur.size++;
            int leftSize = cur.l != null ? cur.l.size : 0;
            if (index < leftSize + 1) {
                cur.l = add(cur.l, index, val);
            } else {
                cur.r = add(cur.r, index - leftSize - 1, val);
            }
            return maintain(cur);
        }

        private SBTNode get(SBTNode cur, int index) {
            if (cur == null) {
                return null;
            }
            int leftSize = cur.l != null ? cur.l.size : 0;
            if (index == leftSize) {
                return cur;
            } else if (index < leftSize) {
                return get(cur.l, index);
            } else {
                return get(cur.r, index - leftSize - 1);
            }
        }

        public void insert(int index, int value) {
            SBTNode node = new SBTNode(value);
            if (root == null) {
                root = node;
            } else if (index <= root.size)
                root = add(root, index, node);
        }

        public int get(int index) {
            SBTNode node = get(root, index);
            return node.value;
        }

        public LinkedList<Integer> allIndexes() {
            LinkedList<Integer> indexes = new LinkedList<>();
            process(root, indexes);
            return indexes;
        }

        private void process(SBTNode head, LinkedList<Integer> indexes) {
            if (head == null) {
                return;
            }

            process(head.l, indexes);
            indexes.addLast(head.value);
            process(head.r, indexes);
        }
    }

    public static void main(String[] args) {
        int[][] people = { { 7, 0 }, { 4, 4 }, { 7, 1 }, { 5, 0 }, { 6, 1 }, { 5, 2 } };
        int[][] ans = reconstructQueue1(people);
        printArray(ans);
        System.out.println();
        System.out.println("==============");
        ans = reconstructQueue2(people);
        printArray(ans);
    }

    private static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(Arrays.toString(arr[i]) + ",");
        }
    }
}
