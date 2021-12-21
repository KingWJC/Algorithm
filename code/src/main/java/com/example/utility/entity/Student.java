/**
 * 学生测试类
 */
package com.example.utility.entity;

public class Student {
    public String name;
    public int id;
    public int age;

    public Student(String name, int id, int age) {
        this.name = name;
        this.id = id;
        this.age = age;
    }

    @Override
    public String toString() {
        return "{ name:" + name + ", id:" + id + ", age:" + age + " }";
    }
}
