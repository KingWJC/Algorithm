/**
 * 转圈打印N边星号正方形
 */
package com.example.system.class26;

public class C08_PrintStar {
    public static void printStar(int N) {
        // 生成二维矩阵
        char[][] matrix = new char[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = ' ';
            }
        }

        int leftUp = 0;
        int rightDown = N - 1;
        while (leftUp <= rightDown) {
            set(matrix, leftUp, rightDown);
            leftUp += 2;
            rightDown -= 2;
        }
        for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
    }

    private static void set(char[][] matrix, int leftUp, int rightDown) {
        for (int col = leftUp; col <= rightDown; col++) {
            matrix[leftUp][col] = '*';
        }
        for (int row = leftUp + 1; row <= rightDown; row++) {
            matrix[row][rightDown] = '*';
        }
        for (int col = rightDown - 1; col >= leftUp + 1; col--) {
            matrix[rightDown][col] = '*';
        }
        for (int row = rightDown - 1; row >= leftUp + 2; row--) {
            matrix[row][leftUp + 1] = '*';
        }
    }

    public static void main(String[] args) {
        printStar(5);
    }
}
