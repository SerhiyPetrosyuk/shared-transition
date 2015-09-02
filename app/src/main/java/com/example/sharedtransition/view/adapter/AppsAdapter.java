package com.example.sharedtransition.view.adapter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sharedtransition.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.AppHolder>{
    private List<ApplicationInfo> applicationInfoList;
    private OnItemClickListener onItemClickListener;
    private PackageManager packageManager;

    public AppsAdapter(PackageManager packageManager) {
        applicationInfoList = new ArrayList<>();
        this.packageManager = packageManager;
    }

    @Override
    public AppHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.app_info_item, parent, false);
        return new AppHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AppHolder holder, int position) {
        holder.appIcon.setImageDrawable(applicationInfoList.get(position).loadIcon(packageManager));
        holder.appName.setText(applicationInfoList.get(position).loadLabel(packageManager));
        holder.packageName.setText(applicationInfoList.get(position).packageName);
    }

    @Override
    public int getItemCount() {
        return applicationInfoList.size();
    }

    public class AppHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView appIcon;
        public TextView appName;
        public TextView packageName;

        public AppHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            appIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            appName = (TextView) itemView.findViewById(R.id.app_name);
            packageName = (TextView) itemView.findViewById(R.id.app_package);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                int position = getAdapterPosition();
                View appIcon = v.findViewById(R.id.app_icon);
                onItemClickListener.onItemClick(position, appIcon);
            }
        }
    }

    public void setData(List<ApplicationInfo> applicationInfoList) {
        this.applicationInfoList = applicationInfoList;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View imageView);
    }
}
