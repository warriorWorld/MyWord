package com.insightsurfface.myword.widget.imageview;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.insightsurfface.myword.R;
import com.insightsurfface.myword.listener.OnImgSizeListener;
import com.insightsurfface.myword.utils.DisplayUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class WrapHeightImageView extends ImageView {
    private Context mContext;
    private Bitmap mBitmap;
    private int position;
    /**
     * Handler处理类
     */
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    post(new Runnable() {
                        @Override
                        public void run() {
                            if (mBitmap == null) {
                                setImageResource(R.drawable.ic_refresh_green);
                                return;
                            }
                            setImageBitmap(mBitmap);
                        }
                    });
                    break;
            }
        }
    };

    public WrapHeightImageView(Context context) {
        super(context);
        init(context);
    }

    public WrapHeightImageView(Context context, AttributeSet attr) {
        super(context, attr);
        init(context);
    }

    public WrapHeightImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public void setBitmap(final Bitmap bm, final int width, final int height) {
        post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams vgl = getLayoutParams();
                if (bm == null) {
                    setVisibility(GONE);
                    return;
                } else {
                    setVisibility(VISIBLE);
                }
                vgl.width = width;
                vgl.height = height;

                //设置图片充满ImageView控件
                setScaleType(ScaleType.FIT_XY);
                //等比例缩放
                setAdjustViewBounds(true);
                setLayoutParams(vgl);
                setImageBitmap(bm);
            }
        });
    }

    public void setBitmap(final Bitmap bm, final OnImgSizeListener listener) {
        post(new Runnable() {
            @Override
            public void run() {
                ViewGroup.LayoutParams vgl = getLayoutParams();
                if (bm == null) {
                    setVisibility(GONE);
                    return;
                } else {
                    setVisibility(VISIBLE);
                }
                //获取bitmap的宽度
                float bitWidth = bm.getWidth();
                //获取bitmap的宽度
                float bithight = bm.getHeight();

                //计算出图片的宽高比，然后按照图片的比列去缩放图片
                float bitScalew = bitWidth / bithight;
                float maxWidth = DisplayUtil.getScreenWidth(mContext);
                if (maxWidth <= bitWidth) {
                    vgl.width = (int) maxWidth;
                    vgl.height = (int) (maxWidth / bitScalew);
                } else {
                    vgl.width = (int) bitWidth;
                    vgl.height = (int) bithight;
                }

                //设置图片充满ImageView控件
                setScaleType(ScaleType.FIT_XY);
                //等比例缩放
                setAdjustViewBounds(true);
                setLayoutParams(vgl);
                setImageBitmap(bm);
                if (null != listener) {
                    listener.onSized(vgl.width, vgl.height);
                }
            }
        });
    }

    public void setImgUrl(final String url, final DisplayImageOptions options) {
        setImgUrl(url, options, -1);
    }

    public void setImgUrl(final String url, final DisplayImageOptions options, int position) {
        this.position = position;
        setImageResource(R.drawable.ic_refresh_green);
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBitmap = ImageLoader.getInstance().loadImageSync(url, options);
                Message msg = Message.obtain();
                msg.what = 0;
                mHandler.sendMessage(msg);
            }
        }).start();
    }
}