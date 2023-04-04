
import java.util.concurrent.TimeUnit;

public class volatileTest {

    /**
     * 这里开了两个线程，如果加了volatile则线程二会退出，如果不加则线程二不会退出
     * 可见性，是指当一个线程修改了某一个共享变量的值，其他线程是否能够立即知道该变更，JMM规定了所有的变量都存储在主内存中。
     * 答案：volatile保证了可见性
     *      当写一个volatile变量时，JMM会把该线程对应的本地内存中的共享变量值立即刷新回主内存中。
     *      当读一个volatile变量时，JMM会把该线程对应的本地内存设置为无效，重新回到主内存中读取最新共享变量。
     *      所以volatile的写内存语义是直接刷新到主内存中，读的内存语义是直接从主内存中读取，从而保证了可见性
     */
    static volatile boolean flag = true;
    //static boolean flag = true;


    public static void main(String[] args) {

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"：进入");
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = false;
        },"线程1").start();


        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"：进入");
            while (flag){

            }
            System.out.println(Thread.currentThread().getName()+"：退出");
        },"线程2").start();
    }
}
