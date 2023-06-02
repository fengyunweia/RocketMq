package DuoXianCheng;

public class Test {

    private static int i =100;


    public static void main(String[] args) {


        new Thread(()->{

        },"a").start();

        new Thread(()->{

        },"b").start();

        new Thread(()->{

        },"c").start();
    }

    public static void buy(){
        synchronized (Test.class){

        }
    }



}
