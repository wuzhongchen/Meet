package com.example.administrator.meet.model;

import java.util.Map;

public interface LoginModel {

     void loginVerifyInfo(Map<String,String> map, LoginModelCallBack loginModelCallBack);
}
