/**
 * 单向链表
 */
package com.example.utility.entity;

public class ListNode {
    public int val;
    public ListNode next;

    public ListNode(int v) {
        this.val = v;
    }

    public ListNode(int v, ListNode n) {
        this.val = v;
        this.next = n;
    }
}
