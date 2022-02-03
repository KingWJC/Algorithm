/**
 * 柱状图中最大的矩形
 * 测试链接：https://leetcode.com/problems/largest-rectangle-in-histogram
 */
package com.example.system.class13;

import java.util.Stack;

public class C03_LargestRectangleInHitogram {
    /**
     * 单调栈
     */
    public static int getLargestRectangleArea1(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[stack.peek()] >= height[i]) {
                int index = stack.pop();
                int leftless = stack.isEmpty() ? -1 : stack.peek();
                // i为右侧位置，左侧位置加1，才是两位置之间的长度
                max = Math.max(max, height[index] * (i - (leftless + 1)));
            }
            stack.push(i);
        }

         // 在数组中，右边没有小于它的数，只计算左边,使用height.length
        while (!stack.isEmpty()) {
            int index = stack.pop();
            int leftless = stack.isEmpty() ? -1 : stack.peek();
            // 没有右侧，用弹出的位置
            max = Math.max(max, height[index] * (height.length - (leftless + 1)));
        }
        return max;
    }

    /**
     * 使用数组代替Java的栈
     */
    public static int getLargestRectangleArea2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int N = height.length;
        int[] stack = new int[N];
        int si = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < N; i++) {
            while (si != -1 && height[stack[si]] >= height[i]) {
                int index = stack[si--];
                int leftLess = si == -1 ? -1 : stack[si];
                int curArea = (i - leftLess - 1) * height[index];
                max = Math.max(max, curArea);
            }
            stack[++si] = i;
        }
        // 在数组中，右边没有小于它的数，只计算左边,使用N
        while (si != -1) {
            int index = stack[si--];
            int leftless = si == -1 ? -1 : stack[si];

            int curArea = (N - leftless - 1) * height[index];
            max = Math.max(max, curArea);
        }
        return max;
    }

    public static void main(String[] args) {
        int[] height = { 3, 2, 4, 2, 5 };
        System.out.println(getLargestRectangleArea1(height));
        System.out.println(getLargestRectangleArea2(height));
    }
}
