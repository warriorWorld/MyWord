package com.insightsurfface.myword.utils;

import android.text.TextUtils;

import com.insightsurfface.myword.config.ShareKeys;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.WebExplain;

/**
 * Created by acorn on 2023/7/30.
 */
public class StringFormer {
    public static String formatTranslate(Translate translate) {
        String result = "";
        if (null != translate && null != translate.getExplains() && translate.getExplains().size() > 0) {
            String word = translate.getQuery();
            StringBuilder translateSb = new StringBuilder();
            for (int i = 0; i < translate.getExplains().size(); i++) {
                translateSb.append(translate.getExplains().get(i)).append(";\n");
            }
            result = translateSb.toString();
            if (null != translate.getWebExplains() && translate.getWebExplains().size() > 1) {
                StringBuilder webTranslateSb = new StringBuilder();
                webTranslateSb.append(translateSb);
                for (int i = 1; i < translate.getWebExplains().size(); i++) {
                    WebExplain item = translate.getWebExplains().get(i);
                    for (int j = 0; j < item.getMeans().size(); j++) {
                        webTranslateSb.append(item.getMeans().get(j)).append(";\n");
                    }
                    webTranslateSb.append("\n");
                }
                result = translateSb.toString();
            }
        }
        return result;
    }
    public static String formatTranslateWithSpeakUrl(Translate translate){
        String result=formatTranslate(translate);
        if (null!=translate&&!TextUtils.isEmpty(translate.getUSSpeakUrl())){
            result+= ShareKeys.SPEAK_URL_SEPERATER +translate.getUSSpeakUrl();
        }
        return result;
    }
}
