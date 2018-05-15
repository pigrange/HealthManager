package com.support.johnpig.healthmanager;

import android.util.Log;

import java.net.HttpURLConnection;
import java.net.URL;

import static java.util.regex.Pattern.matches;

public class URLUtils {
    public static String MY_HTTP_URL = null;

    public static boolean IsUrl(String str) {
        String regex = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
        return matches(regex, str);
    }

    public static boolean IsConnect(String MY_HTTP_URL) {
        try {
            URL url = new URL(MY_HTTP_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            int response = connection.getResponseCode();
            if (response != 404) {
                return true;
            }
        } catch (Exception e) {
            Log.e("UrlUtils", "Url无效");
        } finally {
            return false;
        }
    }
}
