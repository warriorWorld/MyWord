package com.insightsurfface.myword.business.main;

import android.content.Context;

import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.greendao.WordsBook;

public class MainPresenter implements MainContract.Presenter {
    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(Context context, MainContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void getWordsTables() {
        mView.displayWordsTables(DbController.getInstance(mContext.getApplicationContext()).querryAllWordsBook());
    }

    @Override
    public void insertBook(WordsBook book) {
        DbController.getInstance(mContext.getApplicationContext()).insert(book);
        getWordsTables();
    }

    @Override
    public void deleteBook(long id) {
        DbController.getInstance(mContext.getApplicationContext()).deleteWordsBookById(id);
        getWordsTables();
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
