/**
 * 整数数组子数组平均值小于等于V的最大长度
 */
package com.example.system.class26;

import java.util.TreeMap;

public class C03_LongestAvgLessSumSubArray {
    /**
     * 暴力解，时间复杂度O(N^3)
     */
    public static int ways1(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int ans = 0;
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j < N; j++) {
                int sum = 0;
                for (int l = i; l <= j; l++) {
                    sum += arr[l];
                }

                int k = j - i + 1;
                double avg = (double) sum / (double) k;
                if (avg <= v) {
                    ans = Math.max(ans, k);
                }
            }
        }
        return ans;
    }

    /**
     * 次优解，时间复杂度O(N*logN)
     */
    public static int ways2(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        // 存储[0-i]范围内，每个元素减去v的累加和。
        int modify = 0;
        // -(modify+v) 最早出现的索引位置。
        TreeMap<Integer, Integer> origins = new TreeMap<>();
        int ans = 0;
        for (int i = 0; i < N; i++) {
            int p1 = arr[i] <= v ? 1 : 0;
            int p2 = 0;
            int query = -(arr[i] + modify);
            if (origins.floorKey(query) != null) {
                p2 = i - origins.get(origins.floorKey(query)) + 1;
            }
            ans = Math.max(ans, Math.max(p1, p2));

            // arr[i]等于curOrigin时，才能和map.get(curOrigin)位置，组成最长子数组，且avg<=v
            int curOrigin = -(modify + v);
            if (origins.containsKey(curOrigin)) {
                origins.put(curOrigin, i);
            }

            modify += arr[i] - v;
        }
        return ans;
    }

    /**
     * 最优解，时间复杂度O(N)
     */
    public static int ways3(int[] arr, int v) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int N = arr.length;
        for (int i = 0; i < N; i++) {
            arr[i] = arr[i] - v;
        }

        int[] sums = new int[N];
        int[] ends = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            if (i != N - 1 && sums[i + 1] < 0) {
                sums[i] = arr[i] + sums[i + 1];
                ends[i] = ends[i + 1];
            } else {
                sums[i] = arr[i];
                ends[i] = i;
            }
        }

        int k = 0;
        int end = 0;
        int sum = 0;
        int ans = 0;
        for (int i = 0; i < N; i++) {
            while (end < N && sum + sums[end] <= k) {
                sum += sums[end];
                end = ends[end] + 1;
            }
            ans = Math.max(ans, end - i);

            if (end > i) {
                sum -= arr[i];
            } else {
                end = i + 1;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
		System.out.println("测试开始");
		int maxLen = 20;
		int maxValue = 100;
		int testTime = 500000;
		for (int i = 0; i < testTime; i++) {
			int[] arr = randomArray(maxLen, maxValue);
			int value = (int) (Math.random() * maxValue);

            int[] arr1 = copyArray(arr);
			int[] arr2 = copyArray(arr);
			int[] arr3 = copyArray(arr);
			int ans1 = ways1(arr1, value);
			int ans2 = ways2(arr2, value);
			int ans3 = ways3(arr3, value);

            if (ans1 != ans2 || ans1 != ans3) {
				System.out.println("测试出错！");
				System.out.print("测试数组：");
				System.out.println("子数组平均值不小于 ：" + value);
				System.out.println("方法1得到的最大长度：" + ans1);
				System.out.println("方法2得到的最大长度：" + ans2);
				System.out.println("方法3得到的最大长度：" + ans3);
				System.out.println("=========================");
                break;
			}
        }
        System.out.println("测试结束");
    }

    public static int[] randomArray(int maxLen, int maxValue) {
		int len = (int) (Math.random() * maxLen) + 1;
		int[] ans = new int[len];
		for (int i = 0; i < len; i++) {
			ans[i] = (int) (Math.random() * maxValue);
		}
		return ans;
	}

	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}
}
