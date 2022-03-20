/**
 * 邮局选址问题：四边形不等式
 */
package com.example.system.class27;

import java.util.Arrays;

public class C05_PostOfficeIssue {
    /**
     * 动态规划：从左往右的尝试模型
     * 最短的 所有的居民点到最近邮局的总距离。
     */
    public static int min1(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K < 1) {
            return -1;
        }

        int N = arr.length;
        // L....R范围上的居民点，只有一个邮局时，最短的总距离是多少
        int[][] minDistance = new int[N][N];
        for (int L = 0; L < N; L++) {
            // L和R成对角线，从左往右填充,依赖左位置。
            for (int R = L + 1; R < N; R++) {
                minDistance[L][R] = minDistance[L][R - 1] + arr[R] - arr[(R + L) >> 1];
            }
        }

        int[][] dp = new int[N][K + 1];
        // dp[0][i]=0; 第一行只有一个居民点，所以代价都为0.
        // 第一列，0个邮局，无效数据，没有结果。
        // 第二列，1个邮局在L....R上的最短总距离。
        for (int i = 1; i < N; i++) {
            dp[i][1] = minDistance[0][i];
        }
        for (int i = 1; i < N; i++) {
            for (int j = 2; j <= K; j++) {
                int min = Integer.MAX_VALUE;
                for (int leftEnd = 0; leftEnd <= i; leftEnd++) {
                    int leftDis = dp[leftEnd][j - 1];
                    int rightDis = leftEnd == i ? 0 : minDistance[leftEnd + 1][i];
                    min = Math.min(min, leftDis + rightDis);
                }
                dp[i][j] = min;
            }
        }

        return dp[N - 1][K];
    }

    /**
     * 四边形不等式：枚举优化，位置对是左下。
     */
    public static int min2(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K < 1) {
            return -1;
        }

        int N = arr.length;
        // L....R范围上的居民点，只有一个邮局时，最短的总距离是多少
        int[][] minDistance = new int[N][N];
        for (int L = 0; L < N; L++) {
            // L和R成对角线，从左往右填充,依赖左位置。
            for (int R = L + 1; R < N; R++) {
                minDistance[L][R] = minDistance[L][R - 1] + arr[R] - arr[(R + L) >> 1];
            }
        }

        int[][] dp = new int[N][K + 1];
        int[][] bestSplit = new int[N][K + 1];
        for (int i = 0; i < N; i++) {
            dp[i][1] = minDistance[0][i];
            bestSplit[i][1] = -1;
        }
        for (int j = 2; j <= K; j++) {
            // 居民点比邮局的个数相等或少时，最短距离为0，不用计算。
            for (int i = N - 1; i >= j; i--) {
                int down = bestSplit[i][j - 1];
                int up = i == N - 1 ? N - 1 : bestSplit[i + 1][j];
                int min = Integer.MAX_VALUE;
                int bestChoose = -1;
                // 此处有三种修改方式
                // 第一种：上边界和i取最小值，
                // 第二种：rightDis = leftEnd >= i
                // 第三种：minDistance = new int[N+1][N+1]
                for (int leftEnd = down; leftEnd <= Math.min(up, i); leftEnd++) {
                    try {
                        int leftDis = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                        int rightDis = leftEnd == i ? 0 : minDistance[leftEnd + 1][i];
                        int cur = leftDis + rightDis;
                        if (cur <= min) {
                            bestChoose = leftEnd;
                            min = cur;
                        }
                    } catch (Exception ex) {
                        System.out.println(Arrays.toString(arr));
                        System.out.println("K=" + K);
                        System.out.println("i=" + i);
                        System.out.println("j=" + j);
                        System.out.println("leftEnd=" + leftEnd);
                        System.out.println("up=" + up);
                        throw ex;
                    }
                }
                dp[i][j] = min;
                bestSplit[i][j] = bestChoose;
            }
        }

        return dp[N - 1][K];
    }

    public static void main(String[] args) {
        int size = 20;
        int max = 100;
        int testTime = 10000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomSortedArray(size, max);
            int K = (int) (Math.random() * size);
            int ans1 = min1(arr, K);
            int ans2 = min2(arr, K);
            if (ans1 != ans2) {
                System.out.println("K=" + K);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

    /**
     * 生成有序随机数组
     */
    private static int[] randomSortedArray(int size, int max) {
        int len = (int) (Math.random() * size) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max) + 1;
        }
        Arrays.sort(ans);
        return ans;
    }
}
