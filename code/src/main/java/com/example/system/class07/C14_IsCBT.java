/**
 * 判断二叉树是不是完全二叉树
 */
package com.example.system.class07;

import java.util.LinkedList;
import java.util.Queue;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeNodeTestHelper;

public class C14_IsCBT {
    /**
     * 递归后序
     */
    public static boolean isCompleteBT(BinaryTreeNode head) {
        return process(head).isCBT;
    }

    /**
     * 对每一棵子树, 是否满二叉树，是否为完全二叉树，子数的高度
     */
    public static class Info {
        boolean isFull;
        boolean isCBT;
        int height;

        public Info(int height, boolean isFull, boolean isCBT) {
            this.isCBT = isCBT;
            this.isFull = isFull;
            this.height = height;
        }
    }

    private static Info process(BinaryTreeNode node) {
        if (node == null) {
            //空信息好设置
            return new Info(0, true, true);
        }

        Info left = process(node.left);
        Info right = process(node.right);

        int height = Math.max(left.height, right.height) + 1;
        boolean isFull = left.isFull && right.isFull && right.height == left.height;
        boolean isCBT = false;
        // 4中可能的情况
        if (left.height == right.height) {
            if (left.isFull && right.isFull)
                isCBT = true;
            else if (left.isFull && right.isCBT)
                isCBT = true;
        } else if (left.height == right.height + 1) {
            if (left.isFull && right.isFull)
                isCBT = true;
            else if (left.isCBT && right.isFull)
                isCBT = true;
        }

        return new Info(height, isFull, isCBT);
    }

    /**
     * 对数器
     * 层级遍历树，根据上一个节点是否只有一个子节点，来判断是否是完整二叉树
     */
    public static boolean isCompleteBTUseQueue(BinaryTreeNode head) {
        if (head == null)
            return true;

        Queue<BinaryTreeNode> queue = new LinkedList<>();
        queue.add(head);
        BinaryTreeNode left;
        BinaryTreeNode right;
        // 是否遇到过左右两个孩子不双全的节点
        boolean isLeaf = false;
        while (!queue.isEmpty()) {
            BinaryTreeNode node = queue.poll();
            left = node.left;
            right = node.right;

            if (left == null && right != null)
                return false;
            // 如果遇到了不双全的节点之后，又发现当前节点不是叶节点
            if (isLeaf && (left != null || right != null))
                return false;

            if (left != null) {
                queue.add(left);
            }
            if (right != null) {
                queue.add(right);
            }

            if (left == null || right == null) {
                isLeaf = true;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int maxValue = 100;
        int maxLevel = 5;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeNodeTestHelper.generateRandomBTS(maxLevel, maxValue);
            boolean flag1 = isCompleteBT(head);
            boolean flag2 = isCompleteBTUseQueue(head);
            if (flag1 != flag2) {
                TreeNodeTestHelper.printTree(head);
                System.out.println(flag1 + " error " + flag2);
                break;
            }
        }
        System.out.println("finished");
    }
}
