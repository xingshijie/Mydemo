package com.xingshijie.mydemo.basic.impl;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.yami.common.basic.ActivityResponsable;
import com.yami.common.basic.BaseApplication;
import com.yami.common.basic.BaseApplicationContext;
import com.yami.common.basic.FrameworkExceptionHandler;

public class BaseApplicationContextImpl implements BaseApplicationContext {

    /**
     */
    private BaseApplication mTodapplication;

    /**
     */
    private Activity        mActiveActivity;

    @Override
    public void attachContext(BaseApplication application) {
        mTodapplication = application;
        init();
    }

    private void init() {
        FrameworkExceptionHandler.getInstance().init(mTodapplication);
    }

    /**
     * 当前activity
     * 
     * @param activity
     */
    @Override
    public void updateActivity(Activity activity) {
        mActiveActivity = null;
        mActiveActivity = activity;
    }

    @Override
    public BaseApplication getApplicationContext() {
        return mTodapplication;
    }

    @Override
    public void clearState() {

    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub

    }

    @Override
    public void Toast(String msg, int period) {
        if (mActiveActivity instanceof ActivityResponsable) {
            ((ActivityResponsable) mActiveActivity).toast(msg, period);
        } else {
            throw new IllegalAccessError("current Activity must be ActivityInterface��");
        }
    }

    @Override
    public void Alert(String title, String msg, String positive, OnClickListener positiveListener,
                      String negative, OnClickListener negativeListener) {
        if (mActiveActivity instanceof ActivityResponsable) {
            ((ActivityResponsable) mActiveActivity).alert(title, msg, positive, positiveListener,
                negative, negativeListener);
        } else {
            throw new IllegalAccessError("current Activity must be ActivityInterface:"
                                         + mActiveActivity);
        }
    }

    @Override
    public void showProgressDialog(final String msg) {
        if (mActiveActivity instanceof ActivityResponsable) {
            ((ActivityResponsable) mActiveActivity).showProgressDialog(msg);
        } else {
            throw new IllegalAccessError("current Activity must be ActivityInterface");
        }
    }

    /**
     * 
     * @param msg
     */
    @Override
    public void showProgressDialog(final String msg, final boolean cancelable,
                                   final OnCancelListener cancelListener) {
        if (mActiveActivity instanceof ActivityResponsable) {
            ((ActivityResponsable) mActiveActivity).showProgressDialog(msg, cancelable,
                cancelListener);
        } else {
            throw new IllegalAccessError("current Activity must be ActivityInterface");
        }
    }


    @Override
    public void dismissProgressDialog() {
        if (mActiveActivity instanceof ActivityResponsable) {
            ((ActivityResponsable) mActiveActivity).dismissProgressDialog();
        } else {
            throw new IllegalAccessError("current Activity must be ActivityInterface");
        }
    }

    /** 检查网络是否已连接 */
    public boolean isNetworkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) getApplicationContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }
        return false;
    }
}
