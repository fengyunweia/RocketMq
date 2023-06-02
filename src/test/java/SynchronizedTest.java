
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SynchronizedTest {
    /***
     * 锁代码块{
     * 锁 this ：锁本实例对象
     * 锁 .class 锁这个类
     * 锁 object 一个实例对象 相当于this
     * }

     *
     * 锁方法{
     *     锁这个实例的方法
     *     加上 static  锁这个类的这个方法
     * }
     */

    static synchronized void test1(){
        int a =5;
        while (a>=0){
            System.out.println(Thread.currentThread().getName()+"："+a);
            a--;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

     void test2(int i){
        int a =5;
        while (a>=0){
            System.out.println(i+":"+Thread.currentThread().getName()+"："+a);
            a--;
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        /******************************************/
        /**
         * 测试 方法加 static 则锁全局实例 无论是不是同一个实例调用这个方法都会被锁
         *        不加 则不影响各自实例调用
         */
        //testMethonStatic();

        /**
         * 测试synchronized（this）
         */
        for(int i=5;i>=0;i--){
            testSynchronizedThis(i);
        }
    }

    private static void testSynchronizedThis(int i) {
        Object b =new Object();
        new Thread(()->{
            synchronized (b){
                //new SynchronizedTest().test2(i);

                int a =5;
                while (a>=0){
                    System.out.println(i+":"+a);
                    a--;
 /*                   try {
                        TimeUnit.MILLISECONDS.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        },String.valueOf(i)).start();

/*        new Thread(()->{
            synchronized (SynchronizedTest.class){
                new SynchronizedTest().test2(i);
            }
        },"线程2").start();*/
    }

    private static void testMethonStatic() {
        new Thread(()->{
            new SynchronizedTest().test1();
        },"线程1").start();


        new Thread(()->{
            new SynchronizedTest().test1();
        },"线程2").start();
    }
}
