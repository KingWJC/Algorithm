/**
 * 对称二叉树
 */
package com.example.primary.class06;

public class C04_SymmetricTree {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static boolean isSymmetric(TreeNode root) {
        return isMirror(root.left, root.right);
    }

    private static boolean isMirror(TreeNode l, TreeNode r) {
        if (l == null ^ r == null)
            return false;
        if (l == null && r == null)
            return true;
        return l.val == r.val && isMirror(l.left, r.right) && isMirror(l.right, r.left);
    }
}
