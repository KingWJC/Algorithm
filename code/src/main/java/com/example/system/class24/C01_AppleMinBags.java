/**
 * 至少使用多少袋子装苹果
 */
package com.example.system.class24;

public class C01_AppleMinBags {
    /**
     * 暴力尝试
     */
    public static int minBags(int apple) {
        if (apple < 1) {
            return -1;
        }
        int bag8 = apple >> 3;
        int rest = apple - (bag8 << 3);
        while (bag8 >= 0) {
            if (rest % 6 == 0) {
                return bag8 + rest / 6;
            }
            bag8--;
            rest += 8;
        }

        return -1;
    }

    /**
     * 规律：从18开始，每8个数为1组。
     * 18-25 8个数 偶数返回3，奇数返回-1
     * 26-33 8个数 偶数返回4，奇数返回-1
     * 时间复杂度 O(1)
     */
    public static int minBagsAwesome(int apple) {
        // 奇数一定不符合，返回-1
        if ((apple & 1) != 0) {
            return -1;
        }

        if (apple <= 0) {
            return 0;
        }

        if (apple < 18) {
            if (apple == 6 || apple == 8) {
                return 1;
            } else if (apple == 12 || apple == 14 || apple == 16) {
                return 2;
            } else {
                return -1;
            }
        }

        return (apple - 18) / 8 + 3;
    }

    public static void main(String[] args) {
        // 用对数器分析输出规律
        for (int apple = 0; apple < 100; apple++) {
            System.out.println(apple + ":" + minBags(apple));
        }

        System.out.println(minBagsAwesome(100));
    }
}
