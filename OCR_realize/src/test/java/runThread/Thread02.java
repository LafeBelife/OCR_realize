package runThread;

import java.io.IOException;
import java.net.Socket;

public class Thread02 implements Runnable {
    int num = 10;

    public static void main(String[] args) {
        Thread02 thread = new Thread02();
        Thread t1 = new Thread(thread, "线程一：");
        Thread t2 = new Thread(thread, "线程二：");
        Thread t3 = new Thread(thread, "线程三：");
        t1.setPriority(1);
        t1.start();
        t2.start();
        t3.start();
//        Socket socket = new Socket();
//        try {
//            if (thread.num == 0)
//                socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        t1.interrupt();
//        System.out.println(t1.getName() + "停止运行.........");
//        t2.interrupt();
//        System.out.println(t2.getName() + "停止运行.........");
//        t3.interrupt();
//        System.out.println(t3.getName() + "停止运行.........");

    }

    @Override       // 使用 synchronized 会有一个标志位，有0和1值，为0则表示有线程正在使用需要等待，为1则可以直接使用
    public synchronized void run() {
        if (num > 0) {
            synchronized (this) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "" + num--);
            }
        }
    }
}
