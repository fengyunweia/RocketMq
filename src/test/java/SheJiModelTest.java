import main.Application;
import main.Controller.SheJiModel.FactoryModel.WeiLaiCar;
import main.Controller.SheJiModel.FactoryModel.XiaoMiCar;
import main.Controller.SheJiModel.JianZaoZhe.BeefHamburgServiceImpl;
import main.Controller.SheJiModel.JianZaoZhe.ChickenHamburgServiceImpl;
import main.Controller.SheJiModel.JianZaoZhe.Director;
import main.Controller.SheJiModel.Single.SingleClass;
import main.Controller.SheJiModel.Strategy.Strategy;
import main.Controller.SheJiModel.observer.CannonFodder;
import main.Controller.SheJiModel.observer.CannonShooter;
import main.Controller.SheJiModel.observer.Commander;
import main.Controller.SheJiModel.proxy.Image;
import main.Controller.SheJiModel.proxy.ProxyImage;
import main.Controller.SheJiModel.proxy.dynamicProxy.Animal;
import main.Controller.SheJiModel.proxy.dynamicProxy.CglibDynamicProxy;
import main.Controller.SheJiModel.proxy.dynamicProxy.JdkDynamicProxy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Proxy;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class SheJiModelTest {

    /**
     * 建造者模式：将一个复杂对象的构建与表示分离，使得同样的构建过程可以创建不同的表示。
     * 使用场景： 当一个类的构造函数参数个数超过4个，而且这些参数有些是可选的参数，考虑使用构造者模式
     */
    @Test
    public void test(){
        //创建指导者 指导者主要是为了让 不同的产品 走各自的设置产品属性方法（指导方法）
        Director director = new Director();
        //各自实现类的构造方法都会给产品填充必填属性
        ChickenHamburgServiceImpl chickenHamburgService = new ChickenHamburgServiceImpl("黄包","鸡排");
        //director.makeHamburg(chickenHamburgService);
        System.out.println(chickenHamburgService.getHamburg().toString());

        BeefHamburgServiceImpl beefHamburgService = new BeefHamburgServiceImpl("红包", "牛排");
        director.makeHamburg(beefHamburgService);
        System.out.println(beefHamburgService.getHamburg().toString());
    }

    /**
     * 工厂模式 意图：定义一个创建对象的接口，让其子类自己决定实例化哪一个工厂类，工厂模式使其创建过程延迟到子类进行。
     * 例子：您需要一辆汽车，可以直接从工厂里面提货，而不用去管这辆汽车是怎么做出来的，以及这个汽车里面的具体实现。
     *       Hibernate 换数据库只需换方言和驱动就可以
     */
    @Test
    public void test1(){
        String carName = "xiaoMi";
        if(carName.equals("xiaoMi")){
            new XiaoMiCar().create();
        }else {
            new WeiLaiCar().create();
        }
    }

    /**
     * 设置一个静态类 设置为null 获取的时候如果为null 再加载到内存 实现懒加载
     * 如果直接在这里实例化 则为预加载 还没使用就加载到内存，造成内存资源浪费
     */

    /**
     * 单例模式讲解
     * 为什么要使用引用synchronized？
     * 因为getInstance方法再单线程没问题，但是多线程的情况下（多线程随时会切换）可能线程A进入第一个判定之后，线程B也走到第一个判定，这时候会创建两个对象
     * 所以要使用synchronized
     * 为什么要不把锁放到方法上？
     * 如果加载getInstance方法上，不管有没有初始化实例，
     * 都会唤醒和阻塞线程。**为了避免线程的上下文切换消耗大量时间，如果对象已经实例化了，我们没有必要再使用synchronized加锁，直接返回对象。
     * 因此我们把锁加到实例化对象上，这样保证只有未实例化的时候才加锁
     * 为什么要双重检查模式？
     * 两个线程都进来了，判断singleton为null A抢到锁，B等待，A 直接new结束，释放线程 ，B拿到锁，因为之前已经进入new，则导致创建两次
     * 为什么要使用volatile关键字修饰对象？ @link volatileTest
     * 这里看似完美了，但是还有一个问题，new对象会发生重排序
     * {
     *     singleton = new Singleton(); 这个操作不是原子的，可能发生重排序 ，可以分为三步：
     *      步骤1：在堆内存申请一块内存空间；
     *      步骤2：初始化申请好的内存空间；
     *      步骤3：将内存空间的地址赋值给 singleton；
     *      线程A走到了重排序是1-3-2 此时线程A走到3 线程B来了 发现已经把地址赋值给对象，不为null 则假设此时线程 B 进入 getInstance 方法，
     *      由于 singleton 已经不是 null 了，所以会通过第一重检查并直接返回，但其实这时的 singleton 并没有完成初始化，所以使用这个实例的时候会报错，
     * }
     *
     * 因此需要使用 volatile 修饰对象，禁止重排序，让new操作按照顺序来实例化对象；
     */
    @Test
    public void test2() {
        SingleClass instance = SingleClass.getInstance();
    }

    /**
     * 策略模式 在有多种算法相似的情况下，使用 if...else 所带来的复杂和难以维护。
     * 应用场景：1、诸葛亮的锦囊妙计，每一个锦囊就是一个策略。 2、旅行的出游方式，选择骑自行车、坐汽车，每一种旅行方式都是一个策略
     * 。 3、JAVA AWT 中的 LayoutManager。
     */
    @Test
    public void test3() {
        Strategy.Context context = new Strategy.Context(new Strategy.Add());
        System.out.println(context.executeStrategy(5, 2));
    }

    /**
     * 观察者模式
     * 当对象间存在一对多关系时，则使用观察者模式（Observer Pattern）。比如，当一个对象被修改时，则会自动通知依赖它的对象。观察者模式属于行为型模式。
     * 拍卖的时候，拍卖师观察最高标价，然后通知给其他竞价者竞价。
     *
     * 根据项目距离 ：比如排期有一个业务 更改了一条明细的价格，就会更改主明细的价格，更改排期的价格
     * 创建一个被观察者Subject接口，将明细价格类实现这个接口，创建一个hashmap 用来放实现了观察者接口的两个类
     * （创建一个观察者接口，主明细和排期实现这个接口的update方法，）
     * 修改某一个明细价格之后 在明细价格类接口里调取通知接口，循环调取hashmap 这两个类的update方法，同步修改金额
     */
    @Test
    public void testObserver(){
        //指挥官实现被观察接口，被观察
        Commander cmder = new Commander();
        //给指挥官设置指令为107
        cmder.setState(107);
        //将指令赋值给炮兵
        CannonShooter cster = new CannonShooter(cmder);
        //将指令设置给炮兵，并将炮灰设置编号
        CannonFodder cfder1 = new CannonFodder(cmder,1);
        CannonFodder cfder2 = new CannonFodder(cmder,2);
        CannonFodder cfder3 = new CannonFodder(cmder,3);

        //将炮兵和炮灰都注册到指挥官（被观察者）名下
        cmder.attach(cster);
        cmder.attach(cfder1);
        cmder.attach(cfder2);
        cmder.attach(cfder3);

        //指挥官下令炮兵开炮，炮灰1，2，3倒下
        cmder.sNotify();


        //设置新的命令 移除炮灰三号
        cmder.setState(108);
        cmder.detach(cfder3);

        //指挥官下令炮兵开炮，炮灰1，2，倒下
        cmder.sNotify();

    }

    /**
     * 代理模式  1:在直接访问对象时带来的问题，比如说：要访问的对象在远程的机器上。在面向对象系统中，有些对象由于某些原因（比如对象创建开销很大，
     * 或者某些操作需要安全控制，或者需要进程外的访问），直接访问会给使用者或者系统结构带来很多麻烦，我们可以在访问此对象时加上一个对此对象的访问层。
     * 2:买火车票不一定在火车站买，也可以去代售点
     * 3:能够使得在不修改源目标的前提下，额外扩展源目标的功能。即通过访问源目标的代理类，再由代理类去访问源目标。这样一来，要扩展功能，
     * 就无需修改源目标的代码了。只需要在代理类上增加就可以了
     */
    @Test
    public void proxy(){
        Image image = new ProxyImage("test_10mb.jpg");

        // 图像将从磁盘加载
        image.display();
        System.out.println("");
        // 图像不需要从磁盘加载
        image.display();
    }

    /**
     * jdk动态代理测试
     *  * jdk 动态代理需要有接口类即Animal jdk动态代理只能基于接口，代理生成的对象只能赋值给接口变量，而Cglib就不存在这个问题
     */
    @Test
    public void dynamicProxy(){
        //这里需要穿要被代理的类，因为，执行完代理类的增强代码之后，还要执行原有的方法逻辑，如果不设置会造成死循环即 代类一直代理自己无限代理
        JdkDynamicProxy proxy = new JdkDynamicProxy(new Animal.Cat("小狸花"));
        Animal o = (Animal)Proxy.newProxyInstance(proxy.getClass().getClassLoader(), new Class[]{Animal.class}, proxy);
        o.sleep();
        o.wakeup();
    }

    /**
     * cglib动态代理测试
     * Cglib是通过生成子类来实现的，代理对象既可以赋值给实现类，又可以赋值给接口。
     *  * Cglib速度比jdk动态代理更快，性能更好。
     */
    @Test
    public void cglibDynamicProxy(){

        CglibDynamicProxy proxy = new CglibDynamicProxy(new Animal.Cat("张三"));
        Animal.Cat student = (Animal.Cat) proxy.getProxy();
        student.wakeup();
        student.sleep();
    }

}
