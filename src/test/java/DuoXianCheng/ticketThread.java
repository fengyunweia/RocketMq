package DuoXianCheng;

public class ticketThread implements Runnable{

    private int a = 10;
    Object object = new Object();

    @Override
    public void run() {
        while (true){
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"还未购买"+a);
            synchronized (ticketThread.class){
                if(a<=0){
                    break;
                }
                System.out.println(Thread.currentThread().getName()+":"+a--);
            }


        }
    }

    public static void main(String[] args) {
        ticketThread ticketThread = new ticketThread();
        new Thread(ticketThread,"小明").start();
        new Thread(ticketThread,"黄牛党").start();
    }
}
