package com.insightsurfface.myword.business.words1;

import android.content.Context;

import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.greendao.DbController;
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
    public void getWords(Long bookId) {
        mView.displayWords(DbController.getInstance(mContext.getApplicationContext()).querryWordsByBook(bookId));
    }

    @Override
    public void killWord(int position, String word) {
        DbController.getInstance(mContext.getApplicationContext()).deleteWord(word);
        mView.displayKillWord(position);
    }

    @Override
    public void translateWord(final int position, final String word) {
        DisposableObserver<YoudaoResponse> observer = new DisposableObserver<YoudaoResponse>() {
            @Override
            public void onNext(YoudaoResponse result) {
                if (null != result && result.getErrorCode() == 0) {
                    YoudaoResponse.BasicBean item = result.getBasic();
                    if (null != item) {
                        mView.displayTranslate(result);
                    } else {
                        mView.displayErrorMsg("没查到该词");
                        killWord(position, word);
                        mView.displayKillWord(position);
                    }
                } else {
                    mView.displayErrorMsg("网络连接失败");
                }
            }

            @Override
            public void onError(Throwable e) {
                mView.displayErrorMsg("error\n" + e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        ((BaseActivity) mContext).mObserver.add(observer);
        RetrofitUtil.getInstance().create(HttpService.class)
                .translate(word)
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
