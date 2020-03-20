package com.insightsurfface.myword.base;

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
