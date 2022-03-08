/**
 * 背包中有多少种零食放法
 */
package com.example.system.class24;

public class C06_SnacksWays {
    /**
     * 暴力尝试
     */
    public static int ways1(int[] arr, int w) {
        return process1(arr, w, 0);
    }

    /**
     * 从左往右的经典模型
     * 还剩的容量是rest，arr[index...]自由选择，
     * 返回选择方案
     */
    private static int process1(int[] arr, int rest, int index) {
        // 无方案
        if (rest < 0) {
            return 0;
        }

        // 无零食可选 且rest>=0
        if (index == arr.length) {
            return 1;
        }

        // index号零食，要 or 不要
        return process1(arr, rest, index + 1) + process1(arr, rest - arr[index], index + 1);
    }

    /**
     * 动态规划：总体积很大，但背包容量w不大
     * arr数组的 i-n范围上 的零食，体积不超过容量j，有多少种方法。
     */
    public static int ways2(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N + 1][w + 1];
        for (int i = 0; i <= w; i++) {
            dp[N][i] = 1;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= w; j++) {
                // 当前要或不要，再看后
                dp[i][j] = dp[i + 1][j];
                if (j >= arr[i]) {
                    dp[i][j] += dp[i + 1][j - arr[i]];
                }
            }
        }

        return dp[0][w];
    }

    /**
     * 动态规划：总体积不大，但背包容量w很大
     * arr数组的 0-i范围上 的零食，体积正好凑够容量j，有多少种方法
     */
    public static int ways3(int[] arr, int w) {
        int N = arr.length;
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }

        int[][] dp = new int[N][sum + 1];
        // 体积为0也算一种放法
        for (int i = 0; i < N; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] <= w) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                // 当前要或不要，再看前
                dp[i][j] = dp[i - 1][j];
                if (j >= arr[i]) {
                    dp[i][j] += dp[i - 1][j - arr[i]];
                }
            }
        }

        // 小于等于w的累加，就是最终结果。
        int ans = 0;
        for (int i = 0; i <= w; i++) {
            ans += dp[N - 1][i];
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = { 4, 3, 2, 9 };
        int w = 8;
        System.out.println(ways1(arr, w));
        System.out.println(ways2(arr, w));
        System.out.println(ways3(arr, w));
    }
}
