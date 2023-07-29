package com.insightsurfface.myword.utils;

import android.content.Context;

import com.insightsurfface.myword.config.ShareKeys;

public class PermissionUtil {
    public static boolean isMaster(Context context) {
        return SharedPreferencesUtils.getBooleanSharedPreferencesData(context, ShareKeys.IS_MASTER, false);
    }

    public static boolean isCreator(Context context) {
        return SharedPreferencesUtils.getBooleanSharedPreferencesData(context, ShareKeys.IS_CREATOR, false);
    }

    public static boolean isImageSwitched(Context context) {
        return SharedPreferencesUtils.getBooleanSharedPreferencesData(context, ShareKeys.IMAGE_SWITCH, false);
    }
}
