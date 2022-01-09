/**
 * 贪心算法:3. 金条分割的最小代价
 */
package com.example.system.class08;

import java.util.Arrays;
import java.util.PriorityQueue;

import com.example.utility.helper.ArrayTestHelper;

public class C03_LessMoneySplitGold {
    /**
     * 贪心策略: 加入小根堆， 每次前两个数弹出，相加为最后一次的分割费用。
     * 最后第二次的分割费用，肯定包含下一次的费用
     */
    public static int getLessMoney(int[] arr) {
        if (arr == null || arr.length == 0)
            return 0;

        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int i : arr) {
            queue.offer(i);
        }

        int money = 0;
        int cur = 0;
        while (queue.size() > 1) {
            cur = queue.poll() + queue.poll();
            money += cur;
            queue.offer(cur);
        }
        return money;
    }

    /**
     * 暴力！所有情况都尝试！
     */
    public static int getLessMoneyTest(int[] arr) {
        if (arr == null || arr.length == 0)
            return 0;

        return process(arr, 0);
    }

    /**
     * 等待合并的数都在arr里，pre之前的合并行为产生了多少总代价
     */
    private static int process(int[] arr, int pre) {
        // arr中只剩一个数字的时候，停止合并，返回最小的总代价
        if (arr.length == 1)
            return pre;

        int cost = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                int[] next = copyAndMergeTwo(arr, i, j);
                cost = Math.min(cost, process(next, pre + arr[i] + arr[j]));
            }
        }
        return cost;
    }

    private static int[] copyAndMergeTwo(int[] arr, int i, int j) {
        int[] copy = new int[arr.length - 1];
        int index = 0;
        for (int n = 0; n < arr.length; n++) {
            if (n != i && n != j) {
                copy[index++] = arr[n];
            }
        }
        copy[index] = arr[i] + arr[j];
        return copy;
    }

    public static void main(String[] args) {
        int maxLength = 6;
        int maxValue = 1000;
        int testTimes = 10000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = ArrayTestHelper.generateRandomArray(maxLength, maxValue);
            int res1 = getLessMoney(arr);
            int res2 = getLessMoneyTest(arr);
            if (res1 != res2) {
                System.out.println(Arrays.toString(arr));
                System.out.println(res1 + " error " + res2);
                break;
            }
        }
        System.out.println("finished");
    }
}
