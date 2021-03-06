package com.support.johnpig.healthmanager;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyService extends Service {

    private ServerSocket ss;
    private volatile boolean flag = true;
    ExecutorService mThreadPool = Executors.newCachedThreadPool();
    private serviceBinder mBinder = new serviceBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class serviceBinder extends Binder {

        public void openServer(final CallBack mainActivity) {
            mThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ss = new ServerSocket(60000);
                        while (flag) {
                            try {
                                final Socket socket = ss.accept();
                                mainActivity.setSensorStatus(true);
                                mThreadPool.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        handleSocket(socket, mainActivity);
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        private void handleSocket(Socket socket, CallBack mainActivity) {
            Scanner scanner = null;
            try {
                InputStream input = socket.getInputStream();
                scanner = new Scanner(input);
                while (scanner.hasNextLine() && flag) {
                    String line = scanner.nextLine();
                    mainActivity.onDataReceived();
                    EventBus.getDefault()
                            .post(new MessageEvent(createUser(parseData(line), mainActivity)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                IOUtils.safeClose(scanner);
                IOUtils.safeClose(socket);
                mainActivity.setSensorStatus(false);
            }
        }

        public void closeServer() {
            flag = false;
            IOUtils.safeClose(ss);
        }

        private Map<String, String> parseData(String str) {
            String[] strs = str.split(",");
            Map<String, String> map = new HashMap<>();
            for (String s : strs) {
                String[] ms = s.split(":");
                map.put(ms[0], ms[1]);
            }
            return map;
        }

        private UserData createUser(Map<String, String> map, CallBack callBack) {
            User user = callBack.getUser();
            UserData userData = new UserData();

            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            String date = df.format(new Date());

            userData.setAccount(user.account);
            userData.setSex(user.sex);
            userData.setCreatedTime(date);
            userData.setHeart_rate(Double.valueOf(map.get("HeartRate")));
            userData.setTemperature(Double.valueOf(map.get("Temperature")));
            userData.setWeight(Double.valueOf(map.get("Weight")));
            userData.setHigh_pressure(Double.valueOf(map.get("HighPressure")));
            userData.setLow_pressure(Double.valueOf(map.get("LowPressure")));
            if (userData.save()) {
                Log.e("myService", "uerData has saved");
            } else {
                Log.e("myService", "uerData save failed");
            }
            return userData;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
