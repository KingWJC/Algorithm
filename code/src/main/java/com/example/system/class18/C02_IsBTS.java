/**
 * 判断是否为搜索二叉树
 */
package com.example.system.class18;

import com.example.utility.entity.BinaryTreeNode;

public class C02_IsBTS {
    /**
     * morris遍历判断
     */
    public static boolean isBTS(BinaryTreeNode head) {
        if (head == null) {
            return true;
        }

        boolean isBTS = true;
        Integer pre = null;
        BinaryTreeNode cur = head;
        BinaryTreeNode mostRight = null;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }

            // morris遍历中途不能退出，否则二叉树结构未恢复，是错误的。
            if (pre != null && pre >= cur.value) {
                isBTS = false;
            }

            pre = cur.value;
            cur = cur.right;
        }

        return isBTS;
    }

    public static void main(String[] args) {
        BinaryTreeNode head = new BinaryTreeNode(4);
        head.left = new BinaryTreeNode(2);
        head.right = new BinaryTreeNode(6);
        head.left.left = new BinaryTreeNode(1);
        head.left.right = new BinaryTreeNode(3);
        head.right.left = new BinaryTreeNode(5);
        head.right.right = new BinaryTreeNode(7);
        System.out.print(isBTS(head));
    }
}
