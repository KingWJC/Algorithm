/**
 * 岛屿数量: 二维数组matrix初始都是'0'，动态加1，动态初始化并查集，返回每次加入后的岛数量
 */
package com.example.system.class09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class C03_NumberOfIslandsII {
    /**
     * 计算每次位置增加后岛屿数量(初始化数组)
     * 
     * @param r         行数
     * @param c         列数
     * @param positions 动态添加的位置。
     * @return
     */
    public static List<Integer> numberOfIsland1(int r, int c, int[][] positions) {
        UnionFind uFind = new UnionFind(r, c);
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            ans.add(uFind.connect(positions[i][0], positions[i][1]));
        }
        return ans;
    }

    /**
     * 区别之前的并查集:
     * 1.union 增加边界判断
     * 2. Size[i] 初始都是0，只要这个位置的数据赋为'1'，size[i] = 1. 并且union后不会将其置为0.
     * 这样就可以通过判断size[i] != 0 来得知这个位置的值是'1',
     */
    private static class UnionFind {
        int[] parents;
        int[] sizes;
        int[] help;
        int sets;
        int col;
        // 用于判断边界
        int row;

        public UnionFind(int r, int c) {
            row = r;
            col = c;
            int N = row * col;
            parents = new int[N];
            sizes = new int[N];
            help = new int[N];
            sets = 0;
        }

        private int getIdnex(int i, int j) {
            return i * col + j;
        }

        private int find(int index) {
            int hi = 0;
            while (parents[index] != index) {
                help[hi++] = index;
                index = parents[index];
            }

            for (hi--; hi >= 0; hi--) {
                parents[help[hi]] = index;
            }
            return index;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || c1 < 0 || c1 == col
                    || r2 < 0 || r2 == row || c2 < 0 || c2 == col) {
                return;
            }

            int p1 = find(getIdnex(r1, c1));
            int p2 = find(getIdnex(r2, c2));

            // 可通过sizes判断这个位置不为'1'
            if (sizes[p1] == 0 || sizes[p2] == 0) {
                return;
            }

            if (p1 != p2) {
                if (sizes[p1] >= sizes[p2]) {
                    parents[p2] = p1;
                    sizes[p1] += sizes[p2];
                } else {
                    parents[p1] = p2;
                    sizes[p2] += sizes[p1];
                }
                sets--;
            }
        }

        /**
         * 动态加1，动态初始化。
         */
        public int connect(int r, int c) {
            int p = getIdnex(r, c);
            if (sizes[p] == 0) {
                // 先建小集合，再上下左右互联。
                parents[p] = p;
                sizes[p] = 1;
                sets++;

                union(r, c, r - 1, c);
                union(r, c, r + 1, c);
                union(r, c, r, c - 1);
                union(r, c, r, c + 1);
            }
            return sets;
        }
    }

    /**
     * 如果m*n比较大，会经历很重的初始化，而k比较小
     * 优化方法：动态初始化Map。
     */
    public static List<Integer> numberOfIsland2(int[][] positions) {
        UnionFindDinamic uFind = new UnionFindDinamic();
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            ans.add(uFind.connect(positions[i][0], positions[i][1]));
        }
        return ans;
    }

    /**
     * 使用组合字符串表示二维位置。
     * 字符串比较，使用equals比较值，而不能比较引用。
     */
    private static class UnionFindDinamic {
        HashMap<String, String> parents;
        HashMap<String, Integer> sizes;
        ArrayList<String> help;
        int sets;

        public UnionFindDinamic() {
            parents = new HashMap<>();
            sizes = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        private String find(String index) {
            while (!index.equals(parents.get(index))) {
                help.add(index);
                index = parents.get(index);
            }
            for (String str : help) {
                parents.put(str, index);
            }
            help.clear();
            return index;
        }

        private void union(String s1, String s2) {
            if (parents.containsKey(s1) && parents.containsKey(s2)) {
                String p1 = find(s1);
                String p2 = find(s2);
                if (!p1.equals(p2)) {
                    int size1 = sizes.get(p1);
                    int size2 = sizes.get(p2);
                    String big = size1 >= size2 ? p1 : p2;
                    String small = big == p1 ? p2 : p1;
                    parents.put(small, big);
                    sizes.put(big, size1 + size2);
                    sets--;
                }
            }
        }

        public int connect(int r, int c) {
            String p = String.valueOf(r) + "_" + String.valueOf(c);
            if (!parents.containsKey(p)) {
                parents.put(p, p);
                sizes.put(p, 1);
                sets++;

                // 1.使用 + “” 拼接方式，本质上是先将字符串转换为StringBuffer 后在使用append ()方法，而append()方法也是使用的String.valueOf()
                // 2.使用String.valueOf(),本质上则是使用Object.toString()方法
                // String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                // String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                // String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                // String right = String.valueOf(r) + "_" + String.valueOf(c + 1);

                union(p, (r - 1) + "_" + c);
                union(p, (r + 1) + "_" + c);
                union(p, r + "_" + (c - 1));
                union(p, r + "_" + (c + 1));
            }
            return sets;
        }
    }

    public static void main(String[] args) {
        int row = 10000;
        int col = 10000;
        int[][] positions = new int[3][2];
        positions[0] = new int[] { 199, 200 };
        positions[1] = new int[] { 200, 200 };
        positions[2] = new int[] { 200, 201 };
        long start = 0;
        long end = 0;

        System.out.println("初始化数组和动态初始化Map的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("初始化数组的执行结果是:" + Arrays.toString(numberOfIsland1(row, col, positions).toArray()));
        end = System.currentTimeMillis();
        System.out.println("初始化数组的执行时间是:" + (end - start) + "ms");

        start = System.currentTimeMillis();
        System.out.println("动态初始化Map的执行结果是:" + Arrays.toString(numberOfIsland2(positions).toArray()));
        end = System.currentTimeMillis();
        System.out.println("动态初始化Map的执行时间是:" + (end - start) + "ms");
    }
}
