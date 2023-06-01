package main.chat;

import java.io.IOException;
import main.chat.TalkReceive;
import main.chat.TalkSend;
/**
 * @author fengyunwei
 */
public class chatA {
    public static void main(String[] args) throws IOException {
        //开启线程
        //向TO_IP发送信息 发送的端口号是TO_PORT
        new Thread(new TalkSend(7777,"localhost",9999)).start();
        //使用8888端口接受数据 对方需要发送到8888端口
        new Thread(new TalkReceive(8888,"冯云伟")).start();
    }
}
