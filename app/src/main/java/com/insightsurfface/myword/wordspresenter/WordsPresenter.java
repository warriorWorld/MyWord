package com.insightsurfface.myword.wordspresenter;

import com.insightsurfface.myword.listener.OnResultCallBack;

import java.util.List;

public interface WordsPresenter {
  void  getWords(String url, OnResultCallBack<List<String>> listener);
}
