package TcpAndUdp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器 网络编程 TCP
 */
public class NetWorkB {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        Socket accept = null;
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream =null;
        while(true){
            accept = serverSocket.accept();
            inputStream = accept.getInputStream();
            outputStream = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len;
            while((len = inputStream.read(bytes))!=-1){
                outputStream.write(bytes,0,len);
            }
            System.out.println(outputStream.toString());
        }

    }
}
