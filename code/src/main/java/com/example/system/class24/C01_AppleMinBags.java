/**
 * 至少使用多少袋子装苹果
 */
package com.example.system.class24;

public class C01_AppleMinBags {
    public static int minBags(int apple) {
        if (apple < 1) {
            return -1;
        }
        int bag8 = apple >> 3;
        int rest = apple - bag8 << 3;
        while (bag8 >= 0) {
            if (rest % 6 == 0) {
                return bag8 + rest / 6;
            }
            bag8--;
            rest += 8;
        }

        return -1;
    }

    public static void main(String[] args) {
        
    }
}
