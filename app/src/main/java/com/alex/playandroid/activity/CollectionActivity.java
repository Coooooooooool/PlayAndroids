package com.alex.playandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alex.playandroid.R;
import com.alex.playandroid.adapter.ArticleAdapter;
import com.alex.playandroid.view.RecyclerViewSpacesItemDecoration;
import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.HashMap;

public class CollectionActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rvCollection;
    private SegmentTabLayout slidingTabLayout;

    private  String[] mTitles = {"热门", "iOS", "Android", "前端", "后端", "设计", "工具资源","工具资源1","工具资源2","工具资源3","工具资源4","工具资源5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);


        initToolbar();

        slidingTabLayout = (SegmentTabLayout) findViewById(R.id.tab_layout);
        slidingTabLayout.setTabData(mTitles);

    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setTitle(R.string.collection);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorMain));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }


    private void initRvAriticel(){
        initItemDecoration();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvCollection.setLayoutManager(manager);
        rvCollection.setNestedScrollingEnabled(false);
//        articleAdapter = new ArticleAdapter(this,articleList);
//        rvCollection.setAdapter(articleAdapter);
    }

    private void initItemDecoration(){
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,20);//底部间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,20);//左间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,20);//右间距
        rvCollection.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

    }


}
