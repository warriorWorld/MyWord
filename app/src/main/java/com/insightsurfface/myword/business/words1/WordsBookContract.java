package com.insightsurfface.myword.business.words1;

import com.insightsurfface.myword.base.BasePresenter;
import com.insightsurfface.myword.base.BaseView;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.greendao.Words;

import java.util.List;

public class WordsBookContract {
    interface Presenter extends BasePresenter {
        void getWords(Long bookId);

        void killWord(int position, String word);

        void translateWord(int position, String word);
    }

    interface View extends BaseView<Presenter> {
        void displayWords(List<Words> list);

        void displayTranslate(YoudaoResponse translate);

        void displayKillWord(int position);

        void displayErrorMsg(String msg);
    }
}
