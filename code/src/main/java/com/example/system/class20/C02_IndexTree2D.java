/**
 * indexTree：二维范围
 * 测试链接(付费)：https://leetcode.com/problems/range-sum-query-2d-mutable
 */
package com.example.system.class20;

public class C02_IndexTree2D {
    public static class IndexTree2D {
        // 索引树
        private int[][] help;
        // 存储原始值和更新的值，用于计算add增加的值是多少
        private int[][] nums;
        private int N;
        private int M;

        public IndexTree2D(int[][] matrix) {
            if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
                return;
            }

            N = matrix.length;
            M = matrix[0].length;
            help = new int[N + 1][M + 1];
            nums = new int[N][M];

            // 初始化索引树和数组
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    update(i, j, matrix[i][j]);
                }
            }
        }

        public void update(int row, int col, int value) {
            if (N == 0 || M == 0) {
                return;
            }

            int addVal = value - nums[row][col];
            nums[row][col] = value;
            for (int i = row + 1; i <= N; i += i & -i) {
                for (int j = col + 1; j <= M; j += j & -j) {
                    help[i][j] += addVal;
                }
            }
        }

        public int sum(int row, int col) {
            if (N == 0 || M == 0) {
                return 0;
            }

            int sum = 0;
            for (int i = row + 1; i > 0; i -= i & -i) {
                for (int j = col + 1; j > 0; j -= j & -j) {
                    sum += help[i][j];
                }
            }
            return sum;
        }

        /**
         * row-1,col-1
         */
        public int sumRegion(int row1, int col1, int row2, int col2) {
            if (N == 0 || M == 0) {
                return 0;
            }
            // 减去的范围中多了边界。
            // return sum(row2, col2) - sum(row1, col2) - sum(row2, col1) + sum(row1, col1);
            return sum(row2, col2) - sum(row1 - 1, col2) - sum(row2, col1 - 1) + sum(row1 - 1, col1 - 1);
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 1, 1 }, { 1, 1, 1 }, { 1, 1, 1 } };
        IndexTree2D tree = new IndexTree2D(matrix);
        tree.update(2, 2, 8);
        System.out.println(tree.sum(2, 2));
        System.out.println(tree.sumRegion(1, 1, 2, 2));
    }
}
