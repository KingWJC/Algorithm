/**
 * indexTree: 范围查找和单点更新。
 */
package com.example.system.class20;

public class C01_IndexTree {
    /**
     * 暴力解：O(N)
     */
    public static class IndexArray {
        private int[] arr;

        public IndexArray(int size) {
            arr = new int[size + 1];
        }

        public void add(int index, int value) {
            arr[index] += value;
        }

        public int sum(int index) {
            int sum = 0;
            for (int i = 1; i <= index; i++) {
                sum += arr[i];
            }
            return sum;
        }
    }

    /**
     * indexTree：O(logN)
     * 下标从1开始
     */
    public static class IndexTree {
        private int[] help;
        private int N;

        public IndexTree(int size) {
            N = size + 1;
            // 0位置弃而不用！
            help = new int[N];
        }

        /**
         * index & -index : 提取出index最右侧的1出来
         * index : 0011001000
         * index & -index : 0000001000
         */
        public void add(int index, int value) {
            while (index < N) {
                help[index] += value;
                // 当前索引加上二进值的最右的1的值，就是下一个受影响的累加和位置。
                index += index & -index;
            }
        }

        /**
         * 1~index 累加和
         */
        public int sum(int index) {
            int sum = 0;
            while (index > 0) {
                sum += help[index];
                index -= index & -index;
            }
            return sum;
        }

        /**
         * L~R 累加和
         */
        public int sumRegion(int L, int R) {
            return sum(R) - sum(L - 1);
        }
    }

    public static void main(String[] args) {
        int N = 100;
        int V = 100;
        int testTimes = 100000;
        System.out.println("start test");
        IndexArray arr = new IndexArray(N);
        IndexTree tree = new IndexTree(N);
        for (int i = 0; i < testTimes; i++) {
            int index = (int) (Math.random() * N) + 1;
            if (Math.random() < 0.5) {
                int value = (int) (Math.random() * V);
                arr.add(index, value);
                tree.add(index, value);
            } else {
                if (arr.sum(index) != tree.sum(index)) {
                    System.out.println("error");
                    break;
                }
            }
        }
        System.out.println("finished");
    }
}
