package com.example.sharedtransition.view.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

/**
 * Created by Serhiy Petrosyuk on 01.09.15.
 */
public class AnimationHelper {

    public static void animateImageIn(Activity activity, ImageView image, int[] imageLocation,
                                      final View viewToShowAfterAnimation) {

        int[] imagePosition = new int[2];
        image.getLocationOnScreen(imagePosition);
        int startPosition = imagePosition[0];
        image.setTranslationX(imageLocation[0] - imagePosition[0]);
        image.setTranslationY(imageLocation[1] - imagePosition[1]);

        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        int xPosition = ((width / 2) - (image.getWidth() / 2));
        ObjectAnimator imageTranslationX = ObjectAnimator.ofFloat(image, "translationX", xPosition);
        ObjectAnimator imageTranslationY = ObjectAnimator.ofFloat(image, "translationY", startPosition);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(imageTranslationX, imageTranslationY);
        animatorSet.setDuration(500);
        animatorSet.setInterpolator(new DecelerateInterpolator());

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                viewToShowAfterAnimation.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });


        animatorSet.start();
    }

    public static void animateBackground(View view) {
        ArgbEvaluator colorEvaluator = new ArgbEvaluator();
        ObjectAnimator colorAnimator = ObjectAnimator.ofObject(view, "backgroundColor", colorEvaluator,
                0, 0);
        colorAnimator.setObjectValues(Color.TRANSPARENT, Color.WHITE);
        colorAnimator.setDuration(500);
        colorAnimator.start();
    }

    public static void animateImageOut() {

    }

}
