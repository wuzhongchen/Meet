package com.example.administrator.meet.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.administrator.meet.R;
import com.example.administrator.meet.view.fragment.FindingFragment;
import com.example.administrator.meet.view.fragment.MeetFragment;
import com.example.administrator.meet.view.fragment.RunningFragment;
import com.example.administrator.meet.view.my_achievements;


public class HomePageActivity extends AppCompatActivity {

    private Fragment currentFragment = new Fragment();
    private RunningFragment runningFragment = new RunningFragment();
    private FindingFragment findingFragment = new FindingFragment();
    private MeetFragment meetFragment = new MeetFragment();

    private BottomNavigationView.OnNavigationItemSelectedListener onBottomNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                    case R.id.navigation_running:
                    switchFragment(runningFragment).commit();
                    return true;
                case R.id.navigation_search:
                    switchFragment(findingFragment).commit();
                    return true;
                case R.id.navigation_Meet:
                    switchFragment(meetFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private FragmentTransaction  switchFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(!fragment.isAdded()) {
            if(currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.content,fragment,fragment.getClass().getName());
        } else {
            transaction.hide(currentFragment).show(fragment);
        }
        changeStatusColor();
        currentFragment = fragment;
        return transaction;
    }

    //隐藏所有的fragment
    private void hideAllFragment(FragmentTransaction transaction){
        if(runningFragment != null) {
            transaction.remove(runningFragment);
        }
        if(findingFragment != null) {
            transaction.remove(findingFragment);
        }
        if(meetFragment != null) {
            transaction.remove(meetFragment);
        }
    }

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;

    private void changeStatusColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        drawerLayout.openDrawer(Gravity.LEFT);
        navigationView = (NavigationView) findViewById(R.id.Drawer_navigation_view);
        navigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(onBottomNavigationItemSelectedListener);
        switchFragment(runningFragment).commit();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.activate_vip:
                    return false;

                case R.id.my_album:

                    return false;
                case R.id.my_wallet:

                    return false;
                //我的收藏(主要存放运动攻略及达人的分享)
                case R.id.my_collections:
                    return false;

                case R.id.my_achievements:
                    Intent intent = new Intent(HomePageActivity.this,my_achievements.class);
                    startActivity(intent);
                case R.id.settings:
                    return false;

            }
            return false;
        }
    };




}
