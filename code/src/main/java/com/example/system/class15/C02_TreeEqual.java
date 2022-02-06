/**
 * 判断一个二叉树是否包含另一颗树
 */
package com.example.system.class15;

import java.util.ArrayList;

import com.example.utility.entity.BinaryTreeNode;

public class C02_TreeEqual {
    /**
     * 暴力解：递归判断big的每一个子树是否和small结构相同
     */
    public static boolean containTree1(BinaryTreeNode big, BinaryTreeNode small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }

        if (isSameStructure(big, small)) {
            return true;
        }

        return containTree1(big.left, small) || containTree1(big.right, small);
    }

    /**
     * 递归树结构，判断是否相同
     */
    private static boolean isSameStructure(BinaryTreeNode t1, BinaryTreeNode t2) {
        if (t1 == null && t2 == null) {
            return true;
        }

        if ((t1 == null && t2 != null) || (t1 != null && t2 == null)) {
            return false;
        }

        if (t1.value != t2.value) {
            return false;
        }

        return isSameStructure(t1.left, t2.left) && isSameStructure(t1.right, t2.right);
    }

    /**
     * 转换为String数组，使用KMP算法求解
     */
    public static boolean containTree2(BinaryTreeNode big, BinaryTreeNode small) {
        if (small == null) {
            return true;
        }
        if (big == null) {
            return false;
        }

        ArrayList<String> b = new ArrayList<>();
        preSerial(big, b);
        ArrayList<String> s = new ArrayList<>();
        preSerial(small, s);

        String[] str = new String[b.size()];
        for (int i = 0; i < b.size(); i++) {
            str[i] = b.get(i);
        }

        String[] match = new String[s.size()];
        for (int i = 0; i < s.size(); i++) {
            match[i] = s.get(i);
        }

        return getIndexOf(str, match) != -1;
    }

    /**
     * 先序遍历
     */
    private static void preSerial(BinaryTreeNode head, ArrayList<String> ans) {
        if (head == null) {
            // 二叉树遍历时，为空也要加入
            ans.add(null);
            return;
        }

        ans.add(String.valueOf(head.value));
        preSerial(head.left, ans);
        preSerial(head.right, ans);
    }

    /**
     * 查找匹配位置
     */
    private static int getIndexOf(String[] str, String[] match) {
        if (str == null || match == null || str.length < 1 || str.length < match.length) {
            return -1;
        }

        int x = 0;
        int y = 0;
        int[] next = getNextArray(match);
        while (x < str.length && y < match.length) {
            if (isEquals(str[x], match[y])) {
                x++;
                y++;
            } else if (y == 0) {
                x++;
            } else {
                y = next[y];
            }
        }
        return y == match.length ? x - y : -1;
    }

    /**
     * 快速获取next数组
     */
    private static int[] getNextArray(String[] match) {
        if (match.length == 1) {
            return new int[] { -1 };
        }

        int[] ans = new int[match.length];
        ans[0] = -1;
        ans[1] = 0;

        int index = 2;
        int cn = 0;
        while (index < match.length) {
            if (isEquals(match[index - 1], match[cn])) {
                ans[index++] = ++cn;
            } else if (cn > 0) {
                cn = ans[cn];
            } else {
                ans[index++] = 0;
            }
        }
        return ans;
    }

    /**
     * 判断两个字符串是否相等，排除为null的情况
     */
    private static boolean isEquals(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else {
            if (a == null || b == null) {
                return false;
            } else {
                return a.equals(b);
            }
        }
    }

    public static void main(String[] args) {
        int bigTreeLevel = 7;
        int smallTreeLevel = 4;
        int nodeMaxValue = 5;
        int testtimes = 10000000;
        System.out.println("Start test");
        for (int i = 0; i < testtimes; i++) {
            BinaryTreeNode big = generateRandomBTS(bigTreeLevel, nodeMaxValue);
            BinaryTreeNode small = generateRandomBTS(smallTreeLevel, nodeMaxValue);
            boolean ans1 = containTree1(big, small);
            boolean ans2 = containTree2(big, small);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }

    private static BinaryTreeNode generateRandomBTS(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static BinaryTreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }

        BinaryTreeNode node = new BinaryTreeNode((int) (Math.random() * maxValue));
        node.left = generate(level + 1, maxLevel, maxValue);
        node.right = generate(level + 1, maxLevel, maxValue);
        return node;
    }
}
