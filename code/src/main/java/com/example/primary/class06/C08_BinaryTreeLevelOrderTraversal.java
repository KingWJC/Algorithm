/**
 * 二叉树按层遍历并收集节点, 返回其节点值自底向上的层序遍历
 */
package com.example.primary.class06;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.example.primary.class06.C07_ConstructBinaryTreeByTraversal.TreeNode;

public class C08_BinaryTreeLevelOrderTraversal {
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();

        if (root != null) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                int size = queue.size();
                List<Integer> item = new LinkedList<>();
                for (int i = 0; i < size; i++) {
                    TreeNode node = queue.poll();
                    item.add(node.val);
                    if (node.left != null) {
                        queue.add(node.left);
                    }
                    if (node.right != null) {
                        queue.add(node.right);
                    }
                }
                result.add(0, item);
            }
        }
        return result;
    }
}
