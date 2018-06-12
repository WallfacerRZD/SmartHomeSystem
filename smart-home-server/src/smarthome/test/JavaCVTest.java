package smarthome.test;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameGrabber;

/**
 * @author WallfacerRZD
 * @date 2018/6/11 23:16
 */
public class JavaCVTest {
    public static void main(String[] args) throws Exception {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(0);
        grabber.start();
        CanvasFrame canvas = new CanvasFrame("摄像头");
        canvas.setAlwaysOnTop(true);

    }
}
