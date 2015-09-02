package com.example.sharedtransition.view.helper;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
public class AnimationHelper {

    public AnimatorSet getInAnimatorSet(View rootView, View view, WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int offset = displayMetrics.heightPixels  - rootView.getMeasuredHeight();

        int [] viewLocation = new int[2];
        view.getLocationOnScreen(viewLocation);
        viewLocation[1] -= offset;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(view, "translationX", viewLocation[1], offset);
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(view, "translationY", viewLocation[0], offset);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorX, objectAnimatorY);
        animatorSet.setDuration(750);
        return animatorSet;
    }

}
