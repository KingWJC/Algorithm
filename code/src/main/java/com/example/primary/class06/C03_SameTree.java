/**
 * 相同的树
 */
package com.example.primary.class06;

public class C03_SameTree {
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }

    public static boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null ^ q == null)
            return false;

        if (p == null && q == null)
            return true;

        // 都不为空
        return p.val == q.val && isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
