/**
 * 所有咖啡杯变干净的最早时间
 */
package com.example.system.class11;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class C13_Coffee {

    /**
     * 暴力尝试（包含做咖啡和洗杯子两个过程）
     * 
     * @param arr 每一个咖啡机冲一杯咖啡的时间
     * @param n   有n个人需要喝咖啡
     * @param a   洗完一个杯子时间为a
     * @param b   自然挥发干净的时间为b
     * @return 从开始等到所有咖啡杯变干净的最短时间
     */
    public static int bestTimes1(int[] arr, int n, int a, int b) {
        int[] times = new int[arr.length];
        int[] drink = new int[n];

        return forceMake(times, drink, 0, arr, n, a, b);
    }

    /**
     * 每个人暴力尝试用每一个咖啡机给自己做咖啡
     * 
     * @param times 咖啡机需要过多久才能空闲。
     * @param drink 每个人喝完咖啡的时间
     * @param index 第几个人
     * @return
     */
    private static int forceMake(int[] times, int[] drink, int index, int[] arr, int n, int a, int b) {
        // 所有人都做好了咖啡，并喝完
        if (index == n) {
            int[] drinks = Arrays.copyOf(drink, index);
            Arrays.sort(drinks);
            return forceWash(0, 0, 0, drinks, a, b);
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            drink[index] = times[i] + arr[i];
            times[i] += arr[i];
            min = Math.min(min, forceMake(times, drink, index + 1, arr, n, a, b));
            // 之后的决策，需要原始数据，恢复现场。
            times[i] -= arr[i];
            // 不需要
            // drink[index] = 0;
        }
        return min;
    }

    /**
     * 每个杯子暴力尝试每一种清洗方式
     * 
     * @param index    第几个杯子
     * @param time     记录总共花费的时间
     * @param washLine 洗杯机需要过多久才能空闲
     * @return 返回清洗index之后所有的杯子，总共花费的时间。
     */
    private static int forceWash(int index, int washLine, int time, int[] drinks, int a, int b) {
        // 洗完所有杯子返回总共花费的时间。
        if (index == drinks.length) {
            return time;
        }

        // 选择一：当前index号咖啡杯，选择用洗咖啡机刷干净
        int wash = Math.max(drinks[index], washLine) + a;
        int ans1 = forceWash(index + 1, wash, Math.max(time, wash), drinks, a, b);

        // 选择二：当前index号咖啡杯，选择自然挥发
        int air = drinks[index] + b;
        int ans2 = forceWash(index + 1, washLine, Math.max(time, air), drinks, a, b);

        return Math.min(ans1, ans2);
    }

    /**
     * 递归优化：贪心+优良暴力
     * 排队使用贪心策略
     * 清洗使用更少可变参数
     */
    public static int bestTimes2(int[] arr, int n, int a, int b) {
        PriorityQueue<CoffeeMachine> machines = new PriorityQueue<>(new MachineComparator());
        for (int work : arr) {
            machines.add(new CoffeeMachine(0, work));
        }

        int[] drink = new int[n];
        for (int i = 0; i < n; i++) {
            CoffeeMachine machine = machines.poll();
            machine.timePoint += machine.workTime;
            drink[i] = machine.timePoint;
            machines.add(machine);
        }

        return minTime(0, 0, drink, a, b);
    }

    /**
     * 抽象咖啡机
     */
    private static class CoffeeMachine {
        // 机器几个小时后空闲
        int timePoint;
        // 制作咖啡的时间
        int workTime;

        public CoffeeMachine(int time, int work) {
            timePoint = time;
            workTime = work;
        }
    }

    /**
     * 按咖啡机的空闲时间和工作时间的和，从小到大排序
     */
    private static class MachineComparator implements Comparator<CoffeeMachine> {
        @Override
        public int compare(CoffeeMachine arg0, CoffeeMachine arg1) {
            return arg0.timePoint + arg0.workTime - arg1.timePoint - arg1.workTime;
        }
    }

    /**
     * 优良暴力
     * 
     * @param index    第几个杯子
     * @param washline 洗杯机什么时候可用
     * @return 返回清洗index之后所有的杯子，总共花费的时间。
     */
    private static int minTime(int index, int washline, int[] drink, int wash, int air) {
        if (index == drink.length) {
            return 0;
        }
        // index号杯子 决定洗
        int clean1 = Math.max(drink[index], washline) + wash;
        int rest1 = minTime(index + 1, clean1, drink, wash, air);
        // 洗杯子的时间可能会很久，所以需要和下一个杯子清洗完的时间比较，取最大，才是所有杯子洗完的时间
        // 注意：不是相加，因为当前杯子洗完的时刻已传入洗下一个杯子的方法中，取最大就行。
        int ans1 = Math.max(clean1, rest1);

        // index号杯子 决定挥发
        int clean2 = drink[index] + air;
        int rest2 = minTime(index + 1, washline, drink, wash, air);
        int ans2 = Math.max(clean2, rest2);

        return Math.min(ans1, ans2);
    }

    /**
     * 动态规划
     */
    public static int bestTimes3(int[] arr, int n, int a, int b) {
        PriorityQueue<CoffeeMachine> machines = new PriorityQueue<>(new MachineComparator());
        for (int work : arr) {
            machines.add(new CoffeeMachine(0, work));
        }

        int[] drink = new int[n];
        for (int i = 0; i < n; i++) {
            CoffeeMachine machine = machines.poll();
            machine.timePoint += machine.workTime;
            drink[i] = machine.timePoint;
            machines.add(machine);
        }
        Arrays.sort(drink);
        return minTimeDP(drink, a, b);
    }

    private static int minTimeDP(int[] drink, int wash, int air) {
        // 若洗杯子时间大于杯子挥发时间，则最优是选择并行挥发
        if (wash > air) {
            return drink[drink.length - 1] + air;
        }

        int n = drink.length;

        // 分析可变参数，确定严格位置依赖表的范围
        // 不需要与所有杯子都自然挥发的时间比较，取最小值。因为两个时间没有可比性。
        int maxFree = 0;
        for (int i = 0; i < n; i++) {
            maxFree = Math.max(drink[i], maxFree) + wash;
        }

        int[][] dp = new int[n + 1][maxFree + 1];
        // dp[n][j]的值都是0.
        for (int index = n - 1; index >= 0; index--) {
            for (int free = 0; free <= maxFree; free++) {
                int clean1 = Math.max(drink[index], free) + wash;
                // 边界判断：超出洗杯机的最大等待可用的时间，
                // 当0 - n-1都使用洗杯机，则free=maxFree，clean1=maxFree+wash>maxFree
                if (clean1 > maxFree) {
                    break;
                }
                int rest1 = dp[index + 1][clean1];
                int ans1 = Math.max(clean1, rest1);

                int clean2 = drink[index] + air;
                int rest2 = dp[index + 1][free];
                int ans2 = Math.max(clean2, rest2);

                dp[index][free] = Math.min(ans1, ans2);
            }
        }

        return dp[0][0];
    }

    public static void main(String[] args) {
        int maxLen = 10;
        int maxValue = 10;
        int testTimes = 100;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = generateRandomArray(maxLen, maxValue);
            int n = (int) (Math.random() * 7) + 1;
            int a = (int) (Math.random() * 7) + 1;
            int b = (int) (Math.random() * 10) + 1;
            int ans1 = bestTimes1(arr, n, a, b);
            int ans2 = bestTimes2(arr, n, a, b);
            int ans3 = bestTimes3(arr, n, a, b);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("error: ans1=" + ans1 + ",ans2=" + ans2 + ",ans3=" + ans3);
                System.out.println("n=" + n + ",a=" + a + ",b=" + b);
                System.out.println(Arrays.toString(arr));
                break;
            }
        }
        System.out.println("finished");
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
