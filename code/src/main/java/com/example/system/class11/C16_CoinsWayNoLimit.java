/**
 * 面值数组组成面值的方法数-张数不限
 */
package com.example.system.class11;

public class C16_CoinsWayNoLimit {
    /**
     * 暴力尝试
     */
    public static int coinsWay(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }

        return process(arr, 0, aim);
    }

    /**
     * 可变参数：面值的索引位置Index，剩余钱数Rest
     * arr[index.....]所有的面值，每一个面值都可以任意选择张数，组成正好rest这么多钱，方法数多少
     */
    private static int process(int[] arr, int index, int rest) {
        if (index == arr.length) {// 没钱了
            return rest == 0 ? 1 : 0;
        }

        int ans = 0;
        for (int i = 0; i * arr[index] <= rest; i++) {
            ans += process(arr, index + 1, rest - (i * arr[index]));
        }
        return ans;
    }

    /**
     * 动态规划：每个值依赖下一行的多个值。
     */
    public static int useDP(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int ans = 0;
                for (int i = 0; arr[index] * i <= rest; i++) {
                    ans += dp[index + 1][rest - arr[index] * i];
                }
                dp[index][rest] = ans;
            }
        }
        return dp[0][aim];
    }

    /**
     * 严格表结构的优化
     * 枚举行为可以转换为临近位置的依赖
     */
    public static int useDP1(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }
        int n = arr.length;
        int[][] dp = new int[n + 1][aim + 1];
        dp[n][0] = 1;
        for (int index = n - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                dp[index][rest] = dp[index + 1][rest];
                if (rest - arr[index] >= 0) {
                    dp[index][rest] += dp[index][rest - arr[index]];
                }
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = coinsWay(arr, aim);
            int ans2 = useDP(arr, aim);
            int ans3 = useDP1(arr, aim);
            if (ans1 != ans2 && ans2 != ans3) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("测试结束");
    }

    /**
     * 获取不重复的数组
     */
    private static int[] generateRandomArray(int maxLen, int maxValue) {
        int length = (int) (Math.random() * maxLen) + 1;
        int[] ans = new int[length];
        // 使用值的范围的数组存储值是否出现。
        boolean[] has = new boolean[maxValue + 1];
        for (int i = 0; i < length; i++) {
            do {
                ans[i] = (int) (Math.random() * maxValue) + 1;
            } while (has[ans[i]]);
            has[ans[i]] = true;
        }
        return ans;
    }
}
