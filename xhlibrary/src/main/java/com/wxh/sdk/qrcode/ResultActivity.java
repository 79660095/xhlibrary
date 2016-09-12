package com.wxh.sdk.qrcode;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxh.sdk.R;
import com.wxh.sdk.qrcode.decode.DecodeThread;
import com.wxh.sdk.qrcode.decode.DecodeUtils;
import com.wxh.sdk.qrcode.utils.CommonUtils;
import com.wxh.sdk.ui.base.BaseActivity;


/**
 * Author:  Tau.Chen
 * Email:   1076559197@qq.com | tauchen1990@gmail.com
 * Date:    15/7/24
 * Description:
 */
public class ResultActivity extends BaseActivity {

    public static final String BUNDLE_KEY_SCAN_RESULT = "BUNDLE_KEY_SCAN_RESULT";

    ImageView result_image;
    TextView result_type;
    TextView result_content;

    private Bitmap mBitmap;
    private int mDecodeMode;
    private String mResultStr;
    private String mDecodeTime;

    private void getBundleExtras(Bundle extras) {
        if (extras != null) {
            byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                mBitmap = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                mBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }

            mResultStr = extras.getString(BUNDLE_KEY_SCAN_RESULT);
            mDecodeMode = extras.getInt(DecodeThread.DECODE_MODE);
            mDecodeTime = extras.getString(DecodeThread.DECODE_TIME);
        }
    }

    private void initViewsAndEvents() {
        setTitle("扫描结果");

        StringBuilder sb = new StringBuilder();
        sb.append("扫描方式:\t\t");
        if (mDecodeMode == DecodeUtils.DECODE_MODE_ZBAR) {
            sb.append("ZBar扫描");
        } else if (mDecodeMode == DecodeUtils.DECODE_MODE_ZXING) {
            sb.append("ZXing扫描");
        }

        if (!CommonUtils.isEmpty(mDecodeTime)) {
            sb.append("\n\n扫描时间:\t\t");
            sb.append(mDecodeTime);
        }
        sb.append("\n\n扫描结果:");

        result_type.setText(sb.toString());
        result_content.setText(mResultStr);

        if (null != mBitmap) {
            result_image.setImageBitmap(mBitmap);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mBitmap && !mBitmap.isRecycled()) {
            mBitmap.recycle();
            mBitmap = null;
        }
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_result);
    }

    @Override
    protected boolean intentData() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        return true;
    }

    @Override
    protected void initUI() {
        initViewsAndEvents();
    }

    @Override
    protected void loadData() {

    }
}
