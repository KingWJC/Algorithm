/**
 * 母牛生小牛, N年后牛的数量
 */
package com.example.system.class14;

public class C03_NCow {
    /**
     * 递归求
     */
    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        return f1(n - 1) + f1(n - 3);
    }

    /**
     * 迭代求
     */
    public static int f2(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int temp1 = 0;
        int temp2 = 0;
        int first = 1;
        int second = 2;
        int third = 3;
        for (int i = 4; i <= n; i++) {
            temp1 = third;
            temp2 = second;
            third = third + first;
            second = temp1;
            first = temp2;
        }

        return third;
    }

    /**
     * 矩阵加速计算
     */
    public static int f3(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2 || n == 3) {
            return n;
        }

        int[][] transfer = {
                { 1, 0, 1 },
                { 1, 0, 0 },
                { 0, 1, 0 } };
        // n-3, 是几阶乘的递推，就减几
        int[][] ans = matrixPower(transfer, n - 3);
        return 3 * ans[0][0] + 2 * ans[0][1] + ans[0][2];

        // int[][] base = {
        // { 1, 1, 0 },
        // { 0, 0, 1 },
        // { 1, 0, 0 } };
        // int[][] res = matrixPower(base, n - 3);
        // return 3 * res[0][0] + 2 * res[1][0] + res[2][0];
    }

    private static int[][] matrixPower(int[][] m, int p) {
        int N = m.length;
        int[][] base = new int[N][N];
        for (int i = 0; i < N; i++) {
            base[i][i] = 1;
        }

        int[][] t = m; // 矩阵1次方
        while (p != 0) {
            if ((p & 1) == 1) {
                base = multiMatrix(base, t);
            }
            t = multiMatrix(t, t);
            p >>= 1;
        }
        return base;
    }

    private static int[][] multiMatrix(int[][] m1, int[][] m2) {
        int[][] ans = new int[m1.length][m2[0].length];
        for (int i = 0; i < m1.length; i++) {
            for (int j = 0; j < m2[0].length; j++) {
                for (int k = 0; k < m2.length; k++) {
                    ans[i][j] += m1[i][k] * m2[k][j];
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
    }
}
