/**
 * 两个字符串的最长公共子串
 */
package com.example.system.class29;

public class C04_LongestCommonSubstring {
    /**
     * 答案解：动态规划
     * 以任意一个字符结尾，判断两字符串的最长公共子串。
     * 从左往右，从上往下推。
     * 数据量大时报错：OutOfMemoryError: Java heap space
     */
    public static int lcs1(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return 0;
        }

        int N = s1.length();
        int M = s2.length();
        int res = 0;
        int[][] dp = new int[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = s1.charAt(i) == s2.charAt(j) ? 1 : 0;
                } else {
                    // 最后一位相同才加1，否则为0，因为子串是连续的。
                    dp[i][j] = s1.charAt(i) == s2.charAt(j) ? dp[i - 1][j - 1] + 1 : 0;
                }
                res = Math.max(res, dp[i][j]);
            }
        }
        return res;
    }

    /**
     * 嵌套循环：空间压缩
     */
    public static int lcs2(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        int res = 0;

        int row = 0;
        int col = M - 1;
        while (row < N) {
            int i = row;
            int j = col;
            int len = 0;
            // 求以i位置开头的字符串与以j位置开头的字符串的最长公共子串的长度
            while (i < N && j < M) {
                if (str1[i] != str2[j]) {
                    len = 0;
                } else {
                    len++;
                }
                if (len > res) {
                    res = len;
                }
                j++;
                i++;
            }

            if (col > 0) {
                col--;
            } else {
                row++;
            }
        }
        return res;
    }

    /**
     * 最优解：DC3算法
     */
    public static int lcs3(String s1, String s2) {
        if (s1 == null || s1.length() == 0 || s2 == null || s2.length() == 0) {
            return 0;
        }

        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        int N = str1.length;
        int M = str2.length;
        // 查找数组中的最大，最小值。
        int min = 0;
        int max = 0;
        for (char num : str1) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        for (char num : str2) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }
        // 处理DC3的参数数组，值必须>=1。
        int[] nums = new int[N + M + 1];
        int index = 0;
        for (char num : str1) {
            nums[index++] = num - min + 2;
        }
        nums[index++] = 1;
        for (char num : str2) {
            nums[index++] = num - min + 2;
        }

        DC3 dc = new DC3(nums, max - min + 2);
        int[] sa = dc.sa;
        int[] height = dc.height;
        int ans = 0;
        for (int i = 1; i < nums.length; i++) {
            int X = sa[i];
            int Y = sa[i - 1];
            // X,Y为后缀开始位置，如果分别处于分隔符的两侧，则height[i]有效
            if (Math.min(X, Y) < N && Math.max(X, Y) > N) {
                ans = Math.max(ans, height[i]);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int size = 10;
        int range = 50;
        int testTime = 100000;
        System.out.println("功能测试开始");
        for (int i = 0; i < testTime; i++) {
            String s1 = randomString(size, range);
            String s2 = randomString(size, range);
            int ans1 = lcs1(s1, s2);
            int ans2 = lcs2(s1, s2);
            int ans3 = lcs2(s1, s2);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(s1);
                System.out.println(s2);
                System.out.println("ans1=" + ans1 + " ans2=" + ans2 + " ans3=" + ans3);
                break;
            }
        }
        System.out.println("功能测试结束");
        System.out.println("==========");
        System.out.println("性能测试开始");
		int len = 80000;
		range = 26;
		long start;
		long end;
        String str1 = randomFixString(len, range);
		String str2 = randomFixString(len, range);

		start = System.currentTimeMillis();
		int ans1 = lcs1(str1, str2);
		end = System.currentTimeMillis();
		System.out.println("方法2结果 : " + ans1 + " , 运行时间 : " + (end - start) + " ms");
        
        start = System.currentTimeMillis();
		int ans2 = lcs3(str1, str2);
		end = System.currentTimeMillis();
		System.out.println("方法3结果 : " + ans2 + " , 运行时间 : " + (end - start) + " ms");
		System.out.println("性能测试结束");
    }

    private static String randomString(int size, int range) {
        int len = (int) (Math.random() * size) + 1;
        char[] strs = new char[len];
        for (int i = 0; i < len; i++) {
            strs[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(strs);
    }

    private static String randomFixString(int len, int range) {
        char[] strs = new char[len];
        for (int i = 0; i < len; i++) {
            strs[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(strs);
    }
}
