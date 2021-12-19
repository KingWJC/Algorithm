/**
 * 双向链表
 */
package com.example.utility.entity;

public class ListNodeDouble {
    public int val;
    public ListNodeDouble pre;
    public ListNodeDouble next;

    public ListNodeDouble(int val) {
        this.val = val;
    }

    public ListNodeDouble(int val, ListNodeDouble pre, ListNodeDouble next) {
        this.val = val;
        this.next = next;
        this.pre = pre;
    }
}
