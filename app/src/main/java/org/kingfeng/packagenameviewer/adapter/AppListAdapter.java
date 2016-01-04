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



    private onItemClickListener mItemClickListener;

    public AppListAdapter(Context context) {
        this.context = context;
    }

    public onItemClickListener getmItemClickListener() {
        return mItemClickListener;
    }

    public void setmItemClickListener(onItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public ArrayList<AppInfo> getAppInfos() {
        return appInfos;
    }

    public void setAppInfos(ArrayList<AppInfo> appInfos) {
        this.appInfos = appInfos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lv_apps_item, viewGroup, false);
        return new AppViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Log.d(TAG, "onBindViewHolder, position: " + position + ", viewHolder: " + viewHolder);
        AppViewHolder holder = (AppViewHolder) viewHolder;
//        holder.position = position;

        AppInfo appInfo = appInfos.get(position);

        holder.position = position;
        holder.ivAppIcon.setImageDrawable(appInfo.getIcon());
        holder.tvAppName.setText(appInfo.getName());
        holder.tvAppPackageName.setText(appInfo.getPackageName());
        // 减去包名
        if(!TextUtils.isEmpty(appInfo.getActivityName())) {
            holder.tvAppActivityName.setText("启动类名：" + appInfo.getActivityName().replaceAll(appInfo.getPackageName(), ""));
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

    @Override
    public int getItemCount() {
        return appInfos.size();
    }

//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
//        Logger.d(TAG, "onBindViewHolder, position: " + position + ", viewHolder: " + viewHolder);
//        AppViewHolder holder = (AppViewHolder) viewHolder;
//        holder.position = position;
//
//        AssistantSaleDetail saleDetail = assistantSaleDetails.get(position);
//        holder.tvSaleDate.setText(TimeUtil.getDateString(saleDetail.getSaleDate(),"yyyy-MM-dd") + "");
//        holder.tvShopMemName.setText(saleDetail.getAssisantName() +"");
//        holder.tvShareCount.setText(saleDetail.getShareCount() + "");
//        holder.tvSaleCount.setText(saleDetail.getSaleCount() + "");
//    }
//
//    @Override
//    public int getItemCount() {
//        return assistantSaleDetails.size();
//    }


    public class AppViewHolder extends RecyclerView.ViewHolder {

        TextView tvAppName;
        TextView tvAppCategary;
        TextView tvAppPackageName;
        TextView tvAppActivityName;
        ImageView ivAppIcon;
        TextView tvAppVersion;
        public int position;

        public AppViewHolder(final View itemView) {
            super(itemView);

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

    /**
     * item 点击接口回调
     */
    public interface onItemClickListener {
        // item的点击事件
        public void onItemClick(View view, int postion);
    }
}
