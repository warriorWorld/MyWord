package com.insightsurfface.myword.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.insightsurfface.myword.business.words1.WordsBookView;
import com.insightsurfface.myword.greendao.Words;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class WordsBookAdapter extends PagerAdapter {
    private List<Words> wordsList;
    private Context context;
    private WordsBookView currentView;
    private WordsBookView.OnWordsBookViewListener onWordsBookViewListener;
    //为解决删除后不刷新问题
    private int mChildCount = 0;

    public WordsBookAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Words> wordsList) {
        this.wordsList = wordsList;
    }

    @Override
    public int getCount() {
        return wordsList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // 官方提示这样写
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((WordsBookView) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        WordsBookView v0;
        Words item = wordsList.get(position);
        v0 = new WordsBookView(context);
        v0.setWord(item.getWord());
        v0.setOnWordsBookViewListener(onWordsBookViewListener);
        container.addView(v0);
        return v0;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (WordsBookView) object;
    }

    public WordsBookView getCurrentView() {
        return currentView;
    }

    public void setOnWordsBookViewListener(WordsBookView.OnWordsBookViewListener onWordsBookViewListener) {
        this.onWordsBookViewListener = onWordsBookViewListener;
    }

    //为解决删除后不刷新问题
    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    //为解决删除后不刷新问题
    @Override
    public int getItemPosition(Object object) {
        if (mChildCount > 0) {
            mChildCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }
}
