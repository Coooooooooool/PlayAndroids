package com.alex.playandroid.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alex.playandroid.R;
import com.alex.playandroid.activity.ArticleActivity;
import com.alex.playandroid.activity.WebActivity;
import com.alex.playandroid.adapter.ArticleAdapter;
import com.alex.playandroid.bean.Article;
import com.alex.playandroid.bean.HomeArticle;

import com.alex.playandroid.net.api.ArticleApi;
import com.alex.playandroid.net.callback.NetCallback;
import com.alex.playandroid.net.response.NetResponse;
import com.alex.playandroid.utils.BannerImageLoader;
import com.alex.playandroid.utils.LogUtil;
import com.alex.playandroid.view.RecyclerViewSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";

    private View view;
    private SmartRefreshLayout refreshLayout;
    private RecyclerView rvArticle;
    private Banner banner;

    private List<com.alex.playandroid.bean.Banner> bannerList;
    private List<String> bannerPathList;
    private List<Article> articleList;
    private HomeArticle homeArticle;
    private ArticleAdapter articleAdapter;

    private int curPage = 0;
    private int state = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        articleList = new ArrayList<>();

        findView();
        initBanner();
        initRvAriticel();
        setListener();

        requestHomeBanner();
        requestHomeArticle();

        return view;
    }

    private void findView() {
        refreshLayout = view.findViewById(R.id.refreshLayout);
        banner = view.findViewById(R.id.banner);
        rvArticle = view.findViewById(R.id.rv_article);

    }

    private void initRvAriticel(){
        initItemDecoration();

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        rvArticle.setLayoutManager(manager);
        rvArticle.setNestedScrollingEnabled(false);
        articleAdapter = new ArticleAdapter(getActivity(),articleList);
        rvArticle.setAdapter(articleAdapter);
    }

    private void initItemDecoration(){
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.TOP_DECORATION,20);//top间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.BOTTOM_DECORATION,20);//底部间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.LEFT_DECORATION,20);//左间距
        stringIntegerHashMap.put(RecyclerViewSpacesItemDecoration.RIGHT_DECORATION,20);//右间距
        rvArticle.addItemDecoration(new RecyclerViewSpacesItemDecoration(stringIntegerHashMap));

    }

    private void setListener() {
        refreshLayout.setOnRefreshListener(refreshlayout -> {
            state=1;
            curPage = 0;
            requestHomeArticle();
        });
        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
            state=2;
            requestHomeArticle();
        });

        articleAdapter.setItemClickListener(position -> {
            Intent intent = new Intent(getActivity(), WebActivity.class);
            Article article = articleList.get(position);
            intent.putExtra("title",article.getTitle());
            intent.putExtra("url",article.getLink());
            startActivity(intent);

        });
    }

    private void initBanner() {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new BannerImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置banner轮播时间间隔
        banner.setDelayTime(4000);
        banner.setOnBannerListener(position -> {
            String url = bannerList.get(position).getUrl();
            String title = bannerList.get(position).getTitle();
            Intent intent = new Intent(getActivity(), WebActivity.class);
            intent.putExtra("title",title);
            intent.putExtra("url",url);
            startActivity(intent);
        });

    }

    private void requestHomeBanner(){
        ArticleApi.getInstance().requestHomeBanner().enqueue(new NetCallback<NetResponse<List<com.alex.playandroid.bean.Banner>>>() {
            @Override
            public void onSuccess(NetResponse<List<com.alex.playandroid.bean.Banner>> data, String msg) {
                bannerList = data.getData();
                bannerPathList = new ArrayList<>();
                for(int i = 0;i<data.getData().size();i++){
                    bannerPathList.add(data.getData().get(i).getImagePath());
                }
                banner.setImages(bannerPathList);
                banner.start();
            }
        });
    }

    private void requestHomeArticle(){

        ArticleApi.getInstance().requestHomeArticle(curPage).enqueue(new NetCallback<NetResponse<HomeArticle>>() {
            @Override
            public void onSuccess(NetResponse<HomeArticle> data, String msg) {
                homeArticle = data.getData();
                curPage = data.getData().getCurPage();
                if(state==1){
                    articleList.clear();
                }
                articleList.addAll(data.getData().getDatas());
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

    private void loadArticle(NetResponse<HomeArticle> data) {

//        if(curPage==1){
//            articleAdapter = new ArticleAdapter(getActivity(),articleList);
//            rvArticle.setAdapter(articleAdapter);
//        }else{
//            articleAdapter.notifyDataSetChanged();
//        }


    }


}
