package TcpAndUdp.chat;

import java.io.IOException;

public class chatB {
    public static void main(String[] args) throws IOException {
        //开启线程
        new Thread(new TalkSend(5555,"192.168.3.96",8888)).start();
        new Thread(new TalkReceive(9999,"穆爽")).start();
    }
}
