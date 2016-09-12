
package com.wxh.sdk.qrcode;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.wxh.sdk.R;
import com.wxh.sdk.qrcode.camera.CameraManager;
import com.wxh.sdk.qrcode.decode.DecodeUtils;
import com.wxh.sdk.qrcode.utils.BeepManager;
import com.wxh.sdk.qrcode.utils.CommonUtils;
import com.wxh.sdk.qrcode.utils.InactivityTimer;
import com.wxh.sdk.qrcode.utils.ViewHelper;
import com.wxh.sdk.ui.base.BaseActivity;
import com.wxh.sdk.util.StringUtil;
import com.wxh.sdk.view.AlertDialog;

import java.io.IOException;

import static com.wxh.sdk.R.id.capture_mode_barcode;



public class CaptureActivity extends BaseActivity implements SurfaceHolder.Callback {

    public static final int IMAGE_PICKER_REQUEST_CODE = 100;

    SurfaceView capture_preview;
    ImageView capture_error_mask;
    ImageView capture_scan_mask;
    FrameLayout capture_crop_view;
    RadioGroup capture_mode_group;
    RelativeLayout capture_container;
    TextView capture_light_btn;//闪光灯开关按钮
    private boolean isTorchOn = false;// 闪光灯开关判断


    private CameraManager cameraManager;
    private CaptureActivityHandler handler;

    private boolean hasSurface;

    private InactivityTimer mInactivityTimer;
    private BeepManager mBeepManager;

    private int mQrcodeCropWidth = 0;
    private int mQrcodeCropHeight = 0;
    private int mBarcodeCropWidth = 0;
    private int mBarcodeCropHeight = 0;
    private String TAG;//不传为扫描二维码  传任意为条码
    private ObjectAnimator mScanMaskObjectAnimator = null;

    private Rect cropRect;
    private int dataMode = DecodeUtils.DECODE_DATA_MODE_QRCODE;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_capture);
    }

    @Override
    protected boolean intentData() {
        Intent it=getIntent();
        try {
            TAG=it.getStringExtra("TAG");
        }catch (Exception e){
           Logger.i(e.toString());
        }
        return true;
    }

    @Override
    protected void initUI() {
        initTitle("二维码扫描");
        capture_light_btn= (TextView) findViewById(R.id.tv_right_menu);
        initViewsAndEvents();
        if(!StringUtil.isEmpty(TAG))
            barCode1();
        else
            qrCode1();
    }

    @Override
    protected void loadData() {

    }


    protected void initViewsAndEvents() {
        hasSurface = false;
        mInactivityTimer = new InactivityTimer(this);
        mBeepManager = new BeepManager(this);

        initCropViewAnimator();
        capture_light_btn.setVisibility(View.VISIBLE);
        capture_light_btn.setText("开灯");
        capture_light_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTorchOn) {
                    isTorchOn = false;
                    capture_light_btn.setText("开灯");
                    cameraManager.setTorch(false);
                } else {
                    isTorchOn = true;
                    capture_light_btn.setText("关灯");
                    cameraManager.setTorch(true);
                }
            }
        });

        capture_mode_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == capture_mode_barcode) {
                   barCode1();
                } else if (checkedId == R.id.capture_mode_qrcode) {
                  qrCode1();
                }
            }
        });
    }


    /**
     * 二维码
     */
    public void qrCode1(){
        PropertyValuesHolder bar2qrWidthVH = PropertyValuesHolder.ofFloat("width",
                1.0f, (float) mQrcodeCropWidth / mBarcodeCropWidth);
        PropertyValuesHolder bar2qrHeightVH = PropertyValuesHolder.ofFloat("height",
                1.0f, (float) mQrcodeCropHeight / mBarcodeCropHeight);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(bar2qrWidthVH, bar2qrHeightVH);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float fractionW = (Float) animation.getAnimatedValue("width");
                Float fractionH = (Float) animation.getAnimatedValue("height");

                RelativeLayout.LayoutParams parentLayoutParams = (RelativeLayout.LayoutParams) capture_crop_view.getLayoutParams();
                parentLayoutParams.width = (int) (mBarcodeCropWidth * fractionW);
                parentLayoutParams.height = (int) (mBarcodeCropHeight * fractionH);
                capture_crop_view.setLayoutParams(parentLayoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initCrop();
                setDataMode(DecodeUtils.DECODE_DATA_MODE_QRCODE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }

    /**
     * 条码
     */
    public void barCode1(){
        PropertyValuesHolder qr2barWidthVH = PropertyValuesHolder.ofFloat("width",
                1.0f, (float) mBarcodeCropWidth / mQrcodeCropWidth);
        PropertyValuesHolder qr2barHeightVH = PropertyValuesHolder.ofFloat("height",
                1.0f, (float) mBarcodeCropHeight / mQrcodeCropHeight);
        ValueAnimator valueAnimator = ValueAnimator.ofPropertyValuesHolder(qr2barWidthVH, qr2barHeightVH);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float fractionW = (Float) animation.getAnimatedValue("width");
                Float fractionH = (Float) animation.getAnimatedValue("height");

                RelativeLayout.LayoutParams parentLayoutParams = (RelativeLayout.LayoutParams) capture_crop_view.getLayoutParams();
                parentLayoutParams.width = (int) (mQrcodeCropWidth * fractionW);
                parentLayoutParams.height = (int) (mQrcodeCropHeight * fractionH);
                capture_crop_view.setLayoutParams(parentLayoutParams);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                initCrop();
                setDataMode(DecodeUtils.DECODE_DATA_MODE_BARCODE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();


    }


    private void initCropViewAnimator() {
        mQrcodeCropWidth = getResources().getDimensionPixelSize(R.dimen.qrcode_crop_width);
        mQrcodeCropHeight = getResources().getDimensionPixelSize(R.dimen.qrcode_crop_height);

        mBarcodeCropWidth = getResources().getDimensionPixelSize(R.dimen.barcode_crop_width);
        mBarcodeCropHeight = getResources().getDimensionPixelSize(R.dimen.barcode_crop_height);
    }


    public Handler getHandler() {
        return handler;
    }

    public CameraManager getCameraManager() {
        return cameraManager;
    }

    public void initCrop() {
        int cameraWidth = cameraManager.getCameraResolution().y;
        int cameraHeight = cameraManager.getCameraResolution().x;

        int[] location = new int[2];
        capture_crop_view.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1];

        int cropWidth = capture_crop_view.getWidth();
        int cropHeight = capture_crop_view.getHeight();

        int containerWidth = capture_container.getWidth();
        int containerHeight = capture_container.getHeight();

        int x = cropLeft * cameraWidth / containerWidth;
        int y = cropTop * cameraHeight / containerHeight;

        int width = cropWidth * cameraWidth / containerWidth;
        int height = cropHeight * cameraHeight / containerHeight;

        setCropRect(new Rect(x, y, width + x, height + y));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // CameraManager must be initialized here, not in onCreate(). This is necessary because we don't
        // want to open the camera driver and measure the screen size if we're going to show the help on
        // first launch. That led to bugs where the scanning rectangle was the wrong size and partially
        // off screen.
        cameraManager = new CameraManager(getApplication());

        handler = null;

        if (hasSurface) {
            // The activity was paused but not stopped, so the surface still exists. Therefore
            // surfaceCreated() won't be called, so init the camera here.
            initCamera(capture_preview.getHolder());
        } else {
            // Install the callback and wait for surfaceCreated() to init the camera.
            capture_preview.getHolder().addCallback(this);
        }

        mInactivityTimer.onResume();
    }

    @Override
    protected void onPause() {
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }

        mBeepManager.close();
        mInactivityTimer.onPause();
        cameraManager.closeDriver();

        if (!hasSurface) {
            capture_preview.getHolder().removeCallback(this);
        }

        if (null != mScanMaskObjectAnimator && mScanMaskObjectAnimator.isStarted()) {
            mScanMaskObjectAnimator.cancel();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mInactivityTimer.shutdown();
        super.onDestroy();
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (holder == null) {
            Logger.e("*** WARNING *** surfaceCreated() gave us a null surface!");
        }
        if (!hasSurface) {
            hasSurface = true;
//            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera(holder);
    }

    /**
     * A valid barcode has been found, so give an indication of success and show the results.
     */
    public void handleDecode(String result, Bundle bundle) {
        mInactivityTimer.onActivity();
        mBeepManager.playBeepSoundAndVibrate();

        if (!CommonUtils.isEmpty(result) && CommonUtils.isUrl(result)) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(result));
            startActivity(intent);
        } else {
            bundle.putString(ResultActivity.BUNDLE_KEY_SCAN_RESULT, result);
            readyGo(ResultActivity.class, bundle);
        }
    }

    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    private void onCameraPreviewSuccess() {
        initCrop();
        capture_error_mask.setVisibility(View.GONE);

        ViewHelper.setPivotX(capture_scan_mask, 0.0f);
        ViewHelper.setPivotY(capture_scan_mask, 0.0f);

        mScanMaskObjectAnimator = ObjectAnimator.ofFloat(capture_scan_mask, "scaleY", 0.0f, 1.0f);
        mScanMaskObjectAnimator.setDuration(2000);
        mScanMaskObjectAnimator.setInterpolator(new DecelerateInterpolator());
        mScanMaskObjectAnimator.setRepeatCount(-1);
        mScanMaskObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        mScanMaskObjectAnimator.start();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        if (surfaceHolder == null) {
            throw new IllegalStateException("No SurfaceHolder provided");
        }
        if (cameraManager.isOpen()) {
            Logger.w("initCamera() while already open -- late SurfaceView callback?");
            return;
        }
        try {
            cameraManager.openDriver(surfaceHolder);
            // Creating the handler starts the preview, which can also throw a RuntimeException.
            if (handler == null) {
                handler = new CaptureActivityHandler(this, cameraManager);
            }

            onCameraPreviewSuccess();
        } catch (IOException ioe) {
            Logger.w(ioe.toString());
            displayFrameworkBugMessageAndExit();
        } catch (RuntimeException e) {
            // Barcode Scanner has seen crashes in the wild of this variety:
            // java.?lang.?RuntimeException: Fail to connect to camera service
            Logger.w("Unexpected error initializing camera", e);
            displayFrameworkBugMessageAndExit();
        }

    }

    private void displayFrameworkBugMessageAndExit() {
        capture_error_mask.setVisibility(View.VISIBLE);
        new AlertDialog(this).builder().setCancelable(false).setTitle("标题")
                .setMsg("打开相机失败,请确保允许应用使用相机权限后重试!")
                .setPositiveButton("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      finish();
                    }
                }).show();
    }

    public Rect getCropRect() {
        return cropRect;
    }

    public void setCropRect(Rect cropRect) {
        this.cropRect = cropRect;
    }

    public int getDataMode() {
        return dataMode;
    }

    public void setDataMode(int dataMode) {
        this.dataMode = dataMode;
    }


}
