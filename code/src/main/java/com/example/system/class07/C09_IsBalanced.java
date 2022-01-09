/**
 * 二叉树的递归套路:1.判断二叉树是不是平衡二叉树
 */
package com.example.system.class07;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeTestHelper;

public class C09_IsBalanced {
    public static boolean isBalanced(BinaryTreeNode head) {
        return process(head).isBalanced;
    }

    /**
     * 使用自定义类存储节点是否为二叉树和子树的高度.
     */
    private static Info process(BinaryTreeNode x) {
        if (x == null) {
            // 空树好设置
            return new Info(0, true);
        }

        Info left = process(x.left);
        Info right = process(x.right);
        boolean isBalanced = left.isBalanced && right.isBalanced && Math.abs(left.height - right.height) < 2;
        int height = Math.max(left.height, right.height) + 1;
        return new Info(height, isBalanced);
    }

    public static class Info {
        int height;
        boolean isBalanced;

        public Info(int h, boolean b) {
            height = h;
            isBalanced = b;
        }
    }

    /**
     * 对数器
     */
    public static boolean isBalancedUseArray(BinaryTreeNode head) {
        if (head == null)
            return true;
        boolean[] ans = new boolean[1];
        ans[0] = true;
        process(head, ans);
        return ans[0];
    }

    /**
     * boolean基础类型是按值传递, 所以使用数组封装. 存储节点是否为二叉树.
     * 返回子树高度.
     */
    private static int process(BinaryTreeNode x, boolean[] ans) {
        if (!ans[0] || x == null)
            return -1;

        int leftH = process(x.left, ans);
        int rightH = process(x.right, ans);
        if (Math.abs(leftH - rightH) > 1) {
            ans[0] = false;
        }

        return Math.max(leftH, rightH) + 1;
    }

    public static void main(String[] args) {
        int maxLevel = 6;
        int maxValue = 100;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeTestHelper.generateRandomBTS(maxLevel, maxValue);
            if (isBalanced(head) != isBalancedUseArray(head)) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finished");
    }
}
