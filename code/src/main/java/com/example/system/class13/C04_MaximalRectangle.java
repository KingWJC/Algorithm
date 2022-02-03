/**
 * 全是1的最大子矩形面积
 * 测试链接：https://leetcode.com/problems/maximal-rectangle/
 */
package com.example.system.class13;

import java.util.Stack;

public class C04_MaximalRectangle {
    /**
     * 暴力解,O(N^6)
     */
    public static int getMaxArea1(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        int rol = matrix.length;
        int col = matrix[0].length;
        int max = 0;
        for (int r1 = 0; r1 < rol; r1++) {
            for (int c1 = 0; c1 < col; c1++) {
                for (int r2 = 0; r2 < rol; r2++) {
                    for (int c2 = 0; c2 < col; c2++) {
                        if (isFullofOne(r1, c1, r2, c2, matrix)) {
                            // 长和宽计算，需要加1
                            max = Math.max(max, (Math.abs(c1 - c2) + 1) * (Math.abs(r1 - r2) + 1));
                        }
                    }
                }
            }
        }
        return max;
    }

    private static boolean isFullofOne(int r1, int c1, int r2, int c2, int[][] matrix) {
        int y = Math.min(r1, r2);
        int x = Math.min(c1, c2);
        int height = Math.abs(r1 - r2);
        int width = Math.abs(c1 - c2);
        // 注意边界<=
        for (int r = y; r <= y + height; r++) {
            for (int c = x; c <= x + width; c++) {
                if (matrix[r][c] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 单调栈，O(N^2)
     */
    public static int MaximalRectangle(char[][] map) {
        if (map == null || map.length == 0 || map[0].length == 0) {
            return 0;
        }

        int maxArea = 0;
        int N = map[0].length;
        // 数组压缩
        int[] height = new int[N];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < N; j++) {
                // 和上一层得到累计和，生成直方图，
                height[j] = map[i][j] == '0' ? 0 : height[j] + 1;
            }
            maxArea = Math.max(maxArea, getMaxArea2(height));
        }
        return maxArea;
    }

    private static int getMaxArea2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int index = stack.pop();
                int leftless = stack.isEmpty() ? -1 : stack.peek();
                int curArea = (i - leftless - 1) * height[index];
                maxArea = Math.max(maxArea, curArea);
            }
            stack.push(i);
        }
        while (!stack.isEmpty()) {
            int index = stack.pop();
            int leftless = stack.isEmpty() ? -1 : stack.peek();
            int curArea = (height.length - leftless - 1) * height[index];
            maxArea = Math.max(maxArea, curArea);
        }
        return maxArea;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[4][5];
        matrix[0] = new int[] { 1, 1, 1, 1, 1 };
        matrix[1] = new int[] { 1, 0, 1, 1, 1 };
        matrix[2] = new int[] { 1, 1, 1, 0, 1 };
        matrix[3] = new int[] { 1, 0, 1, 1, 1 };
        System.out.println(getMaxArea1(matrix));
    }
}
