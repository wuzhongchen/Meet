package com.example.administrator.meet.adapter;

public class CardItem {
    private  String Start_What;

    private  int imageId;

    public CardItem(String Start_What,int imageId){
        this.Start_What=Start_What;
        this.imageId=imageId;
    }

    public String getStart_What() {
        return Start_What;
    }

    public int getImageId() {
        return imageId;
    }
}
