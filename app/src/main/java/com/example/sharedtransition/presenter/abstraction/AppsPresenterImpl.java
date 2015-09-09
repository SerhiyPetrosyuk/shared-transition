package com.example.sharedtransition.presenter.abstraction;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import com.example.sharedtransition.Constants;
import com.example.sharedtransition.presenter.implementation.AppsPresenter;
import com.example.sharedtransition.view.abstraction.AppListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
public class AppsPresenterImpl implements AppsPresenter {
    private AppListView mView;
    private List<ApplicationInfo> mApplicationInfoList;

    @Inject
    public AppsPresenterImpl() {
    }

    @Override
    public void setView(AppListView view) {
        mView = view;
    }

    @Override
    public void getAppList() {
        if (mApplicationInfoList == null) {
            new AppsLoader().execute();
        } else {
            mView.showAppList(mApplicationInfoList);
        }
    }

    @Override
    public void clearCache() {
        mApplicationInfoList = null;
        mView = null;
    }

    @Override
    public void openAppInfo(int position, View imageView) {
        int [] viewLocation = new int[2];
        imageView.getLocationOnScreen(viewLocation);
        Bundle appData = new Bundle();
        appData.putParcelable(Constants.APP_INFO, mApplicationInfoList.get(position));
        appData.putIntArray(Constants.VIEW_POSITION, viewLocation);
        mView.showDetailAppInfo(appData);
    }

    public class AppsLoader extends AsyncTask<Void, Void, List<ApplicationInfo>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.showProgressBar(true);
        }

        @Override
        protected List<ApplicationInfo> doInBackground(Void... params) {
            List<ApplicationInfo> applicationInfoList = new ArrayList<>();

            try {
                applicationInfoList = mView.getAppPackageManager()
                        .getInstalledApplications(PackageManager.GET_META_DATA);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return getAppsWithLaunchIntent(applicationInfoList);
        }

        @Override
        protected void onPostExecute(List<ApplicationInfo> applicationInfoList) {
            super.onPostExecute(applicationInfoList);
            if (mView != null) {
                mApplicationInfoList = applicationInfoList;
                mView.showProgressBar(false);
                mView.showAppList(applicationInfoList);
            }
        }
    }

    public List<ApplicationInfo> getAppsWithLaunchIntent(List<ApplicationInfo> applicationInfoList) {
        List<ApplicationInfo> appsWithLaunchIntent = new ArrayList<>();

        for (ApplicationInfo applicationInfo : applicationInfoList) {
            try {
                if (mView.getAppPackageManager().getLaunchIntentForPackage(applicationInfo.packageName) != null) {
                    appsWithLaunchIntent.add(applicationInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return appsWithLaunchIntent;
    }

}
