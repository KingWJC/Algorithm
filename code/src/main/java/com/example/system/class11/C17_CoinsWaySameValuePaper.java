/**
 * 货币数组组成面值的方法数-同值无差别(张数限定)  
 */
package com.example.system.class11;

import java.util.HashMap;
import java.util.Map.Entry;

public class C17_CoinsWaySameValuePaper {
    /**
     * 暴力尝试：从左到右的尝试模型
     */
    public static int coinsway(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }
        Info info = getInfo(arr);
        return process(info.coins, info.counts, 0, aim);
    }

    /**
     * coins 面值数组，正数且去重
     * counts 每种面值对应的张数
     */
    private static class Info {
        int[] coins;
        int[] counts;

        public Info(int[] coin, int[] count) {
            this.coins = coin;
            this.counts = count;
        }
    }

    private static Info getInfo(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int i : arr) {
            if (counts.containsKey(i)) {
                counts.put(i, counts.get(i) + 1);
            } else {
                counts.put(i, 1);
            }
        }

        int N = counts.size();
        int[] coin = new int[N];
        int[] count = new int[N];
        int index = 0;
        for (Entry<Integer, Integer> e : counts.entrySet()) {
            coin[index] = e.getKey();
            count[index] = e.getValue();
            index++;
        }
        return new Info(coin, count);
    }

    /**
     * coin[index....],在张数规定好的情况下组成rest的方法数
     */
    private static int process(int[] coins, int[] counts, int index, int rest) {
        if (index == coins.length) {
            return rest == 0 ? 1 : 0;
        }

        int ways = 0;
        for (int i = 0; i <= counts[index] && i * coins[index] <= rest; i++) {
            ways += process(coins, counts, index + 1, rest - i * coins[index]);
        }
        return ways;
    }

    /**
     * 动态规划
     */
    public static int useDP(int[] arr, int aim) {
        if (arr == null || arr.length == 0 || aim < 1) {
            return 0;
        }

        Info info = getInfo(arr);
        int[] coins = info.coins;
        int[] counts = info.counts;
        int N = coins.length;
        int[][] dp = new int[N + 1][aim + 1];
        dp[N][0] = 1;
        for (int index = N - 1; index >= 0; index--) {
            for (int rest = 0; rest <= aim; rest++) {
                // 当前面值的货币，选择0张的情况
                dp[index][rest] = dp[index + 1][rest];
                if (rest - coins[index] >= 0) {
                    dp[index][rest] += dp[index][rest - coins[index]];
                }
                // 判断左位置的rest，是否用完当前面值的全部张数。
                if (rest - coins[index] * (counts[index] + 1) >= 0) {
                    dp[index][rest] -= dp[index + 1][rest - (counts[index] + 1) * coins[index]];
                }
            }
        }
        return dp[0][aim];
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 20;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int[] arr = randomArray(maxLen, maxValue);
            int aim = (int) (Math.random() * maxValue);
            int ans1=coinsway(arr, aim);
            int ans2=useDP(arr, aim);
            if(ans1!=ans2)
            {
                System.out.println("error");
            }
        }
        System.out.println("测试结束");
    }

    private static int[] randomArray(int maxLen, int maxValue) {
        int N = (int) (Math.random() * maxLen) + 1;
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = (int) (Math.random() * maxValue) + 1;
        }
        return arr;
    }
}
