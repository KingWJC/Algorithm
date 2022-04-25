/**
 * 贪心算法:1.字符串组成的数组拼接后字典序最小的结果
 */
package com.example.system.class08;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

import com.example.utility.helper.ArrayTestHelper;

public class C01_LowestLexicography {
    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return (o1 + o2).compareTo(o2 + o1);
        }
    }

    /**
     * 贪心策略: 自定义比较器, 排序后的数组, 拼接后字典序最小.
     */
    public static String lowestString(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        Arrays.sort(strs, new MyComparator());
        StringBuilder sb = new StringBuilder();
        for (String s : strs) {
            sb.append(s);
        }
        return sb.toString();
    }

    /**
     * 对数器
     * 暴力递归解, strs中所有字符串全排列，返回所有可能的结果
     */
    public static String lowestStringTest(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";

        var res = process(strs);
        return res.isEmpty() ? "" : res.first();
    }

    private static TreeSet<String> process(String[] strs) {
        TreeSet<String> ans = new TreeSet<>();
        if (strs.length == 0) {
            // 必须加空字符串, 否则next为空, ans无值,无法拼接.
            ans.add("");
            return ans;
        }

        for (int i = 0; i < strs.length; i++) {
            TreeSet<String> next = process(removeIndexString(strs, i));
            for (String s : next) {
                ans.add(strs[i] + s);
            }
        }
        return ans;
    }

    private static String[] removeIndexString(String[] strs, int index) {
        int len = strs.length;
        String[] ans = new String[len - 1];
        int j = 0;
        for (int i = 0; i < len; i++) {
            if (i != index) {
                // 传递值，而不是引用
                ans[j++] = String.valueOf(strs[i]);
            }
        }
        return ans;
    }

    /**
     * 另一种不同思路的递归
     */
    public static String lowestStringTest1(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }

        ArrayList<String> list = new ArrayList<>();
        process1(strs, new HashSet<>(), "", list);
        list.sort(new Comparator<String>() {
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        return list.get(0);
    }

    /**
     * 
     * @param strs 包含所有的字符串
     * @param use  选择过的字符串，在strs中的下标
     * @param path 选择后拼接好的字符串
     * @param all  收集所有可能的拼接结果
     */
    private static void process1(String[] strs, HashSet<Integer> use, String path, ArrayList<String> all) {
        if (use.size() == strs.length) {
            all.add(path);
        } else {
            for (int i = 0; i < strs.length; i++) {
                if (!use.contains(i)) {
                    use.add(i);
                    process1(strs, use, path + strs[i], all);
                    use.remove(i);
                }
            }
        }
    }

    public static void main(String[] args) {
        int arrLen = 6;
        int strLen = 5;
        int testTimes = 10000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            String[] strs = ArrayTestHelper.generateRandomStringArray(arrLen, strLen);
            String[] copy = ArrayTestHelper.copyArray(strs);

            String res1 = lowestString(strs);
            String res2 = lowestStringTest1(copy);
            if (!res1.equals(res2)) {
                System.out.println(Arrays.toString(strs));
                System.out.println(res1 + " error " + res2);
                break;
            }
        }
        System.out.println("finished");
    }
}
