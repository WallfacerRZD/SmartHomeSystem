package smarthome.appliance;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class Camera {
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public void on() {
        System.out.println("the Camera on");
        try (ServerSocket serverSocket = new ServerSocket(2333)) {
            while (true) {
                Socket socket = serverSocket.accept();
                executor.execute(new SendVideoThread(new OpenCVFrameGrabber(0), socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class SendVideoThread implements Runnable {
        private OpenCVFrameGrabber grabber;

        private Socket clientSocket;

        SendVideoThread(OpenCVFrameGrabber grabber, Socket socket) {
            this.grabber = grabber;
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try {
                Frame frame;
                grabber.start();
                while ((frame = grabber.grab()) != null && clientSocket.isConnected()) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(Java2DFrameUtils.toBufferedImage(frame), "jpg", outputStream);
                    byte[] imageData = outputStream.toByteArray();
                    System.out.println("one frame");
                    clientSocket.getOutputStream().write(imageData);
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
    }

    public static void main(String[] args) {
        Camera camera = new Camera();

        camera.on();

    }

}
