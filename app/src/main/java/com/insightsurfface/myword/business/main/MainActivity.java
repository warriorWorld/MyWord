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
import com.insightsurfface.myword.listener.OnRecycleItemLongClickListener;
import com.insightsurfface.myword.widget.dialog.AddBookDialog;
import com.insightsurfface.myword.widget.dialog.NormalDialog;
import com.insightsurfface.myword.widget.dialog.NormalDialogBuilder;

import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {
    private RecyclerView wordsTablesRcv;
    private WordsTablesAdapter adapter;
    private MainContract.Presenter mPresenter;
    private List<WordsBook> mList;

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

    private void showAddBookDialog() {
        AddBookDialog dialog = new AddBookDialog(this);
        dialog.setOnAddClickListener(new AddBookDialog.OnAddBookListener() {
            @Override
            public void onOkClick(String name, String url) {
                WordsBook wordsBook = new WordsBook();
                wordsBook.setName(name);
                wordsBook.setUrl(url);
                mPresenter.insertBook(wordsBook);
            }
        });
        dialog.show();
    }

    private void showDeleteDialog(final long id) {
        new NormalDialogBuilder(this)
                .setTitle("是否删除该单词本?")
                .setOkText("删除")
                .setCancelText("取消")
                .setTitleBold(true)
                .setOnDialogClickListener(new NormalDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        mPresenter.deleteBook(id);
                    }

                    @Override
                    public void onCancelClick() {

                    }
                })
                .create()
                .show();
    }

    @Override
    public void displayWordsTables(List<WordsBook> list) {
        //这里如果用final的list 会导致之后无法更新list,所有listeener里的list的值都会是错误的 因为final是又复制了一份一样的list 但之后不会随着元数据的更改而更改
        mList = list;
        try {
            if (null == adapter) {
                adapter = new WordsTablesAdapter(this);
                adapter.setList(mList);
                adapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                    }
                });
                adapter.setOnAddClickListener(new OnAddClickListener() {
                    @Override
                    public void onClick() {
                        showAddBookDialog();
                    }
                });
                adapter.setOnRecycleItemLongClickListener(new OnRecycleItemLongClickListener() {
                    @Override
                    public void onItemLongClick(int position) {
                        showDeleteDialog(mList.get(position).getId());
                    }
                });
                wordsTablesRcv.setAdapter(adapter);
            } else {
                adapter.setList(mList);
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
