/**
 * 把链表中的给定值都删除
 */
package com.example.system.class02;

import com.example.utility.entity.ListNode;

public class C02_DeleteGivenValue {
    public static ListNode removeValue(ListNode head, int value) {
        // head来到第一个不需要删的位置
        while (head != null) {
            if (head.val != value) {
                break;
            }
            head = head.next;
        }

        // 1 ) head == null
        // 2 ) head != null
        ListNode pre = head;
        ListNode cur = head;
        while (cur != null) {
            if (cur.val == value) {
                pre.next = cur.next;
            } else {
                pre = cur;
            }

            cur = cur.next;
        }

        return head;
    }
}
