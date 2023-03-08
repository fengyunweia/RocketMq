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
         * 上面两个如果返回失败 则进入发送重试，根据相应的策略（messageDelayLevel16个等级）进行重试(ACK)
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
         * */

        /**
         * rocketMq生产环境开启自动创建topic 会有什么影响
         *
         * 1：用户指定的读写队列数可能不是预期结果。创建的Topic的读队列数和写队列数取值为默认Topic（“TBW102”）的读队列数和Produce端设置的队列数的最小值
         *
         * 2：mq broker启动时，如果开启自动创建topic 则会把默认队列放到topic 配置表里
         * rocketMq的自动开启创建topic 此时如果生产者发送一条消息 发现nameserver 找不到路由信息
         * mq会使用默认主题“TBW102”再次发送（把topic为key  消息放到TopicPublishInfo）若
         * 此时broker 开启了自动创建 则此时自动创建了一个topic （此时刚好到达30S nameserver 拉取路由信息 ）然后把路由信息注册给nameserver
         * （由于生产者会每隔30s拉取nameserver 的路由缓存到本地 恰好此时正好到30S） / （或者生产者30S内只发送一次 Broker 随着心跳把这个路由信息更新到 NameServer 了）
         * 其他生产者下次发送就有了此路由的消息，则以后所有的生产者都会发送此topic 的信息到次broker 压力暴增
         *
         * 3：（不可以）或者如果此时发送到此broker的某条消息发送失败，则由于故障延迟（Producer如果往某个Broker发送消息失败了，就认为该Broker发生了故障
         * ，接下来（的30s）该客户端上的所有消息就都不再往该Broker发送了。）此topic 的消息就全部命中失败
         *
         * （可以）如果开启自动创建 （nameServer每隔30S监测broker并拉取路由信息阶段）
         * 此时生产者还一直发送此topic 的信息，则由于负载均衡会有很多个broker有topic的路由信息，达到负载均衡的作用 这种情况可以
         *
         */

        /**
         *  索引（consumerQueue 和indexFile）
         * 生产者生产的消息保存在commintlog 中 有一个后端的 ReputMessageService 服务 (dispatch 线程) 会异步的构建多种索引，
         * 满足不同形式的读取诉求。索引信息保存在consumerQueue中记录（或者index file中）
         * 消费者消费先获取consumerQueue 得到其中的信息，减小开销，就能获取消费者需要的消息信息（或者messageId）
         *
         *
         * indexFile
         * 每个Broker包含一组indexFile，每个indexFile都是以该indexFile被创建时的时间戳进行命名的。
         *
         * 每个indexFile由三部分组成：indexHeader（索引头），Slots（槽位），indexes（索引数据）。
         * indexHeader索引头里面主要是这个indexFile
         * biginTimestamp(第一条消息存储时间戳)，
         * endTimestamp（最后一条消息存储时间戳）
         * biginPhyoffset(第一条消息在commitlog中的偏移量，即commitlog offset)，
         * endPhyoffset（最后一条消息在commitlog中的偏移量），
         * hashSlotCount（含有index的slot数量），
         * indexCount(包含的索引单元的个数)
         *
         * slots
         * 一个indexfile 里面有500W个槽位
         * 事实上，所有的index统一放在所有的slots后，而不是每个slots后面放该slot挂载的indexes。因为无法确定该slot要挂载的indexes数量并预留空间。
         * key的hash值 % 500万的结果即为slot槽位，然后将该slot值修改为该index索引单元的indexNo，根据这个indexNo可以计算出该index单元在indexFile中的位置。
         * 该取模结果的重复率是很高的，为了解决该问题，在每个index索引单元中增加了preIndexNo，用于指定该slot中当前index索引单元的前一个index索引单元。
         * 每个indexFile包含500万个slot，每个slot有可能会挂载很多index索引单元
         *
         * indexs
         * 存放keyHash（消息中指定业务key的hash值），
         * phyOffset(当前key对应的commitlog offset)，
         * timeDiff（当前key对应消息的存储时间与indexFile的时间差），
         * preIndexNo（当前slot下index索引单元的前一个索引单元的indexNo）
         *
         * indexFile创建时间
         * 当第一条带key的消息发送来后，系统发现没有indexFile，此时会创建第一个indexFile文件
         * 当一个indexFile中挂载的index索引单元数量超出2000w个时，会创建新的indexFile。当带key的消息发送到来后，
         * 系统会找到最新的indexFile，并从indexHeader的最后4字节中读取到indexCount。若indexCount >= 2000w时，会创建新的indexFile。
         *
         */
        /**
         * 如何增加mq 的消费速度
         *
         * 1.RabbitMQ 与 RocketMQ/Kafka 的消费方式是不同的， RabbitMQ可以被多个消费者同时消费，
         * 但是RockeMQ和Kafka每个队列或分区只能被一个消费者消费，所以要提高者两者的消费速度，只能通过增加分区或队列数（rocketMq 同时增加queue的数量和消费者的数量）
         * 2.三种都提供了批量拉取和批量ACK的功能，都是提高速率不错的方法，但需要考虑消息的丢失问题。
         * 3.批量拉取消息的时候，消费速度限制在单个消费者身上，这时候可以通过多线程或者协程提高消费速度。
         */

        /**
         * 怎末防止消息重复消费
         *
         * 解决方案
         * 去重操作直接放在了消费端，消费端处理消息的业务逻辑保持幂等性。那么不管来多少条重复消息，可以实现处理的结果都一样。
         *
         * 数据库表
         * 处理消息前，使用消息主键在表中带有约束的字段中insert。建立一张日志表，使用消息主键作为表的主键，在处理消息前，先insert表，再做消息处理。这样可以避免消息重复消费
         *
         * Map
         * 单机时可以使用map ConcurrentHashMap -> putIfAbsent guava cache
         *
         * Redis
         * 分布式锁搞起来。
         */

        /**
         *
         * 组的作用
         * 作用是在集群HA的情况下，一个生产者down之后，本地事务回滚后，可以继续联系该组下的另外一个生产者实例，不至于导致业务走不下去。在消费者组中，可以实现消息消费的负载均衡和消息容错目标。
         *
         * 另外，有了GroupName，在集群下，动态扩展容量很方便。只需要在新加的机器中，配置相同的GroupName。启动后，就立即能加入到所在的群组中，参与消息生产或消费。
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