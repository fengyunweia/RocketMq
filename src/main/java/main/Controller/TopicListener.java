
package main.Controller;

import main.domain.Student;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;


/**
 * @author fengyunwei
 */

@Component
@RocketMQMessageListener(consumerGroup = "fengyunwei",topic = "fengyunweiTopic",consumeMode = ConsumeMode.ORDERLY)
public class TopicListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        System.out.println("消费消息:"+s);
    }
}
