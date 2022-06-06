/**
 * KMP算法: 利用之前尝试后的结果。
 */
package com.example.system.class15;

public class C01_KMP {
    /**
     * 查询match字符串在str字符串中完全匹配的起始位置
     */
    public static int getIdnexOf(String str, String match) {
        if (str == null || match == null || str.length() < 1 || match.length() > str.length()) {
            return -1;
        }

        char[] s = str.toCharArray();
        char[] m = match.toCharArray();
        // 两个字符串各自比对到的位置（开始位置可以通过长度推出来）
        int x = 0;
        int y = 0;
        // O(M) m <= n
        int[] next = getNextArray(m);
        // O(N) 当有一个条件不满足，说明匹配结束
        while (x < s.length && y < m.length) {
            if (s[x] == m[y]) {
                x++;
                y++;
            } else if (next[y] == -1) {
                // y==0时还不匹配，尝试str的下个位置
                x++;
            } else {
                y = next[y];
            }
        }
        // 如果y==s1.length，则说明一定找到了匹配的位置。
        return y == m.length ? x - y : -1;
    }

    /**
     * 快速获取匹配字符串match的next数组
     */
    private static int[] getNextArray(char[] match) {
        if (match.length == 1) {
            return new int[] { -1 };// 任何字符串，0位置都是-1.
        }

        int[] ans = new int[match.length];
        // 人为规定位置0和1之前的前缀后缀串的相等长度
        ans[0] = -1;
        ans[1] = 0;
        // 计算当前位置之前的前缀后缀串长度相等的长度
        int index = 2;
        // 当前是哪个位置的值再和i-1位置的字符比较, 前一个位置的next信息
        int cn = 0;
        while (index < ans.length) {
            if (match[index - 1] == match[cn]) {
                // 配成功的时候
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
        int posibilities = 5;
        int strSize = 20;
        int matchSize = 8;
        int testTimes = 5000000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(posibilities, strSize);
            String match = getRandomString(posibilities, matchSize);
            int ans1 = getIdnexOf(str, match);
            int ans2 = str.indexOf(match);
            if (ans1 != ans2) {
                System.out.println("error ans1=" + ans1 + " ans2=" + ans2);
                System.out.println(str);
                System.out.println(match);
                break;
            }
        }
        System.out.println("finish");
    }

    private static String getRandomString(int posibilities, int size) {
        int count = (int) (Math.random() * size) + 1;
        char[] ans = new char[count];
        for (int i = 0; i < count; i++) {
            ans[i] = (char) ((int) (Math.random() * posibilities) + 'a');
        }
        return String.valueOf(ans);
    }
}
