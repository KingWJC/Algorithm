/**
 * 贪心算法:5. 做项目获得的最大钱数
 */
package com.example.system.class08;

import java.util.Comparator;
import java.util.PriorityQueue;

public class C05_IPO {
    /**
     * 最多K个项目
     * M是初始资金
     * Profits[] Capital[] 一定等长
     * 返回最终最大的资金
     */
    public static int findMaximizedCapital(int K, int M, int[] profit, int[] capital) {
        PriorityQueue<Program> costQueue = new PriorityQueue<>(new MinCostComparator());
        for (int i = 0; i < profit.length; i++) {
            costQueue.offer(new Program(capital[i], profit[i]));
        }

        PriorityQueue<Program> profitQueue = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < K; i++) {
            while (!costQueue.isEmpty() && costQueue.peek().capital <= M) {
                profitQueue.offer(costQueue.poll());
            }
            if (profitQueue.isEmpty()) {
                break;
            }
            M += profitQueue.poll().profit;
            // 不需要再放入costQueue中，因为profitQueue中的项目下一次肯定能做，项目的收益都是正整数。
            // while (!profitQueue.isEmpty()) {
            // costQueue.offer(profitQueue.poll());
            // }
        }

        return M;
    }

    private static class MinCostComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o1.capital - o2.capital;
        }
    }

    private static class MaxProfitComparator implements Comparator<Program> {
        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }

    private static class Program {
        public int capital;
        public int profit;

        public Program(int capital, int profit) {
            this.capital = capital;
            this.profit = profit;
        }

        @Override
        public String toString() {
            return "[" + capital + "," + profit + "]";
        }
    }

    public static void main(String[] args) {
        int[] capital = new int[] { 5, 1, 2, 6 };
        int[] profit = new int[] { 1, 3, 5, 4 };
        int ans = findMaximizedCapital(3, 3, profit, capital);
        System.out.println(ans);
    }
}
