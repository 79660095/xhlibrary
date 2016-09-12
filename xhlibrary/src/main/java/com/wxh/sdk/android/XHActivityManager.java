package com.wxh.sdk.android;

import android.app.Activity;
import android.content.Context;

import java.util.Stack;

/**
 * Created by lenovo on 2016/8/15.
 */
public class XHActivityManager {

    private static Stack<Activity> activityStack;
    private static XHActivityManager instance;

    private XHActivityManager() {
    }

    public static XHActivityManager getActivityManager() {
        if (instance == null) {
            instance = new XHActivityManager();
        }
        return instance;
    }

    public Activity getLastActivity() {
        if (activityStack.size() == 0)
            return null;
        if (activityStack.size() == 1)
            return activityStack.lastElement();
        else
            return activityStack.elementAt(activityStack.size() - 2);

    }

    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }

    public Activity currentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    public void finishActivity() {
        Activity activity = activityStack.lastElement();
        finishActivity(activity);
    }

    public void finishActivity(Activity activity) {
        try {
            if (activity != null) {
                activityStack.remove(activity);
                activity.finish();
                activity = null;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    public void finishAllActivity() {
        if (activityStack == null)
            return;
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

//    public void finishToMain(final Context ct) {
//        if (activityStack == null)
//            return;
//
//        for (int i = activityStack.size() - 1; i > -1; i--) {
//            Activity activity = activityStack.get(i);
//            System.out.println(activity);
//            if (activity == null || activity instanceof MainTabActivity)
//                continue;
//            activityStack.get(i).finish();
//            activityStack.remove(i);
//            System.out.println("finish " + activity);
//
//        }
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                Intent intent = new Intent(ct, MainTabActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                ct.startActivity(intent);
//            }
//        }, 100);
//
//    }

    public void AppExit(Context context) {
        try {
            finishAllActivity();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            android.os.Process.killProcess(android.os.Process.myPid());

        }
    }
}