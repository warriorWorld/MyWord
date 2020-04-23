package com.insightsurfface.myword.business.main;

import android.content.Context;

import com.insightsurfface.myword.greendao.DbController;

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
    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
