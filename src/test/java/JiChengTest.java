import main.Application;
import main.Controller.JiCheng.Knife;
import main.Controller.JiCheng.KnifeThingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class JiChengTest {

    @Autowired
    private KnifeThingService knifeThingService;

    /**
     * 使用范围:
     * 比如审批等具有相同动作 抽出来在基础里 其他继承实现
     * 问：既然都是实现层实现,为什么不把baseservicesImpl 设置生接口
     *  因为要在里面对方法具体实现
     *  问，为什么具体实现类需要继承基础实现类 并interface自己的业务接口
     *  如果不interface自己的业务接口，则业务接口因为没有自己的具体实现类，不能被调用方成功注入 继承基础实现类则是为了根据自己的具体属性扩展基础实现类的方法
     */
    @Test
    public void test(){
        Knife knife =new Knife();
        knife.setThing("刀子");
        System.out.println(knifeThingService.doSomething(knife));
    }

}
