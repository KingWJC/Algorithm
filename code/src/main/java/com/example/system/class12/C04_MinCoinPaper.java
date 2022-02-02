package com.example.system.class12;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Map.Entry;

import javax.crypto.spec.RC5ParameterSpec;

public class C04_MinCoinPaper {
    /**
     * 暴力递归
     * 
     * @param arr 货币数组，其中的值都是正数
     * @param aim 正数aim
     * @return 组成aim的最少货币数
     */
    public static int getMinCoins(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }
        return process(arr, 0, aim);
    }

    private static int process(int[] arr, int index, int aim) {
        if (index == arr.length) {
            return aim == 0 ? 0 : Integer.MAX_VALUE;
        }

        // 没有选择当前货币
        int p1 = process(arr, index + 1, aim);
        // 选择当前货币
        int p2 = Integer.MAX_VALUE;
        if (arr[index] <= aim) {
            int next = process(arr, index + 1, aim - arr[index]);
            if (next != Integer.MAX_VALUE) {
                p2 = next + 1;
            }
        }
        return Math.min(p1, p2);
    }

    /**
     * 动态规划
     */
    public static int dp1(int[] arr, int aim) {
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
                int p1 = dp[index + 1][rest];
                int p2 = Integer.MAX_VALUE;
                if (arr[index] <= rest) {
                    int next = dp[index + 1][rest - arr[index]];
                    if (next != Integer.MAX_VALUE) {
                        p2 = next + 1;
                    }
                }
                dp[index][rest] = Math.min(p1, p2);
            }
        }
        return dp[0][aim];
    }

    /**
     * 动态规划：数据压缩的优化
     */
    public static int dp2(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }

        Info info = getZipInfo(arr);
        int[] coins = info.coins;
        int[] counts = info.counts;
        int N = coins.length;

        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }

        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                int min = Integer.MAX_VALUE;
                for (int i = 0; i <= counts[index]; i++) {
                    if (rest - i * coins[index] >= 0 && dp[index + 1][rest - i * coins[index]] != Integer.MAX_VALUE)
                        min = Math.min(min, i + dp[index + 1][rest - i * coins[index]]);
                }
                dp[index][rest] = min;
            }
        }
        return dp[0][aim];
    }

    private static class Info {
        int[] coins;
        int[] counts;

        public Info(int[] coin, int[] count) {
            this.coins = coin;
            this.counts = count;
        }
    }

    private static Info getZipInfo(int[] arr) {
        Hashtable<Integer, Integer> set = new Hashtable<>();
        for (int i = 0; i < arr.length; i++) {
            if (set.containsKey(arr[i])) {
                set.put(arr[i], set.get(arr[i]) + 1);
            } else {
                set.put(arr[i], 1);
            }
        }
        int N = set.size();
        int index = 0;
        int[] coin = new int[N];
        int[] count = new int[N];
        for (Entry<Integer, Integer> entry : set.entrySet()) {
            coin[index] = entry.getKey();
            count[index++] = entry.getValue();
        }
        return new Info(coin, count);
    }

    /**
     * 动态规划：枚举行为的优化，使用窗口最小值的更新结构。
     * 时间复杂度为O(货币种数 * aim)
     */
    public static int dp3(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }

        Info info = getZipInfo(arr);
        int[] coins = info.coins;
        int[] counts = info.counts;
        int N = coins.length;

        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 0;
        for (int i = 1; i <= aim; i++) {
            dp[N][i] = Integer.MAX_VALUE;
        }

        for (int index = N - 1; index >= 0; index--) {
            for (int mod = 0; mod < Math.min(coins[index], aim + 1); mod++) {
                // 当前面值 X：mod mod + x mod + 2*x mod + 3 * x
                LinkedList<Integer> minq = new LinkedList<>();
                minq.add(mod);
                // 不选择当前货币
                dp[index][mod] = dp[index + 1][mod];
                // 窗口右边界的范围
                for (int r = mod + coins[index]; r <= aim; r += coins[index]) {
                    while (!minq.isEmpty() && (dp[index + 1][minq.peekLast()] == Integer.MAX_VALUE
                            || dp[index + 1][minq.peekLast()]
                                    + compensate(minq.peekLast(), r, coins[index]) >= dp[index + 1][r])) {
                        minq.pollLast();
                    }
                    minq.addLast(r);

                    // 使用张数限定窗口大小,删除上一个过期位置，张数+1
                    int overdue = r - coins[index] * (counts[index] + 1);
                    if (overdue == minq.peekFirst()) {
                        minq.pollFirst();
                    }

                    // 选择最小值
                    dp[index][r] = dp[index + 1][minq.peekFirst()] + compensate(minq.peekFirst(), r, coins[index]);
                }
            }
        }
        return dp[0][aim];
    }

    private static int compensate(int pre, int cur, int coin) {
        return (cur - pre) / coin;
    }

    public static void main(String[] args) {
        functionTest();

        efficiencyTest();
    }

    public static int[] randomArray(int maxLen, int maxValue) {
        int[] arr = new int[(int) (Math.random() * maxLen)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }

    /**
     * 功能测试
     */
    private static void functionTest() {
        int maxLen = 20;
        int maxValue = 30;
        int testTime = 300000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1 = getMinCoins(arr, aim);
            int ans2 = dp1(arr, aim);
            int ans3 = dp2(arr, aim);
            int ans4 = dp3(arr, aim);
            if (ans1 != ans2 || ans2 != ans3 || ans3 != ans4) {
                System.out.println("Oops!");
                System.out.print(Arrays.toString(arr));
                System.out.println(aim);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println(ans3);
                System.out.println(ans4);
                break;
            }
        }
        System.out.println("功能测试结束");
    }

    /**
     * 性能测试
     */
    private static void efficiencyTest() {
        System.out.println("性能测试开始");
        int maxLen = 30000;
        int maxValue = 20;
        int aim = 60000;
        int[] arr = randomArray(maxLen, maxValue);
        long start = System.currentTimeMillis();
        int ans2 = dp2(arr, aim);
        long end = System.currentTimeMillis();
        System.out.println("dp2答案 : " + ans2 + ", dp2运行时间 : " + (end - start) + " ms");

        start = System.currentTimeMillis();
        int ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3答案 : " + ans3 + ", dp3运行时间 : " + (end - start) + " ms");
        System.out.println("性能测试结束");

        System.out.println("货币大量重复出现情况下，");
        System.out.println("大数据量测试dp3开始");
        maxLen = 20000000;
        aim = 10000;
        maxValue = 10000;
        arr = randomArray(maxLen, maxValue);
        start = System.currentTimeMillis();
        ans3 = dp3(arr, aim);
        end = System.currentTimeMillis();
        System.out.println("dp3运行时间 : " + (end - start) + " ms");
        System.out.println("大数据量测试dp3结束");

        System.out.println("当货币很少出现重复，dp2比dp3有常数时间优势");
        System.out.println("当货币大量出现重复，dp3时间复杂度明显优于dp2");
        System.out.println("dp3的优化用到了窗口内最小值的更新结构");
    }
}
