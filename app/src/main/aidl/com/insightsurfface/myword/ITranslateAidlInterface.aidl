// ITranslateAidlInterface.aidl
package com.insightsurfface.myword;

// Declare any non-default types here with import statements
import com.insightsurfface.myword.aidl.ITranslateCallback;
interface ITranslateAidlInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void translate(String word,boolean showResultDialog,in ITranslateCallback callback);
}
