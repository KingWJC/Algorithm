/**
 * 二叉树的递归先、序、后序遍历
 */
package com.example.system.class07;

import com.example.utility.entity.BinaryTreeNode;

public class C01_RecursiveTraversalBT {

    public static void pre(BinaryTreeNode head) {
        if (head == null)
            return;
        System.out.print(head.value + ",");
        pre(head.left);
        pre(head.right);
    }

    public static void in(BinaryTreeNode head) {
        if (head == null)
            return;

        in(head.left);
        System.out.print(head.value + ",");
        in(head.right);
    }

    public static void after(BinaryTreeNode head) {
        if (head == null)
            return;

        after(head.left);
        after(head.right);
        System.out.print(head.value + ",");
    }

    public static void main(String[] args) {
        BinaryTreeNode head = new BinaryTreeNode(1);
        head.left = new BinaryTreeNode(2);
        head.right = new BinaryTreeNode(3);
        head.left.left = new BinaryTreeNode(4);
        head.left.right = new BinaryTreeNode(5);
        head.right.left = new BinaryTreeNode(6);
        head.right.right = new BinaryTreeNode(7);

        System.out.println("先序:");
        pre(head);
        System.out.println("========");
        System.out.println("中序:");
        in(head);
        System.out.println("========");
        System.out.println("后序:");
        after(head);
        System.out.println("========");
    }
}
