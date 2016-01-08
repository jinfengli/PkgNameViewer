package org.kingfeng.packagenameviewer.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.kingfeng.packagenameviewer.Constants.Constants;
import org.kingfeng.packagenameviewer.R;
import org.kingfeng.packagenameviewer.bean.AppInfo;

import java.util.ArrayList;

/**
 * TODO: AppListAdapter
 *
 * @author Jinfeng Lee
 */
public class AppListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = AppListAdapter.class.getSimpleName();

    private Context context;

    private ArrayList<AppInfo> appInfos = new ArrayList<AppInfo>();
    /**ReccyclerView head 项*/
    private static final int IS_HEADER = 2;
    /** 正常的RecyclerView item*/
    private static final int IS_NORMAL = 1;

    private int appInfosType;

    private onItemClickListener mItemClickListener;

    public AppListAdapter(Context context) {
        this.context = context;
    }

    public void setmItemClickListener(onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public int getAppInfosType() {
        return appInfosType;
    }

    public void setAppInfosType(int appInfosType) {
        this.appInfosType = appInfosType;
    }


    public void setAppInfos(ArrayList<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if(viewType == IS_NORMAL) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lv_apps_item, viewGroup, false);
            return new AppViewHolder(v,IS_NORMAL);
        } else if(viewType == IS_HEADER) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lv_apps_header_item, viewGroup, false);
            return new AppViewHolder(v,IS_HEADER);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder, position: " + position + ", viewHolder: " + viewHolder);
        AppViewHolder holder = (AppViewHolder) viewHolder;

        if(position == 0) {
            if(getAppInfosType() == Constants.ALL_APP) {
                holder.tvTotalAppNums.setText("共" + appInfos.size() + "款应用");
            }
            if(getAppInfosType() == Constants.SYSTEM_APP) {
                holder.tvTotalAppNums.setText("共" + appInfos.size() + "款系统应用");
            }
            if(getAppInfosType() == Constants.USER_APP) {
                holder.tvTotalAppNums.setText("共" + appInfos.size() + "款用户应用");
            }

        } else {
            AppInfo appInfo = appInfos.get(position - 1); // 注意
            holder.position = position;
            holder.ivAppIcon.setImageDrawable(appInfo.getIcon());
            holder.tvAppName.setText(appInfo.getName());
            holder.tvAppPackageName.setText(appInfo.getPackageName());
            // 减去包名
            if(!TextUtils.isEmpty(appInfo.getActivityName())) {
                holder.tvAppActivityName.setText("启动类名："
                        + appInfo.getActivityName().replaceAll(appInfo.getPackageName(), ""));
            } else {
                holder.tvAppActivityName.setText("启动类名:null");
            }

            if(appInfo.getAppCategory()== Constants.SYSTEM_APP) {
                holder.tvAppCategary.setText("[系统应用]");
                holder.tvAppCategary.setTextColor(Color.RED);
            } else if(appInfo.getAppCategory()== Constants.USER_APP){
                holder.tvAppCategary.setTextColor(Color.GRAY);
                holder.tvAppCategary.setText("[用户应用]");
            }

            holder.tvAppPackageName.setText("包名：" + appInfo.getPackageName());
            holder.tvAppVersion.setText("  版本号：" + appInfo.getVersionName()+ "(" + appInfo.getVersionCode() + ")");
        }

    }

    @Override
    public int getItemCount() {
        return appInfos.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return IS_HEADER;
        }

        return IS_NORMAL;
    }

    public class AppViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppName;
        TextView tvAppCategary;
        TextView tvAppPackageName;
        TextView tvAppActivityName;
        ImageView ivAppIcon;
        TextView tvAppVersion;

        TextView tvTotalAppNums;
        public int position;
        public int viewType;

        public AppViewHolder(final View itemView, int viewType) {
            super(itemView);
            this.viewType = viewType;
            if(viewType == IS_HEADER) {
                tvTotalAppNums = (TextView) itemView.findViewById(R.id.tv_total_app_nums);
            }

            if(viewType == IS_NORMAL) {
                ivAppIcon = (ImageView) itemView.findViewById(R.id.iv_app_icon);
                tvAppName = (TextView) itemView.findViewById(R.id.tv_app_name);
                tvAppCategary = (TextView) itemView.findViewById(R.id.tv_app_category);
                tvAppPackageName = (TextView) itemView.findViewById(R.id.tv_app_package_name);
                tvAppActivityName = (TextView) itemView.findViewById(R.id.tv_app_activity_name);
                tvAppVersion = (TextView) itemView.findViewById(R.id.tv_app_version);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(null != mItemClickListener) {
                            mItemClickListener.onItemClick(itemView, getPosition());
                        }
                    }
                });
            }


        }
    }

    /**
     * item 点击接口回调
     */
    public interface onItemClickListener {
        // item的点击事件
        public void onItemClick(View view, int postion);
    }
}
