/**
 * 一个人迈上N级台阶的方法数
 * 条件：一个人可以一次往上迈1个台阶，也可以迈2个台阶
 */
package com.example.system.class14;

public class C02_NStep {
    /**
     * 递归求
     */
    public static int f1(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return n;
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
            return n;
        }

        int temp = 0;
        int first = 1;
        int second = 2;
        for (int i = 3; i <= n; i++) {
            temp = second;
            second = first + second;
            first = temp;
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
            return n;
        }

        int[][] tranfer = { { 1, 1 }, { 1, 0 } };
        int[][] ans = matrixPower(tranfer, n - 2);
        // |f(n),f(n-1)| = |f2,f1| * (2*2矩阵) f2=2,f1=1
        // 行向量，乘以第一列
        return 2 * ans[0][0] + ans[1][0];
    }

    private static int[][] matrixPower(int[][] m, int p) {
        int N = m.length;
        int[][] base = new int[N][N];
        for (int i = 0; i < N; i++) {
            base[i][i] = 1;
        }

        int[][] temp = m;
        while (p != 0) {
            if ((p & 1) == 1) {
                base = multiMatrix(base, temp);
            }
            temp = multiMatrix(temp, temp);
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
