package com.insightsurfface.myword.business.words;

import android.content.Context;

import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.base.DependencyInjector;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.okhttp.HttpService;
import com.insightsurfface.myword.okhttp.RetrofitUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WordsPresenter implements WordsContract.Presenter {
    private Context mContext;
    private DependencyInjector mInjector;
    private WordsContract.View mView;

    public WordsPresenter(Context context, DependencyInjector injector, WordsContract.View view) {
        this.mContext = context;
        this.mInjector = injector;
        this.mView = view;
    }

    @Override
    public void onViewCreated() {
        getWords();
    }

    @Override
    public void getWords() {
        mView.displayWords(mInjector.dataRepository(mContext).queryAllWordsBook());
    }

    @Override
    public void killWord(int position, String word) {
        mInjector.dataRepository(mContext).killWordByWord(word);
        mView.displayKillWord(position);
    }

    @Override
    public void translateWord(final int position, final String word) {
//        String url = Configure.YOUDAO + word;
//        HashMap<String, String> params = new HashMap<String, String>();
//        VolleyCallBack<YoudaoResponse> callback = new VolleyCallBack<YoudaoResponse>() {
//
//            @Override
//            public void loadSucceed(YoudaoResponse result) {
//                if (null != result && result.getErrorCode() == 0) {
//                    YoudaoResponse.BasicBean item = result.getBasic();
//                    String t = "";
//                    if (null != item) {
//                        for (int i = 0; i < item.getExplains().size(); i++) {
//                            t = t + item.getExplains().get(i) + ";";
//                        }
//                        mView.displayTranslate(position, " [" + item.getPhonetic() +
//                                "]; " + t);
//                    } else {
//                        mView.displayErrorMsg("没查到该词");
//                        mInjector.dataRepository(mContext).deleteWordByWord(word);
//                        mView.displayKillWord(position);
//                    }
//                } else {
//                    mView.displayErrorMsg("网络连接失败");
//                }
//            }
//
//            @Override
//            public void loadFailed(VolleyError error) {
//                mView.displayErrorMsg("error\n" + error);
//            }
//
//            @Override
//            public void loadSucceedButNotNormal(YoudaoResponse result) {
//
//            }
//        };
//        VolleyTool.getInstance(mContext).requestData(Request.Method.GET,
//                mContext, url, params,
//                YoudaoResponse.class, callback);

        DisposableObserver<YoudaoResponse> observer = new DisposableObserver<YoudaoResponse>() {
            @Override
            public void onNext(YoudaoResponse result) {
                if (null != result && result.getErrorCode() == 0) {
                    YoudaoResponse.BasicBean item = result.getBasic();
                    String t = "";
                    if (null != item) {
                        for (int i = 0; i < item.getExplains().size(); i++) {
                            t = t + item.getExplains().get(i) + ";";
                        }
                        mView.displayTranslate(position, " [" + item.getPhonetic() +
                                "]; " + t);
                    } else {
                        mView.displayErrorMsg("没查到该词");
                        mInjector.dataRepository(mContext).deleteWordByWord(word);
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
        mInjector = null;
        mView = null;
    }
}
