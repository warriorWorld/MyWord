package com.insightsurfface.myword.business.words1;

import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.adapter.WordsBookAdapter;
import com.insightsurfface.myword.base.TTSActivity;
import com.insightsurfface.myword.bean.YoudaoResponse;
import com.insightsurfface.myword.greendao.Words;
import com.insightsurfface.myword.listener.OnSpeakClickListener;
import com.insightsurfface.myword.utils.VibratorUtil;
import com.insightsurfface.myword.widget.dialog.TranslateResultDialog;

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
    private TextView topBarRight, topBarLeft;
    private ViewPager vp;
    private List<Words> wordsList;
    private int currentPosition = 0;
    private ClipboardManager clip;//复制文本用
    private View killBtn;
    private WordsBookContract.Presenter mPresenter;
    private Long bookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        initUI();
        setPresenter(new WordsBookPresenter(this, this));
        bookId = getIntent().getLongExtra("bookId", -1);
        mPresenter.getWords(bookId);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initUI() {
        vp = (ViewPager) findViewById(R.id.words_viewpager);
        emptyView = findViewById(R.id.empty_view);
        killBtn = findViewById(R.id.kill_btn);
        topBarLeft = (TextView) findViewById(R.id.top_bar_left);
        topBarRight = (TextView) findViewById(R.id.top_bar_right);
        killBtn.setOnClickListener(this);
        baseTopBar.setTitle("生词本");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_words_book;
    }


    private void initViewPager() {
        if (null == adapter) {
            adapter = new WordsBookAdapter(WordsBookActivity.this);
            vp.setOffscreenPageLimit(3);
            adapter.setOnWordsBookViewListener(new WordsBookView.OnWordsBookViewListener() {
                @Override
                public void onWordClick(String word) {

                }

                @Override
                public void queryWord(String word) {
                    mPresenter.translateWord(currentPosition, word);
                }

                @Override
                public void onWordLongClick(String word) {
                    //长按才调用这个
                    clip.setText(word);
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
                    topBarLeft.setText("总计:" + wordsList.size() + "个生词,当前位置:" + (position + 1));
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.kill_btn:
                mPresenter.killWord(currentPosition, wordsList.get(currentPosition).getWord());
                break;
        }
    }

    @Override
    public void displayWords(List<Words> list) {
        try {
            wordsList = list;
            if (null == wordsList || wordsList.size() == 0) {
                emptyView.setVisibility(View.VISIBLE);
                killBtn.setVisibility(View.GONE);
            } else {
                Collections.shuffle(wordsList);
                emptyView.setVisibility(View.GONE);
                killBtn.setVisibility(View.VISIBLE);
            }
            initViewPager();
            text2Speech(wordsList.get(currentPosition).getWord());
            try {
                topBarLeft.setText("总计:" + wordsList.size() + "个生词,当前位置:" + (currentPosition + 1));
            } catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void displayTranslate(YoudaoResponse translate) {
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
    public void displayKillWord(int position) {
        try {
            VibratorUtil.Vibrate(WordsBookActivity.this, 100);
            wordsList.remove(position);
            displayWords(wordsList);
            if (wordsList.size() <= 0) {
                baseToast.showToast("PENTA KILL!!!");
                finish();
            }
        } catch (IndexOutOfBoundsException e) {
            WordsBookActivity.this.finish();
        }
    }

    @Override
    public void displayErrorMsg(String msg) {
        baseToast.showToast(msg);
    }

    @Override
    public void setPresenter(WordsBookContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
