/*
 * @description: 
 * @param: 
 * @return: 
 */
/**
 * 二叉树的递归先、序、后序遍历
 */
package com.example.system.class07;

import com.example.utility.entity.BinaryTreeNode;

public class C01_RecursiveTraversalBT {

    public static void preorder(BinaryTreeNode head) {
        if (head == null)
            return;
        System.out.print(head.value + ",");
        preorder(head.left);
        preorder(head.right);
    }

    public static void inorder(BinaryTreeNode head) {
        if (head == null)
            return;

        inorder(head.left);
        System.out.print(head.value + ",");
        inorder(head.right);
    }

    public static void postorder(BinaryTreeNode head) {
        if (head == null)
            return;

        postorder(head.left);
        postorder(head.right);
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
        preorder(head);
        System.out.println("========");
        System.out.println("中序:");
        inorder(head);
        System.out.println("========");
        System.out.println("后序:");
        postorder(head);
        System.out.println("========");
    }
}
