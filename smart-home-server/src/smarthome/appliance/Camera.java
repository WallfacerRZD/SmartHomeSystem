package smarthome.appliance;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Camera {
    private volatile ServerSocket serverSocket = null;

    public void on() {
        if (serverSocket == null) {
            new Thread(() -> {
                try {
                    System.out.println("the Camera on");
                    serverSocket = new ServerSocket(2333);
                    Socket clientSocket = serverSocket.accept();
                    sendVideo(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (serverSocket != null) {
                            serverSocket.close();
                            serverSocket = null;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }

    private void sendVideo(Socket clientSocket) {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        Java2DFrameConverter converter = new Java2DFrameConverter();
        try {
            Frame frame;
            grabber.setImageHeight(100);
            grabber.setImageWidth(50);
            grabber.start();

            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            while ((frame = grabber.grab()) != null && clientSocket.isConnected()) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(converter.convert(frame), "jpg", outputStream);
                byte[] imageData = outputStream.toByteArray();
                outputStream.close();

                System.out.println("one frame");
                dataOutputStream.writeInt(imageData.length);
                System.out.println(imageData.length);
                dataOutputStream.write(imageData);
                dataOutputStream.flush();
            }
        } catch (IOException e) {
            // 忽略
        } finally {
            try {
                clientSocket.close();
                grabber.stop();
                System.out.println("ends");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void off() {
        // 客户端关闭socket就可以停止抓取图像
    }

    public static void main(String[] args) {
        Camera camera = new Camera();
        camera.on();

    }

}
