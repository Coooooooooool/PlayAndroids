package com.alex.playandroid.widget.login2;

import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;

public class BounceOvershootInterpolator implements Interpolator {

    private OvershootInterpolator overshootInterpolator;
    private BounceInterpolator bounceInterpolator;

    public BounceOvershootInterpolator(float tension){
        overshootInterpolator=new OvershootInterpolator(tension);
        bounceInterpolator=new BounceInterpolator();
    }

    @Override
    public float getInterpolation(float input) {
        if(input>.99f) return bounceInterpolator.getInterpolation(input);
        return overshootInterpolator.getInterpolation(input);
    }
}
