/**
 * 两个链表相加,分三阶段.
 */
package com.example.primary.class04;

import com.example.utility.entity.ListNode;

public class C05_AddTwoNumbers {

    public static ListNode addTwoNumbers(ListNode head1, ListNode head2) {
        int len1 = listLength(head1);
        int len2 = listLength(head2);
        ListNode l = len1 > len2 ? head1 : head2;
        ListNode s = l == head1 ? head2 : head1;

        ListNode curL = l;
        ListNode curS = s;
        // 记录最后一个节点. 因为curL最后是null
        ListNode last = l;

        int carry = 0; // 进阶.每个节点只能存储 一位 数字
        int curNum = 0;
        while (curS != null) {
            curNum = curS.val + curL.val + carry;
            curL.val = curNum % 10;
            carry = curNum / 10;
            curL = curL.next;
            curS = curS.next;
        }

        while (curL != null) {
            curNum = curL.val + carry;
            curL.val = curNum % 10;
            carry = curNum / 10;
            last = curL;
            curL = curL.next;
        }

        if (carry != 0) {
            last.next = new ListNode(1);
        }
        return l;
    }

    public static int listLength(ListNode head) {
        int lenth = 0;
        while (head != null) {
            lenth++;
            head = head.next;
        }
        return lenth;
    }
}
