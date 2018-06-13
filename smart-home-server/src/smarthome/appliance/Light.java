package smarthome.appliance;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Light {
    private static final String LIGHT_ON_URL = "http://192.168.23.2/on";

    private static final String LIGHT_OFF_URL = "http://192.168.23.2/off";

    public void on() {
        request(LIGHT_ON_URL);
    }

    public void off() {
        request(LIGHT_OFF_URL);
    }

    public static void main(String[] args) throws MalformedURLException {
        new Light().on();
        new  Light().off();
    }

    private void request(final String url) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            inputStream = connection.getInputStream();
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bf.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            // NodeMUC的response不符合标准, 直接忽略(逃..
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
