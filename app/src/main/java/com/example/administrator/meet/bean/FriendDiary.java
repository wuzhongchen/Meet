package com.example.administrator.meet.bean;

public class FriendDiary {
    private  String name;

    private  int avatarId;

    private String qianming;

    private int show_Image;

    public FriendDiary(int avatarId, String name, String qianming, int imageId) {
        this.avatarId = avatarId;
        this.name = name;
        this.qianming = qianming;
        this.show_Image = imageId;

    }

    public String getName() {
        return name;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public String getQianming() {
        return qianming;
    }

    public int getShow_Image() {
        return show_Image;
    }
}
