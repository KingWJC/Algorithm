/**
 * 二叉树的序列化和反序列化
 * 可以通过先序、后序或者按层遍历的方式序列化和反序列化，
 */
package com.example.system.class07;

import java.util.LinkedList;
import java.util.Queue;

import javax.print.attribute.IntegerSyntax;

import com.example.utility.entity.BinaryTreeNode;

public class C04_SerializeAndReconstructure {
    /**
     * 先序的序列化
     */
    public static Queue<String> preSerial(BinaryTreeNode head) {
        Queue<String> ans = new LinkedList<>();
        pres(head, ans);
        return ans;
    }

    private static void pres(BinaryTreeNode head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.value));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    /**
     * 先序的反序列化
     */
    public static BinaryTreeNode bulidByPreQueue(Queue<String> preList) {
        if (preList == null || preList.isEmpty())
            return null;

        return preb(preList);
    }

    private static BinaryTreeNode preb(Queue<String> preList) {
        String value = preList.poll();
        if (value == null)
            return null;

        BinaryTreeNode head = new BinaryTreeNode(Integer.valueOf(value));
        head.left = preb(preList);
        head.right = preb(preList);
        return head;
    }
}
