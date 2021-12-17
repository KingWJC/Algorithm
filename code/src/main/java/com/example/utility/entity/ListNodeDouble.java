/**
 * 双向链表
 */
package com.example.utility.entity;

public class ListNodeDouble {
    int val;
    ListNodeDouble pre;
    ListNodeDouble next;

    public ListNodeDouble(int val) {
        this.val = val;
    }

    public ListNodeDouble(int val, ListNodeDouble pre, ListNodeDouble next) {
        this.val = val;
        this.next = next;
        this.pre = pre;
    }
}
