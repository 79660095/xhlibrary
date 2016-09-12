package com.wxh.sdk.util;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;

import java.util.List;

/**
 *Intent 工具
 */
public class IntentUtil {

    public static void startActivity(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static void startActivity(Activity context, Intent intent) {
        context.startActivity(intent);
    }

    public static void startActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    public static void startActivityByFlag(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity context, Class<?> cls,
                                              int requestCode) {
        Intent intent = new Intent(context, cls);
        context.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Activity context, Intent intent,
                                              int requestCode) {
        context.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Class<?> cls,
                                              int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), cls);
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void startActivityForResult(Fragment fragment, Intent intent,
                                              int requestCode) {
        fragment.startActivityForResult(intent, requestCode);
    }

    public static void sendBroadcast(Activity context, String action) {
        Intent intent = new Intent(action);
        context.sendBroadcast(intent);
    }

    public static void sendBroadcast(Context context, String action) {
        Intent intent = new Intent(action);
        context.sendBroadcast(intent);
    }

    public static void startService(Activity context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
    }

    public static void startService(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
    }

    public static boolean isRunningApk(Context context, String pkg_name) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(pkg_name)
                    && info.baseActivity.getPackageName().equals(pkg_name)) {
                return true;
            }
        }
        return false;
    }

    public static void intentWebUrl(Context context, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }
}
