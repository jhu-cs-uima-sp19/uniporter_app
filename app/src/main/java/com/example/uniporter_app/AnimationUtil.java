package com.example.uniporter_app;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;

public class AnimationUtil {
    private final static int FADE_DURATION = 2000; //FADE_DURATION in milliseconds

    public static void animate(RecyclerView.ViewHolder holder, boolean isScale){
        View view = holder.itemView;
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
}

