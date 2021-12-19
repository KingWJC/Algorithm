/**
 * 双向链表实现栈和队列
 */
package com.example.system.class02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import com.example.utility.entity.ListNodeGeneric;

public class C03_DoubleListToStackAndQueue {
    public static class DoubleEndsQueue<T> {
        public ListNodeGeneric<T> head;
        public ListNodeGeneric<T> tail;

        public void addFromHead(T val) {
            ListNodeGeneric<T> cur = new ListNodeGeneric<>(val);
            if (isEmpty()) {
                head = cur;
                tail = cur;
            } else {
                head.pre = cur;
                cur.next = head;
                head = cur;
            }
        }

        public void addFromBottom(T val) {
            ListNodeGeneric<T> cur = new ListNodeGeneric<>(val);
            if (isEmpty()) {
                head = cur;
                tail = cur;
            } else {
                cur.pre = tail;
                tail.next = cur;
                tail = cur;
            }
        }

        public T popFromHead() {
            if (isEmpty()) {
                return null;
            }

            ListNodeGeneric<T> cur = head;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.next;
                cur.next = null;
                head.pre = null;
            }

            return cur.val;
        }

        public T popFromBottom() {
            if (isEmpty()) {
                return null;
            }

            ListNodeGeneric<T> cur = tail;
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                tail = tail.pre;
                cur.pre = null;
                tail.next = null;
            }
            return cur.val;
        }

        public boolean isEmpty() {
            return head == null;
        }
    }

    public static  class MyStack<T> {
        DoubleEndsQueue<T> queue;

        public MyStack() {
            queue = new DoubleEndsQueue<>();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public void push(T val) {
            queue.addFromHead(val);
        }

        public T pop() {
            return queue.popFromHead();
        }
    }

    public static class MyQueue<T> {
        DoubleEndsQueue<T> queue;

        public MyQueue() {
            queue = new DoubleEndsQueue<>();
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }

        public void push(T val) {
            queue.addFromHead(val);
        }

        public T poll() {
            return queue.popFromBottom();
        }
    }

    public static boolean isEquals(Integer o1, Integer o2) {
        if (o1 == null ^ o2 == null) {
            return false;
        }
        if (o1 == null && o2 == null) {
            return true;
        }
        return o1.equals(o2);
    }

    public static void main(String[] args) {
        int maxLength = 100;
        int maxValue = 10000;
        int testTimes = 100000;
        System.out.println("start test");
        for (int i = 0; i < testTimes; i++) {
            MyStack<Integer> myStack = new MyStack<>();
            MyQueue<Integer> myQueue = new MyQueue<>();
            Stack<Integer> stack = new Stack<>();
            Queue<Integer> queue = new LinkedList<>();

            for (int j = 0; j < maxLength; j++) {
                int val = (int) (Math.random() * maxValue);
                if (stack.isEmpty()) {
                    myStack.push(val);
                    stack.push(val);
                } else {
                    if (Math.random() < 0.5) {
                        myStack.push(val);
                        stack.push(val);
                    } else {
                        if (!isEquals(stack.pop(), myStack.pop())) {
                            System.out.println("error");
                        }
                    }
                }

                if (queue.isEmpty()) {
                    myQueue.push(val);
                    queue.offer(val);
                } else {
                    if (Math.random() < 0.5) {
                        myQueue.push(val);
                        queue.offer(val);
                    } else {
                        if (!isEquals(myQueue.poll(), queue.poll())) {
                            System.out.println("error");
                        }
                    }
                }
            }
        }
        System.out.println("finished");
    }
}
