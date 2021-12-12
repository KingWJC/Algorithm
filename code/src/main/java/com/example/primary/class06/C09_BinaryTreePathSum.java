/**
 * 路径总和:判断该树中是否存在 根节点到叶子节点 的路径，这条路径上所有节点值相加等于目标和 targetSum 
 * 叶子节点 是指没有子节点的节点.
 * 三种方法.
 */
package com.example.primary.class06;

import com.example.primary.class06.C07_ConstructBinaryTreeByTraversal.TreeNode;

public class C09_BinaryTreePathSum {
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        return process(root, targetSum);
    }

    //方法错误: 是叶子节点, 此方法逻辑中左右节点有一个为空就尝试判断.
    public boolean process(TreeNode node, int sum) {
        if (node == null) {
            if (sum == 0) {
                return true;
            } else {
                return false;
            }
        }
        sum = sum - node.val;
        return process(node.left, sum) || process(node.right, sum);
    }

    public static boolean process0(TreeNode root, int rest) {
        //叶子节点
        if (root.left == null && root.right == null) {
            return root.val == rest;
        }
        boolean ans = root.left != null ? process0(root.left, rest - root.val) : false;
        ans |= root.right != null ? process0(root.right, rest - root.val) : false;
        return ans;
    }

    public static boolean isSum;

    public static boolean hasPathSum1(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        isSum = false;
        process1(root, 0, sum);
        return isSum;
    }

    public static void process1(TreeNode node, int presum, int sum) {
        if (node.left == null && node.right == null) {
            if (node.val + presum == sum) {
                isSum = true;
            }
            return;
        }

        presum += node.val;
        if (node.left != null) {
            process1(node.left, presum, sum);
        }
        if (node.right != null) {
            process1(node.right, presum, sum);
        }
    }
}
