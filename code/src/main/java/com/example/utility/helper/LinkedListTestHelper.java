/**
 * 对数器
 * 链表的测试帮助类
 */
package com.example.utility.helper;

import com.example.utility.entity.ListNode;

public class LinkedListTestHelper {
    /**
     * 生成随机单向链表
     */
    public static ListNode generateRandomLinkedList(int maxLength, int maxValue) {
        int length = randomNumber(maxLength);

        if (length == 0)
            return null;

        ListNode head = new ListNode(randomNumber(maxValue));
        ListNode cur = head;
        for (int i = 1; i < length; i++) {
            ListNode node = new ListNode(randomNumber(maxValue));
            cur.next = node;
            cur = node;
        }
        return head;
    }

    /**
     * 生成随机双向链表
     */
    public static ListNode generateRandomDoubleList(int maxLength, int maxValue) {
        int length = randomNumber(maxLength);

        if (length == 0)
            return null;

        ListNode head = new ListNode(randomNumber(maxValue));
        ListNode cur = head;
        for (int i = 1; i < length; i++) {
            ListNode node = new ListNode(randomNumber(maxValue));
            cur.next = node;
            cur = node;
        }
        return head;
    }

    /**
     * [0 ~ range]的随机数
     */
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1));
    }
}
