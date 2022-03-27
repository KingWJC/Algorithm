/**
 * 按字典序排在最后的子串
 * 测试链接: https://leetcode.com/problems/last-substring-in-lexicographical-order/
 */
package com.example.system.class29;

public class C01_LastSubstring {

    public static String lastSubString(String s) {
        if (s == null || s.length() == 0) {
            return s;
        }

        int min = 0;
        int max = 0;
        char[] chars = s.toCharArray();
        for (char c : chars) {
            min = Math.min(min, c);
            max = Math.max(max, c);
        }

        int n = s.length();
        int[] nums = new int[n];
        for (int i = 0; i < n; i++) {
            nums[i] = chars[i] - min + 1;
        }

        DC3 dc = new DC3(nums, max - min + 1);
        return s.substring(dc.sa[n - 1]);
    }

    public static void main(String[] args) {
        testDC3();
    }

    /**
     * 线性增粘的说明：随长度增加，耗时增加。
     */
    private static void testDC3() {
        // 10000：9ms
        // 100000: 46ms
        // 1000000: 341ms 比10倍的线性特征要好，所以是最优解。
        // 10000000: 10838ms 触发了JVM的垃圾回收机制。
        int len = 10000000;
        int maxValue = 100;
        long start = System.currentTimeMillis();
        int[] nums = new int[len];
        for (int i = 0; i < len; i++) {
            nums[i] = (int) (Math.random() * maxValue) + 1;
        }
        new DC3(nums, maxValue);
        long end = System.currentTimeMillis();
        System.out.println("数据量 " + len + ", 运行时间 " + (end - start) + " ms");
    }
}
