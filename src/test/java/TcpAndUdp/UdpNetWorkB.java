package TcpAndUdp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class UdpNetWorkB {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(9090);
        byte[] bytes = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(bytes,0,bytes.length);
        socket.receive(datagramPacket);
        System.out.println(datagramPacket.getAddress().getHostAddress());
        String s = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
        System.out.println(s);
        socket.close();

    }
}
