package com.example.user.smarthomeui.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author WallfacerRZD
 * @date 2018/6/13 21:11
 */
public class HttpUtil {
    public static String httpGet(final String url) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader br = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (br != null) {
                br.close();
            }
        }
        return response.toString();
    }

    public static String httpPost(final String url, final String params) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader br = null;
        StringBuilder response = new StringBuilder();
        try {
            connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(params);

            br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (br != null) {
                br.close();
            }
        }
        return response.toString();
    }
}
