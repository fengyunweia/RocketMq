package main.chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @author fengyunwei
 */
public class TalkReceive implements Runnable{
    DatagramSocket socket = null;
    private int port;
    private String message;
    public TalkReceive(int port,String message){
        this.port = port;
        this.message = message;
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true){
            try {
                byte[] bytes = new byte[1024];
                DatagramPacket packet = new DatagramPacket(bytes,0,bytes.length);
                socket.receive(packet);
                String s = new String(packet.getData(), 0, packet.getData().length);
                System.out.println(message+":"+s);
                if(s.equals("拜拜")){
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        socket.close();
    }
}
