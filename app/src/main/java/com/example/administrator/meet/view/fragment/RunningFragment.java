package com.example.administrator.meet.view.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;


import com.example.administrator.meet.R;
import com.example.administrator.meet.adapter.CardItem;
import com.example.administrator.meet.adapter.CardPagerAdapter;
import com.example.administrator.meet.utils.ShadowTransformer;


public class RunningFragment extends Fragment {


    private ViewPager mViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.running_fragment,container,false);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);

        mCardAdapter = new CardPagerAdapter();
        mCardAdapter.addCardItem(new CardItem("Start Running",R.drawable.runner));
        mCardAdapter.addCardItem(new CardItem("Start Riding",R.drawable.ride));
        mCardAdapter.addCardItem(new CardItem("Yoga",R.drawable.bizhiyoga));
        mCardAdapter.addCardItem(new CardItem("Fitness",R.drawable.jianshen));

        mCardShadowTransformer = new ShadowTransformer(mViewPager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);
        mViewPager.setAdapter(mCardAdapter);
        mViewPager.setPageTransformer(false, mCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        return  view;
    }



}
