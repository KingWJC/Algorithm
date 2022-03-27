/**
 * 插入字符串形成的字典序最大结果
 */
package com.example.system.class29;

public class C02_InsertS2MakeMost {
    /**
     * 暴力解：O(N^2)
     */
    public static String mostCombine1(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return str2;
        }
        if (str2 == null || str2.length() == 0) {
            return str1;
        }

        int N = str1.length();
        String ans = str2 + str1;
        for (int i = 1; i <= N; i++) {
            // substring [0,i)范围的字符。
            String end = i == N ? "" : str1.substring(i);
            String cur = str1.substring(0, i) + str2 + end;
            if (cur.compareTo(ans) > 0) {
                ans = cur;
            }
        }
        return ans;
    }

    /**
     * 最优解：DC3算法，O(N)
     */
    public static String mostCombine2(String str1, String str2) {
        if (str1 == null || str1.length() == 0) {
            return str2;
        }
        if (str2 == null || str2.length() == 0) {
            return str1;
        }

        char[] str = (str1 + str2).toCharArray();
        int N = str1.length();
        int M = str2.length();
        int min = str[0];
        int max = str[0];
        for (char num : str) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        int[] nums = new int[N + M + 1];
        int index = 0;
        for (char num : str) {
            if (index == str1.length()) {
                nums[index++] = 1;
            }
            nums[index++] = num - (min - 2);
        }
        DC3 dc = new DC3(nums, max - (min - 2));
        int[] rank = dc.rank;
        for (int i = 0; i < N; i++) {
            if (rank[i] < rank[N + 1]) {
                int bestInsert = bestSplit(str1, str2, i);
                return str1.substring(0, bestInsert) + str2 + str1.substring(bestInsert);
            }
        }

        return str1 + str2;
    }

    private static int bestSplit(String s1, String s2, int begin) {
        int N = s1.length();
        int M = s2.length();
        // 默认为s1的最后位置，否则s1长度为1时出错。
        int end = N;
        for (int i = begin, j = 0; i < N && j < M; i++, j++) {
            if (s1.charAt(i) < s2.charAt(j)) {
                end = i;
                break;
            }
        }

        int bestInsert = begin;
        String prefix = s2;
        for (int i = begin + 1, j = M - 1; i <= end; i++, j--) {
            String cur = s1.substring(begin, i) + s2.substring(0, j);
            if (cur.compareTo(prefix) >= 0) {
                prefix = cur;
                bestInsert = i;
            }
        }
        return bestInsert;
    }

    public static void main(String[] args) {
        int range = 10;
        int maxLen = 50;
        int testTime = 100000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            String str1 = randomString(maxLen, range);
            String str2 = randomString(maxLen, range);
            String ans1 = mostCombine1(str1, str2);
            String ans2 = mostCombine2(str1, str2);
            if (!ans1.equals(ans2)) {
                System.out.println("Oops!");
                System.out.println(str1);
                System.out.println(str2);
                System.out.println(ans1);
                System.out.println(ans2);
                break;
            }
        }
        System.out.println("功能测试结束");

        System.out.println("性能测试开始");
		int s1Len = 1000000;
		int s2Len = 500;
		String s1 = randomString(s1Len, range);
		String s2 = randomString(s2Len, range);
		long start = System.currentTimeMillis();
        // mostCombine1 暴力解需要154402 ms
        // mostCombine2 最优解需要192 ms
		mostCombine2(s1, s2);
		long end = System.currentTimeMillis();
		System.out.println("运行时间 : " + (end - start) + " ms");
		System.out.println("性能测试结束");
    }

    /**
     * 生成随机字符串。
     */
    private static String randomString(int maxLen, int range) {
        int len = (int) (Math.random() * maxLen) + 1;
        char[] chars = new char[len];
        for (int i = 0; i < len; i++) {
            chars[i] = (char) ((int) (Math.random() * range) + '0');
        }
        return String.valueOf(chars);
    }
}
