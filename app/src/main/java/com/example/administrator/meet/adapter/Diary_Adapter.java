package com.example.administrator.meet.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.meet.R;
import com.example.administrator.meet.bean.FriendDiary;

import java.util.List;

public class Diary_Adapter extends RecyclerView.Adapter<Diary_Adapter.ViewHolder>{
    private List<FriendDiary> mFriendDiaryList;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_item,parent,false);
        final Diary_Adapter.ViewHolder holder = new Diary_Adapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FriendDiary friendDiary = mFriendDiaryList.get(position);
        holder.avatar.setImageResource(friendDiary.getAvatarId());
        holder.friendName.setText(friendDiary.getName());
        holder.qianming.setText(friendDiary.getQianming());
        holder.diaryImage.setImageResource(friendDiary.getShow_Image());
    }

    @Override
    public int getItemCount() {
        return mFriendDiaryList.size();
    }


    static  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView avatar;
        TextView friendName;
        TextView qianming;
        ImageView diaryImage;


        public ViewHolder(View view) {

            super(view);

            avatar = (ImageView)view.findViewById(R.id.avatar);
            friendName = (TextView)view.findViewById(R.id.name);
            qianming = (TextView)view.findViewById(R.id.qianming);
            diaryImage = (ImageView)view.findViewById(R.id.show_Image);
        }
    }

    public Diary_Adapter(List<FriendDiary> friendDiaryList) {
        mFriendDiaryList = friendDiaryList;
    }



    //全局变量
}
