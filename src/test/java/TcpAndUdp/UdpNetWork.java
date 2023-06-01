package TcpAndUdp;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UdpNetWork {
    public static void main(String[] args) throws IOException {
        DatagramSocket datagramSocket = new DatagramSocket();
        InetAddress localhost = InetAddress.getByName("localhost");
        String message ="hello";
        DatagramPacket datagramPacket = new DatagramPacket(message.getBytes(StandardCharsets.UTF_8),0,message.getBytes().length,localhost,9090);
        datagramSocket.send(datagramPacket);

        datagramSocket.close();
    }
}
