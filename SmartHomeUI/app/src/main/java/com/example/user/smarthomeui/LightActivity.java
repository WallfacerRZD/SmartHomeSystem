package com.example.user.smarthomeui;

import android.net.http.HttpResponseCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LightActivity extends AppCompatActivity {

   // private  String baseUrl = "http://121.48.193.193:8080/SmartHomesevlet/Connect";
    private Switch mswitch = null;
 //   private HttpResponse response = null;
 //   private  HttpEntity httpEntity = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_layout);
        mswitch = (Switch) findViewById(R.id.light_switch);
        mswitch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(mswitch.isChecked()){
                    boolean flag = true;
                    sendRequest(flag);


                   // String url = baseUrl + "?" + "appliance=" + "light" + "&command" + "on";
                  //  String light = "light";
                  //  String on = "on";
                 /*   NameValuePair pair1 = new BasicNameValuePair("appliance", light);
                    NameValuePair pair2 = new BasicNameValuePair("command", on);

                    List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                    pairList.add(pair1);
                    pairList.add(pair2);
                    try
                    {
                        HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList);
                        // URL使用基本URL即可，其中不需要加参数
                        HttpPost httpPost = new HttpPost(baseUrl);
                        // 将请求体内容加入请求中
                        httpPost.setEntity(requestHttpEntity);
                        // 需要客户端对象来发送请求
                        HttpClient httpClient = new DefaultHttpClient();
                        // 发送请求
                        InputStream inputStream = null;

                        try {
                            response = httpClient.execute(httpPost);
                            Toast.makeText(LightActivity.this, "发送指令......", Toast.LENGTH_SHORT).show();
                            httpEntity = response.getEntity();
                            inputStream = requestHttpEntity.getContent();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                            String result = "";
                            String line = "";
                            while((line = reader.readLine()) != null){
                                result = result + line;
                           }
                            //Toast.makeText(LightActivity.this, "发送指令......", Toast.LENGTH_SHORT).show();
                            // 显示响应
                            System.out.println(result);
                            showResponseResult(response);
                        }catch(Exception e){
                            Toast.makeText(LightActivity.this, "异常......", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }finally {
                            try{
                                inputStream.close();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }*/
                }
                else{

                    boolean flag = false;
                    sendRequest(flag);

                    // String url = baseUrl + "?" + "appliance=" + "light" + "&command" + "on";
                //    String light = "light";
                 //   String off = "off";
                 /*   NameValuePair pair1 = new BasicNameValuePair("appliance", light);
                    NameValuePair pair2 = new BasicNameValuePair("command", off);

                    List<NameValuePair> pairList = new ArrayList<NameValuePair>();
                    pairList.add(pair1);
                    pairList.add(pair2);
                    try
                    {
                        HttpEntity requestHttpEntity = new UrlEncodedFormEntity(pairList);
                        // URL使用基本URL即可，其中不需要加参数
                        HttpPost httpPost = new HttpPost(baseUrl);
                        // 将请求体内容加入请求中
                        httpPost.setEntity(requestHttpEntity);
                        // 需要客户端对象来发送请求
                        HttpClient httpClient = new DefaultHttpClient();
                        // 发送请求
                        HttpResponse response = httpClient.execute(httpPost);
                        // 显示响应
                        showResponseResult(response);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }*/
                }
            }
        });
    }

    private void sendRequest(final boolean flag) {
        // 开启线程来发起网络请求
        final boolean f = flag;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                 //   URL url = new URL("https://hao.360.cn/");
                //    URL url = new URL("http://121.48.193.193:8080/SmartHomesevlet/Connect");
                  URL url = new URL("http://10.132.51.9:8080/SmartHomesevlet/Connect");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000); //连接超时
                    connection.setReadTimeout(8000);  //读取超时
                    Toast.makeText(LightActivity.this, "TSET1......", Toast.LENGTH_SHORT).show();
                 //   connection.connect();

                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());

                    InputStream in = connection.getInputStream();
                    if(f){
                       out.writeBytes("appliance=light&command=on");
                       Toast.makeText(LightActivity.this, "TSET2......", Toast.LENGTH_SHORT).show();
                   }else{
                       out.writeBytes("appliance=light&command=off");
                       Toast.makeText(LightActivity.this, "TSET3......", Toast.LENGTH_SHORT).show();
                   }

                    // 下面对获取到的输入流进行读取
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
                Looper.loop();
            }
        }).start();
    }

/*    private void showResponse(HttpResponse response)
    {
        if (null == response)
        {
            return;
        }

        HttpEntity httpEntity = response.getEntity();

        try
        {
            InputStream inputStream = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inputStream));
            String result = "";
            String line = "";
            while (null != (line = reader.readLine()))
            {
                result += line;

            }

            //System.out.println(result);
            Toast.makeText(LightActivity.this, "发送指令......" + result, Toast.LENGTH_SHORT).show();
          //  mResult.setText("Response Content from server: " + result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }*/

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                Toast.makeText(LightActivity.this, "完成......", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
