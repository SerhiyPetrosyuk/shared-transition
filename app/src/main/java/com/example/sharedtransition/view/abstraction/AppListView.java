package com.example.sharedtransition.view.abstraction;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import java.util.List;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
public interface AppListView {
    void showAppList(List<ApplicationInfo> applicationInfoList);

    void showProgressBar(boolean isShow);

    PackageManager getAppPackageManager();

    void showDetailAppInfo(Bundle appData);

    int getScreenOffset();

    void showSelectedImage();

    void hideSelectedImage();
}
