package com.wxh.sdk.util;


import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;
import com.wxh.sdk.R;
import com.wxh.sdk.http.HttpPath;


/**
 * ImageUtil
 * <p/>
 * wxh on 2015-09-15 14:09
 * version V1.0
 */
public class ImageUtil {


    private static DisplayImageOptions mCircleDisplayImageOptions;

    /**
     * @return
     * @Desc: TODO
     * @author wxh
     */
    public static DisplayImageOptions SimpleDisplayImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder().resetViewBeforeLoading(false)
                .showStubImage(R.drawable.iv_defalut_image) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.iv_defalut_image) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.iv_defalut_image) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new SimpleBitmapDisplayer()) // 正常显示
                .build();
        return options;
    }

    /**
     * @return
     * @Desc: TODO
     * @author wxh
     */
    public static DisplayImageOptions SimpleDisplayImageOptions(int deafultResID) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().resetViewBeforeLoading(false).showStubImage(deafultResID) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(deafultResID) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(deafultResID) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new SimpleBitmapDisplayer()) // 正常显示
                .build();
        return options;
    }

    public static DisplayImageOptions RoundedDisplayImageOptions(int cornerRadiusPixels) {
        return RoundedDisplayImageOptions(R.drawable.iv_defalut_image, cornerRadiusPixels);
    }

    public static DisplayImageOptions RoundedDisplayImageOptions(int deafultResID, int cornerRadiusPixels) {
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(deafultResID) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(deafultResID) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(deafultResID) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels)) // 设置成圆角图片
                .build();
        return options;
    }

    public static DisplayImageOptions CircleDisplayImageOptions() {
        return CircleDisplayImageOptions(R.drawable.iv_defalut_image);
    }

    public static DisplayImageOptions CircleDisplayImageOptions(int defaultResID) {
        if (mCircleDisplayImageOptions == null) {
            mCircleDisplayImageOptions = new DisplayImageOptions.Builder().showStubImage(defaultResID) // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(defaultResID) // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(defaultResID) // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                    .displayer(new CircleBitmapDisplayer()) // 设置成圆型图片
                    .build();
        }
        return mCircleDisplayImageOptions;
    }

    public static void displayImage(String imageUrl, ImageView imageView) {
        displayImage(imageUrl, imageView, null);
    }

    public static void displayImage(String imageUrl, ImageView imageView, int deafultResID) {
        displayImage(imageUrl, imageView, SimpleDisplayImageOptions(deafultResID));
    }

    public static void displayImage(String imageUrl, final ImageView imageView, DisplayImageOptions options) {

        // if (imageUrl == null || imageUrl.length() == 0) {
        // ImageLoader.getInstance().displayImage("drawable://" +
        // R.drawable.iv_defalut_image, imageView, options);
        // return;
        // }

        if (!TextUtils.isEmpty(imageUrl) && !imageUrl.contains(HttpPath.MAIN))
            imageUrl = imageUrl.replace("220.167.54.41", "172.16.20.220");

        try {
            if (options == null)
                options = SimpleDisplayImageOptions();

            // if (!imageUrl.startsWith("drawable://") &&
            // !imageUrl.startsWith("file://") &&
            // !imageUrl.startsWith("http://")
            // && !imageUrl.startsWith("content://") &&
            // !imageUrl.startsWith("assets://")) {
            // && !imageUrl.startsWith(HttpServicePath.IMAGE_HOST)) {
            // int index = imageUrl.indexOf("/");
            // if (index > -1) {
            // imageUrl = HttpServicePath.IMAGE_HOST +
            // imageUrl.substring(index
            // + 1, imageUrl.length());
            // }
            // }
            // imageView.setTag(imageUrl);
            // ImageLoader.getInstance().loadImage(imageUrl, new
            // SimpleImageLoadingListener() {
            //
            // @Override
            // public void onLoadingComplete(String imageUrl, View view,
            // Bitmap
            // loadedImage) {
            // super.onLoadingComplete(imageUrl, view, loadedImage);
            // if (imageUrl.equals(imageView.getTag())) {
            // imageView.setImageBitmap(loadedImage);
            // }
            // }
            // });
            ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
            // ImageLoader.getInstance().displayImage(imageUrl, imageView,
            // options);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class CircleBitmapDisplayer implements BitmapDisplayer {

        protected final int margin;

        public CircleBitmapDisplayer() {
            this(0);
        }

        public CircleBitmapDisplayer(int margin) {
            this.margin = margin;
        }

        @Override
        public void display(Bitmap bitmap, ImageAware imageAware,
                            LoadedFrom loadedFrom) {
            if (!(imageAware instanceof ImageViewAware)) {
                throw new IllegalArgumentException(
                        "ImageAware should wrap ImageView. ImageViewAware is expected.");
            }

            imageAware.setImageDrawable(new CircleDrawable(bitmap, margin));
        }

        class CircleDrawable extends Drawable {
            public static final String TAG = "CircleDrawable";

            protected final Paint paint;

            protected final int margin;
            protected final BitmapShader bitmapShader;
            protected float radius;
            protected Bitmap oBitmap;// 原图

            public CircleDrawable(Bitmap bitmap) {
                this(bitmap, 0);
            }

            public CircleDrawable(Bitmap bitmap, int margin) {
                this.margin = margin;
                this.oBitmap = bitmap;
                bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP,
                        Shader.TileMode.CLAMP);
                paint = new Paint();
                paint.setAntiAlias(true);
                paint.setShader(bitmapShader);
            }

            @Override
            protected void onBoundsChange(Rect bounds) {
                super.onBoundsChange(bounds);
                computeBitmapShaderSize();
                computeRadius();

            }

            @Override
            public void draw(Canvas canvas) {
                Rect bounds = getBounds();// 画一个圆圈
                canvas.drawCircle(bounds.width() / 2F, bounds.height() / 2F,
                        radius, paint);
            }

            @Override
            public int getOpacity() {
                return PixelFormat.TRANSLUCENT;
            }

            @Override
            public void setAlpha(int alpha) {
                paint.setAlpha(alpha);
            }

            @Override
            public void setColorFilter(ColorFilter cf) {
                paint.setColorFilter(cf);
            }

            /**
             * 计算Bitmap shader 大小
             */
            public void computeBitmapShaderSize() {
                Rect bounds = getBounds();
                if (bounds == null)
                    return;
                // 选择缩放比较多的缩放，这样图片就不会有图片拉伸失衡
                Matrix matrix = new Matrix();
                float scaleX = bounds.width() / (float) oBitmap.getWidth();
                float scaleY = bounds.height() / (float) oBitmap.getHeight();
                float scale = scaleX > scaleY ? scaleX : scaleY;
                matrix.postScale(scale, scale);
                bitmapShader.setLocalMatrix(matrix);
            }

            /**
             * 计算半径的大小
             */
            public void computeRadius() {
                Rect bounds = getBounds();
                radius = bounds.width() < bounds.height() ? bounds.width() / 2F
                        - margin : bounds.height() / 2F - margin;
            }
        }
    }

}

