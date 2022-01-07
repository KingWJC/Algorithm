/**
 * 二叉树上两个节点的最低公共祖先
 */
package com.example.system.class07;

import java.util.HashMap;
import java.util.HashSet;

import com.example.utility.entity.BinaryTreeNode;
import com.example.utility.helper.TreeTestHelper;

public class C16_LowestAncestor {
    /**
     * 递归后序
     */
    public static BinaryTreeNode getLowestAncestor(BinaryTreeNode head, BinaryTreeNode a, BinaryTreeNode b) {
        return process(head, a, b).lowestAncestor;
    }

    public static class Info {
        boolean isContainsA;
        boolean isContainsB;
        BinaryTreeNode lowestAncestor;

        public Info(boolean isContainsA, boolean isContainsB, BinaryTreeNode lowestAncestor) {
            this.isContainsA = isContainsA;
            this.isContainsB = isContainsB;
            this.lowestAncestor = lowestAncestor;
        }
    }

    private static Info process(BinaryTreeNode x, BinaryTreeNode a, BinaryTreeNode b) {
        if (x == null) {
            return new Info(false, false, null);
        }

        Info left = process(x.left, a, b);
        Info right = process(x.right, a, b);

        boolean isContainsA = left.isContainsA || right.isContainsA || x == a;
        boolean isContainsB = left.isContainsB || right.isContainsB || x == b;

        BinaryTreeNode lowestAncestor = null;
        if (left.lowestAncestor != null) {
            lowestAncestor = left.lowestAncestor;
        } else if (right.lowestAncestor != null) {
            lowestAncestor = right.lowestAncestor;
        } else {
            if (isContainsA && isContainsB) {
                // 当x==a || x==b 其公共祖先是x.
                lowestAncestor = x;
            }
        }

        return new Info(isContainsA, isContainsB, lowestAncestor);
    }

    /**
     * 对数器
     * 将a所有的父节点放入set中, 遍历b的每一个父节点, 第一个遇到的就是最低公共祖先
     */
    public static BinaryTreeNode getLowestAncestorUseMap(BinaryTreeNode head, BinaryTreeNode a, BinaryTreeNode b) {
        if (head == null)
            return null;

        HashMap<BinaryTreeNode, BinaryTreeNode> parentMap = new HashMap<>();
        parentMap.put(head, null);
        fillParentMap(head, parentMap);

        BinaryTreeNode cur = a;
        HashSet<BinaryTreeNode> set = new HashSet<>();
        set.add(cur);
        while (parentMap.get(cur) != null) {
            cur = parentMap.get(cur);
            set.add(cur);
        }
        cur = b;
        while (!set.contains(cur)) {
            cur = parentMap.get(cur);
        }

        return cur;
    }

    private static void fillParentMap(BinaryTreeNode head, HashMap<BinaryTreeNode, BinaryTreeNode> parentMap) {
        if (head.left != null) {
            fillParentMap(head.left, parentMap);
            parentMap.put(head.left, head);
        }
        if (head.right != null) {
            fillParentMap(head.right, parentMap);
            parentMap.put(head.right, head);
        }

    }

    public static void main(String[] args) {
        int maxValue = 100;
        int maxLevel = 6;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            BinaryTreeNode head = TreeTestHelper.generateRandomBTS(maxLevel, maxValue);
            BinaryTreeNode a = TreeTestHelper.pickRandomOne(head);
            BinaryTreeNode b = TreeTestHelper.pickRandomOne(head);

            BinaryTreeNode ans1 = getLowestAncestor(head, a, b);
            BinaryTreeNode ans2 = getLowestAncestorUseMap(head, a, b);
            if (ans1 != ans2) {
                TreeTestHelper.printTree(head);
                System.out.println(ans1.value + " error " + ans2.value);
                break;
            }
        }
        System.out.println("finished");
    }
}
