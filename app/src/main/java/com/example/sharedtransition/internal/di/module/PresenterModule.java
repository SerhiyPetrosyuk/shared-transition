package com.example.sharedtransition.internal.di.module;

import com.example.sharedtransition.presenter.abstraction.implementation.AppsPresenterImpl;
import com.example.sharedtransition.presenter.abstraction.AppsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
@Module
public class PresenterModule {

    @Provides
    @Singleton
    public AppsPresenter provideAppsPresenter(AppsPresenterImpl appsPresenter) {
        return appsPresenter;
    }

}
