import main.Application;
import main.SPI.Phone;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.ServiceLoader;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class SpringSpiTest {

    /**
     * SPI
     * 接口Phone 有两个实现类  服务调用方定义接口规范（入参，出参）
     * 服务提供放根据接口规范提供实现类 （即实现类打成jar包然后给服务使用方依赖使用）
     * 然后使用ServiceLoader.load（）方法 会根据此接口类的全限定名找到META-INF.services 下的文件 根据里面的文件找到确定的服务提供方
     * 实现可拔插
     * 服务使用方主导
     *
     * API
     * 服务提供方主导
     *
     *
     * Spring API 相同原理 根据  SpringFactoriesLoader  找到 spring.factories 里面 的全限定类名
     */
    @Test
    public void find(){
        List<Phone> phones = SpringFactoriesLoader.loadFactories(Phone.class, Thread.currentThread().getContextClassLoader());
        for (Phone phone : phones) {
            String say = phone.say();
            System.out.println(say);
        }
    }
}
