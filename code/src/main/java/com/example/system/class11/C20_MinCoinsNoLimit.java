/**
 * 面值数组组成面值的最少货币数
 */
package com.example.system.class11;

public class C20_MinCoinsNoLimit {
    /**
     * 递归尝试：样本对应的尝试模型（0...aim,0....N)
     */
    public static int minCoins(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }

        return process(arr, 0, aim);
    }

    /**
     * arr[index...]面值，每种面值张数自由选择，
     * 搞出rest正好这么多钱，返回最小张数
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {
            // Integer.MAX_VALUE标记怎么都搞不定，此次尝试失败
            return rest == 0 ? 0 : Integer.MAX_VALUE;
        }

        int takeCount = Integer.MAX_VALUE;
        for (int count = 0; count * arr[index] <= rest; count++) {
            int next = process(arr, index + 1, rest - count * arr[index]);
            // 当前面值选择了多少张，再加上下一次的张数，才是本次最小张数。
            if (next != Integer.MAX_VALUE) {
                takeCount = Math.min(takeCount, next + count);
            }
        }
        return takeCount;
    }

    /**
     * 动态规划：严格表结构。（注意边界问题）
     */
    public static int useDP1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int takeCount = Integer.MAX_VALUE;
                for (int count = 0; count * arr[index] <= rest; count++) {
                    int next = dp[index + 1][rest - count * arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        takeCount = Math.min(takeCount, next + count);
                    }
                }
                dp[index][rest] = takeCount;
            }
        }
        return dp[0][aim];
    }

    /**
     * 动态规划：斜率优化。
     */
    public static int useDP2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }
        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                // 左位置的值若是Integer.MaxValue, 因为是无限张，所以当前位置的所有左下侧的结果都无效。
                if (rest >= arr[index] && dp[index][rest - arr[index]] != Integer.MAX_VALUE) {
                    dp[index][rest] = Math.min(dp[index][rest], dp[index][rest - arr[index]] + 1);
                }
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int maxLength = 20;
        int maxValue = 30;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateArray(maxLength, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = minCoins(arr, aim);
            int ans2 = useDP1(arr, aim);
            int ans3 = useDP2(arr, aim);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] generateArray(int maxLength, int maxValue) {
        int length = (int) (Math.random() * maxLength) + 1;
        int[] ans = new int[length];
        boolean[] help = new boolean[maxValue + 1];
        for (int i = 0; i < length; i++) {
            do {
                ans[i] = (int) (Math.random() * maxValue) + 1;
            } while (help[ans[i]]);
            help[ans[i]] = true;
        }
        return ans;
    }
}