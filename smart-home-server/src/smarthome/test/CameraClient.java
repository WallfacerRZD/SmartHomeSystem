package smarthome.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author WallfacerRZD
 * @date 2018/6/12 17:31
 */
public class CameraClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("localhost", 2333));
        InputStream inputStream = socket.getInputStream();
        int readCount = 0;
        byte[] bytes =  new byte[1024];
        while ((readCount = inputStream.read(bytes))!= -1) {
            System.out.println(readCount);
        }
    }
}
