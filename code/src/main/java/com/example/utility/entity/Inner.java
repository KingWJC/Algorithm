/**
 * 用于包含基础类型的包裹类
 * （使其使用引用传递）
 */
package com.example.utility.entity;

public class Inner<T> {
    public T value;

    public Inner(T v) {
        value = v;
    }
}
