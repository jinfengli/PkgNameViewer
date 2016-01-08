package org.kingfeng.packagenameviewer.util;

import org.kingfeng.packagenameviewer.bean.AppInfo;

import java.util.Comparator;

/**
 * TODO: 自定义AppName Comparator
 *
 * @author Jinfeng Lee
 */
public class AppNameComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        AppInfo appInfo1 = (AppInfo)o1;
        AppInfo appInfo2 = (AppInfo)o2;
        // 按照名称排序
        return appInfo1.getName().compareTo(appInfo2.getName());
    }
}
