package com.insightsurfface.myword.business.words1;

/**
 * Created by acorn on 2023/2/25.
 */
public interface OnWordsBookViewListener {
    //每次点击都会调用
    public void onWordClick(String word);

    //需要查词的时候才会调用
    public void queryWord(String word);

    //长按都会调用
    public void onWordLongClick(String word);
}
