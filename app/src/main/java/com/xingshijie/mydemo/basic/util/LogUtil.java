package com.xingshijie.mydemo.basic.util;

import android.util.Log;

/**
 * Created by Word Xing  on 2015/7/6 0006.
 */
public class LogUtil {

    public static boolean isLog=true;

    public static void e(String string){
        e("",string);
    }
    public static void e(String tag,String string){
        if(isLog) Log.e(tag,string);
    }

}
