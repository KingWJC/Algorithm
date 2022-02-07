/**
 * 字符串变为回文需要添加的最少字符
 */
package com.example.system.class16;

public class C02_AddShortestEnd {
    /**
     * 找到字符串中包含最后字符的最大回文子串
     * 再加上之前字符串的逆序。
     * 返回逆序的字符串
     */
    public static String shortestEnd(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }

        char[] strChar = getNewString(str);
        int[] pArray = new int[strChar.length];
        int R = -1;
        int C = -1;
        // 包含最后字符串的回文半径
        int maxContainsEnd = -1;
        for (int i = 0; i < strChar.length; i++) {
            pArray[i] = i < R ? Math.min(R - i, pArray[2 * C - i]) : 1;
            while (i - pArray[i] >= 0 && i + pArray[i] < strChar.length) {
                if (strChar[i + pArray[i]] == strChar[i - pArray[i]]) {
                    pArray[i]++;
                } else {
                    break;
                }
            }

            if (i + pArray[i] > R) {
                R = i + pArray[i];
                C = i;
            }

            // 从左往右查找，若找到包含结尾的回文区域，一定是最大的。记录回文半径
            if (R == strChar.length) {
                // 处理串的回文半径就是原始串的回文直径。
                maxContainsEnd = pArray[i];
                break;
            }
        }

        char[] ans = new char[str.length() - maxContainsEnd + 1];
        for (int i = ans.length - 1; i >= 0; i--) {
            // 最后一个位置的字符是，原始串的第一个字符
            // 处理串的位置 = 2 * 原始串的位置 + 1
            ans[i] = strChar[2 * (ans.length - 1 - i) + 1];
        }
        // for (int i = 0; i < ans.length; i++) {
        // ans[ans.length - 1 - i] = strChar[i * 2 + 1];
        // }
        return String.valueOf(ans);
    }

    private static char[] getNewString(String str) {
        char[] s = str.toCharArray();
        char[] ans = new char[2 * s.length + 1];
        int index = 0;
        for (int i = 0; i < ans.length; i++) {
            ans[i] = (i & 1) == 0 ? '#' : s[index++];
        }
        return ans;
    }

    public static void main(String[] args) {
        String str1 = "abcd123321";
        System.out.println(shortestEnd(str1));
    }
}
