// ITranslateCallback.aidl
package com.insightsurfface.myword.aidl;

// Declare any non-default types here with import statements
import com.insightsurfface.myword.aidl.TranslateWraper;
interface ITranslateCallback {
    void onResponse(in TranslateWraper translate);
    void onFailure(String message);
}
