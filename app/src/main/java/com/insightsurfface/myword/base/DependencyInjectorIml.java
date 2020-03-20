package com.insightsurfface.myword.base;

import android.content.Context;

import com.insightsurfface.myword.db.DbAdapter;

public class DependencyInjectorIml implements DependencyInjector {
    @Override
    public DbAdapter dataRepository(Context context) {
        return new DbAdapter(context);
    }
}
