/**
 * 用队列结构实现栈结构
 */
package com.example.system.class02;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class C07_TwoQueueImplementStack {
    public static class MyStack {
        Queue<Integer> queue;
        Queue<Integer> help;

        public MyStack() {
            queue = new LinkedList<>();
            help = new LinkedList<>();
        }

        public void push(int val) {
            queue.offer(val);
        }

        public int pop() {
            // queue留下最后一个
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }

            int result = queue.poll();
            Queue<Integer> temp = queue;
            queue = help;
            help = temp;

            return result;
        }

        public int peek() {
            while (queue.size() > 1) {
                help.offer(queue.poll());
            }

            int result = queue.poll();
            help.offer(result);
            Queue<Integer> temp = queue;
            queue = help;
            help = temp;
            return result;
        }

        public boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        MyStack myStack = new MyStack();
        Stack<Integer> stack = new Stack<>();
        int testTimes = 1000000;
        int maxValue = 10000;
        for (int i = 0; i < testTimes; i++) {
            if (myStack.isEmpty()) {
                int val = (int) (Math.random() * maxValue);
                myStack.push(val);
                stack.push(val);
            } else {
                if (Math.random() < 0.25) {
                    int val = (int) (Math.random() * maxValue);
                    myStack.push(val);
                    stack.push(val);
                } else if (Math.random() < 0.75) {
                    if (myStack.pop() != stack.pop()) {
                        System.out.println("error");
                        break;
                    }
                } else {
                    if (myStack.peek() != stack.peek()) {
                        System.out.println("error");
                        break;
                    }
                }
            }
        }
        System.out.println("finished");
    }
}
