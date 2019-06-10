package com.chat.hechat;

public class Contact {

    private int id;
    private String nickname = null;
    private String comment = null;
    private String info = null;
    private int avatarID;

    public Contact(int id,String nickname,String comment,String info,int avatarID){
        this.id = id;
        this.nickname = nickname;
        this.comment = comment;
        this.info = info;
        this.avatarID = avatarID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getAvatarID(){
        return avatarID;
    }
}
