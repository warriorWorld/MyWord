package com.insightsurfface.myword.business.words1;

import android.content.Context;
import android.text.TextUtils;

import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.config.ShareKeys;
import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.greendao.Words;
import com.insightsurfface.myword.okhttp.HttpService;
import com.insightsurfface.myword.okhttp.RetrofitUtil;
import com.insightsurfface.myword.utils.Logger;
import com.insightsurfface.myword.utils.SharedPreferencesUtils;
import com.insightsurfface.myword.utils.StringFormer;
import com.youdao.sdk.app.LanguageUtils;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.TranslateParameters;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class WordsBookPresenter implements WordsBookContract.Presenter {
    private Context mContext;
    private WordsBookContract.View mView;
    private TranslateParameters tps = new TranslateParameters.Builder()
            .source("MangaReader")
            .from(LanguageUtils.getLangByName("中文"))
            .to(LanguageUtils.getLangByName("英文"))
            .build();

    public WordsBookPresenter(Context context, WordsBookContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void getWords(Long bookId, boolean shuffle) {
        mView.displayWords(DbController.getInstance(mContext.getApplicationContext()).querryWordsByBook(bookId), shuffle);
    }

    @Override
    public void killWord(Words word) {
//        DbController.getInstance(mContext.getApplicationContext()).deleteWord(word);
        DbController.getInstance(mContext.getApplicationContext()).killWord(word);
        mView.displayKillWord();
    }

    @Override
    public void translateWord(final Words word) {
        boolean usePremiumTranslate = SharedPreferencesUtils.getBooleanSharedPreferencesData
                (mContext, ShareKeys.OPEN_PREMIUM_KEY, false);
        if (!TextUtils.isEmpty(word.getTranslate())) {
            if (word.getTranslate().contains(ShareKeys.SPEAK_URL_SEPERATER)) {
                String[] results = word.getTranslate().split(ShareKeys.SPEAK_URL_SEPERATER);
                if (results.length == 2) {
                    mView.displayTranslate(results[0]);
                    mView.playVoice(results[1]);
                } else {
                    mView.displayTranslate(word.getTranslate());
                }
            } else {
                mView.displayTranslate(word.getTranslate());
            }
            return;
        }
        if (usePremiumTranslate) {
            //使用SDK查词
            Translator.getInstance(tps).lookup(word.getWord(), "requestId", new TranslateListener() {

                @Override
                public void onError(TranslateErrorCode code, String s) {
                    Logger.d("error" + s);
                    mView.displayMsg("没查到该词(error)");
                }

                @Override
                public void onResult(final Translate translate, final String s, String s1) {
                    if (null != translate && null != translate.getExplains() && translate.getExplains().size() > 0) {
                        mView.displayTranslate(translate);
                        DbController.getInstance(mContext.getApplicationContext()).
                                updateTranslate(word, StringFormer.formatTranslateWithSpeakUrl(translate));
                    } else {
                        mView.displayMsg("没查到该词");
                        DbController.getInstance(mContext.getApplicationContext()).
                                updateTranslate(word, "没查到该词");
                    }
                }

                @Override
                public void onResult(List<Translate> list, List<String> list1, List<TranslateErrorCode> list2, String s) {
                    Logger.d(s);
                }
            });
        } else {
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
                            mView.displayMsg("没查到该词...");
//                            killWord(word);
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
    }

    @Override
    public void recognizeWord(Words word) {
        DbController.getInstance(mContext.getApplicationContext()).updateRecongnizeTime(word);
        if (word.getRecognize_frequency() >= SharedPreferencesUtils.getIntSharedPreferencesData
                (mContext, ShareKeys.DELETE_LIMIT_KEY, 3)) {
            killWord(word);
        } else {
            mView.displayReconizeWord();
        }
    }

    @Override
    public void incognizanceWord(Words word) {
        mView.displayIncogizanceWord();
    }

    @Override
    public void onDestroy() {
        mContext = null;
        mView = null;
    }
}
