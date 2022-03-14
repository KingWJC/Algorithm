/**
 * zigzag打印矩阵
 */
package com.example.system.class26;

public class C07_ZigZagPrintMatrix {
    /**
     * 斜线结构
     */
    public static void printMatrixZigZag(int[][] matrix) {
        // 先往右移，到了右边界，再往下移
        int tR = 0;
        int tC = 0;
        // 先往下移，到了下边界，再往右移
        int dR = 0;
        int dC = 0;
        int endR = matrix.length - 1;
        int endC = matrix[0].length - 1;
        boolean fromUp = false;
        while (tR < endR + 1) {
            printLevel(matrix, tR, tC, dR, dC, fromUp);
            tC = tC == endC ? tC : tC + 1;
            tR = tC == endC ? tR + 1 : tR;
            dC = dR == endR ? dC + 1 : dC;
            dR = dR == endR ? dR : dR + 1;
            fromUp = !fromUp;
        }
        System.out.println();
    }

    /**
     * 按行遍历斜线。
     */
    private static void printLevel(int[][] matrix, int tR, int tC, int dR, int dC, boolean fromUp) {
        if (fromUp) {
            // 从A点开始
            while (tR <= dR) {
                System.out.print(matrix[tR++][tC--] + " ");
            }
        } else {
            // 从B点开始
            while (dR >= tR) {
                System.out.print(matrix[dR--][dC++] + " ");
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 } };
		printMatrixZigZag(matrix);
    }
}
 