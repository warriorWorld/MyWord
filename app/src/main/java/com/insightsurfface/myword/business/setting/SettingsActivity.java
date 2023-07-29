package com.insightsurfface.myword.business.setting;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.config.ShareKeys;
import com.insightsurfface.myword.utils.BaseParameterUtil;
import com.insightsurfface.myword.utils.SharedPreferencesUtils;
import com.insightsurfface.myword.widget.dialog.EditDialog;
import com.insightsurfface.myword.widget.dialog.EditDialogBuilder;
import com.insightsurfface.myword.widget.dialog.GestureDialog;
import com.insightsurfface.myword.widget.gesture.GestureLockViewGroup;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iconIv;
    private TextView versionTv;
    private CheckBox closeSoundCb;
    private CheckBox clickCopyCb;
    private RelativeLayout deleteWordRl;
    private TextView deleteWordTv;
    private RelativeLayout reemergenceGapRl;
    private TextView reemergenceGapTv;
    private CheckBox openPremiumCb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
        refreshUI();
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2020-04-28 14:19:25 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void initUI() {
        iconIv = (ImageView) findViewById(R.id.icon_iv);
        versionTv = (TextView) findViewById(R.id.version_tv);
        closeSoundCb = (CheckBox) findViewById(R.id.close_sound_cb);
        clickCopyCb = (CheckBox) findViewById(R.id.click_copy_cb);
        deleteWordRl = (RelativeLayout) findViewById(R.id.delete_word_rl);
        deleteWordTv = (TextView) findViewById(R.id.delete_word_tv);
        reemergenceGapRl = (RelativeLayout) findViewById(R.id.reemergence_gap_rl);
        reemergenceGapTv = (TextView) findViewById(R.id.reemergence_gap_tv);

        closeSoundCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.setSharedPreferencesData
                        (SettingsActivity.this, ShareKeys.CLOSE_SOUND_KEY, isChecked);
            }
        });
        clickCopyCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.setSharedPreferencesData
                        (SettingsActivity.this, ShareKeys.CLICK_COPY_KEY, isChecked);
            }
        });
        openPremiumCb = findViewById(R.id.open_premium_cb);
        openPremiumCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.setSharedPreferencesData
                        (SettingsActivity.this, ShareKeys.OPEN_PREMIUM_KEY, isChecked);
            }
        });
        openPremiumCb.setChecked
                (SharedPreferencesUtils.getBooleanSharedPreferencesData(this,
                        ShareKeys.OPEN_PREMIUM_KEY, false));
        reemergenceGapRl.setOnClickListener(this);
        deleteWordRl.setOnClickListener(this);
        findViewById(R.id.lock_rl).setOnClickListener(this);
    }

    private void refreshUI() {
        versionTv.setText(BaseParameterUtil.getInstance().getAppVersionName(this));
        closeSoundCb.setChecked
                (SharedPreferencesUtils.getBooleanSharedPreferencesData(SettingsActivity.this,
                        ShareKeys.CLOSE_SOUND_KEY, false));
        clickCopyCb.setChecked
                (SharedPreferencesUtils.getBooleanSharedPreferencesData(SettingsActivity.this,
                        ShareKeys.CLICK_COPY_KEY, false));
        int reemergenceGap = SharedPreferencesUtils.getIntSharedPreferencesData(this, ShareKeys.REEMERGENCE_GAP_KEY, 3);
        reemergenceGapTv.setText("已选择\"认识\"的单词" + reemergenceGap + "小时后再次出现");
        int deleteLimit = SharedPreferencesUtils.getIntSharedPreferencesData(this, ShareKeys.DELETE_LIMIT_KEY, 3);
        deleteWordTv.setText("点击" + deleteLimit + "次\"认识\"后直接删除单词");
    }

    private void showGestureDialog() {
        final GestureDialog gestureDialog = new GestureDialog(this);
        gestureDialog.show();
        gestureDialog.setOnGestureLockViewListener(new GestureLockViewGroup.OnGestureLockViewListener() {
            @Override
            public void onBlockSelected(int cId) {

            }

            @Override
            public void onGestureEvent(boolean matched) {

            }

            @Override
            public void onGestureEvent(String choose) {
                SharedPreferencesUtils.setSharedPreferencesData(SettingsActivity.this, ShareKeys.IS_MASTER, false);
                SharedPreferencesUtils.setSharedPreferencesData(SettingsActivity.this, ShareKeys.IS_CREATOR, false);
                if (choose.equals("7485")) {
                    SharedPreferencesUtils.setSharedPreferencesData(SettingsActivity.this, ShareKeys.IS_CREATOR, true);
                } else if (choose.equals("3427")) {
                    SharedPreferencesUtils.setSharedPreferencesData(SettingsActivity.this, ShareKeys.IS_MASTER, true);
                }
                gestureDialog.dismiss();
            }

            @Override
            public void onUnmatchedExceedBoundary() {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reemergence_gap_rl:
                new EditDialogBuilder(SettingsActivity.this)
                        .setTitle("设置已选择\"认识\"的单词再次出现的时间间隔")
                        .setHint("请输入时间间隔,单位小时.")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setOkText("确定")
                        .setCancelText("取消")
                        .setTitleLeft(true)
                        .setTitleBold(true)
                        .setEditDialogListener(new EditDialog.OnEditDialogClickListener() {
                            @Override
                            public void onOkClick(String result) {
                                SharedPreferencesUtils.setSharedPreferencesData
                                        (SettingsActivity.this, ShareKeys.REEMERGENCE_GAP_KEY, Integer.valueOf(result));
                                refreshUI();
                            }

                            @Override
                            public void onCancelClick() {

                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.delete_word_rl:
                new EditDialogBuilder(SettingsActivity.this)
                        .setTitle("设置点击多少次\"认识\"后直接删除单词")
                        .setHint("请输入次数")
                        .setInputType(InputType.TYPE_CLASS_NUMBER)
                        .setOkText("确定")
                        .setCancelText("取消")
                        .setTitleLeft(true)
                        .setTitleBold(true)
                        .setEditDialogListener(new EditDialog.OnEditDialogClickListener() {
                            @Override
                            public void onOkClick(String result) {
                                SharedPreferencesUtils.setSharedPreferencesData
                                        (SettingsActivity.this, ShareKeys.DELETE_LIMIT_KEY, Integer.valueOf(result));
                                refreshUI();
                            }

                            @Override
                            public void onCancelClick() {

                            }
                        })
                        .create()
                        .show();
                break;
            case R.id.lock_rl:
                showGestureDialog();
                break;
        }
    }
}
