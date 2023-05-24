import main.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class Docker {
    //docker 概述
    /**
     * 相比于虚拟机把一整个linux的系统 软件硬件所用东西的下载 会很笨重 一般G以上
     * docker数据轻量化的容器技术 比如只拉mysql的镜像 一般几M-几百M 甚至KB
     *
     * 不需要重复的造轮子（运行环境 ，或者集群配置之类的）
     * 保持了隔离性
     */

    /**
     * linux 能ping通容器内的网卡 
     */

}
