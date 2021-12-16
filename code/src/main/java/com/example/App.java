package com.example;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        getRightOne(-8);
        getOneCount(-8);

        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);

        int[] arr3 = { -1, 3, 1, 3, 3, 1, 1, -1 };
        int k1 = 2;
        int m1 = 3;
        System.out.println(onlyKTimes(arr3, k1, m1));

        int kinds = 5;
        int range = 200;
        int maxValue = 9;
        int testTimes = 100000;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            int a = (int) (Math.random() * maxValue) + 1; // a 1 ~ 9
            int b = (int) (Math.random() * maxValue) + 1; // b 1 ~ 9
            int k = Math.min(a, b);
            int m = Math.max(a, b);
            if (k == m)
                m++;

            int[] arr = randomArray(range, kinds, k, m);
            int ans1 = testKTimes(arr, k, m);
            int ans2 = onlyKTimes(arr, k, m);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    /**
     * 生成随机arr (只有一种数出现了K次，其他数都出现了M次)
     */
    public static int[] randomArray(int range, int kinds, int k, int m) {
        // 50%概率使用k, 否则使用 [1 ~ m-1]间的数.
        int times = Math.random() < 0.5 ? k : ((int) (Math.random() * (m - 1)) + 1);

        // 数组总共包含多少种数.(2-7)
        int numKinds = (int) (Math.random() * kinds) + 2;
        int[] arr = new int[times + (numKinds - 1) * m];

        // 添充出现k次的数
        int ktime = randomNumber(range);
        int index = 0;
        for (; index < times; index++) {
            arr[index] = ktime;
        }
        numKinds--;

        // 保证每种数都不同
        HashSet<Integer> set = new HashSet<>();
        set.add(ktime);
        while (numKinds != 0) {
            int cur = 0;
            do {
                cur = randomNumber(range);
            } while (set.contains(cur));
            set.add(cur);
            numKinds--;

            for (int i = 0; i < m; i++) {
                arr[index++] = cur;
            }
        }

        for (int i = 0; i < arr.length; i++) {
            // 随机和j位置的数做交换
            int j = (int) (Math.random() * arr.length);// 0 ~ N-1
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }

        return arr;
    }

    /**
     * [-range,range]区间的随机数
     */
    public static int randomNumber(int range) {
        return (int) ((Math.random() * range) + 1) - (int) ((Math.random() * range) + 1);
    }

    /**
     * 一个数组中有一种数出现了奇数次，其他数都出现了偶数次, 找到并打印奇数.
     * arr中，只有一种数，出现奇数次
     */
    public static void printOddTimesNum1(int[] arr) {
        int odd = arr[0];
        for (int i = 1; i < arr.length; i++) {
            odd ^= arr[i];
        }
        System.out.println("arr中，只有一种数，出现奇数次: " + odd);
    }

    /**
     * arr中，有两种数，出现奇数次
     */
    public static void printOddTimesNum2(int[] arr) {
        // a 和 b是两种数, a^b = eor != 0
        int eor = 0;
        for (int i = 0; i < arr.length; i++) {
            eor ^= arr[i];
        }

        // eor最右侧的1，提取出来
        int rightOne = eor & (-eor);

        int first = 0;
        for (int i = 0; i < arr.length; i++) {
            // 用a 和 b 的不同位,找出只包含一个数的集合.
            if ((arr[i] & rightOne) != 0)
                first ^= arr[i];
        }

        System.out.println("arr中，有两种数，出现奇数次: " + first + " " + (first ^ eor));
    }

    /**
     * 提取整形数最右侧的1
     */
    public static void getRightOne(int num) {
        for (int i = 31; i >= 0; i--) {
            int b = (num & (1 << i)) == 0 ? 0 : 1;
            System.out.print(b);
        }
        System.out.println("");
        int x = num & ((~num) + 1);
        System.out.println("整形数最右侧的1在: 第" + Math.log(x) / Math.log(2) + "位");
    }

    /**
     * 计算整型的1的数量
     */
    public static void getOneCount(int num) {
        int count = 0;
        while (num != 0) {
            int rightOne = num & ((~num) + 1);
            count++;
            num ^= rightOne;
        }
        System.out.println("整型的1的数量为:" + count);
    }

    /**
     * 保证arr中，只有一种数出现了K次，其他数都出现了M次,
     * M > 1, K < M
     * 额外空间复杂度O(1)，时间复杂度O(N)
     */
    public static int onlyKTimes(int[] arr, int k, int m) {
        // 统计数组每个数, 位值为1的个数.
        int[] cache = new int[31];
        for (int num : arr) {
            // 虽然两个循环, 但while循环固定执行32次. 时间复杂度为O(N)
            while (num != 0) {
                int rightOne = num & (-num);
                cache[(int) (Math.log(rightOne) / Math.log(2))]++;
                num ^= rightOne;
            }

            // num右移,判断最后一位是否为1.
            // for (int i = 0; i <= 31; i++) {
            // cache[i] += (num >> i) & 1;
            // }
        }

        int ans = 0;

        // k * a + m * b = cache[i];
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
}
