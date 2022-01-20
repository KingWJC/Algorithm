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

        // 后手的计算，不需要加arr[L]或arr[R], 因为已被对手拿走。
        int left = f1(arr, L + 1, R);// 对手拿走了L位置的数
        int right = f1(arr, L, R - 1);// 对手拿走了R位置的数
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
            int left = f2(arr, L + 1, R, dpf, dpg);
            int right = f2(arr, L, R - 1, dpf, dpg);
            ans = Math.min(left, right);
        }
        dpg[L][R] = ans;
        return ans;
    }

    public static int win3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        int[][] dpf = new int[N][N];
        int[][] dpg = new int[N][N];
        // 设置dpf二维表的对角线， dpg对角线为0，不用设。
        for (int i = 0; i < N; i++) {
            dpf[i][i] = arr[i];
        }
        // R=L的对角线已设置，填充R-L=1的对角线，然后R-L=2的对角线 ......
        for (int col = 1; col < N; col++) {
            int L = 0;
            int R = col;
            // 列越界
            while (R < N) {
                dpf[L][R] = Math.max(arr[L] + dpg[L + 1][R], arr[R] + dpg[L][R - 1]);
                dpg[L][R] = Math.min(dpf[L + 1][R], dpf[L][R - 1]);
                L++;
                R++;
            }
        }

        return Math.max(dpf[0][N - 1], dpg[0][N - 1]);
    }

    public static void main(String[] args) {
        int[] arr = { 5, 7, 4, 5, 8, 1, 6, 0, 3, 4, 6, 1, 7 };

        System.out.println(win1(arr));
        System.out.println(win2(arr));
        System.out.println(win3(arr));

        int[] arr2 = { 7, 4, 16, 15, 1 };
        System.out.println(win2(arr2));
    }
}
