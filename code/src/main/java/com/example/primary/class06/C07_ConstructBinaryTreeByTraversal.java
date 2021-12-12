/**
 * 从前序与中序遍历序列构造二叉树
 * 有一棵树，先序结果是pre[L1...R1]，中序结果是in[L2...R2],请建出整棵树返回头节点
 */
package com.example.primary.class06;

import java.util.HashMap;

public class C07_ConstructBinaryTreeByTraversal {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }
        return buildNode(preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    /**
     * 两个数组分为三部分:
     * 根 左 右
     * 左 根 右
     */
    public static TreeNode buildNode(int[] pre, int l1, int r1, int[] in, int l2, int r2) {
        // 若前序与中序遍历序列一样, 是1,2,3, 只有右节点,没有左节点.
        if (l1 > r1) {
            return null;
        }

        // No enclosing instance of type C07_ConstructBinaryTreeByTraversal is
        // accessible. Must qualify the allocation with an enclosing instance of type
        // C07_ConstructBinaryTreeByTraversal (e.g. x.new A() where x is an instance of
        // C07_ConstructBinaryTreeByTraversal).
        // 没有可访问的内部类E的实例，必须分配一个合适的内部类E的实例（如x.new A()，x必须是E的实例。）
        // 内部类是动态的，也就是开头以public class开头。而主程序是public static class
        // main。在Java中，类中的静态方法不能直接调用动态方法。只有将某个内部类修饰为静态类，然后才能够在静态类中调用该类的成员变量与成员方法。
        TreeNode head = new TreeNode(pre[l1]);

        // 若等于,则无子节点, 直接返回.
        if (l1 != r1) {
            int find = l2;
            while (in[find] != pre[l1]) {
                find++;
            }
            head.left = buildNode(pre, l1 + 1, l1 + find - l2, in, l2, find - 1);
            head.right = buildNode(pre, l1 + find - l2 + 1, r1, in, find + 1, r2);
        }
        return head;
    }

    /**
     * 使用HashMap存储中序的值, 方便查找.
     */
    public static TreeNode buildTreeNew(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length) {
            return null;
        }

        HashMap<Integer, Integer> inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }

        return buildNodeNew(inorderMap, preorder, 0, preorder.length - 1, inorder, 0, inorder.length - 1);
    }

    public static TreeNode buildNodeNew(HashMap<Integer, Integer> inorderMap, int[] pre, int l1, int r1, int[] in,
            int l2, int r2) {
        if (l1 > r1) {
            return null;
        }
        TreeNode head = new TreeNode(pre[l1]);

        // 若等于,则无子节点, 直接返回.
        if (l1 != r1) {
            int find = inorderMap.get(pre[l1]);
            head.left = buildNodeNew(inorderMap, pre, l1 + 1, l1 + find - l2, in, l2, find - 1);
            head.right = buildNodeNew(inorderMap, pre, l1 + find - l2 + 1, r1, in, find + 1, r2);
        }
        return head;
    }

}
