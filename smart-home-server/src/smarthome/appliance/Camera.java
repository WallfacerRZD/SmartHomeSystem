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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Camera {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private ServerSocket serverSocket = null;

    public void on() {
        if (serverSocket == null) {
            new Thread(() -> {
                try {
                    System.out.println("the Camera on");
                    serverSocket = new ServerSocket(2333);
                    while (true) {
                        Socket socket = serverSocket.accept();
                        executor.execute(new SendVideoThread(new OpenCVFrameGrabber(0), socket));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (serverSocket != null) {
                            serverSocket.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }

    }

    private class SendVideoThread implements Runnable {
        private OpenCVFrameGrabber grabber;

        private Java2DFrameConverter converter = new Java2DFrameConverter();

        private Socket clientSocket;

        SendVideoThread(OpenCVFrameGrabber grabber, Socket socket) {
            this.grabber = grabber;
            this.clientSocket = socket;
        }

        @Override
        public void run() {
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
    }

    public void off() {
        // 客户端关闭socket就可以停止抓取图像
    }

    public static void main(String[] args) {
        Camera camera = new Camera();
        camera.on();

    }

}
