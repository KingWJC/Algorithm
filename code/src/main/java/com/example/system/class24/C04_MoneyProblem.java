/**
 * 打怪兽需要花的最小钱数
 */
package com.example.system.class24;

public class C04_MoneyProblem {
    /**
     * 暴力递归尝试 (power值的范围比较小)
     * 
     * @param power 怪兽的能力
     * @param money 雇佣怪兽的费用
     * @return 需要花的最少钱数
     */
    public static long minMoney1(int[] power, int[] money) {
        return process1(power, money, 0, 0);
    }

    /**
     * 目前，你的能力是ability，你来到了index号怪兽的面前，如果要通过后续所有的怪兽，
     * 请返回需要花的最少钱数
     */
    private static long process1(int[] power, int[] money, int ability, int index) {
        if (index == power.length) {
            return 0;
        }

        long minValue = 0;
        // process1调用时，index++和index+1有区别，index++会改变当前的index的值。
        if (ability < power[index]) {
            minValue = money[index] + process1(power, money, ability + power[index], index + 1);
        } else {// ability >= power[index] 可以雇佣，也可以不雇佣
            long hire = money[index] + process1(power, money, ability + power[index], index + 1);
            long noHire = process1(power, money, ability, index + 1);
            minValue = Math.min(hire, noHire);
        }
        return minValue;
    }

    /**
     * 暴力递归尝试 (money值的范围比较小)
     */
    public static long minMoney2(int[] power, int[] money) {
        int N = power.length;

        long allWaste = 0;
        for (int i = 0; i < N; i++) {
            allWaste += money[i];
        }

        for (int waste = 0; waste < allWaste; waste++) {
            if (process2(power, money, N - 1, waste) != -1) {
                return waste;
            }
        }

        return allWaste;
    }

    /**
     * 从0.....index号怪兽，花的钱，必须严格等于waste
     * 如果通过不了，返回-1
     * 如果可以通过，返回能通过情况下的最大能力值。
     */
    private static long process2(int[] power, int[] money, int index, int waste) {
        if (index == -1) {// 一个怪兽也没遇到呢
            return waste == 0 ? 0 : -1;
        }

        // 不雇佣
        long noHire = -1;
        long preMaxAbblity = process2(power, money, index - 1, waste);
        if (preMaxAbblity != -1 && preMaxAbblity >= power[index]) {
            noHire = preMaxAbblity;
        }

        // 雇佣
        long hire = -1;
        preMaxAbblity = process2(power, money, index - 1, waste - money[index]);
        if (preMaxAbblity != -1) {
            hire = preMaxAbblity + power[index];
        }

        return Math.max(noHire, hire);
    }

    /**
     * 动态规划： 能力power * 怪兽数量index
     */
    public static long fun1(int[] power, int[] money) {
        int N = power.length;

        int maxPower = 0;
        for (int i = 0; i < N; i++) {
            maxPower += power[i];
        }

        // 数值转换为索引.要加1.
        int[][] dp = new int[N + 1][maxPower + 1];

        for (int i = N - 1; i >= 0; i--) {
            for (int j = 0; j <= maxPower; j++) {
                // 如果这种情况发生，那么这个hp必然是递归过程中不会出现的状态
                // 既然动态规划是尝试过程的优化，尝试过程碰不到的状态，不必计算
                if (j + power[i] > maxPower) {
                    continue;
                }

                // 当前英雄能力小于i号怪兽的能力。
                if (j < power[i]) {
                    // 当前怪兽的雇佣花费，加上，通过i之后所有怪兽的最小花费
                    dp[i][j] = money[i] + dp[i + 1][j + power[i]];
                } else {
                    dp[i][j] = Math.min(dp[i + 1][j], money[i] + dp[i + 1][j + power[i]]);
                }
            }
        }

        // 通过0号之后所有怪兽的最小花费
        return dp[0][0];
    }

    /**
     * 动态规划：花费waste * 怪兽数量index
     */
    public static long fun2(int[] power, int[] money) {
        int N = money.length;

        int maxMoney = 0;
        for (int i = 0; i < N; i++) {
            maxMoney += money[i];
        }

        // dp[i][j]含义：
        // 能经过0～i的怪兽，且花钱为j（花钱的严格等于j）时的武力值最大是多少？
        // 如果dp[i][j]==-1，表示经过0～i的怪兽，花钱为j是无法通过的，或者之前的钱怎么组合也得不到正好为j的钱数
        // 数值转换为索引.要加1.
        int[][] dp = new int[N][maxMoney + 1];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j <= maxMoney; j++) {
                dp[i][j] = -1;
            }
        }

        // 从上往下填，经过0-0位置的怪兽，花钱数一定为money[0]，达到武力值power[0]的地步。其他第0行的状态一律是无效的
        dp[0][money[0]] = power[0];
        for (int i = 1; i < N; i++) {
            for (int j = 0; j <= maxMoney; j++) {
                // 雇佣
                // 存在条件：
                // j - p[i]要不越界，当前花费至少比i位置怪兽的价格大
                // 并且在钱数为j - p[i]时，要能通过0～i-1的怪兽，并且钱数组合是有效的。
                if (j >= money[i] && dp[i - 1][j - money[i]] != -1) {
                    dp[i][j] = dp[i - 1][j - money[i]] + power[i];
                }

                // 不雇佣
                // 存在条件：
                // 0~i-1怪兽在花钱为j的情况下，能保证通过当前i位置的怪兽
                if (dp[i - 1][j] >= power[i]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                }
            }
        }

        int ans = 0;
        for (int i = 0; i <= maxMoney; i++) {
            if (dp[N - 1][i] != -1) {
                ans = i;
                break;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] power = { 2, 2, 3 };
        int[] money = { 5, 1, 3 };
        System.out.println(minMoney1(power, money));
        System.out.println(minMoney2(power, money));
        System.out.println(fun1(power, money));
        System.out.println(fun2(power, money));

        int len = 10;
        int value = 20;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            int[][] arr = generateTwoArray(len, value);
            power = arr[0];
            money = arr[1];
            long ans1 = minMoney1(power, money);
            long ans2 = minMoney2(power, money);
            long ans3 = fun1(power, money);
            long ans4 = fun2(power, money);
            if (ans1 != ans2 || ans2 != ans3 || ans1 != ans4) {
                System.out.println("oops!");
            }
        }
    }

    private static int[][] generateTwoArray(int len, int value) {
        int size = (int) (Math.random() * len) + 1;
        int[][] arr = new int[2][size];
        for (int i = 0; i < size; i++) {
            arr[0][i] = (int) (Math.random() * value) + 1;
            arr[1][i] = (int) (Math.random() * value) + 1;
        }
        return arr;
    }
}
