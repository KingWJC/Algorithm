/**
 *  二叉树的最大深度
 */
package com.example.primary.class06;

import com.example.primary.class06.C03_SameTree.TreeNode;

public class C05_MaxNumDepthofBinaryTree {
    public static int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
