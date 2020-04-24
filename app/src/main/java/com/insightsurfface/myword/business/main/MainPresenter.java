package com.insightsurfface.myword.business.main;

import android.content.Context;

import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.greendao.Words;
import com.insightsurfface.myword.greendao.WordsBook;
import com.insightsurfface.myword.listener.OnResultCallBack;
import com.insightsurfface.myword.wordspresenter.WordsCrawler;

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
                WordsBook newBook = new WordsBook(book.getId(), book.getName(), book.getUrl());
                if (null == book.getWords() || book.getWords().size() == 0) {
                    for (String item : result) {
                        Words word = new Words();
                        word.setWord(item);

                    }
                }else {

                }
                DbController.getInstance(mContext.getApplicationContext()).update(newBook);
                getWordsTables();
            }

            @Override
            public void loadFailed(String error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
