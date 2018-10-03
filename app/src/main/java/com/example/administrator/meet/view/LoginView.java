package com.example.administrator.meet.view;

public interface LoginView {

    void showProgress();
    void hindProgress();
    void toastError();
    void turnToHomepage();
    void getToken(String msg);
}
