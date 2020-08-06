package com.insightsurfface.myword.service;

import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.WindowManager;

import com.insightsurfface.myword.ITranslateAidlInterface;
import com.insightsurfface.myword.aidl.ITranslateCallback;
import com.insightsurfface.myword.aidl.TranslateWraper;
import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.config.Configure;
import com.insightsurfface.myword.config.ShareKeys;
import com.insightsurfface.myword.db.DbAdapter;
import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.okhttp.HttpService;
import com.insightsurfface.myword.okhttp.RetrofitUtil;
import com.insightsurfface.myword.utils.Logger;
import com.insightsurfface.myword.utils.SharedPreferencesUtils;
import com.insightsurfface.myword.widget.dialog.TranslateResultDialog;
import com.youdao.sdk.ydonlinetranslate.Translator;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.TranslateErrorCode;
import com.youdao.sdk.ydtranslate.TranslateListener;
import com.youdao.sdk.ydtranslate.WebExplain;

import java.util.List;

import androidx.annotation.Nullable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by acorn on 2020/8/4.
 */
public class TranslateService extends Service {
    private CompositeDisposable mObserver = new CompositeDisposable();
    private DbAdapter mDbAdapter;
    private Handler mHandler;
    private TranslateResultDialog mTranslateResultDialog;
    private ITranslateAidlInterface.Stub translateAidlInterface = new ITranslateAidlInterface.Stub() {
        @Override
        public void translate(final String word, final boolean showResultDialog, final ITranslateCallback callback) throws RemoteException {
            boolean usePremiumTranslate = SharedPreferencesUtils.getBooleanSharedPreferencesData
                    (TranslateService.this, ShareKeys.OPEN_PREMIUM_KEY, false);
            mDbAdapter.insertWordsBookTb(word, "");
            if (usePremiumTranslate) {
                Translator.getInstance(Configure.TPS).lookup(word, "requestId", new TranslateListener() {

                    @Override
                    public void onError(TranslateErrorCode code, String s) {
                        Logger.d("error" + s);
                        try {
                            callback.onFailure(s);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onResult(final Translate translate, final String s, String s1) {
                        TranslateWraper wraper = new TranslateWraper();
                        if (null != translate && null != translate.getExplains() && translate.getExplains().size() > 0) {
                            wraper.setQuery(translate.getQuery());
                            if (TextUtils.isEmpty(translate.getUSSpeakUrl()) && TextUtils.isEmpty(translate.getUKSpeakUrl())) {
                                wraper.setUSSpeakUrl(translate.getSpeakUrl());
                                wraper.setUKSpeakUrl(translate.getSpeakUrl());
                            } else {
                                wraper.setUKSpeakUrl(translate.getUKSpeakUrl());
                                wraper.setUSSpeakUrl(translate.getUSSpeakUrl());
                            }
                            StringBuilder translateSb = new StringBuilder();
                            for (int i = 0; i < translate.getExplains().size(); i++) {
                                translateSb.append(translate.getExplains().get(i)).append(";\n");
                            }
                            wraper.setTranslate(translateSb.toString());
                            if (!TextUtils.isEmpty(translate.getUkPhonetic())) {
                                wraper.setUKPhonetic(translate.getUkPhonetic());
                            }
                            if (!TextUtils.isEmpty(translate.getUsPhonetic())) {
                                wraper.setUSPhonetic(translate.getUsPhonetic());
                            }
                            if (null != translate.getWebExplains() && translate.getWebExplains().size() > 1) {
                                StringBuilder webTranslateSb = new StringBuilder();
                                for (int i = 1; i < translate.getWebExplains().size(); i++) {
                                    WebExplain item = translate.getWebExplains().get(i);
                                    webTranslateSb.append(item.getKey()).append(":\n");
                                    for (int j = 0; j < item.getMeans().size(); j++) {
                                        webTranslateSb.append(item.getMeans().get(j)).append(";\n");
                                    }
                                    webTranslateSb.append("\n");
                                }
                                wraper.setWebTranslate(webTranslateSb.toString());
                            }
                            if (showResultDialog) {
                                showTranslateDialog(wraper);
                            }
                            try {
                                callback.onResponse(wraper);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                callback.onFailure("response is null");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
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
                                TranslateWraper wraper = new TranslateWraper();
                                String t = word + ":\n";
                                for (int i = 0; i < item.getExplains().size(); i++) {
                                    t = t + item.getExplains().get(i) + ";\n";
                                }
                                wraper.setQuery(word);
                                wraper.setTranslate(t);
                                if (showResultDialog) {
                                    showTranslateDialog(wraper);
                                }
                                try {
                                    callback.onResponse(wraper);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                try {
                                    callback.onFailure("response is null");
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            try {
                                callback.onFailure("response is null");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            callback.onFailure(e.toString());
                        } catch (RemoteException remoteException) {
                            remoteException.printStackTrace();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                };
                mObserver.add(observer);
                RetrofitUtil.getInstance().create(HttpService.class)
                        .translate(word)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(observer);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mDbAdapter = new DbAdapter(this);
        mHandler = new Handler(Looper.getMainLooper());
        mTranslateResultDialog = new TranslateResultDialog(this);
    }

    private void showTranslateDialog(final TranslateWraper wraper) {
        Logger.d("handler" + mHandler + " ,dialog" + mTranslateResultDialog);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                /*8.0系统加强后台管理，禁止在其他应用和窗口弹提醒弹窗，如果要弹，必须使用TYPE_APPLICATION_OVERLAY，否则弹不出
                 *需要SYSTEM_OVERLAY_WINDOW和SYSTEM_ALERT_WINDOW权限
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mTranslateResultDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY));
                } else {
                    mTranslateResultDialog.getWindow().setType((WindowManager.LayoutParams.TYPE_SYSTEM_ALERT));
                }
                mTranslateResultDialog.show();
                mTranslateResultDialog.setTranslate(wraper);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mObserver.dispose();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return translateAidlInterface;
    }
}
