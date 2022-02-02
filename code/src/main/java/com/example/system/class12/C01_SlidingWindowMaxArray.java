/**
 * 滑动窗口最大值
 */
package com.example.system.class12;

import java.util.Arrays;
import java.util.LinkedList;

public class C01_SlidingWindowMaxArray {
    /**
     * 暴力遍历求解（面试无分）
     * 
     * @param arr 窗口划过的数组
     * @param w   固定大小为W的窗口
     * @return 每一次滑出状况的最大值
     */
    public static int[] getMaxWindow(int[] arr, int w) {
        if (arr == null || arr.length < w || w < 1) {
            return null;
        }

        int length = arr.length - w + 1;
        int[] ans = new int[length];
        int index = 0;
        int L = 0;
        int R = w - 1;
        while (R < arr.length) {
            int max = arr[L];
            for (int i = L + 1; i <= R; i++) {
                max = Math.max(arr[i], max);
            }
            ans[index++] = max;
            L++;
            R++;
        }
        return ans;
    }

    /**
     * 双端队列优化
     */
    public static int[] getMaxWindowRight(int[] arr, int w) {
        if (arr == null || arr.length < w || w < 1) {
            return null;
        }
        // 窗口最大值的更新结构，
        // 放下标而不放值，因为要检查下标是否过期，也可以通过下标获取值
        LinkedList<Integer> qMax = new LinkedList<>();
        int[] ans = new int[arr.length - w + 1];
        int index = 0;// 结果索引
        for (int R = 0; R < arr.length; R++) {
            while (!qMax.isEmpty() && arr[qMax.peekLast()] <= arr[R]) {
                qMax.pollLast();
            }

            // 存储数组中当前数值的索引
            qMax.addLast(R);

            // 删除qMax中的过期下标
            if (qMax.peekFirst() == R - w) {
                qMax.pollFirst();
            }

            // 形成一个完整窗口后，收集答案
            if (R >= w - 1) {
                ans[index++] = arr[qMax.peekFirst()];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int testTime = 100000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArr(maxSize, maxValue);
            int w = (int) (Math.random() * (arr.length + 1));
            int[] ans1 = getMaxWindow(arr, w);
            int[] ans2 = getMaxWindowRight(arr, w);
            if (!isEqual(ans1, ans2)) {
                System.out.println("w=" + w);
                System.out.println("Oops!" + Arrays.toString(arr));
                System.out.println("ans1=" + Arrays.toString(ans1));
                System.out.println("ans2=" + Arrays.toString(ans2));
                break;
            }
        }
        System.out.println("test finish");
    }

    private static int[] generateRandomArr(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * (maxValue + 1));
        }
        return arr;
    }

    private static boolean isEqual(int[] arr1, int[] arr2) {
        if (arr1 == null && arr2 == null) {
            return true;
        }

        if ((arr1 == null && arr2 != null) || (arr2 == null && arr1 != null)) {
            return false;
        }

        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i = 0; i < arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
