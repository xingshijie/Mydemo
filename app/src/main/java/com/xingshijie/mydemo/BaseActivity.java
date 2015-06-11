package com.xingshijie.mydemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;


public class BaseActivity extends Activity implements View.OnClickListener{
    //用户事件->改变数据源->控件状态改变

    private String dataSource;//TODO 本界面上显示的数据源
    private String volatileDataSource;

    private Adapter adapter;


    //TODO 所有打开Activity的方式都应该使用此种，而不应该在其他类中直接使用startActivity
    //使用此种方式可以很好的控制Acitivity的启动，此方法与此类紧耦合
    /**
     * @param context context
     * @param mode  打开活动的模式
     * @param data1  需要传递的数据
     * @param data2
     */
    public static void startActivity(Context context,int mode,String data1,String data2){
        Intent intent=new Intent(context,BaseActivity.class);
        Bundle bundle=new Bundle();
        intent.putExtras(bundle);
        switch (mode){
            case 1:
                break;
        }
        context.sendBroadcast(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        initView();
        initEvent();
    }

    //TODO 如果需要重设所有数据需要先设置数据源，然后初始化所有控件
    //实际操作中可以暴力的直接初始化所有
    //TODO 初始化imageView（也许有类似的）会导致屏幕上出现图片的切换效果，而文字看不见切换
    private void initView(){
        //设置本界面上的所有View
        if(dataSource==null){
            return;
        }
        //TODO listView之类需要设置Adapter的东西，如果重新设置Adapter，可能会出现改变效果，例如recycelView
        //TODO 可以设置if(adapter==null) 为Adapter设置适配器，e
        //TODO 。。。
        initVolatileView();
    }

    //TODO 初始化所有易变的view
    //TODO 异变view类似，很多时候需要随着用户的点击而改变
    //异变view的数据源可能是dataSource的一部分
    private void initVolatileView(){
        if(volatileDataSource==null){
            return;
        }
    }

    private void initEvent(){
        //TODO 为此界面上的所有控件设置事件
        //TODO butterKnife有没有直接设置界面上的所有控件的点击事件为本类
    }

    @Override
    public void onClick(View v) {

    }
}
