/**
 * 奇怪的打印机
 * 测试链接 : https://leetcode.com/problems/strange-printer/
 */
package com.example.system.class30;

public class C04_StrangePrinter {
    /**
     * 暴力递归
     */
    public static int printer1(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        return process1(str, 0, str.length - 1);
    }

    /**
     * 打印str[L...R]范围上的字符，返回最少的打印次数。
     * 二分，再合并结果。
     */
    private static int process1(char[] str, int L, int R) {
        if (L == R) {
            return 1;
        }

        int min = R - L + 1;
        for (int i = L; i < R; i++) {
            min = Math.min(min, process1(str, L, i) + process1(str, i + 1, R) - (str[i + 1] == str[L] ? 1 : 0));
        }
        return min;
    }

    /**
     * 记忆化搜索
     */
    public static int printer2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        return process2(str, 0, N - 1, dp);
    }

    private static int process2(char[] str, int L, int R, int[][] dp) {
        if (dp[L][R] != 0) {
            return dp[L][R];
        }

        int ans = R - L + 1;
        if (L == R) {
            ans = 1;
        } else {
            for (int i = L; i < R; i++) {
                int left = process2(str, L, i, dp);
                int right = process2(str, i + 1, R, dp);
                int same = (str[L] == str[i + 1] ? 1 : 0);
                ans = Math.min(ans, left + right - same);
            }
        }
        dp[L][R] = ans;
        return ans;
    }

    /**
     * 动态规划表
     */
    public static int printer3(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }

        char[] str = s.toCharArray();
        int N = str.length;
        int[][] dp = new int[N][N];
        for (int i = 0; i < N; i++) {
            dp[i][i] = 1;
        }
        for (int i = N - 2; i >= 0; i--) {
            for (int j = i + 1; j < N; j++) {
                int min = j - i + 1;
                for (int k = i; k < j; k++) {
                    min = Math.min(min, dp[i][k] + dp[k + 1][j] - (str[i] == str[k + 1] ? 1 : 0));
                }
                dp[i][j] = min;
            }
        }
        return dp[0][N - 1];
    }

    public static void main(String[] args) {
        int size = 16;
        int range = 3;
        int testTime = 1000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String s = randomString(size, range);
            int ans1 = printer1(s);
            int ans2 = printer2(s);
            int ans3 = printer3(s);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(s);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static String randomString(int size, int range) {
        int len = (int) (Math.random() * size) + 1;
        char[] ans = new char[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(ans);
    }
}
