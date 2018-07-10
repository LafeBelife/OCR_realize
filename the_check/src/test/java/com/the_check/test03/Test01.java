package com.the_check.test03;

import java.io.File;

public class Test01 {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder("asdfg");
        System.out.println(sb.reverse());
        StringBuffer s_b = new StringBuffer("asdfg");
        System.out.println(s_b.reverse());

        File file = new File("C:\\Users\\Administrator\\Desktop\\_img\\564.jpg");
        System.out.println(file.getName());
        new Thread().stop();
        throw new RuntimeException();
    }
}