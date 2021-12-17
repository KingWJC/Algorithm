/**
 * 异或运算的练习
 * 数组中偶数次，奇数次出现的数
 */
package com.example.system.class01;

public class C04_OddEvenTimes {
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

    public static void main(String[] args) {
        getRightOne(-8);
        getOneCount(-8);

        int[] arr1 = { 3, 3, 2, 3, 1, 1, 1, 3, 1, 1, 1 };
        printOddTimesNum1(arr1);

        int[] arr2 = { 4, 3, 4, 2, 2, 2, 4, 1, 1, 1, 3, 3, 1, 1, 1, 4, 2, 2 };
        printOddTimesNum2(arr2);
    }

}