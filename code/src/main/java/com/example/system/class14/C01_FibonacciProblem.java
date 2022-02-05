/**
 * 斐波那契数列，第N项求解
 */
package com.example.system.class14;

public class C01_FibonacciProblem {
    /**
     * 递归求
     */
    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        return f1(n - 1) + f1(n - 2);
    }

    /**
     * 迭代求
     */
    public static int f2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int tmp = 0;
        int first = 1;
        int second = 1;
        for (int i = 3; i <= n; i++) {
            tmp = second;
            second = first + second;
            first = tmp;
        }
        return second;
    }

    /**
     * 矩阵的快速幂求
     */
    public static int f3(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1 || n == 2) {
            return 1;
        }

        int[][] base = { { 1, 1 }, { 1, 0 } };
        int[][] res = matrixPower(base, n - 2);
        return res[0][0] + res[1][0];
    }

    /**
     * 矩阵快速幂计算
     */
    private static int[][] matrixPower(int[][] base, int p) {
        int[][] ans = new int[base.length][base[0].length];
        //单位矩阵，对角线为1
        for (int i = 0; i < ans.length; i++) {
            ans[i][i] = 1;
        }

        int[][] temp = base;
        while (p != 0) {
            if ((p & 1) == 1) {
                ans = muliMatrix(ans, temp);
            }
            temp = muliMatrix(temp, temp);
            p >>= 1;
        }
        return ans;
    }

    /**
     * 矩阵的乘法
     * 两个矩阵乘完之后的结果返回
     */
    private static int[][] muliMatrix(int[][] a, int[][] b) {
        int row = a.length;
        int col = b[0].length;
        int k = b.length;
        int[][] ans = new int[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                for (int x = 0; x < k; x++) {
                    ans[i][j] += a[i][x] * b[x][j];
                }
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int n = 19;
		System.out.println(f1(n));
		System.out.println(f2(n));
		System.out.println(f3(n));
		System.out.println("===");
    }
}
