/**
 * 朋友圈
 */
package com.example.system.class09;

import java.util.Arrays;

public class C01_FriendCircles {
    public static int findCircleNum(int[][] M) {
        int len = M.length;
        // {0} {1} {2} ..... {N-1}
        UnionFind unionFind = new UnionFind(len);
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (M[i][j] == 1) {
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.size();
    }

    public static class UnionFind {
        // 每个元素节点的父亲节点
        private int[] parents;
        // 每个集合代表元素的长度：size[i] = k 如果i是代表节点，size[i]才有意义，否则无意义
        private int[] sizes;
        // 一共有多少个集合
        private int sets;
        // 辅助结构，代替栈
        private int[] help;

        public UnionFind(int N) {
            parents = new int[N];
            sizes = new int[N];
            help = new int[N];
            sets = N;
            for (int i = 0; i < N; i++) {
                parents[i] = i;
                sizes[i] = 1;
            }
        }

        public void union(int a, int b) {
            int p1 = find(a);
            int p2 = find(b);
            if (p1 != p2) {
                int size1 = sizes[p1];
                int size2 = sizes[p2];
                if (size1 >= size2) {
                    parents[p2] = p1;
                    sizes[p1] = size1 + size2;
                } else {
                    parents[p1] = p2;
                    sizes[p2] = size1 + size2;
                }
                sets--;
            }
        }

        /**
         * 从i开始一直往上，往上到不能再往上，找到代表节点，返回
         */
        private int find(int i) {
            int index = 0;
            while (parents[i] != i) {
                // 存储不是代表节点的节点。
                help[index++] = i;
                i = parents[i];
            }
            // 路径压缩
            for (index--; index >= 0; index--) {
                parents[help[index]] = i;
            }

            return i;
        }

        public int size() {
            return sets;
        }
    }

    public static void main(String[] args) {
        int[][] M = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = i; j < 5; j++) {
                if (i == j) {
                    // 自己认识自己
                    M[i][j] = 1;
                } else {
                    int val = Math.random() < 0.25 ? 1 : 0;
                    M[i][j] = val;
                    M[j][i] = val;
                }
            }
        }

        for (int i = 0; i < M.length; i++) {
            System.out.println(Arrays.toString(M[i]));
        }

        System.out.println(findCircleNum(M));
    }
}
