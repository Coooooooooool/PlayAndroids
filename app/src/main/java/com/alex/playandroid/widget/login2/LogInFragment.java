package com.alex.playandroid.widget.login2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.activity.MainActivity;
import com.alex.playandroid.bean.User;
import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.utils.SharedPreUtils;

import java.util.List;
import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class LogInFragment extends AuthFragment {

    @BindViews(value = {R.id.email_input_edit,R.id.password_input_edit})
    protected List<View> views;

    @BindView(R.id.caption)
    protected TextView caption;
    @BindView(R.id.email_input_edit)
    protected TextInputEditText etAccount;
    @BindView(R.id.password_input_edit)
    protected TextInputEditText etPassword;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_login2,container,false);
        ButterKnife.bind(this,root);
        controller.setText(mergeColoredText(getString(R.string.or),getString(R.string.log_in_or_label),
                ContextCompat.getColor(getContext(),R.color.white_trans),
                ContextCompat.getColor(getContext(),R.color.color_text)));

        caption.setOnClickListener(view -> login());

        return root;
    }

    private void login() {
        WeakHashMap<String,String> map = new WeakHashMap<String, String>();
        map.put("username",etAccount.getText().toString());
        map.put("password",etPassword.getText().toString());

        ArticleApi.getInstance().login(map).enqueue(new NetCallback<NetResponse<User>>() {
            @Override
            public void onSuccess(NetResponse<User> data, String msg) {

                User user = data.getData();
                SharedPreUtils.putBoolean("isLogin",true,getActivity());
                SharedPreUtils.putString("username",user.getUsername(),getActivity());
                SharedPreUtils.putString("password",user.getPassword(),getActivity());
                SharedPreUtils.putString("icon",user.getIcon(),getActivity());
                SharedPreUtils.putString("email",user.getEmail(),getActivity());
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
    }

    @Override
    public void cleaFocus() {
        views.forEach(View::clearFocus);
    }

    @Override
    public void fireAnimation() {
        float offsetX=parent.getWidth()-(last.getX()+last.getWidth())-getResources().getDimension(R.dimen.option_size2);
        ObjectAnimator firstAnimator= ObjectAnimator.ofFloat(first, View.TRANSLATION_X,0);
        ObjectAnimator secondAnimator= ObjectAnimator.ofFloat(second, View.TRANSLATION_X,0);
        ObjectAnimator lastAnimator= ObjectAnimator.ofFloat(last, View.TRANSLATION_X,0);
        ObjectAnimator buttonAnimator= ObjectAnimator.ofFloat(controller, View.TRANSLATION_X,controller.getTranslationX());
        first.setTranslationX(offsetX);
        second.setTranslationX(offsetX);
        last.setTranslationX(offsetX);
        controller.setTranslationX(controller.getWidth());

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
