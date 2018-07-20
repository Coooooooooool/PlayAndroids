package com.alex.playandroid.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alex.playandroid.R;
import com.alex.playandroid.adapter.ArticleAdapter;
import com.alex.playandroid.bean.Article;
import com.alex.playandroid.bean.HomeArticle;
import com.alex.playandroid.bean.KnowledgeHierarchy;
import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.utils.LogUtil;
import com.alex.playandroid.view.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.R.attr.data;

public class HierarchyActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private TabLayout tabLayout;
    private RecyclerView rvArticle;
    private SmartRefreshLayout refreshLayout;

    private HomeArticle homeArticle;
    private List<Article> articles;
    private ArticleAdapter articleAdapter;

    private KnowledgeHierarchy knowledgeHierarchy;
    private int cid ;
    private int selectedIndex = 0;

    private int curPage = 0;//页码
    private int state = 1;//上拉下拉

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hierarchy);

        knowledgeHierarchy = getIntent().getParcelableExtra("children");
        cid = knowledgeHierarchy.getChildren().get(0).getId();
        selectedIndex = getIntent().getIntExtra("select",0);

        initToolbar();
        initTabLayout();
        initRvArticle();
        initRefreshLayout();

        if(selectedIndex==0){
            requestArticleByHierarchy();
        }
        else{
            tabLayout.getTabAt(selectedIndex).select();
            recomputeTlOffset1(selectedIndex);
        }

    }

    private void initRefreshLayout(){
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            state=1;
            curPage = 0;
            requestArticleByHierarchy();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            state=2;
            requestArticleByHierarchy();
        });

    }

    private void initRvArticle() {
        rvArticle = (RecyclerView) findViewById(R.id.rv_article);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvArticle.setLayoutManager(manager);
        articles = new ArrayList<>();
        articleAdapter = new ArticleAdapter(HierarchyActivity.this,articles);
        rvArticle.setAdapter(articleAdapter);
        initItemDecoration();

        articleAdapter.setItemClickListener(position -> {
            Intent intent = new Intent(HierarchyActivity.this, WebActivity.class);
            Article article = articles.get(position);
            intent.putExtra("title",article.getTitle());
            intent.putExtra("url",article.getLink());
            startActivity(intent);
        });
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setTitle(knowledgeHierarchy.getName());
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorMain));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());
    }

    private void initItemDecoration(){
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,20);//底部间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,20);//左间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,20);//右间距
        rvArticle.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

    }

    private void requestArticleByHierarchy() {
        ArticleApi.getInstance().requestArticleByHierarchy(curPage,cid).enqueue(new NetCallback<NetResponse<HomeArticle>>() {
            @Override
            public void onSuccess(NetResponse<HomeArticle> data, String msg) {
                homeArticle = data.getData();
                curPage = data.getData().getCurPage();
                if(state==1){
                    articles.clear();
                }
                articles.addAll(data.getData().getDatas());
                articleAdapter.notifyDataSetChanged();
                if(state==1){
                    refreshLayout.finishRefresh(true);
                }else{
                    refreshLayout.finishLoadMore(true);
                }
            }

            @Override
            public void onFail(int errorCode, String msg) {
                super.onFail(errorCode, msg);
                if(state==1){
                    refreshLayout.finishRefresh(false);
                }else{
                    refreshLayout.finishLoadMore(false);
                }
            }
        });
    }

    private void initTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for(int i = 0;i<knowledgeHierarchy.getChildren().size();i++){
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(knowledgeHierarchy.getChildren().get(i).getName());
            tabLayout.addTab(tab);
        }

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cid = knowledgeHierarchy.getChildren().get(tab.getPosition()).getId();
                state=1;
                curPage = 0;
                requestArticleByHierarchy();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void recomputeTlOffset1(int index) {
        if (tabLayout.getTabAt(index) != null) tabLayout.getTabAt(index).select();
        final int width = (int) (getTablayoutOffsetWidth(index) * getResources().getDisplayMetrics().density);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.smoothScrollTo(width, 0);
            }
        });
    }

    private int getTablayoutOffsetWidth(int index) {
        String str = "";
        for (int i = 0; i < index; i++) {
            //channelNameList是一个List<String>的对象，里面转载的是30个词条
            //取出直到index的tab的文字，计算长度
            str += knowledgeHierarchy.getChildren().get(i);
        }
        return str.length() * 14 + index * 12;
    }

}
