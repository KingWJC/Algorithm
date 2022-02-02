/**
 * 加油站的良好出发点问题
 * 测试链接：https://leetcode.com/problems/gas-station
 */
package com.example.system.class12;

import java.util.Arrays;
import java.util.LinkedList;

public class C03_GasStation {
    /**
     * 暴力解
     */
    public static boolean[] goodArray1(int[] gas, int[] cost) {
        boolean[] ans = new boolean[gas.length];
        int N = gas.length;
        for (int i = 0; i < N; i++) {
            boolean r = true;
            int result = 0;
            for (int j = 0; j < N; j++) {
                int index = (i + j) % N;
                result += gas[index] - cost[index];
                if (result < 0) {
                    r = false;
                }
            }
            ans[i] = r;
        }
        return ans;
    }

    /**
     * 窗口的最小值的更新结构：时间复杂度O(N)，额外空间复杂度O(N)
     */
    public static boolean[] goodArray2(int[] gas, int[] cost) {
        int N = gas.length;
        int M = N << 1;

        int[] arr = new int[M];
        // 生成到达下一加油站，油的剩余单位。
        for (int i = 0; i < N; i++) {
            arr[i] = gas[i] - cost[i];
            arr[i + N] = gas[i] - cost[i];
        }
        // 生成前缀和的复合数组。
        for (int i = 1; i < M; i++) {
            arr[i] += arr[i - 1];
        }

        // 生成窗口最小值的更新结构
        LinkedList<Integer> minQ = new LinkedList<>();
        for (int i = 0; i < N; i++) {
            while (!minQ.isEmpty() && arr[minQ.peekLast()] >= arr[i]) {
                minQ.pollLast();
            }
            minQ.addLast(i);
        }

        boolean[] ans = new boolean[N];
        // 以每一个位置作为出发点，记录结果
        for (int i = 0; i < N; i++) {
            int pre = i == 0 ? 0 : arr[i - 1];
            if (arr[minQ.peekFirst()] - pre >= 0) {
                // 窗口的最小值大于等于0，可以绕完一圈。
                ans[i] = true;
            }

            // 窗体向右滑动，移除左边界过期的值 -1
            if (minQ.peekFirst() == i) {
                minQ.pollFirst();
            }
            // 窗体向右滑动，增加右边界临近的值 +1 （值从N开始）
            while (!minQ.isEmpty() && arr[minQ.peekLast()] >= arr[i + N]) {
                minQ.pollLast();
            }
            minQ.addLast(i + N);
        }
        return ans;
    }

    /**
     * leetcode方法
     */
    public static int canCompleteCircuit(int[] gas, int[] cost) {
        boolean[] goods = goodArray2(gas, cost);
        for (int i = 0; i < goods.length; i++) {
            if (goods[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] gas = { 1, 1, 3, 1 };
        int[] cost = { 2, 2, 1, 1 };
        boolean[] ans1 = goodArray1(gas, cost);
        System.out.println(Arrays.toString(ans1));

        boolean[] ans2 = goodArray2(gas, cost);
        System.out.println(Arrays.toString(ans2));
    }
}
