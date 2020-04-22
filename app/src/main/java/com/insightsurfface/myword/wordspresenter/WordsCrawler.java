package com.insightsurfface.myword.wordspresenter;


import com.insightsurfface.myword.listener.OnResultCallBack;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordsCrawler implements WordsPresenter {
    @Override
    public void getWords(final String url, final OnResultCallBack<List<String>> listener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    org.jsoup.nodes.Document doc = Jsoup
                            .connect(url)
                            .ignoreHttpErrors(true)
                            .timeout(60000)
                            .method(Connection.Method.GET)
                            .execute()
                            .parse();
                    if (null != doc) {
                        Elements wordElements = doc.getElementsByClass("blob-code blob-code-inner js-file-line");
                        List<String> result = new ArrayList<>();
                        for (int i = 0; i < wordElements.size(); i++) {
                            result.add(wordElements.get(i).text());
                        }
                        if (null != listener) {
                            listener.loadSucceed(result);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    if (null != listener) {
                        listener.loadFailed(e.toString());
                    }
                }
            }
        }.start();
    }
}
