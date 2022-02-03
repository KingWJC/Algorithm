/**
 * 正数数组中子数组累加和乘最小值的最大值
 */
package com.example.system.class13;

import java.util.Stack;

public class C02_SumMinToMax {
    /**
     * 暴力解：O(N^3)
     */
    public static int getMax1(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int l = 0; l < arr.length; l++) {
            for (int r = l; r < arr.length; r++) {
                int sum = 0;
                int min = Integer.MAX_VALUE;
                for (int i = l; i <= r; i++) {
                    sum += arr[i];
                    min = Math.min(min, arr[i]);
                }
                max = Math.max(max, sum * min);
            }
        }
        return max;
    }

    /**
     * 单调栈：O(N)
     * 求前缀和时，会覆盖之前重复的最小值
     */
    public static int getMax2(int[] arr) {
        // 1.前缀和数组 O(N)
        int[] sum = new int[arr.length];
        sum[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            sum[i] = sum[i - 1] + arr[i];
        }

        // 2.生成单调栈和弹出时计算
        int max = Integer.MIN_VALUE;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < arr.length; i++) {
            while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
                int index = stack.pop();
                max = Math.max(max, arr[index] * (stack.isEmpty() ? sum[i - 1] : (sum[i - 1] - sum[stack.peek()])));
            }
            stack.push(i);
        }
         // 在数组中，右边没有小于它的数，只计算左边，使用arr.length
        while (!stack.isEmpty()) {
            int index = stack.pop();
            max = Math.max(max,
                    arr[index] * (stack.isEmpty() ? sum[arr.length - 1] : sum[arr.length - 1] - sum[stack.peek()]));
        }
        return max;
    }

    public static void main(String[] args) {
        int testTimes = 2000000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = gerenareRondomArray();
            int ans1 = getMax1(arr);
            int ans2 = getMax2(arr);
            if(ans1!=ans2)
            {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] gerenareRondomArray() {
        int[] arr = new int[(int) (Math.random() * 20) + 10];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 101);
        }
        return arr;
    }
}
