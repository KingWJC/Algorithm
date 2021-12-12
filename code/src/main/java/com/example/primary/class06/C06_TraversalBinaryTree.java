/**
 * 二叉树递归遍历
 * （1）先(根)序遍历（根左右）
 * （2）中(根)序遍历（左根右）
 *  (3）后(根)序遍历（左右根）
 */
package com.example.primary.class06;

import com.example.primary.class06.C03_SameTree.TreeNode;

public class C06_TraversalBinaryTree {
    public static void preOrder(TreeNode head) {
        if (head == null)
            return;
        System.out.println(head.val);
        preOrder(head.left);
        preOrder(head.right);
    }

    public static void inOrder(TreeNode head) {
        if (head == null)
            return;

        inOrder(head.left);
        System.out.println(head.val);
        inOrder(head.right);
    }

    public static void posOrder(TreeNode head) {
        if (head == null)
            return;

        posOrder(head.left);
        posOrder(head.right);
        System.out.println(head.val);
    }
}
