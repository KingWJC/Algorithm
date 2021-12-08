/**
 * 打印int整型的二进制数.
 */
package com.example.primary.class01;

public class C01_PrintB {
    private static void print(int num) {
        for (int i = 31; i > -1; i--) {
            int value = (num & (1 << i)) == 0 ? 0 : 1;
            System.out.print(value);
        }
    }

    public static void main(String[] args) {
        print(3);
    }
}
