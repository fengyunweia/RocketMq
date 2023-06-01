package TcpAndUdp;

import main.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.InternetAddressEditor;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.io.OutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.function.DoubleToIntFunction;

/**
 * 网络编程 TCP
 */
public class NetWork {


    /**
     * 网络编程要素
     * 1：ip
     * 2：端口
     * 3：通讯协议(TCP /UDP) TCP 需要链接一下   UDP不用，可以直接发
     * 4:
     */
    /**
     *  三次握手 四次挥手
     *  A: 我想链接B
     *  B: 我同意跟你链接
     *  B: 我收到了你想跟我链接 开始链接
     *
     *  A :我想断开了
     *  B: 我知道你要断开了
     *  B: 告诉A  B也准备好要断开了
     *  D: A发送B 可以t断开连接了
     *
     */
    public static void main(String[] args) throws IOException {
/*        InetAddress byName = InetAddress.getByName("www.baidu.com");
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(byName);
        System.out.printf(localHost.toString());
        InetSocketAddress inetSocketAddress = new InetSocketAddress("www.baidu.com",8080);
        */
        InetAddress byName;
        Socket socket = null;
        OutputStream outputStream = null;
        try {
            byName = InetAddress.getByName("127.0.0.1");
            socket = new Socket(byName,9999);
            outputStream = socket.getOutputStream();
            outputStream.write("你在干什么".getBytes(StandardCharsets.UTF_8));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(socket!=null){
                socket.close();
            }

        }



    }
}
