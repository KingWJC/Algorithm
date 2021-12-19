/**
 * 双向链表 (泛型)
 */
package com.example.utility.entity;

public class ListNodeGeneric<T> {
    public T val;
    public ListNodeGeneric<T> pre;
    public ListNodeGeneric<T> next;

    public ListNodeGeneric(T val) {
        this.val = val;
    }

    public ListNodeGeneric(T val, ListNodeGeneric<T> pre, ListNodeGeneric<T> next) {
        this.val = val;
        this.next = next;
        this.pre = pre;
    }
}
