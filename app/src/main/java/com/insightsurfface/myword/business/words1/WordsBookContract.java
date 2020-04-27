package com.insightsurfface.myword.business.words1;

import com.insightsurfface.myword.base.BasePresenter;
import com.insightsurfface.myword.base.BaseView;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.greendao.Words;

import java.util.List;

public class WordsBookContract {
    interface Presenter extends BasePresenter {
        void getWords(Long bookId,boolean shuffle);

        void killWord(int position, Words word);

        void translateWord(int position, Words word);
    }

    interface View extends BaseView<Presenter> {
        void displayWords(List<Words> list,boolean shuffle);

        void displayTranslate(String translate);

        void displayKillWord(int position);

        void displayMsg(String msg);
    }
}
