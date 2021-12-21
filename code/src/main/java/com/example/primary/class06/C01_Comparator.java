/**
 * 
 */
package com.example.primary.class06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.example.utility.entity.Student;

public class C01_Comparator {
    /**
     * 大数放前.
     */
    public static class IdComparator implements Comparator<Student> {
        /**
         * 如果返回负数，认为第一个参数应该排在前面
         * 如果返回正数，认为第二个参数应该排在前面
         * 如果返回0，认为谁放前面无所谓
         */
        @Override
        public int compare(Student o1, Student o2) {
            if (o1.id < o2.id) {
                return 1;
            } else if (o1.id > o2.id) {
                return -1;
            }
            return 0;
        }
    }

    public static void printStudents(Student[] students) {
        for (int i = 0; i < students.length; i++) {
            System.out.println(students[i].name + ", " + students[i].id + ", " + students[i].age);
        }
    }

    public static void testSort() {
        int[] arr = { 8, 1, 4, 1, 6, 8, 4, 1, 5, 8, 2, 3, 0 };
        System.out.println(Arrays.toString(arr));
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        Student s1 = new Student("张三", 5, 27);
        Student s2 = new Student("李四", 1, 17);
        Student s3 = new Student("王五", 4, 29);
        Student s4 = new Student("赵六", 3, 9);
        Student s5 = new Student("左七", 2, 34);

        Student[] students = { s1, s2, s3, s4, s5 };
        printStudents(students);
        Arrays.sort(students, new IdComparator());
        System.out.println("==== sort ====");
        printStudents(students);

        System.out.println("=======");
        ArrayList<Student> arrList = new ArrayList<>();
        arrList.add(s1);
        arrList.add(s2);
        arrList.add(s3);
        arrList.add(s4);
        arrList.add(s5);
        for (Student s : arrList) {
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }
        System.out.println("==== sort ====");
        arrList.sort(new IdComparator());
        for (Student s : arrList) {
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }
    }

    public static void main(String[] args) {

        // testSort();

        /**
         * 字典序
         * 1. 长度相同, 转为ASSIC码,比较数值.
         * 2. 长度不同, 短的用最低的ASSIC码补齐.
         */
        String str1 = "abc";
        String str2 = "b";
        System.out.println(str1.compareTo(str2));

        PriorityQueue<Student> heap = new PriorityQueue<>(new IdComparator());
        Student s1 = new Student("张三", 5, 27);
        Student s2 = new Student("李四", 1, 17);
        Student s3 = new Student("王五", 4, 29);
        Student s4 = new Student("赵六", 3, 9);
        Student s5 = new Student("左七", 2, 34);
        heap.add(s1);
        heap.add(s2);
        heap.add(s3);
        heap.add(s4);
        heap.add(s5);
        System.out.println("=========");
        while (!heap.isEmpty()) {
            Student s = heap.poll();
            System.out.println(s.name + ", " + s.id + ", " + s.age);
        }
    }
}
