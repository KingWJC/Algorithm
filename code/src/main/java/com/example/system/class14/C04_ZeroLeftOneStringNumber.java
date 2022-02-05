/**
 * 由0和1构成的达标字符串
 */
package com.example.system.class14;

public class C04_ZeroLeftOneStringNumber {
    /**
     * 递归求，组成长度为N的字符串
     */
    public static int getNum1(int n) {
        if (n < 1) {
            return 0;
        }
        // 从第一个字符开始
        return process(1, n);
    }

    private static int process(int i, int n) {
        if (i == n - 1) {
            return 2;
        }
        if (i == n) {
            return 1;
        }

        return process(i + 1, n) + process(i + 2, n);
    }

    /**
     * 迭代求
     */
    public static int getNum2(int n) {
        if (n < 1) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }

        int temp = 0;
        // 数列索引右移时，计算第一项和第二项的值。
        int first = 1;
        int second = 1;
        for (int i = 2; i <= n; i++) {
            temp = second;
            second += first;
            first = temp;
        }
        return second;
    }

    /**
     * 矩阵快速幂
     */
    public static int getNum3(int n) {
        if (n < 1) {
            return 0;
        }

        if (n == 1 || n == 2) {
            return n;
        }

        int[][] transfer = { { 1, 1 }, { 1, 0 } };
        int[][] ans = matrixPower(transfer, n - 2);
        // 行向量乘以列
        return 2 * ans[0][0] + ans[1][0];
    }

    private static int[][] matrixPower(int[][] m, int p) {
        int N = m.length;
        int[][] base = new int[N][N];
        for (int i = 0; i < N; i++) {
            base[i][i] = 1;
        }

        int[][] t = m;
        while (p != 0) {
            if ((p & 1) != 0) {
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
        int i=10;
        System.out.println(getNum1(i));
        System.out.println(getNum2(i));
        System.out.println(getNum3(i));
    }
}
