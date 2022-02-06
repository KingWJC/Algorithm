/**
 * 判断是否是旋转字符串
 */
package com.example.system.class15;

public class C03_IsRotation {
    /**
     * 举例并推断。
     */
    public static boolean isRotation(String a, String b) {
        if (a == null || b == null || a.length() != b.length()) {
            return false;
        }

        // a+a如果包含b，则a和b是旋转字符串。
        String str = a + a;
        return getIdnexOf(str.toCharArray(), b.toCharArray()) != -1;
    }

    /**
     * KMP
     */
    private static int getIdnexOf(char[] str, char[] match) {
        if (str == null || match == null || str.length < 1 || str.length < match.length) {
            return -1;
        }

        int x = 0;
        int y = 0;
        int[] next = getNextArray(match);
        while (x < str.length && y < match.length) {
            if (str[x] == match[y]) {
                x++;
                y++;
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == match.length ? x - y : -1;
    }

    private static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[] { -1 };
        }

        int[] ans = new int[match.length];
        ans[0] = -1;
        ans[1] = 0;
        int index = 2;
        int cn = 0;
        while (index < match.length) {
            if (match[index - 1] == match[cn]) {
                ans[index++] = ++cn;
            } else if (cn > 0) {
                cn = ans[cn];
            } else {
                ans[index++] = 0;
            }
        }
        return ans;
    }

    public static void main(String[] args) {
		String str1 = "yunzuocheng";
		String str2 = "zuochengyun";
		System.out.println(isRotation(str1, str2));
    }
}
