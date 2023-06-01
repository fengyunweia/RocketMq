package main.chat;

import java.io.IOException;

/**
 * @author fengyunwei
 */
public class chatB {
    public static void main(String[] args) throws IOException {
        //开启线程
        new Thread(new TalkSend(5555,"localhost",8888)).start();
        new Thread(new TalkReceive(9999,"穆爽")).start();
    }
}
