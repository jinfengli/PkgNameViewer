package org.kingfeng.packagenameviewer.fragment;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kingfeng.packagenameviewer.Constants.Constants;
import org.kingfeng.packagenameviewer.R;
import org.kingfeng.packagenameviewer.adapter.AppListAdapter;
import org.kingfeng.packagenameviewer.bean.AppInfo;
import org.kingfeng.packagenameviewer.util.AppNameComparator;
import org.kingfeng.packagenameviewer.util.CommonUtil;
import org.kingfeng.packagenameviewer.view.DividerItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppSysFragment extends Fragment {
    RecyclerView recyclerView;

    private View mainView;
    private ArrayList<AppInfo> appSysInfos;

    private AppListAdapter appListAdapter;

    public AppSysFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_app_sys, container, false);
        initViews();
        init();
        setListener();

        return mainView;
    }

    private void setListener() {
        appListAdapter.setmItemClickListener(new AppListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 第一项是列表标题，position须减1
                String packageName = appSysInfos.get(position - 1).getPackageName();
                String appName = appSysInfos.get(position - 1).getName();

                if (!TextUtils.isEmpty(packageName) && !TextUtils.isEmpty(appName)) {
                    CommonUtil.showBootOrUnInstallAppDialog(getActivity(), packageName, appName);
                }
            }
        });
    }

    private void initViews() {
        recyclerView = (RecyclerView) mainView.findViewById(R.id.sys_app_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));

        appListAdapter = new AppListAdapter(getContext());
    }

    private void init() {
        appSysInfos = new ArrayList<AppInfo>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppInfo appInfo = new AppInfo(getActivity(), resolveInfo);
            PackageInfo packageInfo = appInfo.getPackageInfo();
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                appInfo.setAppCategary(0); // user application
            } else {
                appInfo.setAppCategary(1);
                appSysInfos.add(appInfo); // system application
            }
        }

        Collections.sort(appSysInfos, new AppNameComparator());

        appListAdapter.setAppInfos(appSysInfos);
        appListAdapter.setAppInfosType(Constants.SYSTEM_APP);
        recyclerView.setAdapter(appListAdapter);

    }

}
