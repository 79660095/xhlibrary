package com.wxh.sdk.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.wxh.sdk.android.XHApp;

/**
 * 文件操作类
 *
 * @author Visual
 *
 */
public class FileUtil {

    /**
     * 获取sdcard的根目录
     *
     * @return 返回sd卡的根目录
     */
    public final static String FILE_DIR = "lenMo";
    public final static String HEAD_ICON_DIR = "HeadIcon";
    public final static String FILE_SEPARATOR = "/";
    public final static String FILE_DOUHAO = ",";
    private final static String FILE_BITMAP_CACHE_DIR = "iShare/bitmapCache/";

    /**
     * 判断sd卡的状态，是否存在，可写
     *
     * @return
     */
    public static String getExternalStoragePath() {
        // 获取sdcard的状态
        String state = Environment.getExternalStorageState();
        // 判断sdcard是否存在并且是否可用
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (Environment.getExternalStorageDirectory().canWrite()) {
                return Environment.getExternalStorageDirectory().getPath();
            }
        }
        return null;
    }

    /**
     * 获取sd路径
     *
     * @return
     */
    public static String getAppBasePath() {
        String sdcardPath = getExternalStoragePath();
        if (sdcardPath == null) {
            return null;
        }
        String basePath = sdcardPath + FileUtil.FILE_SEPARATOR
                + FileUtil.FILE_DIR + FileUtil.FILE_SEPARATOR;
        File baseDir = new File(basePath);
        if (!baseDir.exists()) {
            baseDir.mkdir();
        }
        return basePath;
    }

    public static File getBitmapCacheFile() {
        File file = null;
        String str = getExternalStoragePath();
        if (str == null) {
            file = XHApp.context.getCacheDir();
            return file;
        }
        String path = str + "/" + FILE_BITMAP_CACHE_DIR;
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     *
     * @return
     */
    public static String getHeadPath() {
        String appPath = getAppBasePath();
        String headPath = appPath + HEAD_ICON_DIR + FILE_SEPARATOR;
        File headIconFile = new File(headPath);

        if (!headIconFile.exists()) {
            headIconFile.mkdir();
        }
        return headPath;
    }

    /**
     * 从filePath加载图片
     *
     * @param filePath
     *            图片路径
     * @return 返回bitmap
     */
    public static Bitmap loadPicFromFilePath(String filePath) {
        Bitmap bitmap = null;
        BufferedInputStream buf;
        try {
            buf = new BufferedInputStream(new FileInputStream(filePath));
            bitmap = BitmapFactory.decodeStream(buf);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     *
     * @param path
     * @return
     */
    public static byte[] getBytesFromPath(String path) {
        FileInputStream inputStream = null;
        byte[] bytes = null;
        try {
            inputStream = new FileInputStream(path);
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**
     * 保存压缩之后的图片
     *
     * @param path
     * @param bitmap
     */
    public static void saveBitmap(String path, Bitmap bitmap) {
        FileOutputStream outputStream = null;
        try {
            File file = new File(path);
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存压缩之后的图片
     *
     * @param
     * @param bitmap
     */
    public static void saveBitmap(File file, Bitmap bitmap) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.flush();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public static void saveBitmap(String picPath, byte[] bytes) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(picPath);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 根据路径判断图片是否存在
     *
     * @param picPath
     *            图片的路径
     * @return 存在：true，不存在：false
     */
    public static boolean isBitmapExist(String picPath) {
        File file = new File(picPath);

        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 根据图片的url 得到图片的路径
     *
     * @param picUrl
     *            传入的图片url
     * @return 返回图片的路径
     */
    public static String getPicPath(String picUrl) {
        String[] str = picUrl.split(FileUtil.FILE_SEPARATOR);
        String picPath = FileUtil.getAppBasePath() + str[str.length - 1];
        return picPath;
    }

    public static String getTakePhotoPath(String path) {
        File file = new File(getRootFilePath(path));
        if (!file.exists()) {
            file.mkdir();
        }
        return getRootFilePath(path) + File.separator + "takephoto.jpg";
    }

    public static String getRootFilePath(String path) {
        StringBuffer stbPath = new StringBuffer();
        stbPath.append(Environment.getExternalStorageDirectory().getPath());
        stbPath.append(File.separator);
        stbPath.append(path);
        stbPath.append(File.separator);
        return stbPath.toString();
    }

    public static String convert2Save(final String originalFile,
                                      final String path) throws IOException, FileNotFoundException {

        // 获取当前系统时间
        Calendar cl = Calendar.getInstance();
        cl.setTime(Calendar.getInstance().getTime());
        long milliseconds = cl.getTimeInMillis();
        // 当前系统时间作为文件名
        String fileName = String.valueOf(milliseconds) + ".jpg";
        // 保存图片
        // 创建文件
        File file = new File(path, fileName);
        file.createNewFile();

        Bitmap bm = getSmallBitmap(originalFile);

        FileOutputStream fos = new FileOutputStream(file);

        bm.compress(Bitmap.CompressFormat.JPEG, 40, fos);
        // 关闭输出流
        fos.flush();
        fos.close();
        return file.getAbsolutePath();

    }

    /**
     * 根据路径获得突破并压缩返回bitmap用于显示
     *
     * @param
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

}
