/**
 * 矩阵中的最小路径和
 * 动态规划的优化：空间压缩
 */
package com.example.system.class11;

public class C14_MinPathSum {
    /**
     * 从左上角出发，最后到达右下角
     * 沿途只可以向下或者向右走，沿途的数字都累加就是距离累加和
     * 返回最小距离累加和
     */
    public static int getMinPathSumDP1(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];

        dp[0][0] = matrix[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + matrix[i][0];
        }
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + matrix[0][i];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + matrix[i][j];
            }
        }
        return dp[row-1][col-1];
    }

    /**
     * 动态规划的优化：空间压缩
     */
    public static int getMinPathSumDP2(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[] dp = new int[col];

        dp[0] = matrix[0][0];
        for (int i = 1; i < col; i++) {
            dp[i] = dp[i - 1] + matrix[0][i];
        }

        for (int i = 1; i < row; i++) {
            dp[0] += matrix[i][0];
            for (int j = 1; j < col; j++) {
                dp[j] = Math.min(dp[j - 1], dp[j]) + matrix[i][j];
            }
        }
        return dp[col - 1];
    }

    public static void main(String[] args) {
        int rowSize = 10;
        int colSize = 10;
        int testTimes = 10000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            int[][] matrix = generateRandomMatrix(rowSize, colSize);
            int ans1 = getMinPathSumDP1(matrix);
            int ans2 = getMinPathSumDP2(matrix);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finish");
    }

    private static int[][] generateRandomMatrix(int rowSize, int colSize) {
        if (rowSize < 0 || colSize < 0) {
            return null;
        }
        int[][] result = new int[rowSize][colSize];
        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < colSize; j++) {
                result[i][j] = (int) (Math.random() * 100);
            }
        }
        return result;
    }
}
