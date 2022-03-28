/**
 * 字符消除游戏,返回最少的剩余字符数量
 */
package com.example.system.class30;

public class C03_DeleteAdjacentSameCharacter {
    /**
     * 暴力递归解：穷举L~R的所有可能性
     */
    public static int restMin1(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {
            return s.length();
        }

        int N = s.length();
        // 最大长度就是原字符串的长度
        int min = N;
        for (int L = 0; L < N; L++) {
            for (int R = L + 1; R < N; R++) {
                // subString截取范围【L,R)
                char[] str = s.substring(L, R + 1).toCharArray();
                boolean canDelete = true;
                for (int i = 1; i < str.length; i++) {
                    if (str[i] != str[i - 1]) {
                        canDelete = false;
                    }
                }

                if (canDelete) {
                    min = Math.min(min, restMin1(s.substring(0, L) + s.substring(R + 1, N)));
                }
            }
        }
        return min;
    }

    /**
     * 优良尝试的暴力递归：范围上的尝试
     */
    public static int restMin2(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {
            return s.length();
        }
        char[] str = s.toCharArray();
        return process2(str, 0, str.length - 1, false);
    }

    /**
     * str[L...R]上消除字符，且str[L-1]是否等于str[L]
     * 返回最少的剩余字符数量
     */
    private static int process2(char[] str, int L, int R, boolean has) {
        if (L > R) {
            return 0;
        }
        if (L == R) {
            return has ? 0 : 1;
        }
        // 第一个不是str[L]字符的位置
        int index = L;
        int K = has ? 1 : 0;
        while (index <= R && str[index] == str[L]) {
            // 肯定会执行一次。
            K++;
            index++;
        }
        // K总会++，不可能等于0.
        // str[L]位置能否被消掉
        int min = (K > 1 ? 0 : 1) + process2(str, index, R, false);
        for (int i = index + 1; i <= R; i++) {
            if (str[i] == str[L] && str[i] != str[i - 1]) {
                if (process2(str, index, i - 1, false) == 0) {
                    min = Math.min(min, process2(str, i, R, true));
                }
            }
        }
        return min;
    }

    /**
     * 记忆化搜索
     */
    public static int restMin3(String s) {
        if (s == null) {
            return 0;
        }
        if (s.length() < 2) {
            return s.length();
        }

        char[] str = s.toCharArray();
        int N = str.length;
        int[][][] dp = new int[N][N][2];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < 2; k++) {
                    dp[i][j][k] = -1;
                }
            }
        }
        return process3(str, 0, N - 1, false, dp);
    }

    private static int process3(char[] str, int L, int R, boolean has, int[][][] dp) {
        if (L > R) {
            return 0;
        }
        // L-1有相同，K=1。
        int K = has ? 1 : 0;
        if (dp[L][R][K] != -1) {
            return dp[L][R][K];
        }

        int ans = 0;
        if (L == R) {
            ans = has ? 0 : 1;
        } else {
            int index = L;
            int all = K;
            while (index <= R && str[index] == str[L]) {
                index++;
                all++;
            }
            ans = (all > 1 ? 0 : 1) + process3(str, index, R, false, dp);
            for (int i = index; i <= R; i++) {
                if (str[i] == str[L] && str[i] != str[i - 1]) {
                    if (process3(str, index, i - 1, false, dp) == 0) {
                        ans = Math.min(ans, process3(str, i, R, true, dp));
                    }
                }
            }
        }
        dp[L][R][K] = ans;
        return ans;
    }

    public static void main(String[] args) {
        int size = 16;
        int range = 3;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            String s = randomString(size, range);
            int ans1 = restMin1(s);
            int ans2 = restMin2(s);
            int ans3 = restMin3(s);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
                System.out.println(s);
				System.out.println(ans1);
				System.out.println(ans2);
				System.out.println(ans3);
                break;
            }
        }
        System.out.println("测试结束");
    }

    private static String randomString(int size, int range) {
        int len = (int) (Math.random() * size) + 1;
        char[] ans = new char[len];
        for (int i = 0; i < len; i++) {
            ans[i] = (char) ((int) (Math.random() * range) + 'a');
        }
        return String.valueOf(ans);
    }
}
