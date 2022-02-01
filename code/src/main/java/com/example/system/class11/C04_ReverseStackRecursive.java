/**
 * 逆序一个栈，不能申请额外的数据结构，只能使用递归函数
 */
package com.example.system.class11;

import java.util.Stack;

public class C04_ReverseStackRecursive {
    /**
     * 逆序栈
     * 步骤：先把最后一个拿出来，其它的先放进去，再放入最后一个，完成逆序。
     */
    public static void reverse(Stack<Integer> stack) {
        if (stack.isEmpty()) {
            return;
        }

        int last = getLast(stack);
        reverse(stack);
        stack.push(last);
    }

    /**
     * 栈底元素移除掉, 上面的元素盖下来, 返回移除掉的栈底元素,
     * 栈失去最后一个元素。
     */
    private static int getLast(Stack<Integer> stack) {
        if (stack.size() == 1) {
            return stack.pop();
        }

        int cur = stack.pop();
        int last = getLast(stack);
        stack.push(cur);
        return last;
    }

    public static void main(String[] args) {
        Stack<Integer> test = new Stack<>();
        test.push(1);
        test.push(2);
        test.push(3);
        //3,2,1 -> 1,2,3
        reverse(test);
        while (!test.isEmpty()) {
            System.out.print(test.pop() + ",");
        }
    }
}
