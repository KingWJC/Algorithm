/**
 * 数字转化为字符串的结果数 : 从右往左的尝试模型
 * 转换规定1和A对应、2和B对应、3和C对应...26和Z对应
 */
package com.example.system.class11;

public class C08_ConvertToLetterString {
    /**
     * 
     * @param str str只含有数字字符0~9
     * @return 返回多少种转化方案
     */
    public static int getNumber(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0);
    }

    /**
     * str[0..i-1]转化无需过问
     * str[i.....]去转化，返回有多少种转化方法
     */
    private static int process(char[] chars, int index) {
        // 当index到达chars的边界时，证明有一种转换结果。
        if (chars.length == index) {
            return 1;
        }

        // 如果后续字符串的前缀是0，规定中没有对应的转换，证明无法转换。
        if (chars[index] == '0') {
            return 0;
        }

        // 转1位数
        int ans = process(chars, index + 1);
        // 转两位数，第一位的值要乘以10.
        if (index + 1 < chars.length && ((chars[index] - '0') * 10 + chars[index + 1] - '0' < 27)) {
            ans += process(chars, index + 2);
        }
        return ans;
    }

    /**
     * 从右往左的动态规划
     * dp[i]表示：str[i...]有多少种转化方式
     */
    public static int useDPR(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chars = str.toCharArray();
        int N = chars.length;
        // 有边界判断，所以加1
        int[] dp = new int[N + 1];
        dp[N] = 1;
        for (int i = N - 1; i >= 0; i--) {
            // 数组默认值为0，所以只考虑有效的结果。
            if (chars[i] != '0') {
                int ans = dp[i + 1];
                if (i + 1 < N && ((chars[i] - '0') * 10 + chars[i + 1] - '0' < 27)) {
                    ans += dp[i + 2];
                }
                dp[i] = ans;
            }
        }

        return dp[0];
    }

    /**
     * 从左往右的动态规划
     * dp[i]表示：str[0...i]有多少种转化方式
     */
    public static int useDPL(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }

        char[] chars = str.toCharArray();
        if (chars[0] == '0') {
            return 0;
        }

        int N = chars.length;
        // 有边界判断，所以加1
        int[] dp = new int[N];
        dp[0] = 1;
        for (int i = 1; i < N; i++) {
            if (chars[i] == '0') {
                // 当前为'0', 只能与前一个字符拼接。
                // 1. 要求前一个字符，不能也是‘0’
                // 2. 要求拼完了要么是10，要么是20，如果更大的话，拼不了
                // 3. 如果str[0...i-2]都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了
                if (chars[i - 1] == '0' || chars[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0)) {
                    return 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                int ans = dp[i - 1];
                if (chars[i - 1] != '0' && ((chars[i - 1] - '0') * 10 + chars[i] - '0' < 27)) {
                    ans += i - 2 >= 0 ? dp[i - 2] : 1;
                }
                dp[i] = ans;
            }
        }
        return dp[N - 1];
    }

    public static void main(String[] args) {
        String str1 = "7210231231232031203123";
        System.out.println(getNumber(str1));
        System.out.println(useDPR(str1));
        System.out.println(useDPL(str1));

        int testTimes = 100000;
        int maxLenth = 30;
        System.out.println("begin test ");
        for (int i = 0; i < testTimes; i++) {
            String str = generateRandomString(maxLenth);
            int ans1 = getNumber(str);
            int ans2 = useDPL(str);
            int ans3 = useDPR(str);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("error " + ans1 + " " + ans2 + " " + ans3);
                break;
            }
        }
        System.out.println("finished");
    }

    private static String generateRandomString(int maxLen) {
        int length = (int) (Math.random() * maxLen + 1);
        char[] chars = new char[length];
        for (int i = 0; i < length; i++) {
            chars[i] = (char) ('0' + (int) (Math.random() * 10));
        }
        return String.valueOf(chars);
    }
}
