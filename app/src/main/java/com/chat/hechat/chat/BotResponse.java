package com.chat.hechat.chat;

public class BotResponse {
    public String id;
    public String result;

    @Override
    public String toString() {
        return "BotResponse{" +
                "id='" + id + '\'' +
                ", result='" + result + '\'' +
                ", msg='" + msg + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    public String msg;
    public String response;
}
