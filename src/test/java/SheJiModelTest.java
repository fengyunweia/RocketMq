import main.Application;
import main.Controller.SheJiModel.FactoryModel.Car;
import main.Controller.SheJiModel.FactoryModel.WeiLaiCar;
import main.Controller.SheJiModel.FactoryModel.XiaoMiCar;
import main.Controller.SheJiModel.JianZaoZhe.BeefHamburgServiceImpl;
import main.Controller.SheJiModel.JianZaoZhe.ChickenHamburgServiceImpl;
import main.Controller.SheJiModel.JianZaoZhe.Director;
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

}
