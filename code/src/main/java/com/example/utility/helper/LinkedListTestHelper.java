/**
 * 对数器
 * 链表的测试帮助类
 */
package com.example.utility.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.example.utility.entity.ListNode;
import com.example.utility.entity.ListNodeDouble;
import com.example.utility.function.ReverseDoubleListMethod;
import com.example.utility.function.ReverseListMethod;

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
    public static ListNodeDouble generateRandomDoubleList(int maxLength, int maxValue) {
        int length = randomNumber(maxLength);

        if (length == 0)
            return null;

        ListNodeDouble head = new ListNodeDouble(randomNumber(maxValue));
        ListNodeDouble pre = head;
        length--;
        // 设置上一项的next, 和当前项的pre
        while (length != 0) {
            ListNodeDouble cur = new ListNodeDouble(randomNumber(maxValue));
            pre.next = cur;
            cur.pre = pre;
            pre = cur;
            length--;
        }

        return head;
    }

    /**
     * 拷贝双向链表
     */
    public static ListNodeDouble cloneDoubleLinkedList(ListNodeDouble head) {
        if (head == null)
            return null;

        ListNodeDouble h = new ListNodeDouble(head.val);
        ListNodeDouble p = h;
        head = head.next;
        while (head != null) {
            ListNodeDouble current = new ListNodeDouble(head.val);
            p.next = current;
            current.pre = p;
            p = current;
            head = head.next;
        }

        return h;
    }

    /**
     * [0 ~ range]的随机数
     */
    public static int randomNumber(int range) {
        return (int) (Math.random() * (range + 1));
    }

    /**
     * 获取单向链表的顺序
     */
    public static List<Integer> getLinkedListOriginOrder(ListNode node) {
        List<Integer> list = new ArrayList<>();
        while (node != null) {
            list.add(node.val);
            node = node.next;
        }
        return list;
    }

    /**
     * 获取双向链表的顺序
     */
    public static List<Integer> getDoubleListOriginOrder(ListNodeDouble node) {
        List<Integer> list = new ArrayList<>();
        while (node != null) {
            list.add(node.val);
            node = node.next;
        }
        return list;
    }

    /**
     * 比较两个双向链表是否一样
     */
    public static boolean isEquals(ListNodeDouble h1, ListNodeDouble h2) {
        String str1 = Arrays.toString(getDoubleListOriginOrder(h1).toArray());
        String str2 = Arrays.toString(getDoubleListOriginOrder(h2).toArray());
        return str1.equals(str2);
    }

    /**
     * 检查单向反转是否正确.
     */
    public static boolean checkLinkedListReverse(List<Integer> list, ListNode head) {
        for (int i = list.size() - 1; i > -1; i--) {
            if (list.get(i) != head.val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 检查双向反转是否正确.
     */
    public static boolean checkLinkedListReverse(List<Integer> list, ListNodeDouble head) {
        ListNodeDouble end = null;
        for (int i = list.size() - 1; i > -1; i--) {
            if (list.get(i) != head.val) {
                return false;
            }
            end = head;
            // 最终head为null
            head = head.next;
        }

        for (int i = 0; i < list.size() - 1; i++) {
            if (!list.get(i).equals(end.val)) {
                return false;
            }
            end = end.pre;
        }

        return true;
    }

    /**
     * 测试单向链表反转
     */
    public static void testReverseList(ReverseListMethod m1, ReverseListMethod m2) {
        int maxLength = 50;
        int maxValue = 100;
        int testTimes = 100000;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            ListNode node = generateRandomLinkedList(maxLength, maxValue);
            List<Integer> list = getLinkedListOriginOrder(node);
            if (!checkLinkedListReverse(list, m1.reverse(node))) {
                System.out.println("error!");
            }

            ListNode node1 = generateRandomLinkedList(maxLength, maxValue);
            List<Integer> list1 = getLinkedListOriginOrder(node1);
            if (!checkLinkedListReverse(list1, m2.reverse(node1))) {
                System.out.println("error!");
            }
        }
        System.out.println("finished");
    }

    /**
     * 测试 双向链表反转
     */
    public static void testReverseDoubleList(ReverseDoubleListMethod m1, ReverseDoubleListMethod m2) {
        int maxLength = 50;
        int maxValue = 100;
        int testTimes = 1000;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            ListNodeDouble node = generateRandomDoubleList(maxLength, maxValue);
            List<Integer> list = getDoubleListOriginOrder(node);
            if (!checkLinkedListReverse(list, m1.reverse(node))) {
                System.out.println("error!");
            }

            ListNodeDouble node1 = generateRandomDoubleList(maxLength, maxValue);
            List<Integer> list1 = getDoubleListOriginOrder(node1);
            if (!checkLinkedListReverse(list1, m2.reverse(node1))) {
                System.out.println("error!");
            }
        }
        System.out.println("finished");
    }

    /**
     * 测试双向链表的随机快速排序
     */
    public static void testQuickSort(ReverseDoubleListMethod m1, ReverseDoubleListMethod m2) {
        int maxLength = 50;
        int maxValue = 100;
        int testTimes = 1000;
        System.out.println("begin test");
        for (int i = 0; i < testTimes; i++) {
            ListNodeDouble head1 = generateRandomDoubleList(maxLength, maxValue);
            ListNodeDouble head2 = cloneDoubleLinkedList(head1);

            ListNodeDouble sort1 = m1.reverse(head1);
            ListNodeDouble sort2 = m2.reverse(head2);
            if (!isEquals(sort1, sort2)) {
                System.out.println("error!");
                System.out.println(Arrays.toString(getDoubleListOriginOrder(sort1).toArray()));
                System.out.println(Arrays.toString(getDoubleListOriginOrder(sort2).toArray()));
                break;
            }
        }
        System.out.println("finished");
    }
}
