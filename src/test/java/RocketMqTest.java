import main.Application;
import main.domain.Student;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.apache.rocketmq.client.producer.SendStatus.SEND_OK;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
class RocketMQTest {

    @Autowired
    private RocketMQTemplate template;

    @Test
    public void send() throws InterruptedException, MQBrokerException, RemotingException, MQClientException {
/*        //同步发送 对结果很关注
        for (int i=0;i<=100;i++){
            SendResult sendResult = template.syncSend("fengyunweiTopic",String.valueOf(i));
            System.out.printf(sendResult.getSendStatus().equals(SEND_OK)?"成功:"+i:"失败:"+i);
        }*/
        for (int i = 0; i <100 ; i++) {
            MessageQueueSelector queueSelector = new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    int integer = Integer.parseInt(o.toString());
                    int size = list.size();
                    return list.get(integer%size);
                }
            };
            Message message =new Message();
            message.setTopic("fengyunweiTopic");
            message.setBody(String.valueOf(i).getBytes(StandardCharsets.UTF_8));
            template.getProducer().send(message,queueSelector,1234);
            /*template.setMessageQueueSelector(queueSelector);
            template.syncSendOrderly("fengyunweiTopic",i,String.valueOf(1234));*/
            System.out.printf("发送消息:"+i);

        }
       /* //异步发送
        for (int i=0;i<=100;i++){
            int finalI = i;
            template.asyncSend("fengyunweiTopic", String.valueOf(i), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("成功:"+ finalI);
                }

                @Override
                public void onException(Throwable throwable) {
                    System.out.printf("失败:"+ finalI);
                }
            });
        }*/

        //单向- 只管发送，不管结果
        /*for (int i=0;i<=100;i++){
            template.sendOneWay("fengyunweiTopic","i");
        }*/
    }

}