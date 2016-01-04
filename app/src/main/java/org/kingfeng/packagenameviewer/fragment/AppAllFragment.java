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
import android.widget.Toast;

import org.kingfeng.packagenameviewer.R;
import org.kingfeng.packagenameviewer.adapter.AppListAdapter;
import org.kingfeng.packagenameviewer.bean.AppInfo;
import org.kingfeng.packagenameviewer.util.CommonUtil;
import org.kingfeng.packagenameviewer.view.DividerItemDecoration;

import java.util.ArrayList;
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
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.fragment_app_all, container, false);
        initViews();
        init();

        appListAdapter.setAppInfos(appAllInfos);
        recyclerView.setAdapter(appListAdapter);

        appListAdapter.setmItemClickListener(new AppListAdapter.onItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                String packageName = appAllInfos.get(postion).getPackageName();
                if (!TextUtils.isEmpty(packageName)) {
                    CommonUtil.unInstallApp(getActivity(), packageName);
                }
            }
        });
        Toast.makeText(getActivity(), "共安装" + appAllInfos.size() + "款应用", Toast.LENGTH_LONG).show();
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

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//
//    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState){
//        super.onActivityCreated(savedInstanceState);
//        Toast.makeText(getActivity(), "共安装" + appAllInfos.size() + "款应用", Toast.LENGTH_LONG).show();
//    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Toast.makeText(getActivity(), "共安装" + appAllInfos.size() + "款应用", Toast.LENGTH_LONG).show();
//    }
}
