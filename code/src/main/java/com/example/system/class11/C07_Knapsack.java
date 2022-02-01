/**
 * 背包能装下最多的价值 : 从左往右的尝试模型
 */
package com.example.system.class11;

public class C07_Knapsack {
    /**
     * 获取最大价值
     * 
     * @param weights 货物的重量
     * @param values  货物的价值
     * @param bag     背包容量
     * @return 不超重的情况下，能够得到的最大价值
     */
    public static int maxValue(int[] weights, int[] values, int bag) {
        if (weights == null || values == null || weights.length != values.length || values.length == 0 || bag < 0) {
            return 0;
        }
        // 尝试函数！
        return process2(weights, values, 0, bag);
    }

    /**
     * index 0~N, rest剩余容量 负~bag
     */
    private static int process(int[] weights, int[] values, int index, int rest) {
        // 两个base case顺序不能换，要先判断无效。
        // base case 新增条件，rest<0时，选择无效。
        if (rest < 0) {
            return -1;
        }

        if (weights.length == index) {
            return 0;
        }

        // 选择当前的货物
        int v1 = 0;
        int next = process(weights, values, index + 1, rest - weights[index]);
        if (next != -1) {
            v1 = values[index] + next;
        }
        // 不选择当前的货物
        int v2 = process(weights, values, index + 1, rest);
        return Math.max(v1, v2);
    }

    /**
     * base case 放在执行前判断
     */
    private static int process2(int[] weights, int[] values, int index, int rest) {
        if (weights.length == index) {
            return 0;
        }

        // 选择当前的货物
        int v1 = 0;
        if (rest - weights[index] >= 0) {
            v1 = values[index] + process2(weights, values, index + 1, rest - weights[index]);
        }
        // 不选择当前的货物
        int v2 = process2(weights, values, index + 1, rest);
        return Math.max(v1, v2);
    }

    /**
     * 动态规划
     * 填充单元：行
     */
    public static int useDP(int[] weights, int[] values, int bag) {
        if (weights == null || values == null || weights.length != values.length || values.length == 0 || bag < 0) {
            return 0;
        }

        int index = weights.length;
        int rest = bag;
        // index到达weights边界,返回0，所以加1.
        int[][] dp = new int[index + 1][rest + 1];
        for (int i = 0; i <= rest; i++) {
            dp[index][i] = 0;
        }
        for (int i = index - 1; i >= 0; i--) {
            for (int j = 0; j <= rest; j++) {
                int v1 = 0;
                if (j >= weights[i]) {
                    v1 = values[i] + dp[i + 1][j - weights[i]];
                }
                int v2 = dp[i + 1][j];
                dp[i][j] = Math.max(v1, v2);
            }
        }
        return dp[0][bag];
    }

    public static void main(String[] args) {
        int[] weights = { 3, 2, 4, 7, 3, 1, 7 };
        int[] values = { 5, 6, 3, 19, 12, 4, 2 };
        int bag = 15;
        // int[] weights = { 3, 2, 5 };
        // int[] values = { 7, 4, 6 };
        // int bag = 7;
        System.out.println(maxValue(weights, values, bag));
        System.out.println(useDP(weights, values, bag));
    }
}
