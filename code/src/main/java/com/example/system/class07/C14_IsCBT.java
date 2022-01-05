/**
 * 判断二叉树是不是完全二叉树
 */
package com.example.system.class07;

import com.example.utility.entity.BinaryTreeNode;

public class C14_IsCBT {
    public static boolean isComplateBT(BinaryTreeNode head) {
        return process(head).isCBT;
    }

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
            return new Info(0, true, true);
        }

        Info left = process(node.left);
        Info right = process(node.right);

        int height = Math.max(left.height, right.height) + 1;
        boolean isFull = left.isFull && right.isFull && right.height == left.height;
        boolean isCBT = false;
        if (left.isFull && right.isFull && left.height == right.height) {
            isCBT = true;
        } else if (left.isFull && right.isFull && left.height == right.height + 1) {
            isCBT = true;
        } else if (left.isFull && right.isCBT && left.height == right.height) {
            isCBT = true;
        } else if (left.isCBT && right.isFull && left.height == right.height + 1) {
            isCBT = true;
        }

        return new Info(height, isFull, isCBT);
    }
}
