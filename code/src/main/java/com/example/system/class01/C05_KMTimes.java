/**
 * 异或运算的练习
 * 数组中，出现K次，M次的数
 */
package com.example.system.class01;

import java.util.HashMap;
import java.util.Map;

import com.example.utility.helper.ArrayTestHelper;

public class C05_KMTimes {
    /**
     * 保证arr中，只有一种数出现了K次，其他数都出现了M次,
     * M > 1, K < M
     * 额外空间复杂度O(1)，时间复杂度O(N)
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        // 统计数组每个数, 位值为1的个数.
        int[] cache = new int[32];
        for (int num : arr) {
            // 虽然两个循环, 但while循环固定执行32次. 时间复杂度为O(N)
            // while (num != 0) {
            // int rightOne = num & (-num);
            // log计算出错.(原因未知)
            // cache[(int) (Math.log(rightOne) / Math.log(2))]++;
            // cache[map.get(rightOne)]++
            // num ^= rightOne;
            // }

            // num右移,判断最后一位是否为1.
            for (int i = 0; i <= 31; i++) {
                cache[i] += (num >> i) & 1;
            }
        }

        int ans = 0;

        // k * a + m * b = cache[i];
        // 两种种情况：k不存在，k存在（出现K次的值为0，或不为0）
        for (int i = 0; i < 32; i++) {
            // k 在i位置上为1
            if (cache[i] % m != 0) {
                if (cache[i] % m == k) {
                    ans |= (1 << i);
                } else {
                    // 不包含K次的数
                    return -1;
                }
            }
        }

        // 检查是否含K个0.
        if (ans == 0) {
            int count = 0;
            for (int i : arr) {
                if (i == 0) {
                    count++;
                }
            }
            if (count != k)
                return -1;
        }

        return ans;
    }

    /**
     * 用Hashmap存储每位1的值.
     */
    public static void mapCreate(HashMap<Integer, Integer> map) {
        int value = 1;
        for (int i = 0; i < 32; i++) {
            map.put(value, i);
            value <<= 1;
        }
    }

    /**
     * 保证arr中，只有一种数出现了K次，其他数都出现了M次
     * 额外空间复杂度O(N)，时间复杂度O(N)
     */
    public static int testKTimes(int[] arr, int k, int m) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            if (map.containsKey(arr[i])) {
                map.put(arr[i], map.get(arr[i]) + 1);
            } else {
                map.put(arr[i], 1);
            }
        }

        for (Map.Entry<Integer, Integer> obj : map.entrySet()) {
            if (obj.getValue() == k) {
                return obj.getKey();
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] arr3 = { -1, 3, 1, 3, 3, 1, 1, -1 };
        int k1 = 2;
        int m1 = 3;
        System.out.println(onlyKTimes(arr3, k1, m1));

        ArrayTestHelper.testKMTimes((arr, k, m) -> onlyKTimes(arr, k, m), (arr, k, m) -> testKTimes(arr, k, m));
    }
}
