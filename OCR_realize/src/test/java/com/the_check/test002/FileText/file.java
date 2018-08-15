package com.the_check.test002.FileText;

import java.io.*;
import java.util.Objects;

public class file {
    private static File _file = new File("C:\\Users\\Administrator\\Desktop\\_img");

    public static void main(String[] args) {
        String path = "C:\\Users\\Administrator\\Desktop\\img\\img";        //要遍历的路径
        File file = new File(path);        //获取其file对象
        func(file);
    }

    private static int i = 0;

    private static void func(File file) {
        File ff;
        File[] fs = file.listFiles();

        assert fs != null;
        for (File f : Objects.requireNonNull(fs)) {
            if (i > 10)
                break;
            if (f.isDirectory())    //若是目录，则递归打印该目录下的文件
                func(f);

            if (f.isFile()) {        //若是文件，直接打印
                copyFile(f, ff = new File(_file + "\\" + i++ + ".jpg"));
                System.out.println(i + "\t" + ff);
            }
        }
    }

    /**
     * 复制文件
     *
     * @param fromFile
     * @param toFile   <br/>
     *                 2016年12月19日  下午3:31:50
     */
    public static void copyFile(File fromFile, File toFile) {
        if (toFile.exists()) {
            toFile.mkdir();
        }
        FileInputStream ins;
        try {
            ins = new FileInputStream(fromFile);
            FileOutputStream out = new FileOutputStream(toFile);
            byte[] b = new byte[1024];
            int n;
            while ((n = ins.read(b)) != -1) {
                out.write(b, 0, n);
            }

            ins.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
