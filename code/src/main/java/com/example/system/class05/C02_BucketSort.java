/**
 * 桶排序,计数排序
 */
package com.example.system.class05;

import java.util.ArrayList;
import java.util.List;

import com.example.utility.helper.ArrayTestHelper;

public class C02_BucketSort {
    /**
     * 桶排序
     */
    public static void bucketSort(int[] arr) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            list.add(arr[i]);
        }
        List<Integer> result = process(list, arr.length / 2);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = result.get(i);
        }
    }

    /**
     * 每次分配的
     */
    private static List<Integer> process(List<Integer> arr, int bucketSize) {
        if (arr == null || arr.size() < 2) {
            return arr;
        }
        // 找到最大值最小值
        int max = arr.get(0), min = arr.get(0);
        for (int i : arr) {
            if (i > max)
                max = i;
            if (i < min)
                min = i;
        }

        // 计算需要多少个桶，并生成桶的容器
        int bucketCount = (max - min) / bucketSize + 1;
        List<List<Integer>> bucketArr = new ArrayList<>();
        for (int i = 0; i < bucketCount; i++) {
            bucketArr.add(new ArrayList<Integer>());
        }

        // 遍历每个元素，计算需要放入几号桶，并放入
        for (int i = 0; i < arr.size(); i++) {
            bucketArr.get((arr.get(i) - min) / bucketSize).add(arr.get(i));
        }

        // 返回排序好的数据结果
        ArrayList<Integer> ans = new ArrayList<>();

        // 每个桶内的数据，再进行桶排序, 然后拼接起来。
        for (int i = 0; i < bucketCount; i++) {
            if (bucketSize == 1) {
                for (int j = 0; j < bucketArr.get(i).size(); j++) {
                    ans.add(bucketArr.get(i).get(j));
                }
            } else {
                if (bucketCount == 1)
                    bucketSize--;

                List<Integer> temp = process(bucketArr.get(i), bucketSize);
                for (Integer integer : temp) {
                    ans.add(integer);
                }
            }
        }

        return ans;
    }

    /**
     * 计数排序
     * 桶的次序就是数据的值。所以不基于比较，且数据样本为正整数。
     * 时间复杂度O(N),额外空间复杂度O(Max)
     */
    public static void countSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        int[] bucket = new int[max + 1];
        for (int i = 0; i < arr.length; i++) {
            bucket[arr[i]]++;
        }

        int j = 0;
        for (int i = 0; i < bucket.length; i++) {
            while (bucket[i]-- > 0) {
                arr[j++] = i;
            }
        }
    }

    public static void main(String[] args) {
        ArrayTestHelper.testSortMethod(C02_BucketSort::countSort,
                C02_BucketSort::bucketSort);
    }
}
