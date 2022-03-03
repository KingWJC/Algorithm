/**
 * 是否可以表示成若干连续正数和的数
 */
package com.example.system.class24;

public class C03_MSumToN {
    /**
     * 迭代尝试
     * 以1开始，不断加后面的数，判断是否满足条件
     * 以2开始，不断加后面的数，判断是否满足条件
     */
    public static boolean isMSum1(int n) {
        for (int i = 1; i < n; i++) {
            int sum = i;
            for (int j = i + 1; j < n; j++) {
                if (sum + j == n) {
                    return true;
                } else if (sum + j > n) {
                    // 减枝
                    break;
                }
                sum += j;
            }
        }
        return false;
    }

    public static boolean isMSum2(int n) {
        // ((-n)&n) != n;
        return ((~n + 1) & n) != n;
    }

    public static boolean isMSum3(int n) {
        return ((n - 1) & n) != 0;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            System.out.println(i + ":" + isMSum1(i));
        }

        System.out.println("test begin");
		for (int num = 1; num < 5000; num++) {
			if (isMSum1(num) != isMSum3(num)) {
				System.out.println("Oops!");
                break;
			}
		}
		System.out.println("test end");
    }
}
