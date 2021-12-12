/**
 * 路径总和:找出所有 从根节点到叶子节点 路径总和等于给定目标和的路径。
 */
package com.example.primary.class06;

import java.util.LinkedList;
import java.util.List;

import com.example.primary.class06.C07_ConstructBinaryTreeByTraversal.TreeNode;

public class C10_PathSumList {
    public static List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> list = new LinkedList<>();
        if (root == null) {
            return list;
        }
        process(root, targetSum, new LinkedList<Integer>(), list);
        return list;
    }

    public static void process(TreeNode root, int targetSum, List<Integer> path, List<List<Integer>> list) {
        if (root.left == null && root.right == null) {
            if (targetSum - root.val == 0) {
                list.add(path);
            }
        }
        path.add(root.val);
        targetSum -= root.val;
        if (root.left != null) {
            process(root.left, targetSum, new LinkedList<Integer>(path), list);
        }
        if (root.right != null) {
            process(root.right, targetSum, new LinkedList<Integer>(path), list);
        }
    }

    public static void main(String[] args) {
        TreeNode head1 = new TreeNode(5);
        head1.left = new TreeNode(1);
        head1.right = new TreeNode(4);
        head1.right.left = new TreeNode(3);
        head1.right.right = new TreeNode(6);

        System.out.println(pathSum(head1, 12));
    }
}
