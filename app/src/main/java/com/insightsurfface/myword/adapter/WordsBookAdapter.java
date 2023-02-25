package com.insightsurfface.myword.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.insightsurfface.myword.business.words1.OnWordsBookViewListener;
import com.insightsurfface.myword.business.words1.WordView;
import com.insightsurfface.myword.business.words1.WordsBookView;
import com.insightsurfface.myword.business.words1.WriteWordsBookView;
import com.insightsurfface.myword.enums.WordStatus;
import com.insightsurfface.myword.greendao.Words;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;

public class WordsBookAdapter extends PagerAdapter {
    private List<Words> wordsList;
    private List<WordStatus> mWordStatusList;
    private Context context;
    private WordView currentView;
    private OnWordsBookViewListener onWordsBookViewListener;
    //为解决删除后不刷新问题
    private int mChildCount = 0;
    private boolean isWriteBook;

    public WordsBookAdapter(Context context) {
        this.context = context;
    }

    public void setList(List<Words> wordsList) {
        this.wordsList = wordsList;
        mWordStatusList = new ArrayList<>();
        for (int i = 0; i < wordsList.size(); i++) {
            mWordStatusList.add(WordStatus.DEFAULT);
        }
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
        container.removeView((View) object);
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (isWriteBook) {
            WriteWordsBookView writeWordsBookView;
            Words item = wordsList.get(position);
            writeWordsBookView = new WriteWordsBookView(context);
            writeWordsBookView.setWord(item.getWord());
            writeWordsBookView.setOnWordsBookViewListener(onWordsBookViewListener);
            switch (mWordStatusList.get(position)) {
                case DELETED:
                    writeWordsBookView.markDeleted();
                    break;
                case RECONIZED:
                    writeWordsBookView.markReconized();
                    break;
                case DEFAULT:
                    writeWordsBookView.hideMark();
                    break;
            }
            container.addView(writeWordsBookView);
            return writeWordsBookView;
        } else {
            WordsBookView v0;
            Words item = wordsList.get(position);
            v0 = new WordsBookView(context);
            v0.setWord(item.getWord());
            v0.setOnWordsBookViewListener(onWordsBookViewListener);
            switch (mWordStatusList.get(position)) {
                case DELETED:
                    v0.markDeleted();
                    break;
                case RECONIZED:
                    v0.markReconized();
                    break;
                case DEFAULT:
                    v0.hideMark();
                    break;
            }
            container.addView(v0);
            return v0;
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (WordView) object;
    }

    public WordView getCurrentView() {
        return currentView;
    }

    public void setOnWordsBookViewListener(OnWordsBookViewListener onWordsBookViewListener) {
        this.onWordsBookViewListener = onWordsBookViewListener;
    }

    //为解决删除后不刷新问题
    @Override
    public void notifyDataSetChanged() {
        mChildCount = getCount();
        super.notifyDataSetChanged();
    }

    public List<WordStatus> getWordStatusList() {
        return mWordStatusList;
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

    public void setWriteBook(boolean writeBook) {
        isWriteBook = writeBook;
    }
}
