package com.support.johnpig.healthmanager;

public class MessageEvent {
    private UserData userData;

    public MessageEvent(UserData userData) {
        this.userData = userData;
    }

    public UserData getUserData() {
        return userData;
    }
}
