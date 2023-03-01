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

    @Test
    public void test(){
        Knife knife =new Knife();
        knife.setThing("刀子");
        System.out.println(knifeThingService.doSomething(knife));
    }

}
