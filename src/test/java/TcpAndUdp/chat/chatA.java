package TcpAndUdp.chat;

import java.io.IOException;

public class chatA {
    public static void main(String[] args) throws IOException {
        //开启线程
        //向TO_IP发送信息 发送的端口号是TO_PORT
        new Thread(new TalkSend(7777,"192.168.3.197",9999)).start();
        //
        new Thread(new TalkReceive(8888,"冯云伟")).start();
    }
}
