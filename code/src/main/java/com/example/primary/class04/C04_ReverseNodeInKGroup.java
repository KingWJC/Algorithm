/*
 * @description: 
 * @param: 
 * @return: 
 */
/**
 * K个节点的组内逆序调整
 */
package com.example.primary.class04;

import com.example.utility.entity.ListNode;

public class C04_ReverseNodeInKGroup {

    public static ListNode reverseKGroup(ListNode head, int k) {
        ListNode start = head;
        ListNode end = getGroupEnd(start, k);
        if (end == null) {
            return head;
        }

        head = end;
        reverse(start, end);
        ListNode lastEnd = start;
        while (lastEnd.next != null) {
            start = lastEnd.next;
            end = getGroupEnd(start, k);
            if (end == null) {
                return head;
            }
            reverse(start, end);
            lastEnd.next = end;
            lastEnd = start;
        }

        return head;
    }

    private static ListNode getGroupEnd(ListNode start, int k) {
        while (--k != 0 && start != null) {
            start = start.next;
        }
        return start;
    }

    private static void reverse(ListNode start, ListNode end) {
        // 先记下end.next节点的引用, 否则节点更新后, end.next也会更新.
        end = end.next;

        ListNode pre = null;
        ListNode cur = start;
        ListNode next = null;
        while (cur != end) {
            next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        start.next = end;
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        node1.next = new ListNode(2);
        node1.next.next = new ListNode(3);
        node1.next.next.next = new ListNode(4);
        node1.next.next.next.next = new ListNode(5);

        ListNode result = reverseKGroup(node1, 2);
        while (result != null) {
            System.out.print(result.val + " ");
            result = result.next;
        }
    }
}
