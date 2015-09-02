package com.example.sharedtransition.internal.di;

import com.example.sharedtransition.Application;
import com.example.sharedtransition.internal.di.module.ApplicationModule;
import com.example.sharedtransition.internal.di.module.PresenterModule;
import com.example.sharedtransition.view.fragment.AppInfoFragment;
import com.example.sharedtransition.view.fragment.AppListFragment;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
@Singleton
@Component(modules = {ApplicationModule.class, PresenterModule.class})
public interface ApplicationComponent {
    void inject(Application application);
    void inject(AppListFragment appListFragment);
    void inject(AppInfoFragment appInfoFragment);
}
