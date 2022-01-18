/**
 * 打印一个字符串的全部排列（字符都要，但顺序不一样）
 */
package com.example.system.class11;

import java.util.ArrayList;
import java.util.List;

public class C03_PrintAllPermutations {
    /**
     * 使用集合存储字符
     */
    public static List<String> permutation1(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }

        ArrayList<Character> chars = new ArrayList<>();
        char[] strs = str.toCharArray();
        for (char c : strs) {
            chars.add(c);
        }
        process1(chars, "", ans);
        return ans;
    }

    private static void process1(ArrayList<Character> chars, String path, List<String> ans) {
        if (chars.isEmpty()) {
            ans.add(path);
        } else {
            int N = chars.size();
            for (int i = 0; i < N; i++) {
                char cur = chars.get(i);
                // 不能直接修改path引用的字
                // path = path + String.valueOf(cur);
                chars.remove(i);
                process1(chars, path + cur, ans);

                // 方法的决策需要原始数据时，恢复现场
                chars.add(i, cur);
            }
        }
    }

    /**
     * 在字符串中交换位置
     */
    public static List<String> permutation2(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }
        process2(str.toCharArray(), 0, ans);
        return ans;
    }

    private static void process2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
            return;
        }

        for (int i = index; i < str.length; i++) {
            swap(str, i, index);
            process2(str, index + 1, ans);
            swap(str, i, index);
        }
    }

    private static void swap(char[] str, int a, int b) {
        char temp = str[a];
        str[a] = str[b];
        str[b] = temp;
    }

    /**
     * 不要出现重复的排列
     */
    public static List<String> permutationNoRepeat(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }
        process3(str.toCharArray(), 0, ans);
        return ans;
    }

    private static void process3(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            // 记录当前循环中已出现的字符。
            boolean[] visited = new boolean[256];
            for (int i = index; i < str.length; i++) {
                // 减支：提前减少分支, 字符不重复，
                if (!visited[str[i]]) {
                    visited[str[i]] = true;
                    swap(str, index, i);
                    process3(str, index + 1, ans);
                    swap(str, index, i);
                }
            }
        }
    }

    public static void main(String[] args) {
        String str = "acc";
        List<String> ans1 = permutation1(str);
        for (String s : ans1) {
            System.out.println(s);
        }
        System.out.println("=======");

        List<String> ans2 = permutation2(str);
        for (String s : ans2) {
            System.out.println(s);
        }
        System.out.println("=======");

        List<String> ans3 = permutationNoRepeat(str);
        for (String s : ans3) {
            System.out.println(s);
        }
    }
}
