import main.Application;
import main.Controller.SheJiModel.FactoryModel.Car;
import main.Controller.SheJiModel.FactoryModel.WeiLaiCar;
import main.Controller.SheJiModel.FactoryModel.XiaoMiCar;
import main.Controller.SheJiModel.JianZaoZhe.BeefHamburgServiceImpl;
import main.Controller.SheJiModel.JianZaoZhe.ChickenHamburgServiceImpl;
import main.Controller.SheJiModel.JianZaoZhe.Director;
import main.Controller.SheJiModel.Single.SingleClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
     * 策略模式
     */
    @Test
    public void test3() {

    }

}
