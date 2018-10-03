package com.example.administrator.meet.bean;

/**
 * Created by wuzhongcheng on 2018/2/9.
 */

public class editUsers {
    private String token;
    private String id;
    private String nickname;
    private String gender;
    private String birthday;





    public editUsers(String token,String id,String nickname,String gender,String birthday){
        this.token=token;
        this.id=id;
        this.nickname=nickname;
        this.gender=gender;
        this.birthday=birthday;
    }

    public String getToken(){
        return token;
    }
    public String get_Id() { return  id;}
    public String getGender(){ return  gender;}
    public String getNickname() { return  nickname;}
    public String getBirthday() { return  birthday;}
}
