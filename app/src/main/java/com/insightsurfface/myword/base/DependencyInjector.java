package com.insightsurfface.myword.base;

import android.content.Context;

import com.insightsurfface.myword.db.DbAdapter;

public interface DependencyInjector {
    DbAdapter dataRepository(Context context);
}
