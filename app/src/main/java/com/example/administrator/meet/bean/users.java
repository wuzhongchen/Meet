package com.example.administrator.meet.bean;

public class users {
    private String token;
    private String gender;
    private String birthday;
    private String id;
    private String username;
    private String nickname;
    private String email;
    private String phoneNumber;
    private String avatar;
    private String desc;
    private String love;
    private String hate;




    public users(
            String token,String gender,String birthday,String id,
            String username,String nickname,String email,String phoneNumber,String avatar,
            String desc,String love,String hate){
        this.token=token;
        this.gender=gender;
        this.birthday=birthday;
        this.id=id;
        this.username=username;
        this.nickname=nickname;
        this.email=email;
        this.phoneNumber=phoneNumber;
        this.avatar=avatar;
        this.desc=desc;
        this.love=love;
        this.hate=hate;
    }


    public String gerToken(){
        return token;
    }
    public String getGender(){
        return gender;
    }
    public String getBirthday() { return  birthday;}
    public String get_Id() { return  id;}
    public String getUsername(){
        return username;
    }
    public String getNickname() { return  nickname;}
    public String getEmail(){
        return email;
    }
    public String getPhoneNumber(){
        return phoneNumber;
    }
    public String getAvatar() { return  avatar;}
    public String getDesc() { return  desc;}
    public String getLove() { return  love;}
    public String getHate() { return  hate;}
}
