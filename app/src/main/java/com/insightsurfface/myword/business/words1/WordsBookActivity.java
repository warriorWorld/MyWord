package com.insightsurfface.myword.business.words1;

import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.adapter.WordsBookAdapter;
import com.insightsurfface.myword.base.TTSActivity;
import com.insightsurfface.myword.config.ShareKeys;
import com.insightsurfface.myword.enums.WordStatus;
import com.insightsurfface.myword.greendao.Words;
import com.insightsurfface.myword.listener.OnSpeakClickListener;
import com.insightsurfface.myword.utils.AudioMgr;
import com.insightsurfface.myword.utils.SharedPreferencesUtils;
import com.insightsurfface.myword.utils.VibratorUtil;
import com.insightsurfface.myword.widget.dialog.TranslateResultDialog;
import com.insightsurfface.stylelibrary.keyboard.KeyBoardDialog;
import com.insightsurfface.stylelibrary.listener.OnKeyboardChangeListener;
import com.insightsurfface.stylelibrary.listener.OnKeyboardListener;
import com.youdao.sdk.common.YouDaoLog;
import com.youdao.sdk.ydtranslate.Translate;
import com.youdao.sdk.ydtranslate.WebExplain;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import androidx.viewpager.widget.ViewPager;

/**
 * /storage/sdcard0/reptile/one-piece
 * <p/>
 * Created by Administrator on 2016/4/4.
 */
public class WordsBookActivity extends TTSActivity implements OnClickListener, WordsBookContract.View {
    private WordsBookAdapter adapter;
    private View emptyView;
    private ViewPager vp;
    private List<Words> wordsList;
    private int currentPosition = 0;
    private ClipboardManager clip;//复制文本用
    private View killBtn;
    private WordsBookContract.Presenter mPresenter;
    private Long bookId;
    private TextView pageTv;
    private Button recognizeBtn;
    private Button incognizanceBtn;
    private int continuousKill = 0;
    private boolean isWriteBook = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        isWriteBook = getIntent().getBooleanExtra("isWriteBook", false);
        initUI();
        setPresenter(new WordsBookPresenter(this, this));
        bookId = getIntent().getLongExtra("bookId", -1);
        mPresenter.getWords(bookId, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initUI() {
        vp = (ViewPager) findViewById(R.id.words_viewpager);
        emptyView = findViewById(R.id.empty_view);
        killBtn = findViewById(R.id.kill_btn);
        pageTv = findViewById(R.id.page_tv);
        recognizeBtn = (Button) findViewById(R.id.recognize_btn);
        recognizeBtn.setText(isWriteBook ? "发音" : "认识");
        incognizanceBtn = (Button) findViewById(R.id.incognizance_btn);
        incognizanceBtn.setText(isWriteBook ? "显示" : "不认识");

        recognizeBtn.setOnClickListener(this);
        incognizanceBtn.setOnClickListener(this);
        killBtn.setOnClickListener(this);
        baseTopBar.setTitle("");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_words_book;
    }


    private void initViewPager() {
        if (null == adapter) {
            adapter = new WordsBookAdapter(WordsBookActivity.this);
            vp.setOffscreenPageLimit(3);
            adapter.setWriteBook(isWriteBook);
            adapter.setOnWordsBookViewListener(new OnWordsBookViewListener() {
                @Override
                public void onWordClick(String word) {
                    if (isWriteBook) {
                        showKeyboardDialog();
                    }
                }

                @Override
                public void queryWord(String word) {
                    if (SharedPreferencesUtils.getBooleanSharedPreferencesData
                            (WordsBookActivity.this, ShareKeys.CLICK_COPY_KEY, false)) {
                        clip.setText(word);
                    }
                    mPresenter.translateWord(wordsList.get(currentPosition));
                }

                @Override
                public void onWordLongClick(String word) {
                    //长按才调用这个
                    if (isWriteBook) {
                        adapter.getCurrentView().performCardFlip();
                    } else {
                        clip.setText(word);
                    }
                }
            });
            adapter.setList(wordsList);
            vp.setAdapter(adapter);
            vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    currentPosition = position;
                    pageTv.setText((position + 1) + "/" + wordsList.size());
                    text2Speech(wordsList.get(position).getWord());
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
//            mangaPager.setCurrentItem(2);
        } else {
            adapter.setList(wordsList);
            adapter.notifyDataSetChanged();
        }
    }

    private void showKeyboardDialog() {
        final KeyBoardDialog dialog = new KeyBoardDialog(this);
        dialog.setOnKeyboardChangeListener(new OnKeyboardChangeListener() {
            @Override
            public void onChange(String res) {
            }

            @Override
            public void onFinish(String res) {
                if (TextUtils.isEmpty(res)) {
                    baseToast.showToast("word is empty");
                    return;
                }
                if (res.equalsIgnoreCase(wordsList.get(currentPosition).getWord()
                        .replaceAll("'", " ")
                        .replaceAll("-", " "))) {
                    adapter.getCurrentView().showWord();
                    mPresenter.recognizeWord(wordsList.get(currentPosition));
                } else {
                    mPresenter.incognizanceWord(wordsList.get(currentPosition));
                }
            }
        });
        dialog.setOnKeyboardListener(new OnKeyboardListener() {
            @Override
            public void onOptionsClick() {
            }

            @Override
            public void onQuestionClick() {
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kill_btn:
                mPresenter.killWord(wordsList.get(currentPosition));
                break;
            case R.id.recognize_btn:
                if (isWriteBook) {
                    text2Speech(wordsList.get(currentPosition).getWord());
                } else {
                    mPresenter.recognizeWord(wordsList.get(currentPosition));
                }
                break;
            case R.id.incognizance_btn:
                if (isWriteBook) {
                    adapter.getCurrentView().showWord();
                } else {
                    mPresenter.incognizanceWord(wordsList.get(currentPosition));
                }
                break;
        }
    }

    @Override
    public void displayWords(List<Words> list, boolean shuffle) {
        try {
            wordsList = list;
            if (null == wordsList || wordsList.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                killBtn.setVisibility(View.GONE);
            } else {
                if (shuffle) {
                    Collections.shuffle(wordsList);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                                text2Speech(wordsList.get(0).getWord());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                emptyView.setVisibility(View.GONE);
                killBtn.setVisibility(View.VISIBLE);
            }
            initViewPager();
            try {
                pageTv.setText((currentPosition + 1) + "/" + wordsList.size());
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayTranslate(String translate) {
        adapter.getCurrentView().setTranslate(translate);
    }

    @Override
    public void displayTranslate(final Translate translate) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                playVoice(translate.getUSSpeakUrl());
                if (null != translate && null != translate.getExplains() && translate.getExplains().size() > 0) {
                    String word = translate.getQuery();
                    StringBuilder translateSb = new StringBuilder();
                    for (int i = 0; i < translate.getExplains().size(); i++) {
                        translateSb.append(translate.getExplains().get(i)).append(";\n");
                    }
                    adapter.getCurrentView().setTranslate(translateSb.toString());
                    if (null != translate.getWebExplains() && translate.getWebExplains().size() > 1) {
                        StringBuilder webTranslateSb = new StringBuilder();
                        webTranslateSb.append(translateSb);
                        for (int i = 1; i < translate.getWebExplains().size(); i++) {
                            WebExplain item = translate.getWebExplains().get(i);
                            for (int j = 0; j < item.getMeans().size(); j++) {
                                webTranslateSb.append(item.getMeans().get(j)).append(";\n");
                            }
                            webTranslateSb.append("\n");
                        }
                        adapter.getCurrentView().setTranslate(webTranslateSb.toString());
                    }
                }
            }
        });
    }

    public synchronized void playVoice(String speakUrl) {
        YouDaoLog.e(AudioMgr.PLAY_LOG + "TranslateDetailActivity click to playVoice speakUrl = " + speakUrl);
        if (!TextUtils.isEmpty(speakUrl) && speakUrl.startsWith("http")) {
            AudioMgr.startPlayVoice(speakUrl, new AudioMgr.SuccessListener() {
                @Override
                public void success() {
                    YouDaoLog.e(AudioMgr.PLAY_LOG + "TranslateDetailActivity playVoice success");
                }

                @Override
                public void playover() {
                    YouDaoLog.e(AudioMgr.PLAY_LOG + "TranslateDetailActivity playover");
                }
            });
        }
    }

    private void showVIPTranslateResultDialog(Translate translate) {
        TranslateResultDialog translateResultDialog = new TranslateResultDialog(this);
        translateResultDialog.setOnSpeakClickListener(new OnSpeakClickListener() {
            @Override
            public void onSpeakUSClick(String word) {
                setLanguage(Locale.US);
                text2Speech(word);
            }

            @Override
            public void onSpeakUKClick(String word) {
                setLanguage(Locale.UK);
                text2Speech(word);
            }
        });
        translateResultDialog.show();
        translateResultDialog.setTranslate(translate);
    }

    @Override
    public void displayKillWord() {
        try {
            VibratorUtil.Vibrate(WordsBookActivity.this, 100);
            continuousKill();
            adapter.getWordStatusList().set(currentPosition, WordStatus.DELETED);
            adapter.getCurrentView().markDeleted();
            toNextWord();
            if (wordsList.size() <= 0) {
                baseToast.showToast("PENTA KILL!!!");
                finish();
            }
        } catch (IndexOutOfBoundsException e) {
            WordsBookActivity.this.finish();
        }
    }

    @Override
    public void displayMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baseToast.showToast(msg);
            }
        });
    }

    @Override
    public void displayReconizeWord() {
        VibratorUtil.Vibrate(WordsBookActivity.this, 100);
        continuousKill();
        adapter.getWordStatusList().set(currentPosition, WordStatus.RECONIZED);
        adapter.getCurrentView().markReconized();
        toNextWord();
    }

    private void continuousKill() {
        continuousKill++;
        if (continuousKill >= 5) {
            text2Speech("PENTA KILL");
            continuousKill = 0;
        }
    }

    @Override
    public void displayIncogizanceWord() {
        continuousKill = 0;
        toNextWord();
    }

    @Override
    public void toNextWord() {
        vp.setCurrentItem(currentPosition + 1, true);
    }

    @Override
    public void setPresenter(WordsBookContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
