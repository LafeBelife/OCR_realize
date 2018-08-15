package com.nnnew.v1.baiduAi.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 九九乘法表打印<br/>
 * 2018年8月2日14:57:40
 *
 * @author 王保卫
 */
public class NumberTable {
    public static void main(String[] args) {
        try {
            // 九九乘法表
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j <= i; j++) {
                    System.out.print(j);
                    Thread.sleep(500);
                    System.out.print(" * ");
                    Thread.sleep(500);
                    System.out.print(i);
                    Thread.sleep(500);
                    System.out.print(" = ");
                    Thread.sleep(500);
                    System.out.print(i * j);
                    Thread.sleep(500);
                    System.out.print("\t");
                }
                System.out.println();
            }
            System.out.println();
            // 九九加法表
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j <= i; j++) {
                    System.out.print(j);
                    Thread.sleep(500);
                    System.out.print(" + ");
                    Thread.sleep(500);
                    System.out.print(i);
                    Thread.sleep(500);
                    System.out.print(" = ");
                    Thread.sleep(500);
                    System.out.print(i + j);
                    Thread.sleep(500);
                    System.out.print("\t");
                }
                System.out.println();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
