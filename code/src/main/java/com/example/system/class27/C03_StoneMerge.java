/**
 * 合并石子的得分（四边形不等式）
 */
package com.example.system.class27;

public class C03_StoneMerge {
    /**
     * 暴力递归求解
     */
    public static int minScore1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int[] sum = getPreSum(arr);
        return process(sum, 0, arr.length - 1);
    }

    /**
     * 范围上的尝试
     */
    private static int process(int[] arr, int L, int R) {
        if (L == R) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (int leftEnd = L; leftEnd < R; leftEnd++) {
            int left = process(arr, L, leftEnd);
            int right = process(arr, leftEnd + 1, R);
            min = Math.min(min, left + right);
        }

        return min + getRangeSum(arr, L, R);
    }

    /**
     * 获取数组的前缀和数组（N+1）。
     */
    private static int[] getPreSum(int[] arr) {
        int N = arr.length;
        int[] sum = new int[N + 1];
        sum[0] = 0;
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }
        return sum;
    }

    /**
     * 获取区间范围内的累加和
     * sum:前缀和数组。
     */
    private static int getRangeSum(int[] sum, int L, int R) {
        return sum[R + 1] - sum[L];
    }

    /**
     * 动态规划求解:O(N^3)
     */
    public static int minScore2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N][N];
        // for (int i = 0; i < N; i++) {
        // dp[i][i] = 0;
        // }
        int[] sum = getPreSum(arr);
        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 1; R < N; R++) {
                int min = Integer.MAX_VALUE;
                // min(dp(L..leftEnd) + dp[leftEnd+1...R]) + 累加和[L...R]
                for (int leftEnd = L; leftEnd < R; leftEnd++) {
                    min = Math.min(min, dp[L][leftEnd] + dp[leftEnd + 1][R]);
                }
                dp[L][R] = getRangeSum(sum, L, R) + min;
            }
        }

        return dp[0][N - 1];
    }

    /**
     * 四边形不等式的枚举优化::O(N^2)
     */
    public static int minScore3(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int N = arr.length;
        int[] sum = getPreSum(arr);
        int[][] dp = new int[N][N];
        int[][] bestSplit = new int[N][N];
        // L=R的对角线，dp为0，bestSplit为0.
        // L+1=R的对角线，dp为累计和，bestSplit为L。
        for (int i = 0; i < N - 1; i++) {
            dp[i][i + 1] = getRangeSum(sum, i, i + 1);
            bestSplit[i][i + 1] = i;
        }
        for (int L = N - 2; L >= 0; L--) {
            for (int R = L + 2; R < N; R++) {
                int min = Integer.MAX_VALUE;
                int splitIndex = -1;
                for (int leftEnd = bestSplit[L][R - 1]; leftEnd <= bestSplit[L + 1][R]; leftEnd++) {
                    int cur = dp[L][leftEnd] + dp[leftEnd + 1][R];
                    if (cur < min) {
                        min = cur;
                        splitIndex = leftEnd;
                    }
                }
                dp[L][R] = getRangeSum(sum, L, R) + min;
                bestSplit[L][R] = splitIndex;
            }
        }

        return dp[0][N - 1];
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 100;
        int testTimes = 100000;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(size, max);
            int ans1 = minScore1(arr);
            int ans2 = minScore2(arr);
            int ans3 = minScore3(arr);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finished");
    }

    /**
     * 生成正整数数组
     */
    private static int[] randomArray(int size, int max) {
        int len = (int) (Math.random() * size) + 1;
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max) + 1;
        }
        return ans;
    }
}
