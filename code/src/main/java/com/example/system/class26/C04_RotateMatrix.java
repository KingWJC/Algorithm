/**
 * 原地旋转正方形矩阵
 */
package com.example.system.class26;

public class C04_RotateMatrix {
    /**
     * 分圈旋转
     */
    public static void rotate(int[][] matrix) {
        // 左上角
        int a = 0;
        int b = 0;
        // 右下角
        int c = matrix.length - 1;
        int d = matrix[0].length - 1;
        while (a < c) {
            rotateEdge(matrix, a++, b++, c--, d--);
        }
    }

    /**
     * 每次旋转四条边的一个点
     */
    private static void rotateEdge(int[][] matrix, int a, int b, int c, int d) {
        for (int i = 0; i < d - b; i++) {
            int temp = matrix[a][b + i];
            matrix[a][b + i] = matrix[c - i][b];
            matrix[c - i][b] = matrix[c][d - i];
            matrix[c][d - i] = matrix[a + i][d];
            matrix[a + i][d] = temp;
        }
    }

    public static void main(String[] args) {
        int[][] matrix = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 }, { 9, 10, 11, 12 }, { 13, 14, 15, 16 } };
        printMatrix(matrix);
        rotate(matrix);
        System.out.println("=========");
        printMatrix(matrix);
    }

    private static void printMatrix(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
