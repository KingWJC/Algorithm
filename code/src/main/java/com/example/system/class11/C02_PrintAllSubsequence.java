/**
 * 打印一个字符串的全部子序列（可以不连续）
 */
package com.example.system.class11;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class C02_PrintAllSubsequence {
    /**
     * 一个字符串的全部子序列
     */
    public static List<String> getSubs(String str) {
        List<String> ans = new ArrayList<>();
        process(str.toCharArray(), 0, ans, "");
        return ans;
    }

    /**
     * 
     * @param chars 固定参数
     * @param index 来到了str[index]字符，index是位置
     * @param ans   把所有生成的子序列，放入到ans里去
     * @param path  str[0..index-1]已经走过了！之前的决定，都在path上,之前的决定已经不能改变.
     */
    private static void process(char[] chars, int index, List<String> ans, String path) {
        // 每个字符都选择完毕
        if (chars.length == index) {
            ans.add(path);
            return;
        }

        // 当前字符不加入序列
        process(chars, index + 1, ans, path);

        // 当前字符加入序列
        process(chars, index + 1, ans, path + String.valueOf(chars[index]));
    }

    /**
     * 不要出现重复字面值的子序列
     */
    public static List<String> getSubRepeat(String str) {
        HashSet<String> sets = new HashSet<>();
        process1(str.toCharArray(), 0, "", sets);
        List<String> ans = new ArrayList<>();
        for (String string : sets) {
            ans.add(string);
        }
        return ans;
    }

    private static void process1(char[] chars, int index, String path, HashSet<String> sets) {
        if (chars.length == index) {
            sets.add(path);
            return;
        }

        String no = path;
        process1(chars, index + 1, no, sets);
        String yes = path + String.valueOf(chars[index]);
        process1(chars, index + 1, yes, sets);
    }

    public static void main(String[] args) {
        String test = "abb";
        List<String> ans1 = getSubs(test);
        for (String s : ans1) {
            System.out.println(s);
        }

        System.out.println("=================");
        List<String> ans2 = getSubRepeat(test);
        for (String str : ans2) {
            System.out.println(str);
        }
    }
}
