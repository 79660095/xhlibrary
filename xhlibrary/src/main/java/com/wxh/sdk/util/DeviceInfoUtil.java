package com.wxh.sdk.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 获取设备信息
 *
 */
public class DeviceInfoUtil {

/*    public static void getDeviceInfo(Activity context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String imei = tm.getDeviceId();
        String imsi = tm.getSubscriberId();

        // String app_name = GoGoApp.getInstance().getPackageName();
        String app_version = getVersion(context) + "";
        String device_modle = android.os.Build.MODEL;
        // int os_type = RelUtil.OS_TYPE_ANDROID;
        String os_version = android.os.Build.VERSION.RELEASE;
        String carrier = imsi;
        String uuid = NetworkTool.getMac(context);
        String access_model = NetworkTool.NetworkType(context) + "";
        int width = ViewTool.getWidth(context);
        int height = ViewTool.getHeight(context);

        // DeviceInfoDomain domain = new DeviceInfoDomain(app_name, app_version,
        // device_modle, os_type, os_version, carrier, uuid, access_model,
        // width, height);
        //
        // return domain;
    }*/

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersion(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "V1.0";
        }
    }

    /**
     * 获取应用包名
     *
     * @return 当前应用的包名称
     */
    public static String getPackageName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return "V1.0";
        }
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public static String getCarrierByEMSI(String emsi) {

        if (TextUtils.isEmpty(emsi)) {
            return null;
        }
        if (emsi.startsWith("46000") || emsi.startsWith("46002")) {
            return "中国移动";
        } else if (emsi.startsWith("46001")) {
            return "中国联通";
        } else if (emsi.startsWith("46003")) {
            return "中国电信";
        }

        return null;
    }
}