package com.example.uniporter_app;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class AnimationUtil {
    private final static int FADE_DURATION = 1000; //FADE_DURATION in milliseconds

    public static void animate(RecyclerView.ViewHolder holder ,boolean goesDown){

        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        holder.itemView.startAnimation(anim);

        /*
        AnimatorSet animatorSet = new AnimatorSet();

        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown==true ? 200 : -200, 0);
        animatorTranslateY.setDuration(1000);


        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView,"translationX",-50,50,-30,30,-20,20,-5,5,0);
        animatorTranslateX.setDuration(1000);

        animatorSet.playTogether(animatorTranslateX,animatorTranslateY);

        //animatorSet.playTogether(animatorTranslateY);
        animatorSet.start();
        */

    }
}
