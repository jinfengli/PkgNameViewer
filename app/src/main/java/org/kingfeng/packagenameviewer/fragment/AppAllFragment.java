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

/**
 * TODO: All App Fragment
 *
 * @author Jinfeng Lee
 */
public class AppAllFragment extends Fragment {

    private RecyclerView recyclerView;

    private View mainView;
    private ArrayList<AppInfo> appAllInfos;

    private AppListAdapter appListAdapter;

    public AppAllFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_app_all, container, false);
        initViews();
        init();

        appListAdapter.setAppInfos(appAllInfos);
        appListAdapter.setAppInfosType(Constants.ALL_APP);
        recyclerView.setAdapter(appListAdapter);

        appListAdapter.setmItemClickListener(new AppListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                // 第一项是列表标题，所以应该减1
                String packageName = appAllInfos.get(postion - 1).getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    CommonUtil.unInstallApp(getActivity(), packageName);
                }
            }
        });

        return mainView;
    }

    private void initViews() {
        recyclerView = (RecyclerView) mainView.findViewById(R.id.all_app_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext()));
        appListAdapter = new AppListAdapter(getContext());
    }

    private void init() {
        appAllInfos = new ArrayList<AppInfo>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfoList = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        for (ResolveInfo resolveInfo : resolveInfoList) {
            AppInfo appInfo = new AppInfo(getActivity(), resolveInfo);
            PackageInfo packageInfo = appInfo.getPackageInfo();
            if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                appInfo.setAppCategary(0); // user application
            } else {
                appInfo.setAppCategary(1);

            }
            appAllInfos.add(appInfo);
        }

        Collections.sort(appAllInfos, new AppNameComparator());
    }

}
