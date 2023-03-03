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
class RocketMqTest {

    @Autowired
    private RocketMQTemplate template;

    @Test
    public void send() throws InterruptedException, MQBrokerException, RemotingException, MQClientException {

        /**
         * rocketMq 如何保证消息的可靠性
         * 生产者：
         * 同步发送 会等待发送信息的返回结果 只有返回了SEND_OK 才是发送成功
         * 异步发送 生产者发送有 成功和失败的回调函数 ，（回调函数是在另一个线程中执行）也是只有返回的了SEND_OK才是发送成功，
         * 上面两个如果返回失败 则进入发送重试，根据相应的策略（messageDelayLevel16个等级）进行重试
         * 如果如果消费满16次之后还是未能消费成功，则不再重试，会将消息发送到死信队列（人工Console 控制界面干预），从而保证消息存储的可靠性。
         * 单向发送 如果想要保证消息可靠性，建议不适用这种方法
         *
         * 储存端(broker)消息可靠性保证
         * ConsumeQueue: CommitLog里存储了Consume Queues 、Message Key、Tag等所有信息
         * ，即使ConsumeQueue丢失，也可以通过 commitLog完全恢复出来，这样只要保证commitLog数据的可靠性，就可以保证Consume Queue的可靠性。
         *
         * commintLog 可靠性保证
         * RocketMQ存储端也即Broker端在存储消息的时候会面临以下的存储可靠性挑战：
         *
         * 过期文件删除
         * 为了不让硬盘被过期消息占满，导致消息不能持久化到硬盘 过期删除一定文件，使硬盘容量一直保持再一个水平上
         *
         * 1：Broker正常关闭
         * 2：Broker异常Crash
         * 3：OS Crash
         * 4：机器掉电，但是能立即恢复供电情况
         * 5：机器无法开机（可能是cpu、主板、内存等关键设备损坏）
         * 6：磁盘设备损坏
         *
         * 1：正常关闭 broker可以通过正常启动回复所有数据 234同步刷盘（即有一条消息进来就立刻存储） 56单点故障可以设置slave节点
         * 主从异步复制仍然可能有极少量数据丢失，同步复制可以完全避免单点问题。
         *
         * 消费者消息可靠性
         * 通常消费消息的确认机制一般分为两种思路
         * 1：先提交后消费
         * 2：先消费，消费成功后再提交
         * 思路1可以解决重复消费的问题但是会丢失消息，因此RocketMQ默认实现的是思路2，由各自consumer业务方保证幂等来解决重复消费问题。
         *
         *
         */

        /**
         * rocket如何保证顺序一致性
         * 1:将消息放到同一个队列
         * 2:生产者生产消息顺序放到broker 的commintlog
         * 3:消费者消费消息顺序消费
         */

        /**
         * rocketMq读写队列（writeQueueNums和readQueueNums）为什么不固定成读写队列数量一致
         *
         * 生产者写队列设置8 读队列设置4 则发送消息会发送到broker的0，1，2，3，4，5，6，7队列 都队列设置为4
         * 则消费者会从0，1，2，3队列消费数据  4，5，6，7队列的数据不会被消费
         * 反过来，如果写队列个数是4，读队列个数是8，在生产消息时只会往0 1 2 3中
         * 生产消息消费消息时则会从0 1 2 3 4 5 6 7所有的队列中消费，当然 4 5 6 7中压根就没有消息 。
         *
         * 问：为什么不强制设置成读队列等于写队列
         * 为了方便队列的缩容与扩容
         * 缩容：加入一个topic 上有128个队列 但是后来发现并不需要怎末多写队列，现在要缩容成64 则只需要先缩容写队列到64 等读队列把65-128队列的信息
         * 消费完，在将都队列设置生64  完美100%保存数据
         * 扩容：加入64 要换成128 则先将写队列设置成128 新增读写队列为128即可
         * 
         *
         * */

        /**
         * rocketMq生产环境开启自动创建topic 会有什么影响
         * rocketMq的读写队列是
         */
/*        //同步发送 对结果很关注
        for (int i=0;i<=100;i++){
            SendResult sendResult = template.syncSend("fengyunweiTopic",String.valueOf(i));
            System.out.printf(sendResult.getSendStatus().equals(SEND_OK)?"成功:"+i:"失败:"+i);
        }*/
        //保证消息顺序性
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