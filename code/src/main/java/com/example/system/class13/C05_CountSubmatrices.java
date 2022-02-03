/**
 * 全是1的子矩阵数量
 * 测试链接：https://leetcode.com/problems/count-submatrices-with-all-ones
 */
package com.example.system.class13;

public class C05_CountSubmatrices {
    /**
     * 单调栈，使用数组代替系统栈
     * 以每一行为底，生成直方图。
     */
    public static int numSubMatrix(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int N = matrix[0].length;
        int[] height = new int[N];
        int count = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < N; j++) {
                height[j] = matrix[i][j] == 0 ? 0 : height[j] + 1;
            }
            count += countFromBottom(height);
        }
        return count;
    }

    /**
     * 直方图，使用单调栈计算子矩形数组数量
     */
    private static int countFromBottom(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int count = 0;
        int[] stack = new int[height.length];
        int si = -1;
        for (int i = 0; i < height.length; i++) {
            while (si != -1 && height[stack[si]] >= height[i]) {
                int index = stack[si--];
                if (height[index] > height[i]) {
                    int leftLess = si == -1 ? -1 : stack[si];
                    int L = i - leftLess - 1;
                    int down = Math.max(height[i], leftLess == -1 ? 0 : height[leftLess]);
                    count += compute(L) * (height[index] - down);
                }
            }
            stack[++si] = i;
        }
        while (si != -1) {
            int index = stack[si--];
            int leftLess = si == -1 ? -1 : stack[si];
            int L = height.length - leftLess - 1;
            int down = leftLess == -1 ? 0 : height[leftLess];
            count += compute(L) * (height[index] - down);
        }
        return count;
    }

    private static int compute(int L) {
        return L * (L + 1) / 2;
    }
}
