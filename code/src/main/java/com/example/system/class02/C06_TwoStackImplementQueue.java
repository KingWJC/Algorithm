/**
 * 栈结构实现队列结构
 */
package com.example.system.class02;

import java.util.Stack;

public class C06_TwoStackImplementQueue {
    public static class MyQueue {
        Stack<Integer> offerStack;
        Stack<Integer> pollStack;

        public MyQueue() {
            offerStack = new Stack<>();
            pollStack = new Stack<>();
        }

        // offer栈向poll栈倒入数据
        private void offerToPoll() {
            if (pollStack.empty()) {
                while (!offerStack.empty()) {
                    pollStack.push(offerStack.pop());
                }
            }
        }

        public void offer(int val) {
            offerStack.push(val);
        }

        public int poll() {
            if (pollStack.empty() && offerStack.empty()) {
                throw new RuntimeException("your queue is empty");
            }

            offerToPoll();
            return pollStack.pop();
        }

        public int peek() {
            if (pollStack.empty() && offerStack.empty()) {
                throw new RuntimeException("your queue is empty");
            }

            offerToPoll();
            return pollStack.peek();
        }
    }

    public static void main(String[] args) {
        MyQueue test = new MyQueue();
        test.offer(1);
        test.offer(2);
        test.offer(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }
}
