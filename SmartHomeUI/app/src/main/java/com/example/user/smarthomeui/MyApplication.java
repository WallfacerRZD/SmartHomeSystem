package com.example.user.smarthomeui;

import android.app.Application;

/**
 * @author WallfacerRZD
 * @date 2018/6/13 22:47
 */
public class MyApplication extends Application {
    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    private String ipAddr = null;
}
