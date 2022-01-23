/**
 * 贴纸拼词|多少张贴纸可以贴出给定字符串
 */
package com.example.system.class11;

import java.util.Hashtable;

public interface C09_StickerToSpellWord {
    /**
     * 暴力尝试
     */
    public static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * 
     * @param stickers 贴纸
     * @param rest     目标字符串剩余的字符
     * @return
     */
    private static int process1(String[] stickers, String target) {
        if (target.length() == 0) {
            return 0;
        }
        // 计算选择第一张贴纸后，目标字符串需要的张数。
        int min = Integer.MAX_VALUE;
        for (String sticker : stickers) {
            String rest = minusString(target, sticker);
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }
        // 贴纸无法拼成目标字符串,之前的贴纸选择数无效。
        if (min == Integer.MAX_VALUE) {
            return min;
        }
        // 选择第一张贴纸后的张数，加上已选择的第一张。
        return min + 1;
    }

    /**
     * 出现的字符都是小写英文
     */
    private static String minusString(String target, String minusString) {
        char[] tar = target.toCharArray();
        char[] min = minusString.toCharArray();
        int[] ans = new int[26];
        for (int i = 0; i < tar.length; i++) {
            ans[tar[i] - 'a']++;
        }
        for (int i = 0; i < min.length; i++) {
            ans[min[i] - 'a']--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ans.length; i++) {
            if (ans[i] > 0) {
                for (int j = 0; j < ans[i]; j++) {
                    sb.append((char) ('a' + i));
                }
            }
        }
        return sb.toString();
    }

    /**
     * 递归优化:
     * 1.词频统计表
     * 2.剪枝，使用贪心。
     */
    public static int minStickers2(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] chars = stickers[i].toCharArray();
            for (char c : chars) {
                counts[i][c - 'a']++;
            }
        }
        int ans = process2(counts, target);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process2(int[][] counts, String target) {
        if (target.length() == 0) {
            return 0;
        }

        char[] tar = target.toCharArray();
        int[] tcount = new int[26];
        for (char c : tar) {
            tcount[c - 'a']++;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < counts.length; i++) {
            int[] sticker = counts[i];
            // 贪心策略：每次选择包含目标字符串的第一个字符的贴纸。
            if (sticker[tar[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcount[j] > 0) {
                        int num = tcount[j] - sticker[j];
                        if (num > 0) {
                            for (int n = 0; n < num; n++) {
                                sb.append((char) ('a' + j));
                            }
                        }
                    }
                }
                // 因为贪心策略，所以不需要判断sb.toString()!=target.length
                min = Math.min(min, process2(counts, sb.toString()));
            }
        }

        if (min == Integer.MAX_VALUE) {
            return min;
        }
        return min + 1;
    }

    /**
     * 动态规划: 记忆化搜索优化，没有必要改为严格表结构的动态规划
     */
    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] sticker = stickers[i].toCharArray();
            for (char c : sticker) {
                counts[i][c - 'a']++;
            }
        }

        Hashtable<String, Integer> dp = new Hashtable<>();
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    /**
     * HashTable做缓存。
     */
    private static int process3(int[][] counts, String target, Hashtable<String, Integer> dp) {
        if (dp.containsKey(target)) {
            return dp.get(target);
        }

        char[] tarChars = target.toCharArray();
        int[] tCount = new int[26];
        for (char c : tarChars) {
            tCount[c - 'a']++;
        }

        int N = counts.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = counts[i];
            if (sticker[tarChars[0] - 'a'] > 0) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    int num = tCount[i] - sticker[i];
                    if (num > 0) {
                        for (int n = 0; n < num; n++) {
                            sb.append((char) (j + 'a'));
                        }
                    }
                }
                min = Math.min(min, process3(counts, sb.toString(), dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(target, ans);
        return ans;
    }

    public static void main(String[] args) {
        String[] stickers = { "ba", "c", "abcd" };
        String target = "babac";
        int ans1 = minStickers1(stickers, target);
        int ans2 = minStickers2(stickers, target);
        int ans3 = minStickers3(stickers, target);
        System.out.println(ans1 + " " + ans2 + " " + ans3);
    }
}
