package main.Controller.SheJiModel.proxy.dynamicProxy;

public interface Animal {
    void wakeup();

    void sleep();


    //实现类1
    public class Cat implements Animal {
        private String name;

        public Cat() {
        }

        public Cat(String name) {
            this.name = name;
        }

        @Override
        public void wakeup() {
            System.out.println("小猫" + name + "早晨醒来啦");
        }

        @Override
        public void sleep() {
            System.out.println("小猫" + name + "晚上睡觉啦");
        }
    }

    //实现类2
    public class Dog implements Animal {
        private String name;

        public Dog() {
        }

        public Dog(String name) {
            this.name = name;
        }

        @Override
        public void wakeup() {
            System.out.println("小狗" + name + "早晨醒来啦");
        }

        @Override
        public void sleep() {
            System.out.println("小狗" + name + "晚上睡觉啦");
        }
    }
}