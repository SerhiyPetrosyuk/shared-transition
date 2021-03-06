package com.example.sharedtransition.view.fragment;

import android.annotation.TargetApi;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sharedtransition.Application;
import com.example.sharedtransition.Constants;
import com.example.sharedtransition.R;
import com.example.sharedtransition.view.abstraction.MainView;
import com.example.sharedtransition.view.helper.AnimationHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AppInfoFragment extends Fragment {
    @InjectView(R.id.iv_app_icon)
    ImageView mAppIcon;
    @InjectView(R.id.tv_app_name)
    TextView mAppName;
    @InjectView(R.id.tv_app_package)
    TextView mAppPackage;
    @InjectView(R.id.tv_app_description)
    TextView mAppDescription;
    @InjectView(R.id.ll_app_info_holder)
    LinearLayout mAppInfoHolder;
    private MainView mMainView;

    public static AppInfoFragment newInstance(MainView mainView, Bundle appInfoData) {
        AppInfoFragment appInfoFragment = new AppInfoFragment();
        appInfoFragment.setArguments(appInfoData);
        appInfoFragment.mMainView = mainView;
        return appInfoFragment;
    }

    public AppInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((Application) getActivity().getApplication()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_app_info, container, false);
        ButterKnife.inject(this, view);
        Bundle appInfoData = getArguments();
        ApplicationInfo applicationInfo = appInfoData.getParcelable(Constants.APP_INFO);
        final int[] imageLocation = appInfoData.getIntArray(Constants.VIEW_POSITION);

        if (applicationInfo != null && imageLocation != null) {
            mAppIcon.setImageDrawable(applicationInfo.loadIcon(getActivity().getPackageManager()));
            mAppName.setText(applicationInfo.loadLabel(getActivity().getPackageManager()));
            mAppDescription.setText(applicationInfo.loadDescription(getActivity().getPackageManager()));
            mAppPackage.setText(applicationInfo.packageName);
        }

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                AnimationHelper.animateImageIn(getActivity(), mAppIcon, imageLocation, mAppInfoHolder,
                        mMainView);
                AnimationHelper.animateBackground(view);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
