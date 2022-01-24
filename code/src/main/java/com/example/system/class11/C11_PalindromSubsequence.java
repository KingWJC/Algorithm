/**
 * 给定一个字符串str，返回这个字符串的最长回文子序列长度
 * 三和方法。
 * 链接：https://leetcode.com/problems/longest-palindromic-subsequence/
 */
package com.example.system.class11;

public class C11_PalindromSubsequence {
    /**
     * 暴力尝试：范围上的尝试模型,常常讨论范围的开头B和结尾E
     */
    public static int getMaxPS(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0, str.length()-1);
    }

    private static int process(char[] str, int l, int r) {
        // 一个字符的回文长度是1
        if (l == r) {
            return 1;
        }
        // 边界判断
        if (l == r - 1) {
            return str[l] == str[r] ? 2 : 1;
        }
        // 两个可变参数结合的可能性：
        int ans1 = process(str, l + 1, r);
        int ans2 = process(str, l, r - 1);
        int ans3 = process(str, l + 1, r - 1);
        int ans4 = str[l] == str[r] ? 2 + process(str, l + 1, r - 1) : 0;
        return Math.max(Math.max(ans1, ans2), Math.max(ans3, ans4));
    }

    /**
     * 动态规划： 使用严格表结构
     */
    public static int useDP(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] data = str.toCharArray();
        int N = data.length;
        // 两个可变参数是 （0...N-1）
        int[][] dp = new int[N][N];
        // 起始：两条对角线的值。
        dp[0][0] = 1;
        for (int i = 1; i < N; i++) {
            dp[i][i] = 1;
            dp[i - 1][i] = data[i - 1] == data[i] ? 2 : 1;
        }
        for (int l = N - 3; l >= 0; l--) {
            // 因为结束位置必定大于开始位置，
            // 而且 [l,l][l,l+1]两条对角线的值已得,所以r从L+2开始。
            for (int r = l + 2; r < N; r++) {
                // 剪枝, 不用和左下位置进行比较。
                dp[l][r] = Math.max(dp[l][r - 1], dp[l + 1][r]);
                if (data[l] == data[r]) {
                    dp[l][r] = Math.max(dp[l][r], 2 + dp[l + 1][r - 1]);
                }
            }
        }
        return dp[0][N - 1];
    }

    /**
     * 发散思维: 当前字符串与其逆序的字符串的最长公共子序列，就是str的最长回文子序列
     */
    public static int useCommonSubsequence(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] data = str.toCharArray();
        char[] reverse = getReverse(data);
        return getCommonSubsequence(data, reverse);
    }

    private static char[] getReverse(char[] data) {
        int N = data.length;
        char[] ans = new char[N];
        for (int i = 0; i < N; i++) {
            ans[N - 1 - i] = data[i];
        }
        return ans;
    }

    private static int getCommonSubsequence(char[] str1, char[] str2) {
        int N = str1.length;
        int[][] dp = new int[N][N];
        dp[0][0] = str1[0] == str2[0] ? 1 : 0;
        // 两个字符串长度一样。
        for (int i = 1; i < N; i++) {
            dp[0][i] = str1[0] == str2[i] ? 1 : dp[0][i - 1];
            dp[i][0] = str1[i] == str2[0] ? 1 : dp[i - 1][0];
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j < N; j++) {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                if (str1[i] == str2[j]) {
                    dp[i][j] = Math.max(dp[i][j], 1 + dp[i - 1][j - 1]);
                }
            }
        }

        return dp[N - 1][N - 1];
    }

    public static void main(String[] args) {
        String str="a12b3c43def2ghi1kpm";
        System.out.println(getMaxPS(str));
        System.out.println(useDP(str));
        System.out.println(useCommonSubsequence(str));
    }
}
