/**
 * 二叉树节点
 */
package com.example.utility.entity;

public class BinaryTreeNode {
    public int value;
    public BinaryTreeNode left;
    public BinaryTreeNode right;

    // 节点的深度，用于按层打印
    public int depth;

    public BinaryTreeNode(int val) {
        this.value = val;
    }

    public BinaryTreeNode(int val, BinaryTreeNode left, BinaryTreeNode right) {
        this.value = val;
        this.left = left;
        this.right = right;
    }
}
