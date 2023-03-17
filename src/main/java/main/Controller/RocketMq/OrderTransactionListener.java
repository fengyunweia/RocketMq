package main.Controller.RocketMq;

import com.alibaba.fastjson.JSONObject;
import main.utils.ApplicationContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;


/**
 * @author fengyunwei
 */
@Service
public class OrderTransactionListener implements TransactionListener  {
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        String body = Arrays.toString(message.getBody());
        //在下面根据具体的逻辑判断做业务操作

        //将事务id存到数据库，为回查做
        String transactionId = message.getTransactionId();

        return LocalTransactionState.COMMIT_MESSAGE;
    }
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {

        String transactionId = messageExt.getTransactionId();
        //根据事务id从数据库查，如果额能查到则提交 否则不提交

        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
