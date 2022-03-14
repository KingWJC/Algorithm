/**
 * 转圈打印矩阵
 */
package com.example.system.class26;

public class C05_PrintMatrixSpiralOrder {
    /**
     * 分圈结构
     */
    public static void spiralOrderPrint(int[][] matrix) {
        int tR = 0;
        int tC = 0;
        int dR = matrix.length - 1;
        int dC = matrix[0].length - 1;
        while (tR < dR || tC < dC) {
            printEdge(matrix, tR++, tC++, dR--, dC--);
        }
    }

    /**
     * 遍历左上角和右下角形成的矩形的每个边
     */
    private static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
        // 长方形的中心是一条直线或竖线。
        if (tR == dR) {
            for (int i = tC; i <= dC; i++) {
                System.out.print(m[tR][i] + " ");
            }
        } else if (tC == dC) {
            for (int i = tR; i <= dR; i++) {
                System.out.print(m[i][tC] + " ");
            }
        } else {
            int curC = tC;
            int curR = tR;
            // 按顺序打印第一条边。
            while (curC < dC) {
                System.out.print(m[tR][curC] + " ");
                curC++;
            }
            while (curR < dR) {
                System.out.print(m[curR][dC] + " ");
                curR++;
            }
            while (curC > tC) {
                System.out.print(m[dR][curC] + " ");
                curC--;
            }
            while (curR > tR) {
                System.out.print(m[curR][tC] + " ");
                curR--;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 },
                { 13, 14, 15, 16 } };
        spiralOrderPrint(matrix);
    }
}
