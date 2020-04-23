package com.insightsurfface.myword.business.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.adapter.WordsAdapter;
import com.insightsurfface.myword.adapter.WordsTablesAdapter;
import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.db.DbAdapter;
import com.insightsurfface.myword.greendao.WordsTables;
import com.insightsurfface.myword.listener.OnAddClickListener;
import com.insightsurfface.myword.listener.OnRecycleItemClickListener;
import com.insightsurfface.myword.listener.OnRecycleItemLongClickListener;
import com.insightsurfface.myword.utils.Logger;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {
    private RecyclerView wordsTablesRcv;
    private WordsTablesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        wordsTablesRcv = findViewById(R.id.words_tables_rcv);
        wordsTablesRcv.setLayoutManager
                (new LinearLayoutManager
                        (this, LinearLayoutManager.VERTICAL, false));
        wordsTablesRcv.setFocusable(false);
        wordsTablesRcv.setHasFixedSize(true);
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.recycler_load));
        wordsTablesRcv.setLayoutAnimation(controller);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void displayWordsTables(List<WordsTables> list) {
        try {
            if (null == adapter) {
                adapter = new WordsTablesAdapter(this);
                adapter.setList(list);
                adapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                    }
                });
                adapter.setOnAddClickListener(new OnAddClickListener() {
                    @Override
                    public void onClick() {

                    }
                });
                wordsTablesRcv.setAdapter(adapter);
            } else {
                adapter.setList(list);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {

    }
}
