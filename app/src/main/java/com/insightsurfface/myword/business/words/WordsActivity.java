package com.insightsurfface.myword.business.words;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.adapter.WordsAdapter;
import com.insightsurfface.myword.base.DependencyInjectorIml;
import com.insightsurfface.myword.base.TTSActivity;
import com.insightsurfface.myword.bean.WordsBookBean;
import com.insightsurfface.myword.config.Configure;
import com.insightsurfface.myword.listener.OnRecycleItemClickListener;
import com.insightsurfface.myword.listener.OnRecycleItemLongClickListener;
import com.insightsurfface.myword.utils.VibratorUtil;
import com.insightsurfface.myword.widget.bar.TopBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * /storage/sdcard0/reptile/one-piece
 * <p/>
 * Created by Administrator on 2016/4/4.
 */
public class WordsActivity extends TTSActivity implements WordsContract.View, EasyPermissions.PermissionCallbacks {
    private WordsAdapter adapter;
    private View emptyView;
    private ArrayList<WordsBookBean> wordsList = new ArrayList<WordsBookBean>();
    private int nowPosition = 0;
    private ClipboardManager clip;//复制文本用
    private WordsContract.Presenter mPresenter;
    private RecyclerView wordsRcv;
    private TextView sizeTv;

    @Override
    public void displayWords(ArrayList<WordsBookBean> list) {
        wordsList = list;
        initRec();
        sizeTv.setText(list.size() + "");
    }

    @Override
    public void displayTranslate(int position, String translate) {
        wordsList.get(position).setTranslate(translate);
        adapter.setList(wordsList);
        //关于为什么要传第二个参数详见：https://blog.csdn.net/qq15357971925/article/details/78043332
        adapter.notifyItemChanged(position, "not null");
    }

    @Override
    public void displayKillWord(int position) {
        if (Configure.isPad) {
            text2Speech("shit");
        } else {
            //平板没有转子
            VibratorUtil.Vibrate(WordsActivity.this, 60);
        }
        adapter.remove(position);
        sizeTv.setText(wordsList.size() + "");
    }

    @Override
    public void displayErrorMsg(String msg) {
        baseToast.showToast(msg);
    }

    @Override
    public void setPresenter(WordsContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clip = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        initUI();
        refreshUI();
    }

    @AfterPermissionGranted(Configure.PERMISSION_FILE_REQUST_CODE)
    private void refreshUI() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Already have permission, do the thing
            // ...
            setPresenter(new WordsPresenter(this, new DependencyInjectorIml(), this));
            mPresenter.onViewCreated();
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "我们需要写入/读取权限",
                    Configure.PERMISSION_FILE_REQUST_CODE, perms);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        text2Speech(wordsList.get(nowPosition).getWord());
    }

    private void initUI() {
        emptyView = findViewById(R.id.empty_view);
        wordsRcv = findViewById(R.id.words_rcv);
        sizeTv = findViewById(R.id.size_tv);
        wordsRcv.setLayoutManager
                (new LinearLayoutManager
                        (this, LinearLayoutManager.VERTICAL, false));
        wordsRcv.setFocusable(false);
        wordsRcv.setHasFixedSize(true);
        LayoutAnimationController controller = new LayoutAnimationController(AnimationUtils.loadAnimation(this, R.anim.recycler_load));
        wordsRcv.setLayoutAnimation(controller);
        baseTopBar.setTitle("生词本");
        baseTopBar.setRightBackground(R.drawable.ic_shuffle_card);
        baseTopBar.setOnTopBarClickListener(new TopBar.OnTopBarClickListener() {
            @Override
            public void onLeftClick() {
                finish();
            }

            @Override
            public void onRightClick() {
                Collections.shuffle(wordsList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTitleClick() {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_words;
    }


    private void initRec() {
        try {
            if (null == wordsList || wordsList.size() <= 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
            if (null == adapter) {
                adapter = new WordsAdapter(this);
                adapter.setList(wordsList);
                adapter.setOnRecycleItemClickListener(new OnRecycleItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        mPresenter.killWord(position, wordsList.get(position).getWord());
                    }
                });
                adapter.setOnTranslateItemClickListener(new OnRecycleItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        translation(position);
                    }
                });
                adapter.setOnRecycleItemLongClickListener(new OnRecycleItemLongClickListener() {
                    @Override
                    public void onItemLongClick(int position) {
                        clip.setText(wordsList.get(position).getWord());
                    }
                });
                wordsRcv.setAdapter(adapter);
            } else {
                adapter.setList(wordsList);
                adapter.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void translation(int position) {
        String word = wordsList.get(position).getWord();
        clip.setText(word);
//        if (!SharedPreferencesUtils.getBooleanSharedPreferencesData
//                (this, ShareKeys.CLOSE_TTS, false)) {
        text2Speech(word);
//        }
        if (TextUtils.isEmpty(wordsList.get(position).getTranslate())) {
            mPresenter.translateWord(position, word);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        baseToast.showToast("已获得授权,请继续!");
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        baseToast.showToast("没文件读取/写入授权,你让我怎么加载图片?", true);
    }
}
