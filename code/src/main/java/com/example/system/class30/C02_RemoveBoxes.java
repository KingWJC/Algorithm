/**
 * 移除盒子：
 * 给出一些不同颜色的盒子 boxes ，盒子的颜色由不同的正数表示
 * 测试链接 : https://leetcode.com/problems/remove-boxes/
 */
package com.example.system.class30;

public class C02_RemoveBoxes {
    /**
     * 暴力递归：
     */
    public static int removeBoxes1(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        if (boxes.length == 1) {
            return 1;
        }

        return process1(boxes, 0, boxes.length - 1, 0);
    }

    /**
     * 在arr[L...R]范围上消除箱子，而且L前面有K个和arr[L]相同的值
     * 返回所有箱子都消掉的最大得分
     * 因为最优解是相同颜色最多的箱子最后消除，所以需要外部信息K，得知最后消除时的总个数。
     */
    private static int process1(int[] boxes, int L, int R, int K) {
        // 因为下方的递归调用，需要L+1,i-1的情况。所以base-case不能是L==R.
        // 比如L+1位置和L位置的数相同，需要提前消除的范围是空。
        if (L > R) {
            return 0;
        }

        // boxes[L]和之前的数相同，所以特殊处理。
        int max = (K + 1) * (K + 1) + process1(boxes, L + 1, R, 0);
        // 前面的K个X，和arr[L]数合在一起了，现在有K+1个arr[L]位置的数
        for (int i = L + 1; i <= R; i++) {
            if (boxes[L] == boxes[i]) {
                max = Math.max(max, process1(boxes, L + 1, i - 1, 0) + process1(boxes, i, R, K + 1));
            }
        }
        return max;
    }

    /**
     * 记忆化搜索：
     */
    public static int removeBoxes2(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        if (boxes.length == 1) {
            return 1;
        }

        int N = boxes.length;
        int[][][] dp = new int[N][N][N];
        return process2(boxes, 0, N - 1, 0, dp);
    }

    private static int process2(int[] boxes, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }

        if (dp[L][R][K] != 0) {
            return dp[L][R][K];
        }

        int max = (K + 1) * (K + 1) + process2(boxes, L + 1, R, 0, dp);
        for (int i = L + 1; i <= R; i++) {
            if (boxes[L] == boxes[i]) {
                max = Math.max(max, process2(boxes, L + 1, i - 1, 0, dp) + process2(boxes, i, R, K + 1, dp));
            }
        }
        dp[L][R][K] = max;
        return max;
    }

    /**
     * 记忆化搜索：常数上的两个优化。
     */
    public static int removeBoxes3(int[] boxes) {
        if (boxes == null || boxes.length == 0) {
            return 0;
        }
        if (boxes.length == 1) {
            return 1;
        }

        int N = boxes.length;
        int[][][] dp = new int[N][N][N];
        return process3(boxes, 0, N - 1, 0, dp);
    }

    private static int process3(int[] boxes, int L, int R, int K, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        if (dp[L][R][K] != 0) {
            return dp[L][R][K];
        }

        int last = L;
        while (last < R && boxes[last + 1] == boxes[L]) {
            last++;
        }

        int pre = K + last - L;
        int max = (pre + 1) * (pre + 1) + process3(boxes, last + 1, R, 0, dp);
        // boxes[last+1]肯定不等于boxes[L]
        for (int i = last + 2; i <= R; i++) {
            if (boxes[i] == boxes[L] && boxes[i - 1] != boxes[i]) {
                max = Math.max(max, process3(boxes, L + 1, i - 1, 0, dp) + process3(boxes, i, R, K + 1, dp));
            }
        }
        dp[L][R][K] = max;
        return max;
    }

    public static void main(String[] args) {
        int[] boxes = { 1, 1, 1 };
        System.out.println(removeBoxes1(boxes));
        System.out.println(removeBoxes2(boxes));
        System.out.println(removeBoxes3(boxes));
    }
}
