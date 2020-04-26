package com.insightsurfface.myword.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-07-29.
 */
public class WordsBookBean implements Serializable {
    private String word;
    private int time;
    private String example_path;
    private long update_time;
    private int kill_time;
    private String translate;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getExample_path() {
        return example_path;
    }

    public void setExample_path(String example_path) {
        this.example_path = example_path;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public int getKill_time() {
        return kill_time;
    }

    public void setKill_time(int kill_time) {
        this.kill_time = kill_time;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }
}