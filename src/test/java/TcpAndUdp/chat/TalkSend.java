package TcpAndUdp.chat;

import jdk.nashorn.internal.objects.NativeUint8Array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class TalkSend implements Runnable{

    DatagramSocket socket = null;
    BufferedReader bufferedReader = null;
    private int FROM_PORT;
    private String TO_IP;
    private int TO_PORT;
    public TalkSend(int FROM_PORT,String TO_IP,int TO_PORT) {
        this.FROM_PORT = FROM_PORT;
        this.TO_IP = TO_IP;
        this.TO_PORT = TO_PORT;

        try {
            socket = new DatagramSocket(FROM_PORT);
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            while (true){
                String data;
                try {
                    data = bufferedReader.readLine();
                    DatagramPacket packet = new DatagramPacket(data.getBytes(StandardCharsets.UTF_8),
                            0,
                            data.getBytes(StandardCharsets.UTF_8).length,
                            new InetSocketAddress(TO_IP,TO_PORT));
                    socket.send(packet);
                    if(data.equals("拜拜")){
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            socket.close();


    }
}
