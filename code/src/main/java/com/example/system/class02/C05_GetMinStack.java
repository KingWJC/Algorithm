/**
 * 实现一个特殊的栈，在基本功能的基础上，再实现返回栈中最小元素的功能
 * 使用两个栈.  
 */
package com.example.system.class02;

import java.util.Stack;

public class C05_GetMinStack {
    public static class MyStack1 {
        Stack<Integer> data;
        Stack<Integer> min;

        public MyStack1() {
            data = new Stack<>();
            min = new Stack<>();
        }

        public void push(int val) {
            data.push(val);

            if (min.empty() || getMin() > val) {
                min.push(val);
            } else {
                min.push(getMin());
            }
        }

        public int pop() {
            if (data.empty()) {
                throw new RuntimeException("your stack is empty");
            }
            min.pop();
            return data.pop();
        }

        public int getMin() {
            if (min.empty()) {
                throw new RuntimeException("your stack is empty");
            }
            return min.peek();
        }
    }

    public static void main(String[] args) {
        MyStack1 stack1 = new MyStack1();
        stack1.push(3);
        System.out.println(stack1.getMin());
        stack1.push(4);
        System.out.println(stack1.getMin());
        stack1.push(1);
        System.out.println(stack1.getMin());
        System.out.println(stack1.pop());
        System.out.println(stack1.getMin());
    }
}
