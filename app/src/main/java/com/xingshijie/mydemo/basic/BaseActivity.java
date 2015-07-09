package com.xingshijie.mydemo.basic;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.yami.app.R;
import com.yami.app.YamiConsts;
import com.yami.common.LogCatLog;
import com.yami.commonui.widget.CommonTitleBar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

/**
 * Activity封装类
 * 
 * 封装生命周期监控，对话框等
 * 
 */
public abstract class BaseActivity extends Activity implements ActivityResponsable {
    final static String              TAG = BaseActivity.class.getSimpleName();

    /**
     * 上下文
     */
    protected BaseApplicationContext mTodApplicationContext;
    /**
     * Activity辅助类
     */
    private ActivityHelper           mActivityHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityHelper = new ActivityHelper(this);
        mTodApplicationContext = BaseApplication.getApp().getBaseApplicationContext();
        registeReceiver();
    }

    @Optional
    @InjectView(R.id.title_layout)
    public CommonTitleBar titleBar;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.inject(this);
        if (titleBar != null)
            titleBar.setLeftBtnOnclickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
    }
    public BaseApplicationContext getTodApplicationContext() {
        return mTodApplicationContext;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        mTodApplicationContext.updateActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(logoutReceiver);
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
        mActivityHelper.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        ShareUtils.callbakForWeiboSSO(requestCode, resultCode, data);
    }

    /**
     * 弹对话框
     * 
     * @param title
     *            标题
     * @param msg
     *            消息
     * @param positive
     *            确定
     * @param positiveListener
     *            确定回调
     * @param negative
     *            否定
     * @param negativeListener
     *            否定回调
     */
    @Override
    public void alert(String title, String msg, String positive,
                      DialogInterface.OnClickListener positiveListener, String negative,
                      DialogInterface.OnClickListener negativeListener) {
        mActivityHelper.alert(title, msg, positive, positiveListener, negative, negativeListener);
    }

    /**
     * 弹对话框
     * 
     * @param title
     *            标题
     * @param msg
     *            消息
     * @param positive
     *            确定
     * @param positiveListener
     *            确定回调
     * @param negative
     *            否定
     * @param negativeListener
     *            否定回调
     * @param isCanceledOnTouchOutside
     *            外部点是否可以取消对话框
     */
    @Override
    public void alert(String title, String msg, String positive,
                      DialogInterface.OnClickListener positiveListener, String negative,
                      DialogInterface.OnClickListener negativeListener,
                      Boolean isCanceledOnTouchOutside) {
        mActivityHelper.alert(title, msg, positive, positiveListener, negative, negativeListener,
            isCanceledOnTouchOutside);
    }

    /**
     * TOAST
     * 
     * @param msg
     *            消息
     * @param period
     *            时长
     */
    @Override
    public void toast(String msg, int period) {
        mActivityHelper.toast(msg, period);
    }

    /**
     * 显示进度对话框
     * 
     * @param msg
     *            消息
     */
    @Override
    public void showProgressDialog(String msg) {
        mActivityHelper.showProgressDialog(msg);
    }

    /**
     * 显示可取消的进度对话框
     * 
     * @param msg
     *            消息
     */
    public void showProgressDialog(final String msg, final boolean cancelable,
                                   final OnCancelListener cancelListener) {
        mActivityHelper.showProgressDialog(msg, cancelable, cancelListener);
    }

    @Override
    public void dismissProgressDialog() {
        mActivityHelper.dismissProgressDialog();
    }

    @SuppressWarnings("unchecked")
    protected <T> T findServiceByInterface(String interfaceName) {
        return (T) BaseApplication.getApp().findServiceByInterface(interfaceName);
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public String getViewValue(int resId) {
        return getViewValue(null, resId);
    }

    public String getViewValue(View parent, int resId) {
        View view;
        if (parent == null) {
            view = findViewById(resId);
        } else {
            view = parent.findViewById(resId);
        }

        if (view instanceof EditText) {
            return ((EditText) view).getText().toString();
        } else if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        } else {
            LogCatLog.e("BaseActivity", "获取值失败");
            return "";
        }
    }

    public void setViewValue(int resId, String value) {
        setViewValue(null, resId, value);
    }

    public void setViewValue(View parent, int resId, String value) {
        View view;
        if (parent == null) {
            view = findViewById(resId);
        } else {
            view = parent.findViewById(resId);
        }

        if (view instanceof EditText) {
            ((EditText) view).setText(value);
        } else if (view instanceof TextView) {
            ((TextView) view).setText(value);
        } else {
            LogCatLog.e("BaseActivity", "设置值失败：" + resId + "  value:" + value);
        }
    }

    protected boolean canGoon() {
        return !isFinishing();
    }
    private void registeReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(YamiConsts.BRAODCAST_LOGOUT);
        registerReceiver(logoutReceiver, filter);
    }

    private BroadcastReceiver logoutReceiver = new BroadcastReceiver() {
                                                    @Override
                                                    public void onReceive(Context context,
                                                                          Intent intent) {
                                                        if (intent.getAction().equals(
                                                            YamiConsts.BRAODCAST_LOGOUT)) {
                                                         finish();
                                                        }
                                                    }
                                                };

}