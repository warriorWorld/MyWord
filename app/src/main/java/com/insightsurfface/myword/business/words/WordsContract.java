package com.insightsurfface.myword.business.words;

import com.insightsurfface.myword.base.BasePresenter;
import com.insightsurfface.myword.base.BaseView;
import com.insightsurfface.myword.bean.WordsBookBean;

import java.util.ArrayList;

public interface WordsContract {
    interface Presenter extends BasePresenter {
        void onViewCreated();

        void getWords();

        void killWord(int position, String word);

        void translateWord(int position, String word);
    }

    interface View extends BaseView<Presenter> {
        void displayWords(ArrayList<WordsBookBean> list);

        void displayTranslate(int position, String translate);

        void displayKillWord(int position);

        void displayErrorMsg(String msg);
    }
}
