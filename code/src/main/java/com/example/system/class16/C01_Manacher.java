/**
 * Manacher:返回最长回文子串的长度
 */
package com.example.system.class16;

public class C01_Manacher {
    /**
     * 
     * 以每个位置为中心，往两边扩，最长回文半径就是最长回文子串的长度
     */
    public static int getMaxLength(String str) {
        if (str == null || str.length() < 1) {
            return 0;
        }

        char[] s = getNewString(str);
        int max = 0;
        for (int i = 0; i < s.length; i++) {
            int pre = i - 1;
            int next = i + 1;
            while (pre >= 0 && next < s.length) {
                if (s[pre] == s[next]) {
                    pre--;
                    next++;
                } else {
                    break;
                }
            }
            // 注意边界问题,索引x到y的长度是，y-x+1(包含两端);
            // 再减去pre和next多扩的1. 共减2.
            max = Math.max(max, next - pre - 1);
        }
        return max / 2;
    }

    /**
     * 获取处理过的字符串：每个字符两边插入相同的字符
     * 插入字符与原始串中字符相同时，不影响结果
     */
    private static char[] getNewString(String str) {
        char spec = '#';
        char[] s = str.toCharArray();
        char[] ans = new char[s.length * 2 + 1];
        int index = 0;
        ans[index++] = spec;
        for (int i = 0; i < s.length; i++) {
            ans[index++] = s[i];
            ans[index++] = spec;
        }

        // 另一种简写的代码
        // for (int i = 0; i < ans.length; i++) {
        // ans[i] = (i & 1) == 0 ? '#' : s[index++];
        // }

        return ans;
    }

    /**
     * Manacher加速：O(N)
     */
    public static int manacher(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        // 处理后，插入特殊字符 "12132" -> "#1#2#1#3#2#"
        char[] s = getNewString(str);
        // 回文半径数组
        int[] pArray = new int[s.length];
        // 记录最右的回文右边界
        // 讲述中：R代表最右的扩成功的位置
        // coding：最右的扩成功位置的，再下一个位置
        int R = -1;
        // 设置R时，当时的回文中心位置
        int C = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < s.length; i++) {
            // 两个大类，三个小类条件下，i位置扩最小回文区域的长度。
            // i位置被R包含的情况。（i"的左边界与L重合）
            pArray[i] = i < R ? Math.min(R - i, pArray[2 * C - i]) : 1;

            // 扩大最小回文区域，5类条件下，只有i在R范围外，i"的回文区域和L重合的条件下，需要继续检查扩大的可能性
            // 最小回文区域内的字符不用再比较，比较最小回文区域的左右边界的下一个的字符
            while (i - pArray[i] >= 0 && i + pArray[i] < s.length) {
                if (s[i - pArray[i]] == s[i + pArray[i]]) {
                    pArray[i]++;
                } else {
                    break;
                }
            }

            // 如果扩大后的有边界比R大，进行更新。
            if (i + pArray[i] > R) {
                R = i + pArray[i];
                C = i;
            }

            // 处理串的最大回文半径减1，就是原始串的最大回文子串的长度（直径）
            max = Math.max(max, pArray[i]);
        }

        return max - 1;
    }

    public static void main(String[] args) {
        int posibilities = 5;
        int strSize = 20;
        int testTimes = 1000000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            String str = getRandomString(posibilities, strSize);
            int ans1 = getMaxLength(str);
            int ans2 = manacher(str);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
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
