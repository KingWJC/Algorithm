/**
 * 货币数组组成面值的方法数-同值认为不同
 */
package com.example.system.class11;

public class C15_CoinsWayDiff {
    /**
     * 暴力尝试
     */
    public static int coinWays(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    /**
     * 可变参数:货币索引index，还要选多少钱rest。
     * 返回arr[index....] 组成正好rest这么多的钱，有几种方法
     */
    private static int process(int[] arr, int index, int rest) {
        if (rest < 0) {
            return 0;
        }
        if (index == arr.length) {
            return rest == 0 ? 1 : 0;
        }

        int ans1 = process(arr, index + 1, rest - arr[index]);
        int ans2 = process(arr, index + 1, rest);
        return ans1 + ans2;
    }

    /**
     * 动态规划：每个值依赖下一行的某一个值。
     */
    public static int useDP(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }

        int N = arr.length;
        int[][] dp = new int[N + 1][aim + 1];
        // 只有rest为0时，证明有一种方法，dp[N]\[0]=1，其余都为0
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest] + (rest - arr[index] < 0 ? 0 : dp[index + 1][rest - arr[index]]);
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinWays(arr, aim);
            int ans2 = useDP(arr, aim);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static int[] generateRandomArray(int maxLen, int maxValue) {
        int length = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[length];
        for (int i = 0; i < length; i++) {
            ans[i] = (int) (Math.random() * maxValue) + 1;
        }
        return ans;
    }
}
