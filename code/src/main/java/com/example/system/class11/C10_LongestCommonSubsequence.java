/**
 * 两个字符串的最长公共子序列
 */
package com.example.system.class11;

public class C10_LongestCommonSubsequence {
    /**
     * 暴力尝试
     */
    public static int longestCommonSubsequence1(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        return process(str1, str2, str1.length - 1, str2.length - 1);
    }

    /**
     * 样本对应的尝试，在0...i,0...j位置上。
     * 讨论当前样本范围的结尾该如何组织可能性
     */
    private static int process(char[] str1, char[] str2, int i, int j) {
        if (i == 0 && j == 0) {
            return str1[i] == str2[j] ? 1 : 0;
        } else if (i == 0) {
            return str1[i] == str2[j] ? 1 : process(str1, str2, i, j - 1);
        } else if (j == 0) {
            return str1[i] == str2[j] ? 1 : process(str1, str2, i - 1, j);
        } else {// i != 0 && j != 0
            // 公共子序列肯定不以 j 结尾
            int ans1 = process(str1, str2, i, j - 1);
            // 公共子序列肯定不以 i 结尾
            int ans2 = process(str1, str2, i - 1, j);
            // i和j位置字符相同，公共子序列以 i/j 结尾
            int ans3 = str1[i] == str2[j] ? 1 + process(str1, str2, i - 1, j - 1) : 0;
            return Math.max(ans1, Math.max(ans2, ans3));
        }
    }

    /**
     * 严格表结构的动态规划。
     */
    public static int useDP(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int[][] dp = new int[str1.length][str2.length];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        for (int i = 1; i < str1.length; i++) {
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }
        for (int i = 1; i < str2.length; i++) {
            dp[0][i] = str1[0] == str2[i] ? 1 : dp[0][i - 1];
        }
        for (int i = 1; i < str1.length; i++) {
            for (int j = 1; j < str2.length; j++) {
                int ans1 = dp[i - 1][j];
                int ans2 = dp[i][j - 1];
                int ans3 = str1[i] == str2[j] ? 1 + dp[i - 1][j - 1] : 0;
                dp[i][j] = Math.max(ans1, Math.max(ans2, ans3));
            }
        }

        return dp[str1.length - 1][str2.length - 1];
    }

    public static void main(String[] args) {
        String s1 = "a1bc3d2ab45d";
        String s2 = "ef1h2fnw3e4s";
        System.out.println(longestCommonSubsequence1(s1, s2));
        System.out.println(useDP(s1, s2));
    }
}
