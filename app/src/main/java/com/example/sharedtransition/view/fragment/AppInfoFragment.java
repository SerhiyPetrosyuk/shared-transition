package com.example.sharedtransition.view.fragment;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sharedtransition.Application;
import com.example.sharedtransition.Constants;
import com.example.sharedtransition.R;
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
    private TransitionDrawable mFragmentBackgroundDrawable;
    private AnimationHelper mAnimationHelper;

    public static AppInfoFragment newInstance(Bundle appInfoData) {
        AppInfoFragment appInfoFragment = new AppInfoFragment();
        appInfoFragment.setArguments(appInfoData);
        return appInfoFragment;
    }

    public AppInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        ((Application) getActivity().getApplication()).inject(this);
        mAnimationHelper = new AnimationHelper();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View appInfoView = inflater.inflate(R.layout.fragment_app_info, container, false);
        ButterKnife.inject(this, appInfoView);
        mFragmentBackgroundDrawable = (TransitionDrawable) appInfoView.getBackground();
        Bundle appInfoData = getArguments();
        ApplicationInfo applicationInfo = appInfoData.getParcelable(Constants.APP_INFO);
        int[] imageLocation = appInfoData.getIntArray(Constants.VIEW_POSITION);

        if (applicationInfo != null && imageLocation != null) {
            mAppIcon.setImageDrawable(applicationInfo.loadIcon(getActivity().getPackageManager()));
            mAppName.setText(applicationInfo.loadLabel(getActivity().getPackageManager()));
            mAppDescription.setText(applicationInfo.loadDescription(getActivity().getPackageManager()));
            mAppPackage.setText(applicationInfo.packageName);
            mAppIcon.setTranslationX(imageLocation[0]);
            mAppIcon.setTranslationY(imageLocation[1]);
        }

        return appInfoView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mFragmentBackgroundDrawable.startTransition(300);
    }

    @Override
    public void onPause() {
        mFragmentBackgroundDrawable.reverseTransition(300);
        super.onPause();
    }
}
