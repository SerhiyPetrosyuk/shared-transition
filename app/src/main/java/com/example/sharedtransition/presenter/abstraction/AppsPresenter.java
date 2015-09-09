package com.example.sharedtransition.presenter.abstraction;

import android.view.View;

import com.example.sharedtransition.view.abstraction.AppListView;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
public interface AppsPresenter {
    void setView(AppListView view);

    void getAppList();

    void clearCache();

    void openAppInfo(int position, View imageView);

    void showSelectedImage();

    void hideSelectedImage();
}
