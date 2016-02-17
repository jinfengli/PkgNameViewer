package org.kingfeng.packagenameviewer.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.DisplayMetrics;

import org.kingfeng.packagenameviewer.constants.Constants;
import org.kingfeng.packagenameviewer.R;
import org.kingfeng.packagenameviewer.bean.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: CommonUtil
 *
 * @author Jinfeng Lee
 */
public class CommonUtil {

    /**
     * get App List
     *
     * @param context
     * @param appType (app list type)
     * @return
     */
    public ArrayList<AppInfo> getAppInfos(Context context, int appType) {
        ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = context.getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppInfo appInfo = new AppInfo(context, resolveInfo);
            PackageInfo packageInfo = appInfo.getPackageInfo();
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appInfo.setAppCategary(0); // user application
            } else {
                appInfo.setAppCategary(1);
            }

            appInfos.add(appInfo);
        }
        return appInfos;
    }

    public static void showDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.package_name_viewer);
        builder.setMessage(R.string.package_info);
        builder.setPositiveButton(R.string.close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // nothing to do
            }
        });
        builder.create().show();
    }

    /**
     * unInstallApp
     * @param context
     * @param packageName
     */
    public static void unInstallApp(Context context, String packageName) {
        Intent uninstall_intent = new Intent();
        uninstall_intent.setAction(Intent.ACTION_DELETE);
        uninstall_intent.setData(Uri.parse("package:" + packageName));
        context.startActivity(uninstall_intent);
    }

    /**
     * 启动具体的应用
     * @param activityName absolutely name.
     */
    public static void bootApp(Context context,String activityName) {
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(activityName);
        context.startActivity(launchIntent);
    }

    /**
     * 启动或卸载应用AlertDialog
     * @param context
     * @param packageName
     * @param appName
     */
    public static void showBootOrUnInstallAppDialog(final Context context, final String packageName, final String appName) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(appName);

        mBuilder.setItems(new String[]
                        { context.getResources().getString(R.string.boot_app),
                          context.getResources().getString(R.string.uninstall_app) },
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (which == Constants.BOOT_APP) {
                            bootApp(context, packageName);
                        } else if (which == Constants.UNINSTALL_APP) {
                            unInstallApp(context, packageName);
                        }
                    }
                }).create().show();
    }

    /**
     * 获取屏幕宽度（两种方式均可）
     * @param context
     * @return
     */
    public static int getScreenWidth(Activity context) {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        return wm.getDefaultDisplay().getWidth();

        DisplayMetrics dpMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dpMetrics);
        return dpMetrics.widthPixels;
    }

}
