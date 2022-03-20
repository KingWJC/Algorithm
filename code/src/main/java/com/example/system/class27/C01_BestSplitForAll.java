/**
 * 非负数组切分成左右两部分累加和的最大值
 * 具有单调性，最优划分点不回退。
 */
package com.example.system.class27;

public class C01_BestSplitForAll {
    /**
     * 暴力解:O(N^2)
     */
    public static int bestSplit1(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int N = arr.length;
        int ans = 0;
        // 划分点在i后
        for (int i = 0; i < N - 1; i++) {
            int sumLeft = 0;
            for (int l = 0; l <= i; l++) {
                sumLeft += arr[l];
            }

            int sumRight = 0;
            for (int r = i + 1; r < N; r++) {
                sumRight += arr[r];
            }

            ans = Math.max(ans, Math.min(sumLeft, sumRight));
        }

        return ans;
    }

    /**
     * 使用预处理的总累计和，计算右部分的累加和：O(N)
     */
    public static int bestSplit2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }

        int N = arr.length;
        // 总的累加和 - 左部分的累加和 = 右部分的累加和
        int sumAll = arr[0];
        for (int i = 1; i < N; i++) {
            sumAll += arr[i];
        }

        int ans = 0;
        int preSum = 0;
        // 划分点在i后 [0...i] [i+1...N-1]
        for (int i = 0; i < N - 1; i++) {
            preSum += arr[i];
            ans = Math.max(ans, Math.min(preSum, sumAll - preSum));
        }
        return ans;
    }

    public static void main(String[] args) {
        int size = 20;
        int max = 30;
        int testTimes = 1000000;
        System.out.println("test begin!");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = randomArray(size, max);
            int ans1 = bestSplit1(arr);
            int ans2 = bestSplit2(arr);
            if (ans1 != ans2) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("finished");
    }

    private static int[] randomArray(int size, int max) {
        int len = (int) (Math.random() * size);
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }
}
