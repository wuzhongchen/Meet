package com.example.administrator.meet.utils;

import android.util.Log;
import android.view.View;


import com.example.administrator.meet.R;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.collision.shapes.Shape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import java.util.Random;

public class JboxImpl {
    //jbox2d模拟世界
    private World mWorld;

    //公式运算频率
    private float dt = 1f/30f;

    private int mVelocityIterations = 5; //每一帧迭代次数
    private int mPositionIterations = 20;//迭代频率

    private float mWidth,mHeight;

    private float mDesity = 0.5f;

    //和界面的映射比例
    private float mRatio = 50;

    private final Random mRandom = new Random();

    //所有刚体的密度
    public JboxImpl(float desity){
        this.mDesity=desity;
    }

    //传宽度和高度
    public void setWorldSize(int Width,int Height) {
        //映射
        mWidth = Width;
        mHeight = Height;
    }

    public void startWorld() {
        if(mWorld != null) {
//            Log.e("JboxImpl","启动模拟世界运动");
            mWorld.step(dt,mVelocityIterations,mPositionIterations);
        }
    }

    public void createWorld() {
        if(mWorld == null) {
            mWorld = new World(new Vec2(0,10.0f));
            update_Vertical_Bounds();
            update_Horizontal_Bounds();
            Log.e("JboxImpl","开始创建World");
        } else {
            Log.e("JboxImpl","World已经存在");
        }
    }


    //创建上下边界
    private void update_Vertical_Bounds() {
        BodyDef bodyDef = new BodyDef(); //定义的形状
        //BodyType.DYNAMIC是运动的，不受限制
        //STATIC 刚体的边界不能运动
        bodyDef.type = BodyType.STATIC;

        PolygonShape box = new PolygonShape();//形状确定为矩形
        float boxWidth = switchPositionToBody(mWidth);
        float boxHeight = 0.0000000000000000000000000000001f;
        box.setAsBox(boxWidth,boxHeight);


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = mDesity;
        fixtureDef.friction = 0.8f; //摩擦系数
        fixtureDef.restitution = 0.55f;//补偿系统

        //底部的模拟世界
        bodyDef.position.set(0,-0.0000000000000000000000000000001f);
        Body bottomBody = mWorld.createBody(bodyDef);
        bottomBody.createFixture(fixtureDef);
        Log.e("updateVerticalBounds","创建底部模拟世界");

        bodyDef.position.set(0,switchPositionToBody(mHeight + 0.0000000000000000000000000000001f)); //顶部弹力墙
        Body topBody = mWorld.createBody(bodyDef);
        topBody.createFixture(fixtureDef);
        Log.e("updateVerticalBounds","创建顶部模拟世界");
    }


    //创建左右边界
    private void update_Horizontal_Bounds() {

        BodyDef bodyDef = new BodyDef();

        //1.指定类型
        bodyDef.type = BodyType.STATIC;

        //2.形状设置为矩形
        PolygonShape box = new PolygonShape();
        float boxWidth = 0.0000000000000000000000000000001f;
        float boxHeight = switchPositionToBody(mHeight);
        box.setAsBox(boxWidth,boxHeight);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = box;
        fixtureDef.density = mDesity;
        fixtureDef.friction = 0.8f; //摩擦系数
        fixtureDef.restitution = 0.55f;//补偿系统

        bodyDef.position.set(-0.0000000000000000000000000000001f,0); //左边界弹力墙
        Body leftBody = mWorld.createBody(bodyDef);
        leftBody.createFixture(fixtureDef);
        Log.e("updateHorizontalBounds","创建左边界");

        bodyDef.position.set(switchPositionToBody(mWidth + 0.0000000000000000000000000000001f),0); //右边界弹力墙
        Body rightBody = mWorld.createBody(bodyDef);
        rightBody.createFixture(fixtureDef);
        Log.e("updateHorizontalBounds","创建右边界");
    }


    //创建绑定在ImageView的刚体
    public void CreateBody(View view){

        Log.e("CreateBody","开始创建刚体");
        BodyDef bodyDef = new BodyDef();
        bodyDef.type  = BodyType.DYNAMIC;

        Shape shape = null;

        Boolean isCirCle = (Boolean) view.getTag(R.id.dn_view_circle_tag);

        if(isCirCle!=null&&isCirCle) {
            CircleShape box = new CircleShape();
            box.setRadius(switchPositionToBody((view.getWidth() / 2)));
            shape=box;
            Log.e("CreateBody","刚体形状设置为圆形");
        } else {
            return;
        }

        //设置圆心坐标
        bodyDef.position.set(   switchPositionToBody(view.getX()+   (view.getWidth()/2)     ),
                switchPositionToBody(view.getY()+   (view.getHeight()/2)    )
        );

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = mDesity;
        fixtureDef.friction = 0.8f;//摩擦系数
        fixtureDef.restitution = 0.0000000000001f; //补偿系统

        Body circleBody = mWorld.createBody(bodyDef);
        circleBody.createFixture(fixtureDef);

        view.setTag(R.id.dn_view_body_tag,circleBody);

        //刚体之间碰撞,运动的线性加速度
        circleBody.setLinearVelocity(new Vec2(mRandom.nextFloat(),mRandom.nextFloat()));
    }





    //view坐标映射为模拟世界的坐标
    private float switchPositionToBody(float viewPosition){
        return viewPosition/mRatio;
    }

    //模拟世界坐标映射为view坐标
    private float switchPositionToView(float bodyPosition){
        return bodyPosition * mRatio;
    }

    public float getViewX(View view){
        Body body = (Body) view.getTag(R.id.dn_view_body_tag);
        if (body!=null){
            return switchPositionToView(body.getPosition().x)-(view.getWidth()/2);
        }
        return 0;
    }

    public float getViewY(View view){
        Body body = (Body) view.getTag(R.id.dn_view_body_tag);
        if (body!=null){
            //将圆心坐标转化为ImageView的Y轴坐标
            return switchPositionToView(body.getPosition().y)-(view.getHeight()/2);
        }
        return 0;
    }

    public float getViewRotation(View view){
        Body body = (Body)view.getTag(R.id.dn_view_body_tag);
        if(body!=null) {
            float angle = body.getAngle();
            return (angle/3.14f*180f) %360;
        }
        return 0;
    }


    public boolean isBodyView(View view){

        if(view!=null){
            Body body = (Body) view.getTag(R.id.dn_view_body_tag);
            return body!=null;
        }
        return  false;
    }

    public void applyLinearImpulse(float x,float y,View view){

        Body body = (Body)view.getTag(R.id.dn_view_body_tag);

        Vec2 impluse = new Vec2(x, y);

        body.applyLinearImpulse(impluse,body.getPosition(),true);
    }
}
