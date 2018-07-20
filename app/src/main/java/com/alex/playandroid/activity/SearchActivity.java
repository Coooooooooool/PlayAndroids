package com.alex.playandroid.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.adapter.ArticleAdapter;
import com.alex.playandroid.bean.Article;
import com.alex.playandroid.bean.Buzzwords;
import com.alex.playandroid.bean.HomeArticle;
import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.utils.LogUtil;
import com.alex.playandroid.view.RecyclerViewSpacesItemDecoration;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private Toolbar toolbar;
    private MaterialSearchView searchView;
    private RecyclerView rvArticle;
    private TagFlowLayout tagBuzzwords;
    private AppCompatTextView tvBuzzwords;

    private ArticleAdapter articleAdapter;

    private HomeArticle homeArticle;
    private List<Article> articleList;
    private List<Buzzwords> buzzwordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        homeArticle = new HomeArticle();
        articleList = new ArrayList<>();

        tvBuzzwords = (AppCompatTextView) findViewById(R.id.tv_desc);

        initToolbar();

        initSearchView();
        initRvArticle();

        tagBuzzwords = (TagFlowLayout) findViewById(R.id.tag_buzzwords);
        tagBuzzwords.setOnTagClickListener((view, position, parent) -> {
            search(0,buzzwordsList.get(position).getName());
            return true;
        });
        requestBuzzwords();

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchView.showSearch();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
//        toolbar.setTitle(R.string.app_name);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorMain));
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

    }

    private void initSearchView(){
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                search(0,query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 0.4f;
                getWindow().setAttributes(lp);
            }

            @Override
            public void onSearchViewClosed() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
    }

    private void initRvArticle() {
        rvArticle = (RecyclerView) findViewById(R.id.rv_article);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvArticle.setLayoutManager(manager);
        initItemDecoration();
        articleAdapter = new ArticleAdapter(this,articleList);
        rvArticle.setAdapter(articleAdapter);
        articleAdapter.setItemClickListener(position -> {
            Intent intent = new Intent(SearchActivity.this, ArticleActivity.class);
            intent.putExtra("article",articleList.get(position));
            startActivity(intent);
        });
    }

    private void initItemDecoration(){
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,20);//底部间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,20);//左间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,20);//右间距
        rvArticle.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

    }

    private void search(int page,String s){
        ArticleApi.getInstance().search(0,s).enqueue(new NetCallback<NetResponse<HomeArticle>>() {
            @Override
            public void onSuccess(NetResponse<HomeArticle> data, String msg) {
                articleList = data.getData().getDatas();
                articleAdapter.notifyDataSetChanged();
                tagBuzzwords.setVisibility(View.GONE);
                tvBuzzwords.setVisibility(View.GONE);
            }
        });
    }

    private void requestBuzzwords(){
        ArticleApi.getInstance().requestBuzzwords().enqueue(new NetCallback<NetResponse<List<Buzzwords>>>() {
            @Override
            public void onSuccess(NetResponse<List<Buzzwords>> data, String msg) {
                buzzwordsList = data.getData();
                tagBuzzwords.setAdapter(new TagAdapter<Buzzwords>(buzzwordsList){
                    @Override
                    public View getView(FlowLayout parent, int position, Buzzwords buzzwords) {
                        TextView tv = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.item_flow_tag,
                                tagBuzzwords, false);
                        tv.setText(buzzwords.getName());
                        return tv;
                    }
                });

            }
        });
    }

}
