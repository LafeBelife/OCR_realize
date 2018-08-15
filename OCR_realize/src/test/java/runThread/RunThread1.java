package runThread;

public class RunThread1 extends Thread {
    private volatile  boolean isRunning = true;// 不加 volatile 当前线程会从 私有栈读取数据，而main作为主线程会 去设置到主内存堆中，形成活久性失败

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public void run() {
        System.out.println("thread 运行中....");
        while (isRunning) {

        }
        System.out.println("thread 运行结束....");
    }

    public static void main(String[] args) {
        RunThread1 runThread1 = new RunThread1();
        RunThread1 runThread2 = new RunThread1();
        try {
            runThread1.start();
            runThread2.start();
            Thread.sleep(2000);
            runThread1.setIsRunning(false);
            runThread2.setIsRunning(false);
            System.out.println("----------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
