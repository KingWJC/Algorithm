/**
 * 比较器
 */
package com.example.system.class04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

import com.example.utility.entity.Student;

public class C01_Comparator {
    /**
     * 任何比较器：compare方法里，遵循一个统一的规范：
     * 返回负数的时候，认为第一个参数应该排在前面
     * 返回正数的时候，认为第二个参数应该排在前面
     * 返回0的时候，认为无所谓谁放前面
     */
    public static class ComplexComparator implements Comparator<Student> {
        @Override
        public int compare(Student o1, Student o2) {
            // 根据id从小到大，但是如果id一样，按照年龄从大到小
            return o1.id != o2.id ? o1.id - o2.id : o2.age - o1.age;
        }
    }

    public static void main(String[] args) {
        Student student1 = new Student("A", 4, 40);
        Student student2 = new Student("B", 4, 21);
        Student student3 = new Student("C", 3, 12);
        Student student4 = new Student("D", 3, 62);
        Student student5 = new Student("E", 3, 42);

        System.out.println("第一条打印");
        Student[] students = new Student[] { student1, student2, student3, student4, student5 };
        Arrays.sort(students, new ComplexComparator());
        // D E C A B
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].toString());
        }

        System.out.println("第二条打印");
        ArrayList<Student> studentList = new ArrayList<>();
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        studentList.add(student5);
        studentList.sort(new ComplexComparator());
        for (Student student : studentList) {
            System.out.println(student);
        }

        System.out.println("第三条打印");
        // 使用Student的ID 使数据唯一。
        TreeMap<Student, String> treeMap = new TreeMap<>((a, b) -> a.id - b.id);
        treeMap.put(student1, "我是学生1，我的名字叫A");
        treeMap.put(student2, "我是学生2，我的名字叫B");
        treeMap.put(student3, "我是学生3，我的名字叫C");
        treeMap.put(student4, "我是学生4，我的名字叫D");
        treeMap.put(student5, "我是学生5，我的名字叫E");
        for (Student student : treeMap.keySet()) {
            System.out.println(student);
        }
    }
}
