package com.insightsurfface.myword.widget.dialog;/**
 * Created by Administrator on 2016/11/4.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.insightsurfface.myword.R;


/**
 * 作者：苏航 on 2016/11/4 11:08
 * 邮箱：772192594@qq.com
 */
public class AddBookDialog extends Dialog {
    private Context context;
    private Button okBtn;
    private Button cancelBtn;
    private EditText nameEt, urlEt;

    private OnAddBookListener mOnAddClickListener;

    public AddBookDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutId());
        init();

        Window window = this.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        WindowManager wm = ((Activity) context).getWindowManager();
        Display d = wm.getDefaultDisplay();
        // lp.height = (int) (d.getHeight() * 0.4);
        lp.width = (int) (d.getWidth() * 0.9);
        // window.setGravity(Gravity.LEFT | Gravity.TOP);
        window.setGravity(Gravity.CENTER);
//        window.getDecorView().setPadding(0, 0, 0, 0);
        // lp.x = 100;
        // lp.y = 100;
        // lp.height = 30;
        // lp.width = 20;
        window.setAttributes(lp);
    }

    protected int getLayoutId() {
        return R.layout.dialog_add_book;
    }

    private void init() {
        okBtn = (Button) findViewById(R.id.ok_btn);
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        nameEt = findViewById(R.id.name_et);
        urlEt = findViewById(R.id.url_et);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                String name = nameEt.getText().toString().replaceAll(" ", "");
                String url = urlEt.getText().toString().replaceAll(" ", "");
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(url)) {
                    return;
                }
                if (null != mOnAddClickListener) {
                    mOnAddClickListener.onOkClick(name, url);
                }
            }
        });
    }

    public void setOnAddClickListener(OnAddBookListener onAddClickListener) {
        mOnAddClickListener = onAddClickListener;
    }

    public interface OnAddBookListener {
        void onOkClick(String name, String url);
    }
}
