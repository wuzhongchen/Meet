package com.example.administrator.meet.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.example.administrator.meet.utils.JboxImpl;

public class CollisionView extends FrameLayout{
    private JboxImpl jboxImpl;

    public CollisionView(@NonNull Context context) {
        this(context,null);
    }

    public CollisionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CollisionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        jboxImpl = new JboxImpl(getResources().getDisplayMetrics().density);
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        jboxImpl.setWorldSize(w,h);
    }

    @Override
    protected void onLayout(boolean changed,int left,int top,int right,int bottom){
        super.onLayout(changed,left,top,right,bottom);
        jboxImpl.createWorld();
        int childCount = getChildCount();

        if(childCount!=0){
            Log.e("onLayout","childCount!=0");
        }
        //根据addView创建刚体
        for(int i=0;i<childCount;i++){
            View view =getChildAt(i);
            if(!jboxImpl.isBodyView(view)){
                jboxImpl.CreateBody(view);
            }
        }

    }

    @Override
    protected void onDraw(Canvas canvas){

        Log.e("onDraw","startWorld");
        jboxImpl.startWorld();
        int childCount = getChildCount();
        for(int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if(jboxImpl.isBodyView(view)){
                view.setX(jboxImpl.getViewX(view));
                view.setY(jboxImpl.getViewY(view));
                //获取旋转角速度
                view.setRotation(jboxImpl.getViewRotation(view));
            }
        }
        invalidate();
    }




    public void onSensorChanged(float x,float y){
        int childCount = getChildCount();
        for(int i=0; i<childCount ; i++){
            View view = getChildAt(i);
            if(jboxImpl.isBodyView(view)){
                jboxImpl.applyLinearImpulse(x,y,view);
            }
        }
    }
}
