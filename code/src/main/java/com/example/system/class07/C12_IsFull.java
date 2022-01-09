/**
 * 二叉树的递归套路:4.判断二叉树是不是满二叉树
 */
package com.example.system.class07;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeTestHelper;

public class C12_IsFull {
    /**
     * 满二叉树的节点数量为2的h次方-1. h为树的高度
     */
    public static boolean isFull(BinaryTreeNode head) {
        if (head == null)
            return true;

        Info all = process(head);
        return all.count == (1 << all.height) - 1;
    }

    public static class Info {
        int height;
        int count;

        public Info(int height, int count) {
            this.height = height;
            this.count = count;
        }
    }

    private static Info process(BinaryTreeNode node) {
        if (node == null) {
            return new Info(0, 0);
        }

        Info left = process(node.left);
        Info right = process(node.right);

        // 树的高度和节点数量都要加上当前节点, 加1.
        int height = Math.max(left.height, right.height) + 1;
        int count = left.count + right.count + 1;

        return new Info(height, count);
    }

    /**
     * 对数器
     */
    public static boolean isFullTest(BinaryTreeNode head) {
        if (head == null)
            return true;

        int height = getHeight(head);
        int count = getCount(head);
        return count == (1 << height) - 1;
    }

    /**
     * 树的高度
     */
    private static int getHeight(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }

        return Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    /**
     * 树的节点个数
     */
    private static int getCount(BinaryTreeNode node) {
        if (node == null) {
            return 0;
        }

        return getCount(node.left) + getCount(node.right) + 1;
    }

    public static void main(String[] args) {
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeTestHelper.generateRandomBTS(maxLevel, maxValue);
            boolean flag1 = isFull(head);
            boolean flag2 = isFullTest(head);
            if (flag1 != flag2) {
                System.out.println(flag1 + " error " + flag2);
                break;
            }
        }
        System.out.println("finished");
    }
}
