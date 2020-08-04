package com.insightsurfface.myword.config;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydtranslate.TranslateParameters;

public class Configure {
    public static boolean isPad = false;
    final public static DisplayImageOptions smallImageOptions = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(true)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();
    public static final String YOUDAO = "http://fanyi.youdao.com/openapi.do?keyfrom=foreignnews&key=14473" +
            "94905&type=data&doctype=json&version=1.1&q=";
    final public static int PERMISSION_FILE_REQUST_CODE = 8023;
    final public static TranslateParameters TPS = new TranslateParameters.Builder()
            .source("MangaReader")
            .from(LanguageUtils.getLangByName("中文"))
            .to(LanguageUtils.getLangByName("英文"))
            .build();
}
