package com.insightsurfface.myword.business.main;

import com.insightsurfface.myword.base.BasePresenter;
import com.insightsurfface.myword.base.BaseView;
import com.insightsurfface.myword.greendao.WordsBook;

import java.util.List;

public class MainContract {
    interface Presenter extends BasePresenter {
        void getWordsTables();
    }

    interface View extends BaseView<Presenter> {
        void displayWordsTables(List<WordsBook> list);
    }
}
