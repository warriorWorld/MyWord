package com.insightsurfface.myword.business.setting;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.base.BaseActivity;
import com.insightsurfface.myword.config.ShareKeys;
import com.insightsurfface.myword.utils.SharedPreferencesUtils;

public class SettingsActivity extends BaseActivity {
    private ImageView iconIv;
    private TextView versionTv;
    private CheckBox closeSoundCb;
    private CheckBox clickCopyCb;
    private RelativeLayout deleteWordRl;
    private TextView deleteWordTv;
    private RelativeLayout reemergenceGapRl;
    private TextView reemergenceGapTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
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
        closeSoundCb.setChecked
                (SharedPreferencesUtils.getBooleanSharedPreferencesData(SettingsActivity.this,
                        ShareKeys.CLOSE_SOUND_KEY, false));
        clickCopyCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferencesUtils.setSharedPreferencesData
                        (SettingsActivity.this, ShareKeys.CLICK_COPY_KEY, isChecked);
            }
        });
        clickCopyCb.setChecked
                (SharedPreferencesUtils.getBooleanSharedPreferencesData(SettingsActivity.this,
                        ShareKeys.CLICK_COPY_KEY, false));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_settings;
    }
}
