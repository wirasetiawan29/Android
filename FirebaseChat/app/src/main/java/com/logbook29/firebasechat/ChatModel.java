package com.logbook29.firebasechat;

import android.text.format.DateFormat;

/**
 * Created by wira on 5/19/16.
 */
public class ChatModel {

    private String name;
    private String message;
    private long userId;
    private long timestamp;
    private String formattedTime;

    public ChatModel() {

        name = "";
        message = "";
        userId = 32;
        timestamp = 0;


    }

    public ChatModel(String message, String name, long uid,  long time, String formattedTime) {
        this.name = name;
        this.message = message;
        this.userId = uid;
        this.timestamp = time;
        this.formattedTime = formattedTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setTime(long time) {
        this.timestamp = time;

        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - time;

        if(timeDifference < oneDayInMillis){
            formattedTime = DateFormat.format("hh:mm a", time).toString();
        }else{
            formattedTime = DateFormat.format("dd MMM - hh:mm a", time).toString();
        }
    }

    public void setFormattedTime(String formattedTime) {
        this.formattedTime = formattedTime;
    }

    public String getFormattedTime(){
        long oneDayInMillis = 24 * 60 * 60 * 1000;
        long timeDifference = System.currentTimeMillis() - timestamp;

        if(timeDifference < oneDayInMillis){
            return DateFormat.format("hh:mm a", timestamp).toString();
        }else{
            return DateFormat.format("dd MMM - hh:mm a", timestamp).toString();
        }
    }
}
