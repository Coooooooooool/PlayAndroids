package com.alex.playandroid.widget.login2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.playandroid.R;

import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;

public class SignUpFragment extends AuthFragment{

    @BindViews(value = {R.id.email_input_edit, R.id.password_input_edit,R.id.confirm_password_input_edit})
    protected List<View> views;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_register2,container,false);
        ButterKnife.bind(this,root);
        controller.setText(mergeColoredText(getString(R.string.or),getString(R.string.log_in_or_label),
                ContextCompat.getColor(getContext(),R.color.white_trans),
                ContextCompat.getColor(getContext(),R.color.color_text)));
        return root;
    }

    @Override
    public void cleaFocus() {
        views.forEach(View::clearFocus);
    }


    @Override
    public void fireAnimation() {
        float offsetX=parent.getWidth()-(parent.getWidth()-first.getLeft()+getResources().getDimension(R.dimen.option_size2));
        ObjectAnimator firstAnimator= ObjectAnimator.ofFloat(first, View.TRANSLATION_X,0);
        ObjectAnimator secondAnimator= ObjectAnimator.ofFloat(second, View.TRANSLATION_X,0);
        ObjectAnimator lastAnimator= ObjectAnimator.ofFloat(last, View.TRANSLATION_X,0);
        ObjectAnimator buttonAnimator= ObjectAnimator.ofFloat(controller, View.TRANSLATION_X,controller.getTranslationX());

        first.setTranslationX(-offsetX);
        second.setTranslationX(-offsetX);
        last.setTranslationX(-offsetX);
        controller.setTranslationX(-controller.getWidth());

        buttonAnimator.setInterpolator(new BounceOvershootInterpolator(4));
        AnimatorSet buttonSet=new AnimatorSet();
        buttonSet.playTogether(firstAnimator,secondAnimator,lastAnimator);
        buttonSet.setInterpolator(new BounceOvershootInterpolator(2));

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setStartDelay(500);
        animatorSet.playTogether(buttonSet,buttonAnimator);
        animatorSet.start();
    }
}
