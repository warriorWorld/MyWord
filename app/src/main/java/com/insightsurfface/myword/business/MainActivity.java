package com.insightsurfface.myword.business;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.db.DbAdapter;
import com.insightsurfface.myword.utils.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DbAdapter dbAdapter=new DbAdapter(this);
        Logger.d(dbAdapter.queryAllWordsBook().size()+"个单词");
        dbAdapter.closeDb();
    }
}
