package main.config;
import main.Controller.RocketMq.OrderTransactionListener;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author fengyunwei
 */
@Configuration
public class OrderProducer {
/*    *//**
     * 生产组,消费者通过订阅发布，在同一个组内
     *//*
    @Value("${rocketmq.producer.group}")
    private String producerGroup;
    *//**
     * 端口
     *//*
    @Value("${rocketmq.name-server}")
    private String nameServer;*/
    //事务监听,其中rocketMQLisenter实现TransactionListener接口
    private OrderTransactionListener rocketMQLisenter;
    //事务消息生产者配置
    private TransactionMQProducer producer;

    private String producerGroup ="fengyunweiTransaction";
    public OrderProducer(@Value("${rocketmq.name-server}") String nameServer) {
        producer = new TransactionMQProducer(producerGroup);
        rocketMQLisenter = new OrderTransactionListener();
        // 指定nameServer地址,多个地址之间以 ; 隔开
        producer.setNamesrvAddr(nameServer);
        producer.setTransactionListener(rocketMQLisenter);
        start();
    }

    public TransactionMQProducer getProducer() {
        return producer;
    }

    /**
     * 对象在使用之前必须调用一次,并且只能初始化一次
     */
    public void start() {
        try {
            this.producer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * 一般在应用上下文,使用上下文监听器,进行关闭
     */
    public void shutdown() {
        producer.shutdown();
    }

}