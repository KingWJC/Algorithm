/**
 * N-ary tree N叉树的节点
 */
package com.example.utility.entity;

import java.util.ArrayList;
import java.util.List;

public class NaryTreeNode {
    public int val;
    public List<NaryTreeNode> children;

    public NaryTreeNode() {
    }

    public NaryTreeNode(int _val) {
        val = _val;
        children = new ArrayList<>();
    }

    public NaryTreeNode(int _val, List<NaryTreeNode> _children) {
        val = _val;
        children = _children;
    }
}
