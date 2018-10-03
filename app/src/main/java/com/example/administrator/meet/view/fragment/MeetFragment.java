package com.example.administrator.meet.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.meet.R;
import com.example.administrator.meet.adapter.Diary_Adapter;
import com.example.administrator.meet.adapter.FriendAdapter;
import com.example.administrator.meet.bean.Friend;
import com.example.administrator.meet.bean.FriendDiary;

import java.util.ArrayList;
import java.util.List;

public class MeetFragment extends Fragment {

    private List<Friend> friendList = new ArrayList<>();
    private List<FriendDiary> diary_list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.meet_fragment,container,false);

        initFriends();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        FriendAdapter adapter =new FriendAdapter(friendList);
        recyclerView.setAdapter(adapter);


        RecyclerView recyclerView2 = (RecyclerView) view.findViewById(R.id.diary_recycler_view);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(layoutManager2);
        Diary_Adapter diary_adapter =new Diary_Adapter(diary_list);
        recyclerView2.setAdapter(diary_adapter);

        return view;
    }

    private void initFriends() {

        Friend friend = new Friend("Tom",R.drawable.tom);
        friendList.add(friend);
        Friend friend2 = new Friend("Helen",R.drawable.person13);
        friendList.add(friend2);
        Friend friend3 = new Friend("Alina",R.drawable.person14);
        friendList.add(friend3);
        Friend friend4 = new Friend("Bryan",R.drawable.person15);
        friendList.add(friend4);
        Friend friend5 = new Friend("Kane",R.drawable.kane);
        friendList.add(friend5);
        Friend friend6 = new Friend("Daniel",R.drawable.person1);
        friendList.add(friend6);
        Friend friend7 = new Friend("Cindy",R.drawable.cindy);
        friendList.add(friend7);
        Friend friend8 = new Friend("Sun",R.drawable.sun);
        friendList.add(friend8);
        Friend friend9 = new Friend("Yang",R.drawable.yang);
        friendList.add(friend9);
        Friend friend10 = new Friend("Wang",R.drawable.person2);
        friendList.add(friend10);
        Friend friend11 = new Friend("Tony",R.drawable.tony);
        friendList.add(friend11);

        FriendDiary friendDiary1 = new FriendDiary(R.drawable.geo,"George","Keep a food and fitness journal for a month",R.drawable.strong2);
        diary_list.add(friendDiary1);

        FriendDiary friendDiary2 = new FriendDiary(R.drawable.cindy,"Cindy","Yoga can shape your body and make you dress yourself more beautifully.",R.drawable.yoga);
        diary_list.add(friendDiary2);

        FriendDiary friendDiary3 = new FriendDiary(R.drawable.tom,"Tom","Peter parker in infinity war.",R.drawable.spide);
        diary_list.add(friendDiary3);

        FriendDiary friendDiary4 = new FriendDiary(R.drawable.person14,"Alina","Today I finally finished the first 5 kilometres in my life.",R.drawable.running_woman);
        diary_list.add(friendDiary4);

        FriendDiary friendDiary5 = new FriendDiary(R.drawable.person13,"Helen","Yeah ! Return to 45 kilograms.",R.drawable.new_woman);
        diary_list.add(friendDiary5);

        FriendDiary friendDiary6 = new FriendDiary(R.drawable.person1,"Daniel","This is my girlfriend.",R.drawable.weight_bearing_push_up);
        diary_list.add(friendDiary6);

        FriendDiary friendDiary7 = new FriendDiary(R.drawable.yang,"Yang","More and more ring roads have appeared and people's living conditions have improved a lot.",R.drawable.mobike);
        diary_list.add(friendDiary7);
    }

}
