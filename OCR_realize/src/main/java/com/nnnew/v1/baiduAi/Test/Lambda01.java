package com.nnnew.v1.baiduAi.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Lambda01 {
    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println("11111111111111");
        runnable.run();
        List<Student> studentList = new ArrayList<Student>() {
            {
                add(new Student("stu1", 100.0));
                add(new Student("stu2", 97.0));
                add(new Student("stu3", 96.0));
                add(new Student("stu4", 95.0));
            }
        };
        Collections.sort(studentList, (s1, s2) -> s1.getName().compareTo("stu100"));
        System.out.println(studentList);
        int x = 127;
        byte by = (byte) x;
        Runnable xxxx = () -> System.out.println(by);
        xxxx.run();
    }
}