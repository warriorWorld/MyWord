package com.insightsurfface.myword.business.words1;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.utils.Logger;


/**
 * Created by Administrator on 2016/4/4.
 */
public class WriteWordsBookView extends RelativeLayout implements WordView {
    private Context context;
    private String word, translate, TRANSLATING = "查询中";
    private EditText wordEt;
    private TextView translateTv;
    private OnWordsBookViewListener onWordsBookViewListener;
    private int DURATION = 500;//动画时间
    private float rotationValue = 0f;
    private ImageView markIv;
    private Side mSide = Side.Front;
    private AnimatorSet cardInAnimation;
    private AnimatorSet cardOutAnimation;

    enum Side {
        Front, Back
    }

    public WriteWordsBookView(Context context) {
        this(context, null);
    }

    public WriteWordsBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_write_words_book, this);
        markIv = findViewById(R.id.mark_iv);
        wordEt = (EditText) findViewById(R.id.word);
        translateTv = findViewById(R.id.translate_tv);
        translateTv.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performBackCardClick();
            }
        });
        wordEt.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != onWordsBookViewListener) {
                    onWordsBookViewListener.onWordClick(word);
                }
                return true;
            }
        });
        initAnimation(context);
    }

    private void initAnimation(Context context) {
        cardInAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.anim_card_in);
        cardOutAnimation = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.anim_card_out);
        cardOutAnimation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                //切换显示
                switch (mSide) {
                    case Front:
                        Logger.d("front");
                        translateTv.setVisibility(GONE);
                        wordEt.setTextSize(38);
                        wordEt.setGravity(Gravity.CENTER);
                        wordEt.setText("");
                        break;
                    case Back:
                        Logger.d("back");
                        wordEt.setVisibility(GONE);
                        translateTv.setTextSize(22);
                        translateTv.setGravity(Gravity.LEFT);
                        if (TextUtils.isEmpty(translate)) {
                            //如果是空的 就通知查询单词
                            translateTv.setText(TRANSLATING);
                            if (null != onWordsBookViewListener) {
                                onWordsBookViewListener.queryWord(word);
                            }
                        } else {
                            translateTv.setText(translate);
                        }
                        break;
                }
            }
        });
    }

    private void performCardFlip() {
        Logger.d("performCardFlip");
        if (mSide == Side.Back) {
            return;
        }
        this.mSide = Side.Back;
        translateTv.setVisibility(VISIBLE);
        cardOutAnimation.setTarget(wordEt);
        cardInAnimation.setTarget(translateTv);
        cardOutAnimation.start();
        cardInAnimation.start();
    }

    private void performBackCardClick() {
        Logger.d("performBackCardClick");
        if (mSide == Side.Front) {
            return;
        }
        mSide = Side.Front;
        wordEt.setVisibility(VISIBLE);
        cardOutAnimation.setTarget(translateTv);
        cardInAnimation.setTarget(wordEt);
        cardOutAnimation.start();
        cardInAnimation.start();
    }

    public void playWordTvAnimation() {
        rotationValue = rotationValue + 360f;
        ObjectAnimator wordTvAnimation = ObjectAnimator.ofFloat(wordEt, "rotationY", rotationValue);
        AnimatorSet set = new AnimatorSet();
        //属性动画监听类
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //切换显示
                if (word.equals(wordEt.getText().toString())) {
                    wordEt.setTextSize(22);
                    wordEt.setGravity(Gravity.LEFT);
                    if (TextUtils.isEmpty(translate)) {
                        //如果是空的 就通知查询单词
                        wordEt.setText(TRANSLATING);
                        if (null != onWordsBookViewListener) {
                            onWordsBookViewListener.queryWord(word);
                        }
                    } else {
                        wordEt.setText("");
                    }
                } else {
                    wordEt.setTextSize(38);
                    wordEt.setGravity(Gravity.CENTER);
                    wordEt.setText("");
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.playTogether(wordTvAnimation);
        set.setDuration(DURATION);
        set.start();
    }

    public void hideMark() {
        markIv.setVisibility(GONE);
    }

    @Override
    public void markDeleted() {
        markIv.setVisibility(VISIBLE);
        markIv.setImageResource(R.drawable.ic_delete);
    }

    @Override
    public void markReconized() {
        markIv.setVisibility(VISIBLE);
        markIv.setImageResource(R.drawable.ic_light_saber);
    }

    @Override
    public String getText() {
        return word.toLowerCase();
    }

    @Override
    public String getInput() {
        return wordEt.getText().toString().toLowerCase();
    }

    public void setWord(String word) {
        this.word = word;
        wordEt.setText("");
    }

    @Override
    public void setTranslate(String translate) {
        this.translate = translate;
        if (TRANSLATING.equals(translateTv.getText().toString())) {
            translateTv.setText(translate);
        }
    }

    public void setOnWordsBookViewListener(OnWordsBookViewListener onWordsBookViewListener) {
        this.onWordsBookViewListener = onWordsBookViewListener;
    }
}
