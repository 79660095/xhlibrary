package com.wxh.sdk.qrcode.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.wxh.sdk.R;
import com.wxh.sdk.qrcode.decode.DecodeThread;
import com.wxh.sdk.qrcode.decode.DecodeUtils;
import com.wxh.sdk.ui.base.BaseActivity;

/**
 * 二维码 条码 扫描结果
 * 继承此类即可
 *
 */

public abstract class BaseQrResultActivity extends BaseActivity {
    public static final String BUNDLE_KEY_SCAN_RESULT = "BUNDLE_KEY_SCAN_RESULT";
    private Bitmap mBitmap;

    private int mDecodeMode;
    /**
     * 扫描结果
     */
    protected String qResultData;
    /**
     * 扫描时间
     */
    protected String qDecodeTime;

    private void getBundleExtras(Bundle extras) {
        if (extras != null) {
            byte[] compressedBitmap = extras.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                mBitmap = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                mBitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
            }

            qResultData = extras.getString(BUNDLE_KEY_SCAN_RESULT);
            mDecodeMode = extras.getInt(DecodeThread.DECODE_MODE);
            qDecodeTime = extras.getString(DecodeThread.DECODE_TIME);
        }
    }

    private void initViewsAndEvents() {
     /*   StringBuilder sb = new StringBuilder();
        sb.append("扫描方式:\t\t");
        if (mDecodeMode == DecodeUtils.DECODE_MODE_ZBAR) {
            sb.append("ZBar扫描");
            qType="ZBar扫描";
        } else if (mDecodeMode == DecodeUtils.DECODE_MODE_ZXING) {
            sb.append("ZXing扫描");
            qType="ZXing扫描";
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
        }*/
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
    protected boolean intentData() {
        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }
        return true;
    }

/*
    @Override
    protected void initUI() {
        initViewsAndEvents();
    }
*/



}
