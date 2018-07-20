package com.alex.playandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alex.library.widget.materialspinner.MaterialSpinner;
import com.alex.playandroid.R;
import com.alex.playandroid.app.Configure;


public class SettingActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private MaterialSpinner navigationBarMode,navigationBarStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initToolbar();
        initNavigationBar();

    }


    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setTitle(R.string.setting);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorMain));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initNavigationBar(){
        navigationBarMode = (MaterialSpinner) findViewById(R.id.navigationBar_mode);
        navigationBarStyle = (MaterialSpinner) findViewById(R.id.navigationBar_style);

        String[] modes = getResources().getStringArray(R.array.navigationBar_mode);
        String[] styles = getResources().getStringArray(R.array.navigationBar_style);

        navigationBarMode.setItems(modes);
        navigationBarStyle.setItems(styles);

        navigationBarMode.setSelectedIndex(Configure.BOTTOM_NAVIGATION_BAR_MODE);
        navigationBarStyle.setSelectedIndex(Configure.BOTTOM_NAVIGATION_BAR_STYLE);

        navigationBarMode.setOnItemSelectedListener((view, position, id, item) -> {
            Configure.BOTTOM_NAVIGATION_BAR_MODE = position;
        });
        navigationBarStyle.setOnItemSelectedListener((view, position, id, item) -> {
            Configure.BOTTOM_NAVIGATION_BAR_STYLE = position;
        });

    }

}
