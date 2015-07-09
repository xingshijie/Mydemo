package com.xingshijie.mydemo.basic;


import android.app.Application;

public class BaseApplication extends Application {
    protected static BaseApplication mApplication;

    public BaseApplication() {
        super();
        mApplication = this;
    }

    public static BaseApplication getApp() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public void init(){
        //TODO 初始化所有需要初始化一次的东西，如可以在程序入口活动
    }


    /**
     * 获取应用的data/data/....File目录
     * 
     * @return File目录
     */
    public String getFilesDirPath() {
        return getFilesDir().getAbsolutePath();
    }

    /**
     * 获取应用的data/data/....Cache目录
     * 
     * @return Cache目录
     */
    public String getCacheDirPath() {
        return getCacheDir().getAbsolutePath();
    }

}
