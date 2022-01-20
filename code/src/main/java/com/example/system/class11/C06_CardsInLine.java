/**
 * A,B玩家从左右两边拿纸牌,返回最后获胜者的分数
 */
package com.example.system.class11;

public class C06_CardsInLine {
    /**
     * 暴力递归的尝试
     */
    public static int win1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int first = f1(arr, 0, arr.length - 1);
        int second = g1(arr, 0, arr.length - 1);
        return Math.max(first, second);
    }

    /**
     * arr[L..R]，先手获得的最好分数返回
     */
    private static int f1(int[] arr, int L, int R) {
        if (L == R) {
            return arr[L];
        }

        int left = arr[L] + g1(arr, L + 1, R);
        int right = arr[R] + g1(arr, L, R - 1);
        return Math.max(left, right);
    }

    /**
     * arr[L..R]，后手获得的最好分数返回
     */
    private static int g1(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }

        int left = arr[L] + f1(arr, L + 1, R);// 对手拿走了L位置的数
        int right = arr[R] + f1(arr, L, R - 1);// 对手拿走了R位置的数
        return Math.min(left, right);
    }

    /**
     * 记忆化搜索表，优化后的动态规划
     */
    public static int win2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int[][] dpf = new int[N][N];
        int[][] dpg = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                dpf[i][j] = -1;
                dpg[i][j] = -1;
            }
        }

        int first = f2(arr, 0, N - 1, dpf, dpg);
        int second = g2(arr, 0, N - 1, dpf, dpg);
        return Math.max(first, second);
    }

    private static int f2(int[] arr, int L, int R, int[][] dpf, int[][] dpg) {
        if (dpf[L][R] != -1) {
            return dpf[L][R];
        }

        int ans = 0;
        if (L == R) {
            ans = arr[L];
        } else {
            int left = arr[L] + g2(arr, L + 1, R, dpf, dpg);
            int right = arr[R] + g2(arr, L, R - 1, dpf, dpg);
            ans = Math.max(left, right);
        }
        dpf[L][R] = ans;
        return ans;
    }

    private static int g2(int[] arr, int L, int R, int[][] dpf, int[][] dpg) {
        if (dpg[L][R] != -1) {
            return dpg[L][R];
        }

        int ans = 0;
        if (L != R) {
            int left = arr[L] + f1(arr, L + 1, R);
            int right = arr[R] + f1(arr, L, R - 1);
            ans = Math.min(left, right);
        }
        dpg[L][R] = ans;
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };
        System.out.println(win1(arr));
        System.out.println(win2(arr));
    }
}
