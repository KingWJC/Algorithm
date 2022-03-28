/**
 * 打气球的最大分数
 * 测试链接 : https://leetcode.com/problems/burst-balloons/
 */
package com.example.system.class30;

public class C01_BurstBalloons {
    /**
     * 暴力递归：范围上的尝试
     */
    public static int maxCoins1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }

        int N = arr.length;
        int[] help = new int[N + 2];
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        help[0] = 1;
        help[N + 1] = 1;
        return process1(help, 1, N);
    }

    /**
     * L....R范围上打爆所有气球，返回最大分数
     * 潜台词：L-1,R+1位置不越界，且都没被打爆。
     * 所以它的枚举条件是：选择任一个气球最后打破。
     */
    private static int process1(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L - 1] * arr[L] * arr[R + 1];
        }
        // 尝试L...R范围上每一种情况，最后打爆的气球，是什么位置
        // L左边界位置的气球，最后打爆
        int max = arr[L - 1] * arr[L] * arr[R + 1] + process1(arr, L + 1, R);
        // R右边界位置的气球，最后打爆
        max = Math.max(max, process1(arr, L, R - 1) + arr[L - 1] * arr[R] * arr[R + 1]);
        // 尝试所有L...R，中间的位置，(L,R)
        for (int k = L + 1; k < R; k++) {
            int left = process1(arr, L, k - 1);
            int right = process1(arr, k + 1, R);
            // k位置是范围内最后一个被打爆，但L...R范围之外的气球没有打爆
            int last = arr[L - 1] * arr[k] * arr[R + 1];
            max = Math.max(max, left + right + last);
        }
        return max;
    }

    /**
     * 动态规划解
     */
    public static int maxCoins2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0];
        }

        int N = arr.length;
        int[] help = new int[N + 2];
        for (int i = 0; i < N; i++) {
            help[i + 1] = arr[i];
        }
        help[0] = 1;
        help[N + 1] = 1;

        int[][] dp = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            dp[i][i] = help[i - 1] * help[i] * help[i + 1];
        }
        for (int L = N - 1; L >= 1; L--) {
            for (int R = L + 1; R <= N; R++) {
                int max = help[L - 1] * help[L] * help[R + 1] + dp[L + 1][R];
                max = Math.max(max, dp[L][R - 1] + help[L - 1] * help[R] * help[R + 1]);
                for (int k = L + 1; k < R; k++) {
                    max = Math.max(max, dp[L][k - 1] + dp[k + 1][R] + help[L - 1] * help[k] * help[R + 1]);
                }
                dp[L][R] = max;
            }
        }
        return dp[1][N];
    }

    public static void main(String[] args) {
        int[] arr={3,2,5};
        System.out.println(maxCoins1(arr));
        System.out.println(maxCoins2(arr));
    }
}
