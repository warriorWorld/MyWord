package com.insightsurfface.myword.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;


/**
 * Created by Administrator on 2017/11/11.
 */

public class BaseParameterUtil {
    private PackageManager manager;
    private PackageInfo info;
    public String EMPTY = "empty";

    private BaseParameterUtil() {
    }

    private static volatile BaseParameterUtil instance = null;

    public static BaseParameterUtil getInstance() {
        if (instance == null) {
            //线程锁定
            synchronized (BaseParameterUtil.class) {
                //双重锁定
                if (instance == null) {
                    instance = new BaseParameterUtil();
                }
            }
        }
        return instance;
    }

    private void init(Context context) {
        try {
            manager = context.getPackageManager();
            info = manager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getAppVersionName(Context context) {
        if (null == manager || null == info) {
            init(context);
        }
        if (TextUtils.isEmpty(info.versionName)) {
            return EMPTY;
        }
        return info.versionName;
    }

    public int getAppVersionCode(Context context) {
        if (null == manager || null == info) {
            init(context);
        }
        return info.versionCode;
    }

    public String getSystemVersion() {
        if (TextUtils.isEmpty(android.os.Build.VERSION.RELEASE)) {
            return EMPTY;
        }
        return android.os.Build.VERSION.RELEASE;
    }

    public String getPhoneModel() {
        if (TextUtils.isEmpty(android.os.Build.MODEL)) {
            return EMPTY;
        }
        return android.os.Build.MODEL;
    }

    public String getSource() {
        return "ANDROID";
    }
}
