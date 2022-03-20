/**
 * 非负数组切分成左右两部分累加和的最大值的数组
 * 具有单调性，最优划分点不回退。
 */
package com.example.system.class27;

public class C02_BestSplitForEveryPosition {
    /**
     * 暴力解:O(N^3)
     */
    public static int[] bestSplit1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int N = arr.length;
        int[] ans = new int[N];
        ans[0] = 0;
        // range表示当前遍历的数组的索引位置
        for (int range = 1; range < N; range++) {
            for (int i = 0; i < range; i++) {
                // 划分点在i后
                int sumL = 0;
                for (int l = 0; l <= i; l++) {
                    sumL += arr[l];
                }
                int sumR = 0;
                for (int R = i + 1; R <= range; R++) {
                    sumR += arr[R];
                }
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    /**
     * 使用前缀和数组:O(N^2)
     */
    public static int[] bestSplit2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int N = arr.length;
        // 前缀和多一位放0，计算L-R的累加和不需要判断边界情况，直接sum[R+1]-sum[L]
        // arr = {5, 3, 1, 3}
        //        0  1  2  3
        // sum ={0, 5, 8, 9, 12}
        //       0  1  2  3  4
        // 0~2 -> sum[3] - sum[0]
        // 1~3 -> sum[4] - sum[1]
        int[] sum = new int[N + 1];
        sum[0] = 0;
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }

        int[] ans = new int[N];
        ans[0] = 0;
        for (int range = 1; range < N; range++) {
            for (int i = 0; i < range; i++) {
                int sumL = sum[i + 1];
                int sumR = sum[range + 1] - sum[i + 1];
                ans[range] = Math.max(ans[range], Math.min(sumL, sumR));
            }
        }
        return ans;
    }

    /**
     * 使用前缀和，最优划分不回退：O(N)
     */
    public static int[] bestSplit3(int[] arr) {
        if (arr == null || arr.length == 0) {
            return new int[0];
        }

        int N = arr.length;
        int[] sum = new int[N + 1];
        sum[0] = 0;
        for (int i = 0; i < N; i++) {
            sum[i + 1] = sum[i] + arr[i];
        }

        int[] ans = new int[N];
        ans[0] = 0;
        // 上一步的最优划分 0~range-1上，如range=2，则是arr中0，1位置的数。
        // 最优划分是左部分[0~best] 右部分[best+1~range-1]
        int best = 0;
        for (int range = 1; range < N; range++) {
            while (best + 1 < range) {
                int sumL = sum[best + 1];
                int sumR = sum[range + 1] - sum[best + 1];
                int before = Math.min(sumL, sumR);

                sumL = sum[best + 2];
                sumR = sum[range + 1] - sum[best + 2];
                int after = Math.min(sumL, sumR);
                // 注意，一定要是<=，数为0的情况zz
                if (before <= after) {
                    best++;
                } else {
                    break;
                }
            }
            ans[range] = Math.min(sum[best + 1], sum[range + 1] - sum[best + 1]);
        }

        return ans;
    }

    public static void main(String[] args) {
        int N = 20;
        int max = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            int[] arr = randomArray(len, max);
            int[] ans1 = bestSplit1(arr);
            int[] ans2 = bestSplit2(arr);
            int[] ans3 = bestSplit3(arr);
            if (!isSameArray(ans1, ans2) || !isSameArray(ans1, ans3)) {
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static int[] randomArray(int size, int max) {
        int len = (int) (Math.random() * size);
        int[] ans = new int[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (int) (Math.random() * max);
        }
        return ans;
    }

    private static boolean isSameArray(int[] arr1, int[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        int N = arr1.length;
        for (int i = 0; i < N; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
