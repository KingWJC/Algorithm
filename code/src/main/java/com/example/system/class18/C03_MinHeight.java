/**
 * 二叉树的最小深度
 */
package com.example.system.class18;

import com.example.utility.entity.BinaryTreeNode;

public class C03_MinHeight {
    /**
     * 二叉树递归套路
     */
    public static int minHeight1(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }
        return process(head);
    }

    /**
     * 获取当前节点的左右节点的最小高度
     */
    private static int process(BinaryTreeNode node) {
        if (node.left == null && node.right == null) {
            return 1;
        }

        int leftH = Integer.MAX_VALUE;
        if (node.left != null) {
            leftH = process(node.left);
        }

        int rightH = Integer.MAX_VALUE;
        if (node.right != null) {
            rightH = process(node.right);
        }

        return 1 + Math.min(leftH, rightH);
    }

    /**
     * morris遍历
     */
    public static int minHeight2(BinaryTreeNode head) {
        if (head == null) {
            return 0;
        }

        BinaryTreeNode cur = head;
        BinaryTreeNode mostRight = null;
        int curLevel = 0;
        int minHeight = Integer.MAX_VALUE;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                // 左树最右边界长度
                int rightBoardSize = 1;
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                    rightBoardSize++;
                }
                if (mostRight.right == null) {// 第一次到达
                    curLevel++;
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {// 第二次到达,只要左节点为空，就是叶子节点
                    if (mostRight.left == null) {
                        minHeight = Math.min(minHeight, curLevel);
                    }
                    curLevel -= rightBoardSize;
                    mostRight.right = null;
                }
            } else {// 没有左子树的节点，只有一次到达
                curLevel++;
            }
            cur = cur.right;
        }

        // 单独检查树的最右节点是否为叶子节点。
        cur = head;
        int finalRight = 1;
        while (cur.right != null) {
            finalRight++;
            cur = cur.right;
        }
        // 判断是否为叶子节点。
        if (cur.left == null && cur.right == null) {
            minHeight = Math.min(minHeight, finalRight);
        }
        return minHeight;
    }

    public static void main(String[] args) {
        int treeLevel = 7;
        int nodeMaxValue = 5;
        int testTimes = 1000000;
        System.out.println("test begin");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = generateBinaryTreeNode(treeLevel, nodeMaxValue);
            int ans1 = minHeight1(head);
            int ans2 = minHeight2(head);
            if (ans1 != ans2) {
                System.out.println("error");
                break;
            }
        }
        System.out.println("finish");
    }

    private static BinaryTreeNode generateBinaryTreeNode(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    private static BinaryTreeNode generate(int level, int maxLevel, int maxValue) {
        if (level == maxLevel || Math.random() < 0.5) {
            return null;
        }

        BinaryTreeNode node = new BinaryTreeNode((int) (Math.random() * maxValue));
        node.left = generate(level + 1, maxLevel, maxValue);
        node.right = generate(level + 1, maxLevel, maxValue);
        return node;
    }
}
