/**
 * 画匠问题（四边形不等式和猜测）
 * 所有画作完成的最短时间，也就是某一个画家完成时间是最大。
 * 测试链接：https://leetcode.com/problems/split-array-largest-sum/
 */
package com.example.system.class27;

import java.util.Arrays;

public class C04_PainterIssue {
    /**
     * 动态规划：从左往右的尝试模型
     * dp[i][j]表示0-i范围上的画，j个画家并行绘制，需要的最短时间
     */
    public static int splitArray1(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K < 1) {
            return -1;
        }

        int N = arr.length;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }

        int[][] dp = new int[N][K + 1];
        for (int i = 1; i <= K; i++) {
            dp[0][i] = arr[0];
        }
        for (int i = 1; i < N; i++) {
            dp[i][1] = getRangeSum(sum, 0, i);
        }
        for (int i = 1; i < N; i++) {
            for (int j = 2; j <= K; j++) {
                int min = Integer.MAX_VALUE;
                for (int leftEnd = 0; leftEnd <= i; leftEnd++) {
                    int leftCost = dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : getRangeSum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
                    min = Math.min(min, cur);
                }
                dp[i][j] = min;
            }
        }

        return dp[N - 1][K];
    }

    /**
     * 获取区间内的累加和
     */
    private static int getRangeSum(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }

    /**
     * 动态规划：进行枚举优化，使用左下匹配对。
     */
    public static int splitArray2(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K < 1) {
            return -1;
        }

        int N = arr.length;
        int[] sum = new int[N + 1];
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }

        int[][] dp = new int[N][K + 1];
        // 画家分两部分，其它和当前，最优划分点是其它画家负责画作的索引位置。
        int[][] bestSplit = new int[N][K + 1];
        for (int i = 1; i <= K; i++) {
            dp[0][i] = arr[0];
            // 只有一幅画，其它画家没有负责的画作
            bestSplit[0][i] = -1;
        }
        for (int i = 1; i < N; i++) {
            dp[i][1] = getRangeSum(sum, 0, i);
            // 只有一个画家，没有其它画家
            bestSplit[i][1] = -1;
        }
        for (int j = 2; j <= K; j++) {
            for (int i = N - 1; i > 0; i--) {
                int down = bestSplit[i][j - 1];
                // 如果i==N-1是最后一行，则不优化上限
                int up = i == N - 1 ? N - 1 : bestSplit[i + 1][j];
                int min = Integer.MAX_VALUE;
                int bestChoose = -1;
                for (int leftEnd = down; leftEnd <= up; leftEnd++) {
                    int leftCost = leftEnd == -1 ? 0 : dp[leftEnd][j - 1];
                    int rightCost = leftEnd == i ? 0 : getRangeSum(sum, leftEnd + 1, i);
                    int cur = Math.max(leftCost, rightCost);
                    if (cur < min) {
                        min = cur;
                        bestChoose = leftEnd;
                    }
                }
                dp[i][j] = min;
                bestSplit[i][j] = bestChoose;
            }
        }
        return dp[N - 1][K];
    }

    /**
     * 最优解：二分加预测答案
     * 在累计和不超过long类型的范围下，是最优
     */
    public static int splitArray3(int[] arr, int K) {
        if (arr == null || arr.length == 0 || K < 1) {
            return -1;
        }

        int N = arr.length;
        // 完成所有的画作需要的最少时间一定小于所有画作的累加和。
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }

        long l = 0;
        long r = sum;
        long ans = 0;
        while (l <= r) {
            long mid = (l + r) / 2;
            int cur = getNeedPainter(arr, mid);
            if (cur <= K) {
                r = mid - 1;
                ans = mid;
            } else {
                l = mid + 1;
            }
        }
        return (int) ans;
    }

    /**
     * 获取目标答案设为aim是，需要几个画匠。
     */
    private static int getNeedPainter(int[] arr, long aim) {
        for (int value : arr) {
            if (value > aim) {
                return Integer.MAX_VALUE;
            }
        }

        int ans = 1;
        int sum = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (sum + arr[i] > aim) {
                sum = arr[i];
                ans++;
            } else {
                sum += arr[i];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int size = 20;
        int max = 100;
        int testTime = 10000;
        System.out.println("Begin test!");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(size, max);
            int K = (int) (Math.random() * size) + 1;
            int ans1 = splitArray1(arr, K);
            int ans2 = splitArray2(arr, K);
            int ans3 = splitArray3(arr, K);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(Arrays.toString(arr));
                System.out.println("K : " + K);
                System.out.println("ans1 : " + ans1);
                System.out.println("ans2 : " + ans2);
                break;
            }
        }
        System.out.println("Finished!");
    }

    private static int[] randomArray(int size, int max) {
        int len = (int) (Math.random() * size) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max) + 1;
        }
        return ans;
    }
}
