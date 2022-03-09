/**
 * 背包中有多少种零食放法
 */
package com.example.system.class24;

import java.util.TreeMap;
import java.util.Map.Entry;

public class C06_SnacksWays {
    /**
     * 暴力尝试
     */
    public static int ways1(int[] arr, int w) {
        return process1(arr, w, 0);
    }

    /**
     * 从左往右的经典模型
     * 还剩的容量是rest，arr[index...]自由选择，
     * 返回选择方案
     */
    private static int process1(int[] arr, int rest, int index) {
        // 无方案
        if (rest < 0) {
            return 0;
        }

        // 无零食可选 且rest>=0
        if (index == arr.length) {
            return 1;
        }

        // index号零食，要 or 不要
        return process1(arr, rest, index + 1) + process1(arr, rest - arr[index], index + 1);
    }

    /**
     * 动态规划：总体积很大，但背包容量w不大
     * arr数组的 i-n范围上 的零食，体积不超过容量j，有多少种方法。
     */
    public static int ways2(int[] arr, int w) {
        int N = arr.length;
        int[][] dp = new int[N + 1][w + 1];
        for (int i = 0; i <= w; i++) {
            dp[N][i] = 1;
        }

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= w; j++) {
                // 当前要或不要，再看后
                dp[i][j] = dp[i + 1][j];
                if (j >= arr[i]) {
                    dp[i][j] += dp[i + 1][j - arr[i]];
                }
            }
        }

        return dp[0][w];
    }

    /**
     * 动态规划：总体积不大，但背包容量w很大
     * arr数组的 0-i范围上 的零食，体积正好凑够容量j，有多少种方法
     */
    public static int ways3(int[] arr, int w) {
        int N = arr.length;
        int sum = 0;
        for (int i = 0; i < N; i++) {
            sum += arr[i];
        }

        int[][] dp = new int[N][sum + 1];
        // 体积为0也算一种放法
        for (int i = 0; i < N; i++) {
            dp[i][0] = 1;
        }
        if (arr[0] <= w) {
            dp[0][arr[0]] = 1;
        }

        for (int i = 1; i < N; i++) {
            for (int j = 1; j <= sum; j++) {
                // 当前要或不要，再看前
                dp[i][j] = dp[i - 1][j];
                if (j >= arr[i]) {
                    dp[i][j] += dp[i - 1][j - arr[i]];
                }
            }
        }

        // 小于等于w的累加，就是最终结果。
        int ans = 0;
        for (int i = 0; i <= w; i++) {
            ans += dp[N - 1][i];
        }
        return ans;
    }

    /**
     * 暴力尝试：总体积很大，背包容量也很大，不能用动态规划表，只能使用分冶。
     * 牛客的测试链接：https://www.nowcoder.com/questionTerminal/d94bb2fa461d42bcb4c0f2b94f5d4281
     */
    public static long ways4(int[] arr, int w) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int mid = (arr.length - 1) / 2;
        TreeMap<Long, Long> leftPart = new TreeMap<>();
        long ways = process2(arr, 0, mid, 0, w, leftPart);
        TreeMap<Long, Long> rightPart = new TreeMap<>();
        ways += process2(arr, mid + 1, arr.length - 1, 0, w, rightPart);

        long sum = 0;
        TreeMap<Long, Long> rightPre = new TreeMap<>();
        for (Entry<Long, Long> entry : rightPart.entrySet()) {
            sum += entry.getValue();
            rightPre.put(entry.getKey(), sum);
        }

        for (Entry<Long, Long> entry : leftPart.entrySet()) {
            long leftWeight = entry.getKey();
            long leftWays = entry.getValue();
            Long rightWeight = rightPre.floorKey(w - leftWeight);
            if (rightWeight != null) {
                ways += leftWays * rightPre.get(rightWeight);
            }
        }

        // 再加上什么货都没选的一种情况。
        return ways + 1;
    }

    /**
     * 从index出发，到end结束
     * 之前的选择，已经形成的累加和sum
     * 零食[index....end]自由选择，出来的所有累加和，不能超过bag，每一种累加和对应的方法数，填在map里
     */
    private static long process2(int[] arr, int index, int end, long sum, int bag, TreeMap<Long, Long> map) {
        if (sum > bag) {
            return 0;
        }

        // 所有商品自由选择完了！
        if (index > end) {
            // 不能什么货都没选
            if (sum != 0) {
                if (map.containsKey(sum)) {
                    map.put(sum, map.get(sum) + 1);
                } else {
                    map.put(sum, 1L);
                }
                return 1;
            } else {
                return 0;
            }
        }
        // sum <= bag 并且 index <= end(还有货)
        // 1) 不要当前index位置的货
        long ans = process2(arr, index + 1, end, sum, bag, map);
        // 2) 要当前index位置的货
        ans += process2(arr, index + 1, end, sum + arr[index], bag, map);
        return ans;
    }

    public static void main(String[] args) {
        int[] arr = { 3,3,3,3,3,3 };
        int w = 6;
        System.out.println(ways1(arr, w));
        System.out.println(ways2(arr, w));
        System.out.println(ways3(arr, w));
        System.out.println(ways4(arr, w));
    }
}
