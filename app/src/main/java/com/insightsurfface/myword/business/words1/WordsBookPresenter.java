package com.insightsurfface.myword.business.words1;

import android.content.Context;
import android.text.TextUtils;

import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.greendao.Words;
import com.insightsurfface.myword.okhttp.HttpService;
import com.insightsurfface.myword.okhttp.RetrofitUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WordsBookPresenter implements WordsBookContract.Presenter {
    private Context mContext;
    private WordsBookContract.View mView;

    public WordsBookPresenter(Context context, WordsBookContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void getWords(Long bookId, boolean shuffle) {
        mView.displayWords(DbController.getInstance(mContext.getApplicationContext()).querryWordsByBook(bookId), shuffle);
    }

    @Override
    public void killWord(int position, Words word) {
//        DbController.getInstance(mContext.getApplicationContext()).deleteWord(word);
        DbController.getInstance(mContext.getApplicationContext()).killWord(word);
        mView.displayKillWord(position);
    }

    @Override
    public void translateWord(final int position, final Words word) {
        if (!TextUtils.isEmpty(word.getTranslate())) {
            mView.displayMsg("保存的翻译");
            mView.displayTranslate(word.getTranslate());
            return;
        }
        DisposableObserver<YoudaoResponse> observer = new DisposableObserver<YoudaoResponse>() {
            @Override
            public void onNext(YoudaoResponse result) {
                if (null != result && result.getErrorCode() == 0) {
                    YoudaoResponse.BasicBean item = result.getBasic();
                    if (null != item) {
                        String t = "";
                        for (int i = 0; i < item.getExplains().size(); i++) {
                            t = t + item.getExplains().get(i) + ";\n";
                        }
                        mView.displayTranslate(t);
                        DbController.getInstance(mContext.getApplicationContext()).
                                updateTranslate(word, t);
                    } else {
                        mView.displayMsg("没查到该词");
                        killWord(position, word);
                    }
                } else {
                    mView.displayMsg("网络连接失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.displayMsg("error\n" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        ((BaseActivity) mContext).mObserver.add(observer);
        RetrofitUtil.getInstance().create(HttpService.class)
                .translate(word.getWord())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
