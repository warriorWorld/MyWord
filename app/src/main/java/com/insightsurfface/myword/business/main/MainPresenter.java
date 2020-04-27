package com.insightsurfface.myword.business.main;

import android.content.Context;

import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.greendao.Words;
import com.insightsurfface.myword.greendao.WordsBook;
import com.insightsurfface.myword.listener.OnResultCallBack;
import com.insightsurfface.myword.widget.toast.EasyToast;
import com.insightsurfface.myword.wordspresenter.WordsCrawler;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(Context context, MainContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getWordsTables() {
        //为防止单例持有activity 使用application的context
        mView.displayWordsTables(DbController.getInstance(mContext.getApplicationContext()).querryAllWordsBook());
    }

    @Override
    public void insertBook(WordsBook book) {
        DbController.getInstance(mContext.getApplicationContext()).insert(book);
        getWordsTables();
    }

    @Override
    public void deleteBook(Long id) {
        DbController.getInstance(mContext.getApplicationContext()).deleteWordsBookById(id);
        getWordsTables();
    }

    @Override
    public void updateBook(final WordsBook book) {
        new WordsCrawler().getWords(book.getUrl(), new OnResultCallBack<List<String>>() {
            @Override
            public void loadSucceed(List<String> result) {
                if (null == book.getWords() || book.getWords().size() == 0) {
                    for (String item : result) {
                        insertOneWord(book, item);
                    }
                } else {
                    for (String item : result) {
                        boolean isContains = false;
                        for (Words word : book.getWords()) {
                            if (item.equals(word.getWord())) {
                                isContains = true;
                                break;
                            }
                        }
                        if (!isContains) {
                            insertOneWord(book, item);
                        }
                    }
                }
                mView.displayMsg("更新完成");
                getWordsTables();
            }

            @Override
            public void loadFailed(String error) {

            }
        });
    }

    private void insertOneWord(WordsBook book, String item) {
        Words word = new Words();
        word.setWord(item);
        word.setCreated_time(System.currentTimeMillis());
        word.setUpdate_time(System.currentTimeMillis());
        word.setRecognize_time(0);
        word.setReview_time(0);
        word.setFk_bookId(book.getId());
        word.setWordsBook(book);
        DbController.getInstance(mContext.getApplicationContext()).insert(word);
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
