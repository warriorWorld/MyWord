package com.insightsurfface.myword.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class WordsTable {
    @Id(autoincrement = true)//设置自增长
    private long id;
    @Index(unique = true)//设置唯一性
    private String word;
    private int recognize_time;
    private String translate;
    private int review_time;
    @Generated(hash = 881063056)
    public WordsTable(long id, String word, int recognize_time, String translate,
            int review_time) {
        this.id = id;
        this.word = word;
        this.recognize_time = recognize_time;
        this.translate = translate;
        this.review_time = review_time;
    }
    @Generated(hash = 1602084097)
    public WordsTable() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    public int getRecognize_time() {
        return this.recognize_time;
    }
    public void setRecognize_time(int recognize_time) {
        this.recognize_time = recognize_time;
    }
    public String getTranslate() {
        return this.translate;
    }
    public void setTranslate(String translate) {
        this.translate = translate;
    }
    public int getReview_time() {
        return this.review_time;
    }
    public void setReview_time(int review_time) {
        this.review_time = review_time;
    }


}
