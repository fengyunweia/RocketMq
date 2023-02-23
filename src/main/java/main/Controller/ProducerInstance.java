package main.Controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MQProducer;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ProducerInstance {
 
    public static final String APPENDER_TYPE = "APPENDER_TYPE";
 
    public static final String LOG4J_APPENDER = "LOG4J_APPENDER";
 
    public static final String LOG4J2_APPENDER = "LOG4J2_APPENDER";
 
    public static final String LOGBACK_APPENDER = "LOGBACK_APPENDER";
 
    public static final String DEFAULT_GROUP = "rocketmq_appender";
 
    private ConcurrentHashMap<String, MQProducer> producerMap = new ConcurrentHashMap<String, MQProducer>();
 
    private static ProducerInstance instance = new ProducerInstance();
 
    public static ProducerInstance getProducerInstance() {
        return instance;
    }
    
    /**
   根据 nameServerAddress 和 group 生成 producer 在 producerMap 中的键
    **/ 
    private String genKey(String nameServerAddress, String group) {
        return nameServerAddress + "_" + group;
    }
 
/**
根据 nameServerAddress 和 group 获取已注册到producerMap中的producer，如果不存在，则调用 DefaultMQProducer 生成新的producer注册并返回
**/ 
    public MQProducer getInstance(String nameServerAddress, String group) throws MQClientException, MQClientException {
        if (StringUtils.isBlank(group)) {
            group = DEFAULT_GROUP;
        }
 
        String genKey = genKey(nameServerAddress, group);
        MQProducer p = getProducerInstance().producerMap.get(genKey);
        if (p != null) {
            return p;
        }
 
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(group);
        defaultMQProducer.setNamesrvAddr(nameServerAddress);
        MQProducer beforeProducer = null;
        beforeProducer = getProducerInstance().producerMap.putIfAbsent(genKey, defaultMQProducer);
        if (beforeProducer != null) {
            return beforeProducer;
        }
        defaultMQProducer.start();
        return defaultMQProducer;
    }
 
/**
根据 nameServerAddress 和 group 移除已注册到producerMap中的producer，同时shutdown
**/ 
    public void removeAndClose(String nameServerAddress, String group) {
        if (group == null) {
            group = DEFAULT_GROUP;
        }
        String genKey = genKey(nameServerAddress, group);
        MQProducer producer = getProducerInstance().producerMap.remove(genKey);
 
        if (producer != null) {
            producer.shutdown();
        }
    }
 
/**
移除 producerMap 中所有的 producer 并全部关闭。
**/
 
    public void closeAll() {
        Set<Map.Entry<String, MQProducer>> entries = getProducerInstance().producerMap.entrySet();
        for (Map.Entry<String, MQProducer> entry : entries) {
            getProducerInstance().producerMap.remove(entry.getKey());
            entry.getValue().shutdown();
        }
    }
 
}