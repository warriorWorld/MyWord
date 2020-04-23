package com.insightsurfface.myword.business.main;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.adapter.WordsTablesAdapter;
import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.greendao.WordsBook;
import com.insightsurfface.myword.listener.OnAddClickListener;
import com.insightsurfface.myword.listener.OnRecycleItemClickListener;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {
    private RecyclerView wordsTablesRcv;
    private WordsTablesAdapter adapter;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        setPresenter(new MainPresenter(this, this));
        mPresenter.getWordsTables();
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
        baseTopBar.hideLeftButton();
        baseTopBar.setTitle(getString(R.string.app_name));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void displayWordsTables(List<WordsBook> list) {
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
        mPresenter = presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }
}
