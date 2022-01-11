/**
 * 岛屿数量: 二维数组matrix初始都是'0'，动态加1，动态初始化并查集，返回每次加入后的岛数量
 */
package com.example.system.class09;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import apple.laf.JRSUIConstants.Size;

public class C03_NumberOfIslandsII {
    /**
     * 计算每次位置增加后岛屿数量
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

    public static List<Integer> numberOfIsland2(int r, int c, int[][] positions) {

    }

    /**
     * 使用组合字符串表示二维位置。
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

        private String find(String index)
        {
            
        }
    }
}
