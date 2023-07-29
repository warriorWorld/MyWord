package com.insightsurfface.myword.business.main;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.adapter.WordsTablesAdapter;
import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.business.setting.SettingsActivity;
import com.insightsurfface.myword.business.words1.WordsBookActivity;
import com.insightsurfface.myword.config.Configure;
import com.insightsurfface.myword.config.ShareKeys;
import com.insightsurfface.myword.greendao.DbController;
import com.insightsurfface.myword.greendao.WordsBook;
import com.insightsurfface.myword.listener.OnAddClickListener;
import com.insightsurfface.myword.listener.OnListDialogEventListener;
import com.insightsurfface.myword.listener.OnRecycleItemClickListener;
import com.insightsurfface.myword.listener.OnRecycleItemLongClickListener;
import com.insightsurfface.myword.utils.ActivityPoor;
import com.insightsurfface.myword.utils.PermissionUtil;
import com.insightsurfface.myword.utils.SharedPreferencesUtils;
import com.insightsurfface.myword.widget.bar.TopBar;
import com.insightsurfface.myword.widget.dialog.AddBookDialog;
import com.insightsurfface.myword.widget.dialog.EditDialog;
import com.insightsurfface.myword.widget.dialog.EditDialogBuilder;
import com.insightsurfface.myword.widget.dialog.ListDialog;
import com.insightsurfface.myword.widget.dialog.NormalDialog;
import com.insightsurfface.myword.widget.dialog.NormalDialogBuilder;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements MainContract.View {
    private RecyclerView wordsTablesRcv;
    private WordsTablesAdapter adapter;
    private MainContract.Presenter mPresenter;
    private List<WordsBook> mList;
    private String[] selectOptions = {"重置学习进度", "手动添加单词", "删除单词本"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        setPresenter(new MainPresenter(this, this));
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        final View switchMark = findViewById(R.id.switch_mark);
        baseTopBar.hideLeftButton();
        baseTopBar.setTitle(getString(R.string.app_name));
        baseTopBar.setRightBackground(R.drawable.ic_settings);
        baseTopBar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            }

            @Override
            public void onTitleClick() {
                boolean imageSwitch = PermissionUtil.isImageSwitched(MainActivity.this);
                SharedPreferencesUtils.setSharedPreferencesData(MainActivity.this, ShareKeys.IMAGE_SWITCH, !imageSwitch);
                switchMark.setVisibility(PermissionUtil.isImageSwitched(MainActivity.this) ? View.VISIBLE : View.GONE);
            }
        });
        switchMark.setVisibility(PermissionUtil.isImageSwitched(MainActivity.this) ? View.VISIBLE : View.GONE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void showAddBookDialog() {
        AddBookDialog dialog = new AddBookDialog(this);
        dialog.setOnAddClickListener(new AddBookDialog.OnAddBookListener() {
            @Override
            public void onOkClick(boolean isWriteBook, String name, String url) {
                WordsBook wordsBook = new WordsBook();
                wordsBook.setName(name);
                wordsBook.setUrl(url);
                SharedPreferencesUtils.setSharedPreferencesData
                        (MainActivity.this, name + ShareKeys.IS_WRITE_BOOK_KEY, isWriteBook);
                mPresenter.insertBook(wordsBook);
            }
        });
        dialog.show();
    }

    private void showDeleteDialog(final Long id) {
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

    private void showUpdateDialog(final WordsBook wordsBook) {
        new NormalDialogBuilder(this)
                .setTitle("是否更新该单词本?")
                .setOkText("是")
                .setCancelText("否")
                .setTitleBold(true)
                .setOnDialogClickListener(new NormalDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        mPresenter.updateBook(wordsBook);
                    }

                    @Override
                    public void onCancelClick() {

                    }
                })
                .create()
                .show();
    }

    @Override
    public void displayWordsTables(final List<WordsBook> list) {
        //这里如果用final的list 会导致之后无法更新list,所有listeener里的list的值都会是错误的 因为final是又复制了一份一样的list 但之后不会随着元数据的更改而更改
        mList = list;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null == adapter) {
                        adapter = new WordsTablesAdapter(MainActivity.this);
                        adapter.setList(mList);
                        adapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Intent intent = new Intent(MainActivity.this, WordsBookActivity.class);
                                intent.putExtra("bookId", mList.get(position).getId());
                                intent.putExtra("isWriteBook", SharedPreferencesUtils.getBooleanSharedPreferencesData(MainActivity.this, mList.get(position).getName() + ShareKeys.IS_WRITE_BOOK_KEY, false));
                                startActivity(intent);
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
                                showOptionsDialog(position);
                            }
                        });
                        adapter.setOnRefreshClickListener(new OnRecycleItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                showUpdateDialog(mList.get(position));
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
        });
    }

    private void showOptionsDialog(final int bookPosition) {
        ListDialog listDialog = new ListDialog(this);
        listDialog.setOnListDialogEventListener(new OnListDialogEventListener() {
            @Override
            public void onItemClick(String selectedRes, String selectedCodeRes) {

            }

            @Override
            public void onItemClick(String selectedRes) {

            }

            @Override
            public void onItemClick(int position) {
                switch (position) {
                    case 0:
                        mPresenter.resetBook(mList.get(bookPosition).getId());
                        break;
                    case 1:
                        showAddWordsDialog(mList.get(bookPosition));
                        break;
                    case 2:
                        showDeleteDialog(mList.get(bookPosition).getId());
                        break;
                }
            }
        });
        listDialog.show();
        listDialog.setOptionsList(selectOptions);
    }

    private void showAddWordsDialog(final WordsBook wordsBook) {
        EditDialogBuilder builder = new EditDialogBuilder(this);
        builder.setCancelText("cancel")
                .setTitle("add new words")
                .setHint("use , to seperate words")
                .setOkText("confirm")
                .setTitleBold(true)
                .setEditDialogListener(new EditDialog.OnEditDialogClickListener() {
                    @Override
                    public void onOkClick(String result) {
                        if (TextUtils.isEmpty(result)) {
                            baseToast.showToast("empty!");
                        } else {
                            String[] words = result.split(",");

                            if (words.length > 0) {
                                mPresenter.updateBookManually(wordsBook, Arrays.asList(words));
                            } else {
                                baseToast.showToast("empty!");
                            }
                        }
                    }

                    @Override
                    public void onCancelClick() {

                    }
                })
                .create()
                .show();
    }

    @Override
    public void displayMsg(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                baseToast.showToast(msg);
            }
        });
    }

    private void showQuitDialog() {
        new NormalDialogBuilder(this)
                .setTitle("退出生词本?")
                .setOkText("退出")
                .setCancelText("取消")
                .setOnDialogClickListener(new NormalDialog.OnDialogClickListener() {
                    @Override
                    public void onOkClick() {
                        ActivityPoor.finishAllActivity();
                    }

                    @Override
                    public void onCancelClick() {

                    }
                })
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        showQuitDialog();
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
