package com.example.sharedtransition;

import com.example.sharedtransition.internal.di.ApplicationComponent;
import com.example.sharedtransition.internal.di.DaggerApplicationComponent;
import com.example.sharedtransition.internal.di.module.ApplicationModule;
import com.example.sharedtransition.view.fragment.AppInfoFragment;
import com.example.sharedtransition.view.fragment.AppListFragment;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
public class Application extends android.app.Application {
    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        mApplicationComponent.inject(this);
    }

    public void inject(AppListFragment appListFragment) {
        mApplicationComponent.inject(appListFragment);
    }

    public void inject(AppInfoFragment appInfoFragment) {
        mApplicationComponent.inject(appInfoFragment);
    }
}
