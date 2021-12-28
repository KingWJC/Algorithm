/**
 * 基数排序
 */
package com.example.system.class05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class C03_RadixSort {
    /**
     * 基数排序
     * 按个十百千万的每一位作为权重进行排序，
     * 从低位开始，低位排好的顺序不会更改，最后由高位决定。
     * 样本数据是十进制的正整数。
     */
    public static void radixSort(int[] arr) {
        if (arr == null || arr.length < 2)
            return;

        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            max = Math.max(max, arr[i]);
        }

        // 最大值的十进制位数
        int digit = 0;
        while (max > 0) {
            digit++;
            max /= 10;
        }

        // useBucketList(arr, digit);
        useArray(arr, 0, arr.length - 1, digit);
    }

    /**
     * 使用10个桶(队列），存储对应的值。
     */
    private static void useBucketList(int[] arr, int digit) {
        List<List<Integer>> bucket = new ArrayList<>();

        for (int i = 1; i <= digit; i++) {
            bucket.clear();
            for (int j = 0; j < 10; j++) {
                bucket.add(new ArrayList<>());
            }

            for (int m = 0; m < arr.length; m++) {
                int num = getDigit(arr[m], i);
                bucket.get(num).add(arr[m]);
            }

            int index = 0;
            for (int n = 0; n < bucket.size(); n++) {
                for (int m = 0; m < bucket.get(n).size(); m++) {
                    arr[index++] = bucket.get(n).get(m);
                }
            }
        }
    }

    /**
     * 优化版本，减少额外空间复杂度，
     * 没有桶，只需要一个相同长度的辅助数组，和十进制的count数组
     */
    private static void useArray(int[] arr, int L, int R, int digit) {
        final int radix = 10;
        // 准备相同长度的辅助空间
        int[] help = new int[R - L + 1];
        int i = 0, j = 0;
        for (int d = 1; d <= digit; d++) {
            // 记录每一位为0-9的数有多少个
            // count[0] 当前位(d位)是0的数字有多少个
            // count[2] 当前位(d位)是2的数字有多少个
            int[] count = new int[radix];
            for (i = L; i <= R; i++) {
                int index = getDigit(arr[i], d);
                count[index]++;
            }
            // 记录每一位小于等于当前索引值的个数
            // count[2] 当前位(d位)是(0、1和2)的数字有多少个
            // count[i] 当前位(d位)是(0~i)的数字有多少个
            // <<<可以得到当前位为i的数，在新数组help中的索引位置。>>>
            for (i = 1; i < radix; i++) {
                count[i] += count[i - 1];
            }

            // 从右往左遍历，因为count数组是统计index左侧数字(0~index)的出现次数。
            // 《个》位从小到大排序后，计算《十》位的顺序时，就需要从右往左填，
            // 保证在十位相同的情况下，《个》位上的有序不被破坏,所以此处不能从左往右排
            // for (i = L; i <= R; i++) {
            for (i = R; i >= L; i--) {
                int index = getDigit(arr[i], d);
                // 得到最右位置的统计结果，减1后得到索引值
                help[count[index] - 1] = arr[i];
                count[index]--;
            }

            for (i = L, j = 0; j < help.length; i++, j++) {
                arr[i] = help[j];
            }
        }
    }

    /**
     * 获取十进制的位数值。
     */
    private static int getDigit(int value, int digit) {
        return (value / (int) Math.pow(10, digit - 1)) % 10;
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 53, 29, 67, 40, 85, 30, 82, 87 };
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}