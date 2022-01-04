/**
 * N叉树编码(序列化)为二叉树
 */
package com.example.system.class07;

import java.util.ArrayList;
import java.util.List;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.entity.NaryTreeNode;

public class C05_EncodeNaryTreeTBT {
    /**
     * N叉树编码为二叉树
     */
    public static BinaryTreeNode encode(NaryTreeNode root) {
        if (root == null)
            return null;

        BinaryTreeNode head = new BinaryTreeNode(root.val);
        head.left = en(root.children);
        return head;
    }

    /**
     * 深度优先遍历
     * N叉树的一个节点的所有孩子节点, 放入二叉树对应节点的左子树的右边界上
     */
    private static BinaryTreeNode en(List<NaryTreeNode> children) {
        if (children == null)
            return null;
        BinaryTreeNode head = null;
        BinaryTreeNode cur = null;
        for (NaryTreeNode nt : children) {
            BinaryTreeNode node = new BinaryTreeNode(nt.val);
            if (head == null) {
                head = node;
            } else {
                cur.right = node;
            }
            cur = node;
            cur.left = en(nt.children);
        }
        return head;

    }

    /**
     * 二叉树解码为N叉树
     */
    public static NaryTreeNode decode(BinaryTreeNode root) {
        if (root == null)
            return null;

        NaryTreeNode head = new NaryTreeNode(root.value);
        head.children = de(root.left);
        return head;
    }

    /**
     * 左子树的右边界转为孩子节点
     */
    private static List<NaryTreeNode> de(BinaryTreeNode root) {
        List<NaryTreeNode> children = new ArrayList<>();
        NaryTreeNode node = null;
        while (root != null) {
            node = new NaryTreeNode(root.value);
            node.children = de(root.left);
            children.add(node);
            root = root.right;
        }
        return children;
    }

    public static void main(String[] args) {
        NaryTreeNode root = new NaryTreeNode(0);
        NaryTreeNode a1 = new NaryTreeNode(1);
        NaryTreeNode a2 = new NaryTreeNode(2);
        NaryTreeNode a3 = new NaryTreeNode(3);
        NaryTreeNode a11 = new NaryTreeNode(11);
        NaryTreeNode a12 = new NaryTreeNode(12);
        NaryTreeNode a13 = new NaryTreeNode(13);
        NaryTreeNode a31 = new NaryTreeNode(31);

        a1.children = List.of(a11, a12, a13);
        a3.children = List.of(a31);
        root.children = List.of(a1, a2, a3);

        BinaryTreeNode bt = encode(root);
        NaryTreeNode nt = decode(bt);
        System.out.println(nt);
    }
}
