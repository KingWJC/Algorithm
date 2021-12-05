/**
 * 1-N的阶乘之和 的计算
 */
package com.example.primary;

public class C02_SumOfFactorial {
    public static void main(String[] args) {
        // 双循环.
        sumOfFactorial(10);
        // 单循环. 
        sumOfFactorialized(10);
    }

    private static void sumOfFactorial(int n) {
        long result = 0L;
        for (int i = 1; i <= n; i++) {
            result += factorial(i);
        }
        System.out.println(result);
    }

    private static long factorial(int n) {
        long result = 1L;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private static void sumOfFactorialized(int n) {
        long result = 0L;
        int cur = 1; // 记录上一个阶乘的值.
        for (int i = 1; i <= n; i++) {
            cur *= i;
            result += cur;
        }
        System.out.println(result);
    }
}
