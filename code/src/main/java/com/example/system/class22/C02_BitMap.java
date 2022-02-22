/**
 * 位图的具体实现
 */
package com.example.system.class22;

public class C02_BitMap {
    /**
     * 获取index位的状态
     */
    private static String getState(int[] arr, int index) {
        if (index > arr.length * 32) {
            return "无效索引";
        }

        return (arr[index / 32] & (1 << (index % 32))) == 0 ? "0" : "1";
    }

    /**
     * 设置index位的状态
     */
    private static void setState(int[] arr, int index, int value) {
        if (index > arr.length * 32) {
            return;
        }

        int data = arr[index / 32];
        if ((data & (1 << (index % 32))) != value) {
            if (value == 0) {
                data -= 1 << (index % 32);
            } else if (value == 1) {
                data += 1 << (index % 32);
            }
        }
        arr[index / 32] = data;
    }

    public static void main(String[] args) {
        // 使用基础类型实现位图
        // arr[0] int 32位  位数从高到低
        // ...
        // arr[9] int 32位
        int[] arr = new int[10];
        int index = 4;
        System.out.println(index + "位置状态更改前：");
        System.out.println(getState(arr, index));
        System.out.println(arr[0]);
        setState(arr, index, 1);
        System.out.println(index + "位置状态更改后：");
        System.out.println(arr[0]);
        System.out.println(getState(arr, index));
    }
}
