/*
package main.Controller.RocketMq;

import com.alibaba.fastjson.JSON;
import main.config.OrderProducer;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

*/
/**
 * @author fengyunwei
 *//*

@RestController
@RequestMapping("/test")
public class Controller {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/testmq")
    public String sayHello() throws MQClientException, MQBrokerException, RemotingException, InterruptedException, UnsupportedEncodingException {
        //创建一个生产者，指定生产者组为sanyouProducer
        DefaultMQProducer producer = new DefaultMQProducer("fengyunwei");
        // 指定NameServer的地址
        producer.setNamesrvAddr("192.168.253.128:9876");
        // 第一次发送可能会超时，我设置的比较大
        producer.setSendMsgTimeout(60000);

        // 启动生产者
        producer.start();

        // 创建一条消息
        // topic为 sanyouTopic
        // 消息内容为 三友的java日记
        // tags 为 TagA
        Message msg = new Message("fengyunweiTopic", "TagA", "fengyunweiStudy ".getBytes(RemotingHelper.DEFAULT_CHARSET));

        // 发送消息并得到消息的发送结果，然后打印
        SendResult sendResult = producer.send(msg);
        System.out.printf("%s%n", sendResult);

        // 关闭生产者
        producer.shutdown();

        return "你好,世界";
    }

    @PostMapping("/addTransaction")
    public void addTrans(@RequestBody String s) throws InterruptedException {
        RLock rLock = redissonClient.getLock("lockTest");
        if(rLock.tryLock(6,10, TimeUnit.SECONDS)){
            try {
                Message message = new Message("fengyunweiTransaction", s.getBytes());
                orderProducer.getProducer().sendMessageInTransaction(message,null);
            } catch (MQClientException e) {
                System.out.println("发送失败");
            } finally {
                rLock.unlock();
            }

        }
    }

}
*/
