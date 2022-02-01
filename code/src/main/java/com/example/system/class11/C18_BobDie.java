/**
 * k步之后，Bob还在N*M的区域的概率
 */
package com.example.system.class11;

public class C18_BobDie {
    /**
     * 暴力尝试：从左到右额尝试
     */
    public static double livePosibility(int row, int col, int rest, int N, int M) {
        return process(row, col, rest, N, M) / Math.pow(4, rest);
    }

    /**
     * 目前在row，col位置，还有rest步要走，
     * 走完了如果还在棋盘中就获得1个生存点，返回总的生存点数
     */
    private static long process(int row, int col, int rest, int N, int M) {
        // 离开N*M的区域，就直接死亡
        if (row < 0 || row == N || col < 0 || col == M) {
            return 0;
        }
        // 走完rest步，还在N*M的区域
        if (rest == 0) {
            return 1;
        }

        long ways = process(row + 1, col, rest - 1, N, M);
        ways += process(row, col + 1, rest - 1, N, M);
        ways += process(row - 1, col, rest - 1, N, M);
        ways += process(row, col - 1, rest - 1, N, M);
        return ways;
    }

    /**
     * 动态规划：
     */
    public static double useDP(int row, int col, int k, int N, int M) {
        long[][][] dp = new long[N][M][k + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                dp[i][j][0] = 1;
            }
        }

        for (int rest = 1; rest <= k; rest++) {
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < M; c++) {
                    long ways = pick(dp, N, M, r - 1, c, rest - 1);
                    ways += pick(dp, N, M, r + 1, c, rest - 1);
                    ways += pick(dp, N, M, r, c - 1, rest - 1);
                    ways += pick(dp, N, M, r, c + 1, rest - 1);
                    dp[r][c][rest] = ways;
                }
            }
        }

        return (double) dp[row][col][k] / Math.pow(4, k);
    }

    private static long pick(long[][][] dp, int N, int M, int r, int c, int rest) {
        if (r < 0 || r == N || c < 0 || c == M)
            return 0;
        return dp[r][c][rest];
    }

    public static void main(String[] args) {
        System.out.println(livePosibility(6, 6, 10, 50, 50));
		System.out.println(useDP(6, 6, 10, 50, 50));
    }
}
