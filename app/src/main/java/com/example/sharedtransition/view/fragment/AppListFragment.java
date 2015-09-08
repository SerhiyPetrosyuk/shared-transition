package com.example.sharedtransition.view.fragment;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.sharedtransition.Application;
import com.example.sharedtransition.R;
import com.example.sharedtransition.presenter.implementation.AppsPresenter;
import com.example.sharedtransition.view.abstraction.AppListView;
import com.example.sharedtransition.view.activity.MainActivity;
import com.example.sharedtransition.view.adapter.AppsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AppListFragment extends Fragment implements AppListView {
    @InjectView(R.id.rv_app_list)
    RecyclerView mAppList;
    @InjectView(R.id.pb_progress_bar)
    ProgressBar mProgressBar;
    private AppsAdapter mAppsAdapter;
    @Inject
    AppsPresenter mPresenter;
    private View mRootView;
    private int mTopOffset;

    public static AppListFragment newInstance() {
        return new AppListFragment();
    }

    public AppListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((Application) getActivity().getApplication()).inject(this);
        mPresenter.setView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View appListView = inflater.inflate(R.layout.fragment_app_list, container, false);
        mRootView = appListView;
        ButterKnife.inject(this, appListView);
        initAppList();
        return appListView;
    }

    private void initAppList() {
        mAppList.setHasFixedSize(true);
        mAppList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAppsAdapter = new AppsAdapter(getAppPackageManager());
        mAppList.setAdapter(mAppsAdapter);
        mAppsAdapter.setOnItemClickListener(new AppsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View imageView) {
                mPresenter.openAppInfo(position, imageView);
                DisplayMetrics dm = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
                mTopOffset = dm.heightPixels - mRootView.getMeasuredHeight();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.getAppList();
    }


    @Override
    public void showAppList(List<ApplicationInfo> applicationInfoList) {
        mAppsAdapter.setData(applicationInfoList);
    }

    @Override
    public void showProgressBar(boolean isShow) {
        mProgressBar.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public PackageManager getAppPackageManager() {
        return getActivity().getPackageManager();
    }

    @Override
    public void showDetailAppInfo(Bundle appData) {
        ((MainActivity)getActivity()).addFragment(AppInfoFragment.newInstance(appData));
    }

    @Override
    public int getScreenOffset() {
        return mTopOffset;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.clearCache();
    }
}
