package com.example.administrator.meet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.meet.R;
import com.example.administrator.meet.bean.Friend;

import java.util.List;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

    private List<Friend> mFriendList;



    static  class ViewHolder extends RecyclerView.ViewHolder{
        TextView friendName;
        ImageView friendImage;
        View avatarView;

        public ViewHolder(View view) {

            super(view);

            avatarView = (view);
            friendName = (TextView)view.findViewById(R.id.friend_name);
            friendImage = (ImageView)view.findViewById(R.id.friend_image);
        }
    }

    public FriendAdapter(List<Friend> friendList) {
        mFriendList = friendList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_list_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.avatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Friend friend = mFriendList.get(position);
        holder.friendName.setText(friend.getName());
        holder.friendImage.setImageResource(friend.getImageId());

    }

    @Override
    public int getItemCount() {
        return mFriendList.size();
    }

    //全局变量




}

