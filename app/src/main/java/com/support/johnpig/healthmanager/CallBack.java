package com.support.johnpig.healthmanager;

public interface CallBack {
    void setSensorStatus(boolean status);
    void onDataReceived();
    User getUser();
}
