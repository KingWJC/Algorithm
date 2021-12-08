/**
 * 用双链表实现双端队列.
 *  单链表在删除时, 无法找到上一个元素.
 *  LinkedList实现了Deque和List接口.
 */
package com.example.primary.class04;

import java.util.Deque;
import java.util.LinkedList;

public class C03_DoubleLinkedListToDeque {

    private static class Node<V> {
        public V value;
        public Node<V> next;
        public Node<V> last;

        public Node(V value) {
            this.value = value;
            next = null;
            last = null;
        }
    }

    public static class MyDeque<V> {
        private Node<V> head;
        private Node<V> tail;
        private int size;

        public MyDeque() {
            head = null;
            tail = null;
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void pushHead(V value) {
            Node<V> node = new Node<>(value);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head.last = node;
                head = node;
            }
            size++;
        }

        public void pushTail(V value) {
            Node<V> node = new Node<>(value);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.last = tail;
                tail.next = node;
                tail = node;
            }
            size++;
        }

        // C,C++ 需要做旧head节点的析构
        public V pollHead() {
            V ans = null;
            if (head != null) {
                ans = head.value;

                // 移除为空后,保持head, tail一致.
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    head = head.next;
                    head.last = null;
                }
                size--;
            }
            return ans;
        }

        public V pollTail() {
            V ans = null;
            if (tail != null) {
                ans = tail.value;

                // 移除为空后,保持head, tail一致.
                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    tail = tail.last;
                    tail.next = null;
                }
                size--;
            }
            return ans;
        }

        public V peekHead() {
            V ans = null;
            if (head != null) {
                ans = head.value;
            }
            return ans;
        }

        public V peekTail() {
            V ans = null;
            if (tail != null) {
                ans = tail.value;
            }
            return ans;
        }
    }

    public static void testDeque() {
        MyDeque<Integer> myDeque = new MyDeque<>();
        Deque<Integer> test = new LinkedList<>();
        int testTime = 5000000;
        int maxValue = 200000000;
        System.out.println("测试开始！");
        for (int i = 0; i < testTime; i++) {
            if (myDeque.isEmpty() != test.isEmpty()) {
                System.out.println("Oops Empty!");
            }
            if (myDeque.size() != test.size()) {
                System.out.println("Oops Size!");
            }

            double decide = Math.random();
            if (decide < 0.33) {
                int num = (int) (Math.random() * maxValue);
                if (Math.random() < 0.5) {
                    myDeque.pushHead(num);
                    test.addFirst(num);
                } else {
                    myDeque.pushTail(num);
                    test.addLast(num);
                }
            } else if (decide < 0.66) {
                if (!myDeque.isEmpty()) {
                    int num1 = 0;
                    int num2 = 0;
                    if (Math.random() < 0.5) {
                        num1 = myDeque.pollHead();
                        num2 = test.pollFirst();
                    } else {
                        num1 = myDeque.pollTail();
                        num2 = test.pollLast();
                    }
                    if (num1 != num2) {
                        System.out.println("Oops poll!");
                    }
                }
            } else {
                if (!myDeque.isEmpty()) {
                    int num1 = 0;
                    int num2 = 0;
                    if (Math.random() < 0.5) {
                        num1 = myDeque.peekHead();
                        num2 = test.peekFirst();
                    } else {
                        num1 = myDeque.peekTail();
                        num2 = test.peekLast();
                    }
                    if (num1 != num2) {
                        System.out.println("Oops!");
                    }
                }
            }
        }

        if (myDeque.size() != test.size()) {
            System.out.println("Oops!");
        }

        while (!myDeque.isEmpty()) {
            int num1 = myDeque.pollTail();
            int num2 = test.pollLast();
            if (num1 != num2) {
                System.out.println("Oops poll!");
            }
        }
        System.out.println("测试结束！");
    }

    public static void main(String[] args) {
        testDeque();
    }
}
